package com.sungames.pato.service;

import com.sungames.pato.domain.Lobby;
import com.sungames.pato.domain.lobby.User;

import java.util.List;
import java.util.Optional;

public interface LobbyService {

  Lobby create();

  Lobby create(String name, User user);

  List<Lobby> findAll();

  Optional<Lobby> findById(String id);

  void removeUserById(String lobbyId, String userId);

  void deleteLobby(String lobbyId);
}
