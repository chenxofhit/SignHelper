package com.csu.vlab.atlas.framework.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

/**
 * 
 * 
 * @author chenx
 *
 */
public class CustomBeanUtils extends BeanUtils
{
	/**
	 * 拷贝一个List到另外一个List中
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> copyListToList(List<?> source, Class<T> target, String[] ignoreProperties)
	{
		List<T> targets = new ArrayList<T>();
		try
		{
			for (Object object : source)
			{
				Object targetObj = target.newInstance();
				copyProperties(object, targetObj, ignoreProperties);
				targets.add((T) targetObj);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		return targets;
	}

	public static <T> List<T> copyListToList(List<?> source, Class<T> target)
	{
		return copyListToList(source, target, null);
	}

	/**
	 * copy值到JavaBean中，暂时只支持String to String
	 * 
	 * @Title: copyPropertiesToBean
	 * @param properties
	 * @param target
	 */
	public static void copyPropertiesToBean(Properties properties, Object target)
	{
		Assert.notNull(properties, "Properties must not be null");
		Assert.notNull(target, "Target must not be null");
		Class<?> targetClass = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(targetClass);
		for (PropertyDescriptor propertyDescriptor : targetPds)
		{
			if (propertyDescriptor.getWriteMethod() != null)
			{
				try
				{
					Object obj = properties.get(propertyDescriptor.getName());
					Method writeMethod = propertyDescriptor.getWriteMethod();
					if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers()))
					{
						writeMethod.setAccessible(true);
					}
					Class<?> paramClass = writeMethod.getParameterTypes()[0];
					if ("java.lang.String".equals(paramClass.getName()))
					{
						writeMethod.invoke(target, obj);
					}
				}
				catch (Throwable ex)
				{
					throw new FatalBeanException("Could not copy properties from source to target", ex);
				}
			}
		}
	}

	/**
	 * 将java对象转换为map对象
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors)
			{
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class"))
				{
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
				}
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		return map;
	}

	public static void transMap2Bean(Map<String, Object> map, Object obj)
	{
		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors)
			{
				String key = property.getName();

				if (map.containsKey(key))
				{
					Object value = map.get(key);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}
			}

		}
		catch (Exception e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		return;
	}
	
	public static void copyPropertiesIgnoreNull(Object source, Object target) throws BeansException
	{
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
						if (value != null) {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					}
					catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}
}
