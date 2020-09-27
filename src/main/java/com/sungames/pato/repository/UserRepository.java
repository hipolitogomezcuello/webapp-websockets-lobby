package com.sungames.pato.repository;

import com.sungames.pato.domain.lobby.User;

import java.util.Optional;

public interface UserRepository {
  User save(User user);

  Optional<User> findById(String id);
}
