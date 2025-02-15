package com.hopoong.dspmigration.app.core.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableJpaRepositories(
    basePackages = {
            "com.hopoong.dspmigration.app.core.repository.recent",
            "com.hopoong.dspmigration.app.core.repository.migration",
    },
    entityManagerFactoryRef = "recentAdEntityManagerFactory",
    transactionManagerRef = "recentAdTransactionManager"
)
@EnableTransactionManagement
public class RecentAdJpaConfig {

    @Primary
    @Bean("recentAdDataSource")
    @ConfigurationProperties(prefix = "spring.jpa.recent-ad.hikari")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("recentAdJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa.recent-ad.properties")
    public Properties jpaProperties() {
        return new Properties();
    }

    @Primary
    @Bean("recentAdEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean recentAdEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("recentAdDataSource") DataSource dataSource,
            @Qualifier("recentAdJpaProperties") Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean factoryBean = builder
                .dataSource(dataSource)
                .packages(
                        "com.hopoong.dspmigration.app.core.domain.recent",
                        "com.hopoong.dspmigration.app.core.domain.migration")
                .build();
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties);
        return factoryBean;
    }

    @Primary
    @Bean("recentAdTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("recentAdEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
