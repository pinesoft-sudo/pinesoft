package org.pine.boot.rabbit;

import org.springframework.stereotype.Component;

@Component
public class RabbitReceiver {
	
    public void receive(String msg) {
        System.out.println("Receiver  : " +msg );
    }
}