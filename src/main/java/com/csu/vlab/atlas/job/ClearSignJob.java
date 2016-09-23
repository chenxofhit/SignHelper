  
package com.csu.vlab.atlas.job;  

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.csu.vlab.atlas.core.po.SignTask;
import com.csu.vlab.atlas.core.po.UserSign;
import com.csu.vlab.atlas.sign.service.SignService;

/**  
 * ClassName:ClearSignJob <br/>  
 * Date:     Apr 14, 2016 2:09:20 PM <br/>  
 * @author   chenx  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class ClearSignJob {
	
	private final Logger LOGGER = Logger.getLogger(SignJob.class);
	
	@Autowired
	private SignService signService;

	 public void execute(){  
		 List<UserSign>  userSigns = signService.getAllUserSignInAutoMode();
		 for(UserSign userSign: userSigns){
			 userSign.setAutoSignMode(0);
			 userSign.setSignTasks(new ArrayList<SignTask>());
			 
			 signService.updateUserSign(userSign);
			 LOGGER.info("clear job end...");
		 }
	 }
}
  
