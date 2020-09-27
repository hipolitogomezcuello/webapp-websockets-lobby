package com.sungames.pato.service;

import com.sungames.pato.domain.lobby.User;

import java.util.Optional;

public interface UserService {
  User save(User user);
  Optional<User> findById(String id);
}
