package com.hopoong.couponcore;


import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;




@EnableCaching
@EnableJpaAuditing // CreatedDate LastModifiedDate
@ComponentScan
@EnableAutoConfiguration
public class CouponCoreConfiguration {

    private final Environment environment;

    public CouponCoreConfiguration(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void printActiveProfiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            System.out.println("No active profiles");
        } else {
            System.out.println("Active Profiles in CoreConfiguration: " + String.join(", ", activeProfiles));
        }
    }
}