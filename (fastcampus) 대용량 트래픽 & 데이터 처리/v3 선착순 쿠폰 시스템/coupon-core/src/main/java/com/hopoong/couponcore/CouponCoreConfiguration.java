package com.hopoong.couponcore;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing // CreatedDate LastModifiedDate
@ComponentScan
@Configuration
@PropertySource("file:./pwd.ini")
public class CouponCoreConfiguration {
}
