
package com.forezp.rabbit;

import com.forezp.config.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.Date;

//@Component
public class Sender {

//	@Autowired
	private AmqpTemplate rabbitTemplate = null;

	public void send() {
		String context = "hello " + new Date();
		System.out.println("Sender : " + context);
		rabbitTemplate.convertAndSend(RabbitConfig.queueName, "Hello from RabbitMQ!");
	}
}