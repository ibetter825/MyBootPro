package com.mypro.configure.security.customer;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import com.mypro.bean.entity.admin.SysPersistentLogins;
import com.mypro.dao.admin.SysPersistentLoginsDao;
/**
 * 用于security持久化cookie到数据库
 * @author user
 *
 */
@Service
public class MyPersistentTokenRepository implements PersistentTokenRepository {

	private static final Logger logger = LoggerFactory.getLogger(MyPersistentTokenRepository.class);
	
	@Autowired
	private SysPersistentLoginsDao splDao;
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		//新建的时候先查询如果存在则需要先删除老的数据
		removeUserTokens(token.getUsername());
		SysPersistentLogins record = new SysPersistentLogins();
		record.setSeries(token.getSeries());
		record.setToken(token.getTokenValue());
		record.setUsername(token.getUsername());
		record.setLast_used(token.getDate().getTime());
		splDao.insertSelective(record);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		SysPersistentLogins record = new SysPersistentLogins();
		record.setSeries(series);
		record.setToken(tokenValue);
		record.setLast_used(lastUsed.getTime());
		splDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		try {
			SysPersistentLogins record = new SysPersistentLogins();
			record.setSeries(seriesId);
			record = splDao.selectOne(record);
			if(record == null)
				return null;
			Date date = new Date();
			date.setTime(record.getLast_used());
			return new PersistentRememberMeToken(record.getUsername(), record.getSeries(), record.getToken(), date);
		} catch (EmptyResultDataAccessException zeroResults) {
			if (logger.isDebugEnabled()) {
				logger.debug("Querying token for series '" + seriesId
						+ "' returned no results.", zeroResults);
			}
		}
		catch (IncorrectResultSizeDataAccessException moreThanOne) {
			logger.error("Querying token for series '" + seriesId
					+ "' returned more than one value. Series" + " should be unique");
		}
		catch (DataAccessException e) {
			logger.error("Failed to load token for series " + seriesId, e);
		}
		
		return null;
	}

	@Override
	public void removeUserTokens(String username) {
		SysPersistentLogins record = new SysPersistentLogins();
		record.setUsername(username);
		splDao.delete(record);
	}

}
