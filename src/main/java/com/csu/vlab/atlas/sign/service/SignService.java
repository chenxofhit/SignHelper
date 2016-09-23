  
package com.csu.vlab.atlas.sign.service;  

import java.util.List;

import com.csu.vlab.atlas.core.po.SignTask;
import com.csu.vlab.atlas.core.po.UserSign;

/**  
 * ClassName:SignService <br/>  
 * Date:     Apr 13, 2016 8:05:01 PM <br/>  
 * @author   chenx  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public interface SignService {

	public List<UserSign> getAllUsers();
	
	public List<UserSign> getAllUserSignInAutoMode();
	
    public  int insertUserSign(UserSign userSign);
    
    public int updateUserSign(UserSign userSign);
    
    public int removeUserSign();
    
    public int updateUserSignTask(String userid,SignTask userSignTask);
    
    public int deleteUserSignTasks(String userid);
    
	public UserSign getUserSignByUserid(String id);

	public UserSign getUserSignByMac(String mac);
	
}
  
