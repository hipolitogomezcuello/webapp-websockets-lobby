package com.sungames.pato.domain.dto;

import com.sungames.pato.domain.Lobby;
import lombok.Data;

@Data
public class NewLobbyResponse {
  private final String type = "newLobby";
  private Lobby lobby;

}
