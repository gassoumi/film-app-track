package com.apps.filmtrackappv2.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.EnableAsync;


@Configuration
@EntityScan("com.apps.filmtrackappv2.domain")
@EnableJpaRepositories("com.apps.filmtrackappv2.repos")
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class DomainConfig {
}
