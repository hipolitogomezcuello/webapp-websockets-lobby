package com.sungames.pato.domain.dto;

import lombok.Data;

@Data
public class UserLeftLobbyResponse {

  private final String type = "userLeftLobby";
  private String userId;
  private String lobbyId;

}
