package com.sungames.pato.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;

public class PresenceEventListener {

  private SimpMessagingTemplate simpMessagingTemplate;

  public PresenceEventListener(SimpMessagingTemplate simpMessagingTemplate) {
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @EventListener
  private void handleSessionConnected(SessionConnectedEvent event) {
    SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
    //TODO continue from here

  }

}
