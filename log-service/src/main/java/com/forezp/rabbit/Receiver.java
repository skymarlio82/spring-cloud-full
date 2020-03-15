
package com.forezp.rabbit;

import com.alibaba.fastjson.JSON;
import com.forezp.entity.SysLog;
import com.forezp.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

	private CountDownLatch latch = new CountDownLatch(1);

	@Autowired
	private SysLogService sysLogService = null;

	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
		SysLog sysLog = JSON.parseObject(message, SysLog.class);
		sysLogService.saveLogger(sysLog);
		latch.countDown();
	}
}