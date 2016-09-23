package com.csu.vlab.atlas.framework.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 字符串工具类
 * 
 * @author chenx
 *
 */
@SuppressWarnings("deprecation")
public class StringUtil
{
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private StringUtil()
    {
    }

    static
    {
        SerializationConfig serializationConfig = MAPPER
                .getSerializationConfig();
        DeserializationConfig deserializationConfig = MAPPER
                .getDeserializationConfig();
        DateFormat df = new DateFormat()
        {

            private static final long serialVersionUID = 1697029016562632701L;

            @Override
            public Date parse(String source, ParsePosition pos)
            {
                if (StringUtil.isNotBlank(source))
                {
                    return new Date(Long.parseLong(source));
                }

                return null;
            }

            @Override
            public StringBuffer format(Date date, StringBuffer toAppendTo,
                    FieldPosition fieldPosition)
            {
                if (date != null)
                {
                    return new StringBuffer(String.valueOf(date.getTime()));
                }

                return null;
            }
        };
        serializationConfig.withDateFormat(df);
        deserializationConfig.withDateFormat(df);

        serializationConfig.setSerializationInclusion(Inclusion.NON_NULL);
        deserializationConfig.set(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MAPPER.setSerializationConfig(serializationConfig);
        MAPPER.setDeserializationConfig(deserializationConfig);
    }

    /**
     * 判空字符串
     * 
     * @param src
     *            true代表空
     * @return
     */
    public static boolean isBlank(String src)
    {
        if (src == null || "".equals(src))
        {
            return true;
        }

        return false;
    }

    /**
     * 判非空字符串
     * 
     * @param src
     *            true代表非空
     * @return
     */
    public static boolean isNotBlank(String src)
    {
        return !isBlank(src);
    }

    /**
     * 字符串连接
     * 
     * @param join
     *            连接符，默认逗号(join为空)
     * @param strs
     *            被连接的字符串
     * @return
     */
    public static String join(String join, String... strs)
    {
        StringBuilder sb = new StringBuilder();
        join = StringUtil.isBlank(join) ? "," : join;
        for (int i = 0, len = strs.length; i < len; i++)
        {
            if (i > 0)
            {
                sb.append(join);
            }

            sb.append(strs[i]);
        }

        return sb.toString();
    }

    /**
     * 字符串连接
     * 
     * @param join
     *            连接符，默认逗号(join为空)
     * @param strs
     *            被连接的字符串
     * @return
     */
    public static String join(String join, List<String> list)
    {
        StringBuilder sb = new StringBuilder();
        join = StringUtil.isBlank(join) ? "," : join;

        if (list != null)
        {
            for (int i = 0, len = list.size(); i < len; i++)
            {
                if (i > 0)
                {
                    sb.append(join);
                }

                sb.append(list.get(i));
            }
        }

        return sb.toString();
    }

    /**
     * 序列化对象成json形式
     * 
     * @param obj
     *            被序列化对象
     * @return 对象json形式
     */
    public static String toJson(Object obj)
    {
        String json = null;

        if (obj != null)
        {
            try
            {
                json = StringUtil.MAPPER.writeValueAsString(obj);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return json;
    }

    public static <T> T toObject(String json, Class<T> clz)
    {
        T t = null;

        if (StringUtil.isNotBlank(json))
        {
            try
            {
                t = MAPPER.readValue(json, clz);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return t;
    }
    
}
