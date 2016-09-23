package com.csu.vlab.atlas.framework.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author chenx
 *
 */
public class ThreadPoolUtil
{
    public final ExecutorService executorService = Executors
            .newCachedThreadPool();
    
    public static ThreadPoolUtil instance;
    
    public void init()
    {
        ThreadPoolUtil.instance=this;
    }
    
    public void destory()
    {
        ThreadPoolUtil.instance.executorService.shutdownNow();
    }
    
    public static void addRunnable(Runnable command)
    {
        ThreadPoolUtil.instance.executorService.execute(command);
    }
}
