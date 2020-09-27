package com.sungames.pato.service.impl;

import com.sungames.pato.domain.lobby.User;
import com.sungames.pato.repository.UserRepository;
import com.sungames.pato.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public Optional<User> findById(String id) {
    return userRepository.findById(id);
  }
}
