package com.example;

import java.util.HashMap;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import com.example.repository.eventMessage.EventMessageDatasourceProperties;
@Configuration
@EnableJpaRepositories(basePackages = "com.example.repository.eventMessage", entityManagerFactoryRef = "eventMessageEntityManager", transactionManagerRef = "eventMessagePlatformTransactionManager")
@EnableConfigurationProperties(EventMessageDatasourceProperties.class)
public class EventMessageConfig {

	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;

	@Autowired
	private EventMessageDatasourceProperties eventMessageDatasourceProperties;

	@Primary
	@Bean(name = "eventMessageDataSource")
	public DataSource eventMessageDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url(eventMessageDatasourceProperties.getUrl());
        dataSourceBuilder.username(eventMessageDatasourceProperties.getUsername());
        dataSourceBuilder.password(eventMessageDatasourceProperties.getPassword());
        return dataSourceBuilder.build();

	}

	@Primary
	@Bean(name = "eventMessageEntityManager")
	public LocalContainerEntityManagerFactoryBean eventMessageEntityManager() throws Throwable {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(eventMessageDataSource());
		entityManager.setJpaVendorAdapter(jpaVendorAdapter);
		entityManager.setPackagesToScan("com.example.domain.eventMessage");
		entityManager.setPersistenceUnitName("eventMessagePersistenceUnit");
		return entityManager;
	}
	
	@Bean(name="eventMessagePlatformTransactionManager")
	public PlatformTransactionManager eventMessagePlatformTransactionManager(@Qualifier("eventMessageEntityManager") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
