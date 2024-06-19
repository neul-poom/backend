package com.jk.module_lecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ModuleLectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleLectureApplication.class, args);
    }

}
