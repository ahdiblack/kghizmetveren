/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yaser.social_quick.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.yaser.app.SessionUtil;
import com.yaser.data.dao.UserDAO;

/**
 * Main configuration class for the application.
 * Turns on @Component scanning, loads externalized application.properties, and sets up the database.
 * @author Keith Donald
 */
@Configuration
@ComponentScan(basePackages = "com.yaser", excludeFilters = { @Filter(Configuration.class) })
@PropertySource("classpath:com/yaser/social_quick/config/application.properties")
@EnableTransactionManagement
public class MainConfig {
	
	
	 private static final String BASE_PACKAGE = "com.yaser";
//		@Value("${jdbc.driverClassName}")
	    private String driverClassName="com.mysql.jdbc.Driver";
//	    @Value("${jdbc.url}")
	    private String url="jdbc:mysql://localhost:3306/kimegitsem";
//	    @Value("${jpa.generateDdl}")
	    boolean jpaGenerateDdl=true;

	    // Hibernate specific
//	    @Value("${hibernate.dialect}")
	    String hibernateDialect = "org.hibernate.dialect.MySQL5Dialect";
//	    @Value("${hibernate.show_sql}")
	    boolean hibernateShowSql=true;
//	    @Value("${hibernate.hbm2ddl.auto}")
	    String hibernateHbm2ddlAuto="update";

//	    @Value("${jdbc.username}")
	    private String jdbcUsername="root";
//	    @Value("${jdbc.password}")
	    private String jdbcPassword="";


	    // beans

		@Bean
		public UserDAO userDAO() {
			return new UserDAO();
		}
		
		@Bean
		@Scope(value="session")
		public SessionUtil sessionUtil() {
			return new SessionUtil();
		}
	    
		@Bean
		public JavaMailSender javaMailSender() {
			
			JavaMailSenderImpl jm = new JavaMailSenderImpl();
			jm.setPort(465);
			jm.setProtocol("smtp");
			jm.setHost("in.mailjet.com");
			jm.setUsername("2ea124ace8d4f2a18530c1074058c989");
			jm.setPassword("3624fde134f2ebd9131be21f7a5c3b85");
			
			Properties p = new Properties();
			p.setProperty("mail.smtp.auth", "true");
			p.setProperty("mail.smtp.starttls.enable", "true");
			
			jm.setJavaMailProperties(p);
			
			return jm;
		}
		
	    @Bean
	    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
	        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
	        factoryBean.setDataSource(restDataSource());
	        factoryBean.setPackagesToScan(new String[] { BASE_PACKAGE });

	        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter() {
	            {
	                setDatabase( Database.MYSQL );
	                setDatabasePlatform(hibernateDialect);
	                setShowSql(hibernateShowSql);
	                setGenerateDdl(jpaGenerateDdl);
	            }
	        };
	        factoryBean.setJpaVendorAdapter(vendorAdapter);

	        factoryBean.setJpaProperties(additionlProperties());

	        return factoryBean;
	    }
	    
	    @Bean
	    public DataSource restDataSource() {
	        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(driverClassName);
	        dataSource.setUrl(url);
	        dataSource.setUsername(jdbcUsername);
	        dataSource.setPassword(jdbcPassword);
	        return dataSource;
	    }
	    
	    @Bean
	    public JpaTransactionManager transactionManager() {
	        final JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

	        return transactionManager;
	    }

	    @Bean
	    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
	        return new PersistenceExceptionTranslationPostProcessor();
	    }
	    

	    //
	    final Properties additionlProperties() {
	        return new Properties() {
	            /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
	                // use this to inject additional properties in the EntityManager
	                setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
	                setProperty("hibernate.ejb.naming_strategy", org.hibernate.cfg.ImprovedNamingStrategy.class.getName());
	            }
	        };
	    }
	
	private DatabasePopulator databasePopulator() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("JdbcUsersConnectionRepository.sql", JdbcUsersConnectionRepository.class));
		return populator;
	}
}
