package com.sungames.pato.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class WSConfig {

  @Bean
  public PresenceEventListener presenceEventListener(SimpMessagingTemplate messagingTemplate) {
    return new PresenceEventListener(messagingTemplate);
  }
}
