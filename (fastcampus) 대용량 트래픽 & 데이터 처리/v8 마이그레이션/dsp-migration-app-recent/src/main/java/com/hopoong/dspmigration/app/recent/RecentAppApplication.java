package com.hopoong.dspmigration.app.recent;

import com.hopoong.dspmigration.app.core.CoreServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import(CoreServiceConfig.class)
@SpringBootApplication
public class RecentAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecentAppApplication.class, args);
    }
}
