  
package com.csu.vlab.atlas.core.po;  

import java.util.List;

/**  
 * ClassName:User <br/>  
 * Date:     Apr 13, 2016 7:56:59 PM <br/>  
 * @author   chenx  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class UserSign {

	public String _id;
	
	public String name;
	
	public String mac;

	public int autoSignMode;
	
	public List<SignTask> signTasks;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}


	public int getAutoSignMode() {
		return autoSignMode;
	}

	public void setAutoSignMode(int autoSignMode) {
		this.autoSignMode = autoSignMode;
	}

	public List<SignTask> getSignTasks() {
		return signTasks;
	}

	public void setSignTasks(List<SignTask> signTasks) {
		this.signTasks = signTasks;
	}
	
}
  
