  
package com.csu.vlab.atlas.job;  

import java.util.concurrent.Callable;

import com.csu.vlab.atlas.controller.conf.SignBluetoothUtils;

/**  
 * ClassName:SignTask <br/>  
 * Date:     Apr 13, 2016 9:09:14 PM <br/>  
 * @author   chenx  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */

public class SignTimeOutTask implements Callable<String> {  
  
    private String mac;   
    
	private SignBluetoothUtils signBluetoothUtils;
	
    public SignTimeOutTask(String  mac, SignBluetoothUtils signBluetoothUtils){   
        this.mac = mac;   
        this.signBluetoothUtils = signBluetoothUtils;
    } 
	
    @Override  
    public String call() throws Exception {  
		String uploadlist =  signBluetoothUtils.getUploadList();
		String result = signBluetoothUtils.sign(mac,uploadlist);
        return result;  
    }
}  