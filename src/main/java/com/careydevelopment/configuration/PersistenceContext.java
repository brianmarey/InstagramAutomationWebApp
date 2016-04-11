package com.careydevelopment.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {
        "com.careydevelopment.instagramautomation.jpa"
})
@EnableTransactionManagement
class PersistenceContext {
	   private static final String PROPS_FILE = "/etc/tomcat8/resources/mysql.properties";
		
	   @Bean(name = "dataSource")
	   public DriverManagerDataSource dataSource() {
		   DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		   
		   try {
			 Properties props = new Properties();
			 File file = new File(PROPS_FILE);
			 FileInputStream inStream = new FileInputStream(file);
			 props.load(inStream);
			 
		     driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		     driverManagerDataSource.setUrl(props.getProperty("db.url"));
		     driverManagerDataSource.setUsername(props.getProperty("db.username"));
		     driverManagerDataSource.setPassword(props.getProperty("db.password"));
		   } catch (Exception e) {
			   e.printStackTrace();
			   throw new RuntimeException ("Can't get the data source!");
		   }
		   
		   return driverManagerDataSource;
	   }


	   
	    @Bean
	    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
	        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
	        entityManagerFactoryBean.setDataSource(dataSource);
	        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
	        entityManagerFactoryBean.setPackagesToScan("com.careydevelopment.instagramautomation.jpa.entity");
	 
	        Properties jpaProperties = new Properties();

			 Properties props = new Properties();
			 
			 try {
				 File file = new File(PROPS_FILE);
				 FileInputStream inStream = new FileInputStream(file);
				 props.load(inStream);	        
			 } catch (Exception e) {
				 e.printStackTrace();
				 throw new RuntimeException ("Can't read db properties file!");
			 }
			 
	        //Configures the used database dialect. This allows Hibernate to create SQL
	        //that is optimized for the used database.
	        jpaProperties.put("hibernate.dialect", props.getProperty("hibernate.dialect"));
	 
	        //Specifies the action that is invoked to the database when the Hibernate
	        //SessionFactory is created or closed.
	        jpaProperties.put("hibernate.hbm2ddl.auto", 
	                props.getProperty("hibernate.hbm2ddl.auto")
	        );
	 
	        //Configures the naming strategy that is used when Hibernate creates
	        //new database objects and schema elements
	        jpaProperties.put("hibernate.ejb.naming_strategy", 
	                props.getProperty("hibernate.ejb.naming_strategy")
	        );
	 
	        //If the value of this property is true, Hibernate writes all SQL
	        //statements to the console.
	        jpaProperties.put("hibernate.show_sql", 
	                props.getProperty("hibernate.show_sql")
	        );
	 
	        //If the value of this property is true, Hibernate will format the SQL
	        //that is written to the console.
	        jpaProperties.put("hibernate.format_sql", 
	                props.getProperty("hibernate.format_sql")
	        );
	 
	        entityManagerFactoryBean.setJpaProperties(jpaProperties);
	 
	        return entityManagerFactoryBean;
	    }
	    
	    
	    @Bean
	    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
	        JpaTransactionManager transactionManager = new JpaTransactionManager();
	        transactionManager.setEntityManagerFactory(entityManagerFactory);
	        return transactionManager;
	    }
	    
	    
	    
}
