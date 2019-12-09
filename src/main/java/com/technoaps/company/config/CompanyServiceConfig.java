package com.technoaps.company.config;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.client.RestTemplate;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@EnableAutoConfiguration
@Configuration
@Slf4j
public class CompanyServiceConfig {
	
	@Value("${spring.datasource.driver-class-name}")
	private String driver;
	
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String userName;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.jooq.sql-dialect}")
	private String dialect;
	
	@Value("${spring.jpa.show-sql}")
	private String showSql;
	
	
	@Bean(name = "technoapsDataSource")
	@Primary
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setJdbcUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		
		return dataSource;
	}
	
	@Bean(name = "encryptorBean")
	public static StringEncryptor stringEncryptor(@Value("${jasypt.encryptor.password}") String password) {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(password);
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("base64");
		encryptor.setConfig(config);
		return encryptor;
	}
	
	@Bean(name = "technoapsEntityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("technoapsDataSource") DataSource datasource) {
		
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(datasource);
		entityManagerFactory.setPackagesToScan("com.technoaps.products.model");
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
		
		//hibernate properties
		Properties props = new Properties();
		props.put("hibernate.dialect", dialect);
		props.put("hibernate.show_sql", showSql);
		entityManagerFactory.setJpaProperties(props);
		
		return entityManagerFactory;
	}
	
	@Bean(name = "technoapsEntityManager")
	@Primary
	public EntityManager entityManager(@Qualifier("technoapsDataSource") DataSource datasource) {
		EntityManagerFactory entityManagerFactory = entityManagerFactory(datasource).getNativeEntityManagerFactory();
		return entityManagerFactory.createEntityManager();
	}
	
	@Bean(name = "technoapsTransactionManager")
	@Primary
	public JpaTransactionManager transactionManager(@Qualifier("technoapsEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory,
			@Qualifier("technoapsDataSource") DataSource dataSource) {
	
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
		transactionManager.setDataSource(dataSource);
		return transactionManager;
		
	}
	
	
	
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate restTemplate = builder.build();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		return restTemplate;
	}
	
//	@Bean
//	@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
//	public AccessToken getAccessToken() {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//		return ((KeycloakPrincipal) request.getUserPrincipal()).getKeycloakSecurityContext().getToken();
//		
//	}

}
