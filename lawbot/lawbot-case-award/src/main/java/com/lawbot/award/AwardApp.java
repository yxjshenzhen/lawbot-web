package com.lawbot.award;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

/**
 * 
 * @author Cloud Lau
 *
 */
@SpringBootApplication
public class AwardApp {

	public static void main(String[] args) {
		
		SpringApplication.run(AwardApp.class, args);
	}
	
	

	/**
	 * Session Data Source
	 * @return
	 */
	@Bean("dataSource")
	@Primary
	@ConfigurationProperties("spring.datasource.session")
	public DataSource dataSource(){
	    return DruidDataSourceBuilder.create().build();
	}

}
