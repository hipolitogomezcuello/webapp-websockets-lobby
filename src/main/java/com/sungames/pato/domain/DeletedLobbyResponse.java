package com.sungames.pato.domain;

import lombok.Data;

@Data
public class DeletedLobbyResponse {

  private final String type = "deletedLobby";
  private String lobbyId;

}
