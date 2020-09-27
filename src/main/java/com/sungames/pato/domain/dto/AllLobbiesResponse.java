package com.sungames.pato.domain.dto;

import com.sungames.pato.domain.Lobby;
import lombok.Data;

import java.util.List;

@Data
public class AllLobbiesResponse {
  private final String type = "allLobbies";
  private List<Lobby> lobbies;
}
