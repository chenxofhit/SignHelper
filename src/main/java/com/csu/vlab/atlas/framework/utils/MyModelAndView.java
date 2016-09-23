package com.csu.vlab.atlas.framework.utils;  

import org.springframework.web.servlet.ModelAndView;

/**  
 * ClassName:MyModelAndView <br/>  
 * Date:     Apr 2, 2016 9:05:28 PM <br/>  
 * @author   chenx  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */

public class MyModelAndView extends ModelAndView
{
    private String _code;
    
    private String _message;
    
    public MyModelAndView()
    {
        super();
    }
    
    public MyModelAndView(String viewName)
    {
        super(viewName);
    }
    
    public String get_code()
    {
        return _code;
    }
    
    public void set_code(String _code)
    {
        this._code = _code;
    }
    
    public String get_message()
    {
        return _message;
    }
    
    public void set_message(String _message)
    {
        this._message = _message;
    }
}

  
