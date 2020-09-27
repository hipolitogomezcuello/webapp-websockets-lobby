package com.sungames.pato.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/api/test")
  public String test() {

    System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDD");
    return "";
  }

}
