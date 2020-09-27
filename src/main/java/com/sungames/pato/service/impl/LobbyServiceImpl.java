package com.sungames.pato.service.impl;

import com.sungames.pato.domain.DeletedLobbyResponse;
import com.sungames.pato.domain.Lobby;
import com.sungames.pato.domain.dto.UserLeftLobbyResponse;
import com.sungames.pato.domain.lobby.User;
import com.sungames.pato.repository.LobbyRepository;
import com.sungames.pato.service.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LobbyServiceImpl implements LobbyService {

  private final LobbyRepository lobbyRepository;
  private final SimpMessagingTemplate simpMessagingTemplate;

  @Override
  public Lobby create() {
    return lobbyRepository.create();
  }

  @Override
  public Lobby create(String name, User user) {
    Lobby lobby = create();
    lobby.setHost(user);
    lobby.getUsers().add(user);
    lobby.setName(name);
    return lobby;
  }

  @Override
  public List<Lobby> findAll() {
    return lobbyRepository.findAll();
  }

  @Override
  public Optional<Lobby> findById(String id) {
    return lobbyRepository.findById(id);
  }

  @Override
  public void removeUserById(String lobbyId, String userId) {
    Lobby lobby = lobbyRepository.findById(lobbyId).orElseThrow(IllegalArgumentException::new);
    lobby.removeUser(userId);
    simpMessagingTemplate.convertAndSend("/topic/lobbies", createUserLeftLobbyResponse(lobbyId, userId));
  }

  @Override
  public void deleteLobby(String lobbyId) {
    lobbyRepository.deleteById(lobbyId);
    simpMessagingTemplate.convertAndSend("/topic/lobbies", createDeletedLobbyResponse(lobbyId));
  }

  private DeletedLobbyResponse createDeletedLobbyResponse(String lobbyId) {
    DeletedLobbyResponse deletedLobbyResponse = new DeletedLobbyResponse();
    deletedLobbyResponse.setLobbyId(lobbyId);
    return deletedLobbyResponse;
  }

  private UserLeftLobbyResponse createUserLeftLobbyResponse(String lobbyId, String userId) {
    UserLeftLobbyResponse response = new UserLeftLobbyResponse();
    response.setLobbyId(lobbyId);
    response.setUserId(userId);
    return response;
  }

}
