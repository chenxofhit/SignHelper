package com.csu.vlab.atlas.framework.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.csu.vlab.atlas.framework.dao.CommonNoSqlDao;
import com.csu.vlab.atlas.framework.dao.CommonNoSqlDao.SimpleUpdateEntry.Operation;

/**
 * 
 * ClassName: CommonNoSqlDao <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: Mar 10, 2016 11:29:08 AM <br/>  
 *  
 * @author chenx  
 * @version   
 * @since JDK 1.7
 */


@Repository("commonNoSqlDao")
public class CommonNoSqlDaoImpl implements CommonNoSqlDao
{
    @Autowired
    private MongoTemplate mongoTemplate;

    public <T> void insert(T t)
    {
        this.mongoTemplate.insert(t);
    }
    
	@Override
	public <T> void remove(Class<T> clz) {
		  this.mongoTemplate.remove(new Query(), clz);
	}
	
    
    public <T> T findById(Object id, Class<T> clz)
    {
        T t = this.mongoTemplate.findById(id, clz);
        return t;
    }
    
    public <T> List<T> findList(Class<T> clz, SimpleCriteriaEntry... entries)
    {
        Query query = new Query();
        
        if( null != entries){
        	for (int i = 0, len = entries.length; i < len; i++){
        		query.addCriteria(entries[i].toCriteria());
        	}
        }
        List<T> list = this.mongoTemplate.find(query, clz);
        return list;
    }
    
    
    public <T> List<T> findListByQuery(Class<T> clz, Query query)
    {
        List<T> list = this.mongoTemplate.find(query, clz);
        return list;
    }
    
    
    public <T> Long findCount(Class<T> clz, SimpleCriteriaEntry... entries)
    {
        Query query = new Query();
        
        for (int i = 0, len = entries.length; i < len; i++)
        {
            query.addCriteria(entries[i].toCriteria());
        }
        long count = this.mongoTemplate.count(query, clz);
        return count;
    }
    
    public <T> void removeById(Object id, Class<T> clz)
    {
        this.mongoTemplate.remove(
                new Query().addCriteria(new Criteria().and("id").is(id)), clz);
    }
    
    public <T> void updateFirst(List<SimpleUpdateEntry> uentries,
            List<SimpleCriteriaEntry> centries, Class<T> clz)
    {
        Query query = new Query();
        
        for (int i = 0, len = centries.size(); i < len; i++)
        {
            query.addCriteria(centries.get(i).toCriteria());
        }
        
        Update update = this.createAndInitUpdate(null, uentries);
        this.mongoTemplate.updateFirst(query, update, clz);
    }
    
    private Update createAndInitUpdate(Update update,
            List<SimpleUpdateEntry> entries)
    {
        if (update == null)
        {
            update = new Update();
        }
        
        for (SimpleUpdateEntry simpleUpdateEntry : entries)
        {
            if (simpleUpdateEntry.getOperation() == Operation.SET)
            {
                update.set(simpleUpdateEntry.getKey(),
                        simpleUpdateEntry.getValue());
            } else if (simpleUpdateEntry.getOperation() == Operation.INC)
            {
                update.inc(simpleUpdateEntry.getKey(),
                        (Number) simpleUpdateEntry.getValue());
            }
        }
        
        return update;
    }


}
