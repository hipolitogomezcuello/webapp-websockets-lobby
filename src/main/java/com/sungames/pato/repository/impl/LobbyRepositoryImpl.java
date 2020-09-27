package com.sungames.pato.repository.impl;

import com.sungames.pato.domain.Lobby;
import com.sungames.pato.repository.LobbyRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Arrays.asList;

@Repository
public class LobbyRepositoryImpl implements LobbyRepository {

  private Map<String, Lobby> lobbies = new HashMap<>();

  @Override
  public Lobby create() {
    Lobby lobby = createLobby();
    lobbies.put(lobby.getId(), lobby);
    return lobby;
  }

  @Override
  public List<Lobby> findAll() {
    return new ArrayList<>(lobbies.values());
  }

  @Override
  public Optional<Lobby> findById(String id) {
    return Optional.ofNullable(lobbies.get(id));
  }

  @Override
  public void deleteById(String id) {
    lobbies.remove(id);
  }

  private Lobby createLobby() {
    Lobby lobby = new Lobby();
    lobby.setId(UUID.randomUUID().toString());
    lobby.setUsers(new ArrayList<>());
    return lobby;
  }

}
