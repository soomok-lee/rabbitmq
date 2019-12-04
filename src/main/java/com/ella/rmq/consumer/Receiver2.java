package com.ella.rmq.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Receiver2 {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
//		factory.setUsername("jhfivoca");
//		factory.setPassword("v9ffDPuRscN6sDYLInsB339Fw-_ORi9e");
//		factory.setVirtualHost("jhfivoca");
//		factory.setHost("shark-01.rmq.cloudamqp.com");
//		factory.setPort(5672);
		factory.setUsername("lime");
		factory.setPassword("maxst");
		factory.setVirtualHost("vella");
		factory.setHost("192.168.1.13");
		factory.setPort(5672);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare("logs", "fanout");
		String queueName = channel.queueDeclare().getQueue();
	    channel.queueBind(queueName, "logs", QUEUE_NAME);
//		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
        
        /*
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {

				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
		*/
	}
}
