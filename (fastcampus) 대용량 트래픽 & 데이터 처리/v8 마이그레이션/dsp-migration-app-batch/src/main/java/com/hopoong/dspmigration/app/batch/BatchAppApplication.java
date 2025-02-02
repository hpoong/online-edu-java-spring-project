package com.hopoong.dspmigration.app.batch;

import com.hopoong.dspmigration.app.core.CoreServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import(CoreServiceConfig.class)
@SpringBootApplication
public class BatchAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchAppApplication.class, args);
    }
}
