  
package com.csu.vlab.atlas.core.model;  

import java.util.Date;

/**  
 * ClassName:SignItem <br/>  
 * Date:     Apr 11, 2016 4:18:20 PM <br/>  
 * @author   chenx  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public class SignItemDto {

	//实验室 姓名 手机OS 学位 上午到达时间 上午离开时间 下午到达时间 下午离开时间 晚上到达时间 晚上离开时间 按时到勤 日在线时间(分钟)

	public String lab;
	
	public String name;
	
	public String mos;
	
	public String xuewei;
	
	public String morningtime_arrive;
	
	public String morningtime_end;

	public String afternoontime_arrive;
	
	public String afternoontime_end;
	
	public String eveningtime_arrive;
	
	public String eveningtime_end;
	
	public String anshi;
	
	public String onlineminutes;

	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMos() {
		return mos;
	}

	public void setMos(String mos) {
		this.mos = mos;
	}

	public String getXuewei() {
		return xuewei;
	}

	public void setXuewei(String xuewei) {
		this.xuewei = xuewei;
	}

	public String getMorningtime_arrive() {
		return morningtime_arrive;
	}

	public void setMorningtime_arrive(String morningtime_arrive) {
		this.morningtime_arrive = morningtime_arrive;
	}

	public String getMorningtime_end() {
		return morningtime_end;
	}

	public void setMorningtime_end(String morningtime_end) {
		this.morningtime_end = morningtime_end;
	}

	public String getAfternoontime_arrive() {
		return afternoontime_arrive;
	}

	public void setAfternoontime_arrive(String afternoontime_arrive) {
		this.afternoontime_arrive = afternoontime_arrive;
	}

	public String getAfternoontime_end() {
		return afternoontime_end;
	}

	public void setAfternoontime_end(String afternoontime_end) {
		this.afternoontime_end = afternoontime_end;
	}

	public String getEveningtime_arrive() {
		return eveningtime_arrive;
	}

	public void setEveningtime_arrive(String eveningtime_arrive) {
		this.eveningtime_arrive = eveningtime_arrive;
	}

	public String getEveningtime_end() {
		return eveningtime_end;
	}

	public void setEveningtime_end(String eveningtime_end) {
		this.eveningtime_end = eveningtime_end;
	}

	public String getAnshi() {
		return anshi;
	}

	public void setAnshi(String anshi) {
		this.anshi = anshi;
	}

	public String getOnlineminutes() {
		return onlineminutes;
	}

	public void setOnlineminutes(String onlineminutes) {
		this.onlineminutes = onlineminutes;
	}
	
	
}
  
