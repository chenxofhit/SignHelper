package com.csu.vlab.atlas.framework.utils;

import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 
 * @author chenx
 *
 */
public class HTMLCharacterRequest extends HttpServletRequestWrapper
{
    private HttpServletRequest request;
    
    public HTMLCharacterRequest(HttpServletRequest request)
    {
        super(request);
        this.request = request;
    }
    
    // 对需要增强方法 进行覆盖
    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    @Override
    public Map getParameterMap()
    {
        Map<String, String[]> parameterMap = this.request.getParameterMap();
        for (String parameterName : parameterMap.keySet())
        {
            String[] values = parameterMap.get(parameterName);
            if (values != null)
            {
                for (int i = 0, len = values.length; i < len; i++)
                {
                    try
                    {
                        if (values[i] != null)
                        {
                            values[i] = HtmlWordsToStringUtils
                                    .filter(values[i]);
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        return parameterMap;
    }
    
    @Override
    public String getParameter(String name)
    {
        String value = this.request.getParameter(name);
        
        if (value != null)
        {
            value = HtmlWordsToStringUtils.filter(value);
        }
        return value;
    }
    
    @Override
    public String[] getParameterValues(String name)
    {
        String[] values = this.request.getParameterValues(name);
        
        if (values != null)
        {
            for (int i = 0, len = values.length; i < len; i++)
            {
                if (values[i] != null)
                {
                    values[i] = HtmlWordsToStringUtils.filter(values[i]);
                }
            }
        }
        return values;
    }
    
    public static class HtmlWordsToStringUtils
    {
        private static Pattern PATTERN = Pattern.compile("<(/?)(script)>",
                Pattern.CASE_INSENSITIVE);
            
        public static String filter(String value)
        {
            return PATTERN.matcher(value).replaceAll("&lt;$1$2&gt;");
        }
    }
}
