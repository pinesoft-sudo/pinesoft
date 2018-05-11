package org.pine.TestMQ;

/**
 * Unit test for simple App.
 */
public class AppMain {
	private final static String QUEUE_NAME = "PineTest";

	public static void main(String[] args) throws Exception {
		MQSender ms = new MQSender(QUEUE_NAME);
		MQReceiver mr = new MQReceiver(QUEUE_NAME);

		ms.BasicSend();
		Thread.sleep(5000);
		mr.BasicReceive();
	}

}
