package com.exena.anthive;

import com.exena.anthive.application.provided.EmailSender;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnthiveApplicationTests {

    @Test
    void run() {
        try (MockedStatic<SpringApplication> mockedStatic = Mockito.mockStatic(SpringApplication.class)) {

            AnthiveApplication.main(new String[0]);

            mockedStatic.verify(() ->
                    SpringApplication.run(AnthiveApplication.class, new String[]{})
            );
        }
    }

}
