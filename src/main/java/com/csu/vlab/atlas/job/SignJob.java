  
package com.csu.vlab.atlas.job;  

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.csu.vlab.atlas.controller.conf.SignBluetoothUtils;
import com.csu.vlab.atlas.core.po.SignTask;
import com.csu.vlab.atlas.core.po.UserSign;
import com.csu.vlab.atlas.sign.service.SignService;

/**  
 * ClassName:SignJob <br/>  
 * Date:     Apr 13, 2016 8:22:11 PM <br/>  
 * @author   chenx  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class SignJob {

	private final Logger LOGGER = Logger.getLogger(SignJob.class);

	@Autowired
	private SignBluetoothUtils signBluetoothUtils;
	
	@Autowired
	private SignService signService;
	
	public boolean isToSign(SignTask signTask){
		if (null != signTask && signTask.isSuccess() == false &&  signTask.getTryTimes()<=3 ){
			Date toSignDate = signTask.getDate();
			Date now = new Date();
			if(toSignDate.getTime()<= now.getTime() ) return true;
		}
		return false;
	}
	
	 public void execute() throws Exception{  
		 //TODO:遍历所有的用户如果发现是自动模式的, 获取其未执行成功的任务,如果当前时间比目标时间差值在60s以内就开始执行任务,
		 //任务最多执行三次如果三次后失败当做失败处理
		 List<UserSign>  userSigns = signService.getAllUserSignInAutoMode();
		 for(UserSign userSign: userSigns){
			 if( null != userSign.getSignTasks()){
				 List<SignTask> signTasks=  userSign.getSignTasks();
				 for(SignTask signTask : signTasks){
					 if(isToSign(signTask)){
						    Thread.sleep((int)(Math.random()*60*1000));
						 	String mac  =  userSign.getMac();
						 	int tryTimes = signTask.getTryTimes();
						 	signTask.setTryTimes(tryTimes+1);
						 	signTask.setLastExecuteTime(new Date());
				             
						 	ExecutorService exec = Executors.newSingleThreadExecutor();  
						 	Future<String> f = exec.submit(new SignTimeOutTask(mac,signBluetoothUtils));  
							exec.shutdown();
				            try {  
				                  	String res = f.get(10, TimeUnit.SECONDS);  //10s超时
				        			if (res.startsWith("success")) {
				                  		signTask.setSuccess(true);
				                  		signService.updateUserSign(userSign);
				        			}
				             } catch (Exception e) {  
				                  //定义超时后的状态修改  
			                  	  signTask.setSuccess(false);
			                  	  signService.updateUserSign(userSign);
				                  e.printStackTrace();  
				                  
				                  LOGGER.error(e.getMessage(),e);
				              }   finally {
				            	  	f.cancel(true);
				              }
					 }
				 }
			 }
		 }
	 }  
}
  
