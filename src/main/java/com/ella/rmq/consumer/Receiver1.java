package com.ella.rmq.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Receiver1 {

	private final static String QUEUE_NAME = "hello";
	private final static String EXCHANGE_NAME = "logs_direct";
	private final static String ROUTING_KEY = "routing_key";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//커넥션 커넥션팩토리 커넥션풀-매니저역할 차이점?
		ConnectionFactory factory = new ConnectionFactory();
//		factory.setUsername("jhfivoca");
//		factory.setPassword("v9ffDPuRscN6sDYLInsB339Fw-_ORi9e");
//		factory.setVirtualHost("jhfivoca");
//		factory.setHost("shark-01.rmq.cloudamqp.com");
//		factory.setPort(5672);
		factory.setUsername("ella");
		factory.setPassword("maxst");
		factory.setVirtualHost("vella");
		factory.setHost("192.168.1.13");
		factory.setPort(5672);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); 
		//durable true일 경우, 디스크 캐싱 예약 정도? 유실 가능성 있음, 100% 보장은 아님
		
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY);
		
//		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
  
	}
}
