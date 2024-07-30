package com.company.prisoner.game;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author user
 */
@SpringBootApplication
@MapperScan(basePackages = "com.company.prisoner.game.mapper")
public class PrisonerGamerApplication {

    public static void main(String[] args){
        SpringApplication.run(PrisonerGamerApplication.class, args);
    }
}
