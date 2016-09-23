  
package com.csu.vlab.atlas.core.po;  

import java.util.Date;

/**  
 * ClassName:SignTask <br/>  
 * Date:     Apr 13, 2016 7:59:34 PM <br/>  
 * @author   chenx  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class SignTask {

	public String _id;
	
	public Date date;
	
	public boolean isSuccess = false;

	public  Date lastExecuteTime;
	
	public int tryTimes = 0 ;
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public int getTryTimes() {
		return tryTimes;
	}

	public void setTryTimes(int tryTimes) {
		this.tryTimes = tryTimes;
	}

	public Date getLastExecuteTime() {
		return lastExecuteTime;
	}

	public void setLastExecuteTime(Date lastExecuteTime) {
		this.lastExecuteTime = lastExecuteTime;
	}

}
  
