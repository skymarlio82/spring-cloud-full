
package com.forezp.service;

import com.forezp.dao.SysLogDAO;
import com.forezp.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogService {

	@Autowired
	private SysLogDAO sysLogDAO = null;

	public void saveLogger(SysLog sysLog) {
		sysLogDAO.save(sysLog);
	}
}