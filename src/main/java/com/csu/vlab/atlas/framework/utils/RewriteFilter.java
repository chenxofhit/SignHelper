package com.csu.vlab.atlas.framework.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 
 * @author chenx
 *
 */
public class RewriteFilter implements Filter
{
    private List<Pattern> patterns;
    private List<String> replaces;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
	this.patterns = new ArrayList<Pattern>();
	this.replaces=new ArrayList<String>();
	String regex = filterConfig.getInitParameter("regex");
	String[] splits = regex.split("\\s");

	for (String tmp : splits)
	{
	    if (tmp.matches(".*\\-\\-\\>.*"))
	    {
		String[] spls = tmp.split("\\-\\-\\>");
		this.patterns.add(Pattern.compile(spls[0],Pattern.CASE_INSENSITIVE));
		this.replaces.add(spls[1]);
	    }
	}
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain chain) throws IOException, ServletException
    {
	HttpServletRequest r = (HttpServletRequest) request;
	String url = r.getRequestURI().replaceFirst(r.getContextPath(), "");
	
	for(int i=0,len=this.patterns.size();i<len;i++)
	{
	    Matcher matcher = this.patterns.get(i).matcher(url);
	    
	    if(matcher.matches())
	    {
		request.getRequestDispatcher(matcher.replaceAll(this.replaces.get(i))).forward(request, response);;
		return;
	    }
	}
	
	chain.doFilter(request, response);
    }

    @Override
    public void destroy()
    {

    }

    public static void main(String[] args)
    {
	Pattern p = Pattern.compile("^/avatar/(.*)\\.(jpg|jpeg|png|bmp|gif)$",
		Pattern.CASE_INSENSITIVE);
	Matcher matcher = p.matcher("/avatar/skfsldfsldfsdf.jpg");

	if (matcher.matches())
	{
	    System.out.println(matcher.replaceAll("$0|$1|$2"));
	}
    }

}
