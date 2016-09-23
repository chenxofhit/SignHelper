  
package com.csu.vlab.atlas.common.constants;  

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * ClassName: SignHelper <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: Apr 8, 2016 5:19:29 PM <br/>  
 *  
 * @author chenx  
 * @version   
 * @since JDK 1.7
 */

public class SignHelperTest{

	public static void main(String[] args) throws Exception, IOException{

	  //请修改你的mac地址在这里
	  String yourmac = "58:13:49:1C:52:A5";
		
      Socket clientSocket = new Socket("202.197.61.249", 8887);
      PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8")), true);
        
      StringBuilder sb = new StringBuilder();
      sb.append((new StringBuilder(String.valueOf((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()))).append("\n").toString()));
      sb.append((new StringBuilder(String.valueOf("10:2A:B3:5E:26:15"))).append("\n").toString());
      sb.append((new StringBuilder(String.valueOf("-40")).append("\n").toString()));
        
      StringBuilder stringbuilder = new StringBuilder("upload\n");
      stringbuilder.append((new StringBuilder(yourmac).append("\n").toString()));
      stringbuilder.append((new StringBuilder(String.valueOf(sb))).append("\n").toString());
      stringbuilder.append("exit");
      out.println(stringbuilder);
	  out.flush();
	  
      System.out.println("Msg send to server:\n" + stringbuilder);
      		
      InputStreamReader streamReader = new InputStreamReader(clientSocket.getInputStream());  
      BufferedReader reader = new BufferedReader(streamReader);  
      String advice =reader.readLine();  
          
          
      System.out.println("Msg received from server:\n"+advice);  
      reader.close();  
  }
}
  
