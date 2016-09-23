package com.csu.vlab.atlas.framework.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 编码生成器
 * 
 * @author chenx
 * 
 */

public class CodingGenerator
{
    private static Random R = new Random();

    private static String SRC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static String NUMSRC = "0123456789";

    public static String uniqueCoding()
    {
	return null;
    }

    private static String digits(long val, int digits)
    {
	long hi = 1L << (digits * 4);
	return Numbers.toString(hi | (val & (hi - 1)), Numbers.MAX_RADIX)
		.substring(1);
    }

    /**
     * 以62进制（字母加数字）生成19位UUID，最短的UUID
     * 
     * @return
     */
    public static String uuid()
    {
	UUID uuid = UUID.randomUUID();
	StringBuilder sb = new StringBuilder();
	sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));
	sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));
	sb.append(digits(uuid.getMostSignificantBits(), 4));
	sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));
	sb.append(digits(uuid.getLeastSignificantBits(), 12));
	return sb.toString();
    }

    /**
     * 以时间为基础生成16位随机数
     * 
     * @return
     */
    public static String timeLen16()
    {
	StringBuilder sb = new StringBuilder(Long.toHexString(System
		.currentTimeMillis())).reverse();
	sb.insert(R.nextInt(11), (char) (R.nextInt(20) + 103));
	sb.insert(R.nextInt(12), (char) (R.nextInt(20) + 103));
	sb.insert(R.nextInt(13), (char) (R.nextInt(20) + 103));
	sb.insert(R.nextInt(14), (char) (R.nextInt(20) + 103));
	sb.insert(R.nextInt(15), (char) (R.nextInt(20) + 103));
	return sb.toString();
    }

    /**
     * 生成X位随机编码
     * 
     * @param x
     *            位
     * @return
     */
    public static String lenX(int x)
    {
	x = x < 1 ? 16 : x;
	StringBuilder sb = new StringBuilder();

	for (int i = 0; i < x; i++)
	{
	    sb.append(SRC.charAt(R.nextInt(62)));
	}

	return sb.toString();
    }

    public static String numOfLenX(int x)
    {
	x = x < 1 ? 4 : x;
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < x; i++)
	{
	    sb.append(NUMSRC.charAt(R.nextInt(10)));
	}

	return sb.toString();
    }
    
    public static void main(String[] args)
    {
	    for(int i=1;i<100;i++)
	    {
	    	System.out.println(CodingGenerator.uuid());
	    }
    }
}
