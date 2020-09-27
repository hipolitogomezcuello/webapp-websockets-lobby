package com.sungames.pato.repository.impl;

import com.sungames.pato.domain.lobby.User;
import com.sungames.pato.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private Map<String, User> users = new HashMap<>();

  @Override
  public User save(User user) {
    user.setId(UUID.randomUUID().toString());
    users.put(user.getId(), user);
    return user;
  }

  @Override
  public Optional<User> findById(String id) {
    return Optional.ofNullable(users.get(id));
  }
}
