package com.csu.vlab.atlas.framework.utils;

import java.util.List;

/**
 * 
 * @author chenx
 *
 */
public class CollectionUtil
{
    public static boolean isEmpty(List<?> list){
        return !isNotEmpty(list);
    }
    public static boolean isNotEmpty(List<?> list){
        if (list!=null && list.size()>0)
        {
            return true;
        }else{
            return false;
        }
    }
}
