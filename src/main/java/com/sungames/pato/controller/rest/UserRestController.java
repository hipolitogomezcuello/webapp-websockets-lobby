package com.sungames.pato.controller.rest;

import com.sungames.pato.domain.lobby.User;
import com.sungames.pato.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

  private final UserService userService;

  @PostMapping()
  public User createUser(@RequestBody User user) {
    return userService.save(user);
  }

}
