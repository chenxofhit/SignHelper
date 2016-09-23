package com.csu.vlab.atlas.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * 时间工具类
 * 
 * @author chenx
 *
 */
public class DateUtil {
	
	public static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse(beginDate);// 开始日期
            Date end = format.parse(endDate);// 结束日期
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
 
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    private static long random(long begin, long end) {
        long rtnn = begin + (long) (Math.random() * (end - begin));
        if (rtnn == begin || rtnn == end) {
            return random(begin, end);
        }
        return rtnn;
    }
	
	private static final SimpleDateFormat def = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	public static String getDefFormat(Date time) {
		if (time == null) {
			time = new Date();
		}

		return def.format(time);
	}

	public static Date parse(String time, String pattern) {
		Date rs = null;
		if (time != null) {
			try {
				rs = new SimpleDateFormat(pattern).parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return rs;
	}
	
	
	public static long  getDatesBetweenMillseconds(Date date1, Date date2){
	    SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    long between = 0;
	    try {
	        between = (date1.getTime() - date2.getTime());// 得到两者的毫秒数
	        if(between <= 0)  between = -between;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return between;
	}
	
	/**
	 * 根据ms数目返回描述
	 * @param between
	 * @return
	 */
	public static String getDatesBetweenDesc(Long between, Locale locale){
		   
		    long day = between / (24 * 60 * 60 * 1000);
		    long hour = (between / (60 * 60 * 1000) - day * 24);
		    long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
		    long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		    long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
		            - min * 60 * 1000 - s * 1000);
		    
		    if (day >= 1){
		    	if (day >= 7){
		    		return " ";
		    	}
		    	  if(Locale.CHINA.equals(locale)){
		    		  return day + "天"; 
		    	  }
		    	return day + "day"; 
		    }
		    if (hour >= 1){
		    	  if(Locale.CHINA.equals(locale)){
		    		  return hour + "小时"; 
		    	  }
		    	return  hour + "h";
		    }
		    if(min >= 1){
		    	  if(Locale.CHINA.equals(locale)){
		    		  return min + "分"; 
		    	  }
		    	return min + "min";
		    }
		    
		    if(s >= 1 || ms >= 0){
		    	  if(Locale.CHINA.equals(locale)){
		    		  return s + "秒"; 
		    	  }
		    	return s + "s";
		    }
		    if(Locale.CHINA.equals(locale)){
	    		  return "未知"; 
	    	  }
		    return "N/A";
	}
	
	
}
