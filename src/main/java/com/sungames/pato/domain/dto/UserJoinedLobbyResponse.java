package com.sungames.pato.domain.dto;

import com.sungames.pato.domain.lobby.User;
import lombok.Data;

@Data
public class UserJoinedLobbyResponse {
  private final String type = "userJoinedLobby";
  private String lobbyId;
  private User user;
}
