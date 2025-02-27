package com.hopoong.dspmigration.app.internal;

import com.hopoong.dspmigration.app.core.CoreServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import(CoreServiceConfig.class)
@SpringBootApplication
public class InternalAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(InternalAppApplication.class, args);
    }
}
