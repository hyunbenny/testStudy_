package kr.hbcho90.teststudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TestStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestStudyApplication.class, args);
    }

}
