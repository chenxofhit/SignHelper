package com.csu.vlab.atlas.framework.utils;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.i18n.CookieLocaleResolver;

public class MyCookieLocaleResolver extends CookieLocaleResolver
{
    @Override
    protected Locale determineDefaultLocale(HttpServletRequest request)
    {
        Locale defaultLocale = request.getLocale();
        
        if("zh".equals(defaultLocale.getLanguage()))
        {
            return Locale.CHINA;
        }
        else
        {
            return Locale.ENGLISH;
        }
    }
}
