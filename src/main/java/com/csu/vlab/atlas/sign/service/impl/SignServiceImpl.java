  
package com.csu.vlab.atlas.sign.service.impl;  

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.csu.vlab.atlas.core.po.SignTask;
import com.csu.vlab.atlas.core.po.UserSign;
import com.csu.vlab.atlas.framework.dao.CommonNoSqlDao;
import com.csu.vlab.atlas.framework.dao.CommonNoSqlDao.SimpleCriteriaEntry;
import com.csu.vlab.atlas.framework.dao.CommonNoSqlDao.SimpleUpdateEntry;
import com.csu.vlab.atlas.framework.dao.CommonNoSqlDao.SimpleUpdateEntry.Operation;
import com.csu.vlab.atlas.sign.service.SignService;

/**  
 * ClassName:SignService <br/>  
 * Date:     Apr 13, 2016 8:05:01 PM <br/>  
 * @author   chenx  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */

@Service
public class SignServiceImpl implements SignService{

	@Autowired 
	private CommonNoSqlDao commonNoSqlDao;
	
	@Override
	public List<UserSign> getAllUsers() {
		Query query = new Query();
		query.with(new Sort(Direction.ASC, "_id"));
		return commonNoSqlDao.findListByQuery(UserSign.class, query);
	}
	
	@Override
	public int insertUserSign(UserSign userSign) {
		commonNoSqlDao.insert(userSign);
		return 0;
	}
	
	@Override
	public int removeUserSign() {
		commonNoSqlDao.remove(UserSign.class);
		return 0;
	}

	@Override
	public int updateUserSign(UserSign userSign) {
		List<SimpleCriteriaEntry> centries = new ArrayList<>();
		List<SimpleUpdateEntry> uentries = new ArrayList<>();
		
		centries.add(SimpleCriteriaEntry.createEqEntry("_id", userSign.get_id()));
		
		SimpleUpdateEntry simpleUpdateEntry = new SimpleUpdateEntry();
		simpleUpdateEntry.setKey("name");
		simpleUpdateEntry.setValue(userSign.getName());
		simpleUpdateEntry.setOperation(Operation.SET); //haokeng a!!!
		uentries.add(simpleUpdateEntry);
		
	    simpleUpdateEntry = new SimpleUpdateEntry();
		simpleUpdateEntry.setKey("mac");
		simpleUpdateEntry.setValue(userSign.getMac());
		simpleUpdateEntry.setOperation(Operation.SET);
		uentries.add(simpleUpdateEntry);
		
	    simpleUpdateEntry = new SimpleUpdateEntry();
		simpleUpdateEntry.setKey("autoSignMode");
		simpleUpdateEntry.setValue(userSign.autoSignMode);
		simpleUpdateEntry.setOperation(Operation.SET);
		uentries.add(simpleUpdateEntry);
		
	    simpleUpdateEntry = new SimpleUpdateEntry();
		simpleUpdateEntry.setKey("signTasks");
		simpleUpdateEntry.setValue(userSign.getSignTasks());
		simpleUpdateEntry.setOperation(Operation.SET);
		uentries.add(simpleUpdateEntry);
		
		commonNoSqlDao.updateFirst(uentries, centries, UserSign.class);
		
		return 0;
	}

	@Override
	public UserSign getUserSignByUserid(String id) {
		UserSign userSign =  commonNoSqlDao.findById(id,UserSign.class);
		return userSign;
	}

	@Override
	public List<UserSign> getAllUserSignInAutoMode() {
		SimpleCriteriaEntry simpleCriteriaEntry = new SimpleCriteriaEntry();
		simpleCriteriaEntry.setKey("autoSignMode");
		simpleCriteriaEntry.setValue(1);
		simpleCriteriaEntry.setOperation(SimpleCriteriaEntry.Operation.eq);
		List<UserSign> userSigns = commonNoSqlDao.findList(UserSign.class, simpleCriteriaEntry);
		return userSigns;
	}

	@Override
	public int updateUserSignTask(String userid, SignTask userSignTask) {
		
		return 0;
	}

	@Override
	public int deleteUserSignTasks(String userid) {
		UserSign userSign = commonNoSqlDao.findById(userid,UserSign.class);
		if(null != userSign.getSignTasks()&& !userSign.getSignTasks().isEmpty())
			for(SignTask signTask : userSign.getSignTasks()){
				commonNoSqlDao.removeById(signTask.get_id(), SignTask.class);
			}
		return 0;
	}

	@Override
	public UserSign getUserSignByMac(String mac) {
		Criteria criteria = new Criteria().and("mac").is(mac);
		Query query = new Query();
		query.addCriteria(criteria);

		List<UserSign> userSigns = commonNoSqlDao.findListByQuery(
				UserSign.class, query);
		if (null != userSigns && !userSigns.isEmpty()) {
			return userSigns.get(0);
		}
		return null;
	}



}
  
