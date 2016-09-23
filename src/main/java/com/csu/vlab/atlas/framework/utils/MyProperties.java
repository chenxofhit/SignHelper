package com.csu.vlab.atlas.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyProperties extends Properties
{
    
    private static final long serialVersionUID = -8195784633096043537L;
    
    public MyProperties(File file)
    {
        FileInputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(file);
            this.load(inputStream);
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
