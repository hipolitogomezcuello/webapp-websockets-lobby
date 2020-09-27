package com.sungames.pato.domain;

import com.sungames.pato.domain.lobby.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Lobby {

  private String id;
  private List<User> users;
  private User host;
  private String name;

  public void addUser(User user) {
    users.add(user);
  }

  public void removeUser(String userId) {
    if (userId.equals(host.getId())) {
      throw new IllegalArgumentException("Cannot remove host");
    }
    users = users.stream().filter(user -> !user.getId().equals(userId)).collect(Collectors.toList());
  }
}
