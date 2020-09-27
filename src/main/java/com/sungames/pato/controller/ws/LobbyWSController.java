package com.sungames.pato.controller.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.sungames.pato.domain.Lobby;
import com.sungames.pato.domain.dto.AllLobbiesResponse;
import com.sungames.pato.domain.dto.NewLobbyResponse;
import com.sungames.pato.domain.dto.UserJoinedLobbyResponse;
import com.sungames.pato.domain.dto.UserLeftLobbyResponse;
import com.sungames.pato.domain.lobby.User;
import com.sungames.pato.domain.lobby.UserResponse;
import com.sungames.pato.service.LobbyService;
import com.sungames.pato.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LobbyWSController {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final UserService userService;
  private final LobbyService lobbyService;

  @MessageMapping("/lobbies")
  public void lobbies(@Payload JsonNode payload) {
    switch(payload.get("type").asText()) {
      case "getAllLobbies":
        simpMessagingTemplate.convertAndSend("/topic/lobbies", createGetAllLobbiesResponse());
        break;
      case "createLobby":
        Lobby createdLobby = lobbyService.create(payload.get("name").asText(), userService.findById(payload.get("userId").asText()).orElseThrow(IllegalArgumentException::new));
        simpMessagingTemplate.convertAndSend("/topic/lobbies", createNewLobbyResponse(createdLobby));
        break;
      case "joinLobby":
        Lobby lobbyToJoin = lobbyService.findById(payload.get("lobbyId").asText()).orElseThrow(IllegalArgumentException::new);
        User userToJoin = userService.findById(payload.get("userId").asText()).orElseThrow(IllegalArgumentException::new);
        lobbyToJoin.addUser(userToJoin);
        simpMessagingTemplate.convertAndSend("/topic/lobbies", createUserJoinedLobbyResponse(lobbyToJoin, userToJoin));
        break;
      case "leaveLobby":
        String lobbyToBeLeftId = payload.get("lobbyId").asText();
        String userToLeaveId = payload.get("userId").asText();
        lobbyService.removeUserById(lobbyToBeLeftId, userToLeaveId);
        break;
      case "deleteLobby":
        String lobbyToBeDeletedId = payload.get("lobbyId").asText();
        lobbyService.deleteLobby(lobbyToBeDeletedId);
        break;
      default:
        throw new IllegalArgumentException();
    }
  }

  private UserJoinedLobbyResponse createUserJoinedLobbyResponse(Lobby lobby, User user) {
    UserJoinedLobbyResponse response = new UserJoinedLobbyResponse();
    response.setLobbyId(lobby.getId());
    response.setUser(user);
    return response;
  }

  private NewLobbyResponse createNewLobbyResponse(Lobby lobby) {
    NewLobbyResponse response = new NewLobbyResponse();
    response.setLobby(lobby);
    return response;
  }

  private AllLobbiesResponse createGetAllLobbiesResponse() {
    AllLobbiesResponse response = new AllLobbiesResponse();
    response.setLobbies(lobbyService.findAll());
    return response;
  }

}
