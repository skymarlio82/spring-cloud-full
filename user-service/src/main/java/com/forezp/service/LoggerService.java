
package com.forezp.service;

import com.alibaba.fastjson.JSON;
import com.forezp.config.RabbitConfig;
import com.forezp.entity.SysLog;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {

	@Autowired
	private AmqpTemplate rabbitTemplate = null;

	public void log(SysLog sysLog) {
		rabbitTemplate.convertAndSend(RabbitConfig.queueName, JSON.toJSONString(sysLog));
	}
}