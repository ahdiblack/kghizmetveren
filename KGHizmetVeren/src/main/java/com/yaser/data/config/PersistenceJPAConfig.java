//package com.yaser.data.config;
//
//import java.util.Properties;
//
//import javax.sql.DataSource;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.ImportResource;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.Database;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//@ImportResource("classpath*:*persistence.xml")
//@ComponentScan({ "com.yaser" })
//@PropertySource("classpath:com/yaser/social_quick/config/application.properties")
//public class PersistenceJPAConfig {
//
//    private static final String BASE_PACKAGE = "com.yaser";
//    
////	@Value("${jdbc.driverClassName}")
//    private String driverClassName="com.mysql.jdbc.Driver";
////    @Value("${jdbc.url}")
//    private String url="jdbc:mysql://localhost:3306/kimegitsem";
////    @Value("${jpa.generateDdl}")
//    boolean jpaGenerateDdl=true;
//
//    // Hibernate specific
////    @Value("${hibernate.dialect}")
//    String hibernateDialect = "org.hibernate.dialect.MySQL5Dialect";
////    @Value("${hibernate.show_sql}")
//    boolean hibernateShowSql=true;
////    @Value("${hibernate.hbm2ddl.auto}")
//    String hibernateHbm2ddlAuto="create";
//
////    @Value("${jdbc.username}")
//    private String jdbcUsername="root";
////    @Value("${jdbc.password}")
//    private String jdbcPassword="";
//
//    public PersistenceJPAConfig() {
//        super();
//    }
//
//    // beans
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
//        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(restDataSource());
//        factoryBean.setPackagesToScan(new String[] { BASE_PACKAGE });
//
//        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter() {
//            {
//                setDatabase( Database.MYSQL );
//                setDatabasePlatform(hibernateDialect);
//                setShowSql(hibernateShowSql);
//                setGenerateDdl(jpaGenerateDdl);
//            }
//        };
//        factoryBean.setJpaVendorAdapter(vendorAdapter);
//
//        factoryBean.setJpaProperties(additionlProperties());
//
//        return factoryBean;
//    }
//
//    @Bean
//    public DataSource restDataSource() {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setUrl(url);
//        dataSource.setUsername(jdbcUsername);
//        dataSource.setPassword(jdbcPassword);
//        return dataSource;
//    }
//
//    @Bean
//    public JpaTransactionManager transactionManager() {
//        final JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
//
//        return transactionManager;
//    }
//
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//
//    //
//    final Properties additionlProperties() {
//        return new Properties() {
//            /**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			{
//                // use this to inject additional properties in the EntityManager
//                setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
//                setProperty("hibernate.ejb.naming_strategy", org.hibernate.cfg.ImprovedNamingStrategy.class.getName());
//            }
//        };
//    }
//
//}