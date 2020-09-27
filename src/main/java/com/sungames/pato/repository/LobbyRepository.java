package com.sungames.pato.repository;

import com.sungames.pato.domain.Lobby;

import java.util.List;
import java.util.Optional;

public interface LobbyRepository {

  Lobby create();

  List<Lobby> findAll();

  Optional<Lobby> findById(String id);

  void deleteById(String id);
}
