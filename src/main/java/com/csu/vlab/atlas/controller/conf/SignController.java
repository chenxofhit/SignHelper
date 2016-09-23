package com.csu.vlab.atlas.controller.conf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.csu.vlab.atlas.common.constants.AtlasPropertyConfigurer;
import com.csu.vlab.atlas.core.model.SignItemDto;
import com.csu.vlab.atlas.core.po.SignTask;
import com.csu.vlab.atlas.core.po.UserSign;
import com.csu.vlab.atlas.framework.utils.DateUtil;
import com.csu.vlab.atlas.job.SignTimeOutTask;
import com.csu.vlab.atlas.sign.service.SignService;

/**
 * 
 * @author chenx
 * 
 */
@Controller
public class SignController {

	private final Logger LOGGER = Logger.getLogger(SignController.class);

	@Autowired
	private SignBluetoothUtils signBluetoothUtils;
	
	@Autowired 
	private  SignService signService;

	@Autowired
	private AtlasPropertyConfigurer atlasPropertyConfigurer;
	
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request) {
		List<UserSign>  userSigns  = signService.getAllUsers();
		model.addAttribute("userSigns", userSigns);
		return "/index";
	}
	
	
	@RequestMapping("/view.json")
	public Model viewSign(String name, Model model, HttpServletRequest request) throws Exception {
		
		Date date = new Date();
		final SimpleDateFormat def = new SimpleDateFormat("yyyyMMdd");
		String day = def.format(date);
		
		String urlStr = atlasPropertyConfigurer.getProperty("signdetails_prefix") + day;

		Document doc = null;
		try {
			doc = Jsoup
					.connect(urlStr)
					.userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)") // 设置User-Agent
					.timeout(5000) // 设置连接超时时间
					.get();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			model.addAttribute("flag", 0);
			model.addAttribute("message", e.getMessage());
			return model;
		}

		Element body = doc.body();
		for (Element tr : body.getElementsByTag("tr")) {
			
			String text = tr.text();
			String[] textitems = text.split(" ");
			
			String gbkName = new String(name.getBytes("iso8859-1"),"UTF-8");
			String utfName = name;
			if(!textitems[1].equalsIgnoreCase(gbkName) && !textitems[1].equalsIgnoreCase(utfName)) {				
				continue;
			}
			
			Elements tds = tr.getElementsByTag("td");
			
			SignItemDto signItemDto = new SignItemDto();
			signItemDto.setLab(tds.get(0).text());
			signItemDto.setName(tds.get(1).text());
			signItemDto.setMos(tds.get(2).text());
			signItemDto.setXuewei(tds.get(3).text());
			signItemDto.setMorningtime_arrive(getTimeDesc(tds.get(4).text()));
			signItemDto.setMorningtime_end(getTimeDesc(tds.get(5).text()));
			signItemDto.setAfternoontime_arrive(getTimeDesc(tds.get(6).text()));
			signItemDto.setAfternoontime_end(getTimeDesc(tds.get(7).text()));
			signItemDto.setEveningtime_arrive(getTimeDesc(tds.get(8).text()));
			signItemDto.setEveningtime_end(getTimeDesc(tds.get(9).text()));
			signItemDto.setAnshi(tds.get(10).text());
			signItemDto.setOnlineminutes(tds.get(11).text());
			
			
			model.addAttribute("flag", 1);
			model.addAttribute("day",day);
			model.addAttribute("signItemDto", signItemDto);
			return model;
		}
		model.addAttribute("flag", 0);
		return model;
	}

	@RequestMapping(value="/viewautosign.json",method = RequestMethod.POST)
	public Model autoSign(String mac, Model model, HttpServletRequest request) {

		Date date = new Date();
	    final SimpleDateFormat def = new SimpleDateFormat("yyyyMMdd");
		String day = def.format(date);
		UserSign userSign = signService.getUserSignByMac(mac);
		model.addAttribute("userSign", userSign);
		model.addAttribute("day", day);
		return model;
	}
	
	@RequestMapping(value="/autosign.json",method = RequestMethod.POST)
	public Model autoSign(String mac, int autoSignMode , Model model, HttpServletRequest request) {
		if(autoSignMode == 1){ //设置自动签到模式
			UserSign userSign = signService.getUserSignByMac(mac);
			userSign.setAutoSignMode(autoSignMode);
			
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			String dd = sdf.format(new Date());
			
			String morningbegintime1 = dd + " "+"07:45:00";
			String morningbegintime2 = dd + " "+"08:10:00";
			
			String morningendtime1 = dd + " "+"11:55:00";
			String morningendtime2 = dd + " "+"12:20:00";
			
			String afternoonbegintime1 = dd + " "+"13:30:00";
			String afternoonbegintime2 = dd + " "+"13:50:00";
			
			String afternoonendtime1 = dd + " "+"17:50:00";
			String afternoonendtime2 = dd + " "+"18:30:00";

			
			String eveningbegintime1 = dd + " "+"19:33:00";
			String eveningbegintime2 = dd + " "+"19:50:00";
			
			String eveningendtime1 = dd + " "+"22:20:00";
			String eveningendtime2 = dd + " "+"22:55:00";
			
			
			List<SignTask> signTasks = new ArrayList<SignTask>();

			SignTask signTask = new SignTask();
			signTask.setSuccess(false);
			signTask.setTryTimes(0);
			signTask.setDate(DateUtil.randomDate(morningbegintime1, morningbegintime2));
			signTasks.add(signTask);

			signTask = new SignTask();
			signTask.setSuccess(false);
			signTask.setTryTimes(0);
			signTask.setDate(DateUtil.randomDate(morningendtime1, morningendtime2));
			signTasks.add(signTask);

			signTask = new SignTask();
			signTask.setSuccess(false);
			signTask.setTryTimes(0);
			signTask.setDate(DateUtil.randomDate(afternoonbegintime1, afternoonbegintime2));
			signTasks.add(signTask);

			signTask = new SignTask();
			signTask.setSuccess(false);
			signTask.setTryTimes(0);
			signTask.setDate(DateUtil.randomDate(afternoonendtime1, afternoonendtime2));
			signTasks.add(signTask);

			signTask = new SignTask();
			signTask.setSuccess(false);
			signTask.setTryTimes(0);
			signTask.setDate(DateUtil.randomDate(eveningbegintime1, eveningbegintime2));
			signTasks.add(signTask);

			signTask = new SignTask();
			signTask.setSuccess(false);
			signTask.setTryTimes(0);
			signTask.setDate(DateUtil.randomDate(eveningendtime1, eveningendtime2));
			signTasks.add(signTask);

			userSign.setSignTasks(signTasks);
			
			signService.updateUserSign(userSign);
			
		}else{
			
			UserSign userSign = signService.getUserSignByMac(mac);
			userSign.setAutoSignMode(autoSignMode);
			List<SignTask> signTasks = new ArrayList<SignTask>();
			userSign.setSignTasks(signTasks);
			
			signService.updateUserSign(userSign);
		}
		
		model.addAttribute("flag", 1);
		model.addAttribute("msg", "success!");
		return model;
	}
	
	
	@RequestMapping(value="/sign.json",method = RequestMethod.POST)
	public Model sign(String mac, Model model, HttpServletRequest request) {
		ExecutorService exec = Executors.newSingleThreadExecutor();
		Future<String> f = exec.submit(new SignTimeOutTask(mac,signBluetoothUtils));
		exec.shutdown();
		try {
			String res = f.get(10, TimeUnit.SECONDS); // 5s超时
			if (res.startsWith("success")) {
				model.addAttribute("flag", 1);
				model.addAttribute("msg", res);

				return model;
			}
			model.addAttribute("flag", 0);
			model.addAttribute("msg", "exception");
			return model;
		} catch (Exception e) {
			// 定义超时后的状态修改
			model.addAttribute("flag", 0);
			model.addAttribute("msg", "time out exception, retry");
			LOGGER.info(e.getMessage(), e);
			e.printStackTrace();
			return model;
		}finally {
    	  	f.cancel(true);
      }
	}
	
	@RequestMapping("/details")
	public String sign(Model model, HttpServletRequest request) {
		Date date = new Date();
	    final SimpleDateFormat def = new SimpleDateFormat("yyyyMMdd");
		String day = def.format(date);
		
		String urlStr = atlasPropertyConfigurer.getProperty("signdetails_prefix") + day;

		model.addAttribute("urlStr",urlStr);
		String log = "sessionid=["+ request.getSession().getId()+"],request url=["+urlStr+"]";
		LOGGER.info(log);
		return "/details";
	}	
	
	public String getTimeDesc(String date){
		if(null != date ){
			String[] arrays = date.split(" ");
			if(arrays.length == 2){
				return arrays[1];
			}
		}
		return  "";
	}
	
	
}
