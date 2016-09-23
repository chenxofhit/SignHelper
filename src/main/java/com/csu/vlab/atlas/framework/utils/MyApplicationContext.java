package com.csu.vlab.atlas.framework.utils;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

/**
 * 
 * Atlas上下文工具类
 * 
 * @author chenx
 * 
 */
public class MyApplicationContext implements ApplicationContextAware
{
    public static ApplicationContext ctx;
    
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        MyApplicationContext.ctx = applicationContext;
    }
    
    /**
     * 获取spring容器对象
     * 
     * @param requiredType
     * @return
     */
    public static <T> T getBean(Class<T> requiredType)
    {
        return MyApplicationContext.ctx.getBean(requiredType);
    }
    
    /**
     * 获取会话语言
     * 
     * @param request
     * @return
     */
    public static Locale getLocal(HttpServletRequest request)
    {
        CookieLocaleResolver resolver = ctx.getBean(CookieLocaleResolver.class);
        Locale locale = resolver.resolveLocale(request);
        return locale;
    }
    
    /**
     * 设置会话语言
     * 
     * @param request
     * @param response
     * @param locale
     */
    public static void setLocale(HttpServletRequest request,
            HttpServletResponse response, Locale locale)
    {
        CookieLocaleResolver resolver = ctx.getBean(CookieLocaleResolver.class);
        resolver.setLocale(request, response, locale);
    }
    
    /**
     * 获取本地化内容
     * 
     * @param request
     * @param key
     * @param args
     * @return
     */
    public static String getI18n(HttpServletRequest request, String key,
            Object... args)
    {
        return ctx.getBean(ResourceBundleMessageSource.class).getMessage(key,
                args, MyApplicationContext.getLocal(request));
    }
    
    public static void main(String[] args)
    {
        
    }
}
