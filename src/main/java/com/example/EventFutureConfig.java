package com.example;

import java.util.HashMap;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import com.example.repository.eventFuture.EventFutureDatasourceProperties;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.repository.eventFuture", entityManagerFactoryRef = "eventFutureEntityManager", transactionManagerRef = "eventFuturePlatformTransactionManager")
@EnableConfigurationProperties(EventFutureDatasourceProperties.class)
public class EventFutureConfig {

	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;

	@Autowired
	private EventFutureDatasourceProperties eventFutureDatasourceProperties;

	@Bean(name = "eventFutureDataSource")
	public DataSource eventFutureDataSource() {	       
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url(eventFutureDatasourceProperties.getUrl());
        dataSourceBuilder.username(eventFutureDatasourceProperties.getUsername());
        dataSourceBuilder.password(eventFutureDatasourceProperties.getPassword());
        return dataSourceBuilder.build();
	}
	@Bean(name = "eventFutureEntityManager")
	public LocalContainerEntityManagerFactoryBean eventFutureEntityManager() throws Throwable {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(eventFutureDataSource());
		entityManager.setJpaVendorAdapter(jpaVendorAdapter);
		entityManager.setPackagesToScan("com.example.domain.eventFuture");
		entityManager.setPersistenceUnitName("eventFuturePersistenceUnit");
		return entityManager;
	}
	
	@Bean(name="eventFuturePlatformTransactionManager")
	public PlatformTransactionManager eventFuturePlatformTransactionManager(@Qualifier("eventFutureEntityManager") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
