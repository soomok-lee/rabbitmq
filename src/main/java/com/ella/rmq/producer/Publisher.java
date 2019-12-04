package com.ella.rmq.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Publisher {

	private final static String QUEUE_NAME = "hello"; // routing key
	private final static String EXCHANGE_NAME = "logs_direct";
	private final static String ROUTING_KEY = "routing_key";
	
	public static void main(String[] args) throws IOException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();

		factory.setUsername("ella");
		factory.setPassword("maxst");
		factory.setVirtualHost("vella"); // 일종의 context path
		factory.setHost("192.168.1.13");
		factory.setPort(5672);

		// option1
//		factory.setUsername("jhfivoca");
//		factory.setPassword("v9ffDPuRscN6sDYLInsB339Fw-_ORi9e");
//		factory.setVirtualHost("jhfivoca");
//		factory.setHost("shark-01.rmq.cloudamqp.com");
//		factory.setPort(5672);

		// option2
//		factory.setUri("amqp://jhfivoca:v9ffDPuRscN6sDYLInsB339Fw-_ORi9e@shark.rmq.cloudamqp.com/jhfivoca");

		// to open a channel
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "direct", true); // exclusive true
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY);

		String message = "Hello World!";

		while (true) {
			InputStream in = System.in;
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(reader);

			String msg = br.readLine();

			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes(StandardCharsets.UTF_8));
			System.out.println(" [x] Sent '" + message + "'");
		}

//		channel.queueDeclare(QUEUE_NAME, false, false, false, null); //declare a queue - exchange 추가하며 생략 처리
//		channel.basicPublish("logs", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8)); //publish a message - while문으로 처리

		// to close the channel and the connection
//		channel.close();
//		connection.close();
	}
}
