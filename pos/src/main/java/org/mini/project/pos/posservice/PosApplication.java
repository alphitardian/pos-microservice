package org.mini.project.pos.posservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableFeignClients
public class PosApplication {
  public static void main(String[] args) {
    SpringApplication.run(PosApplication.class, args);
  }
}