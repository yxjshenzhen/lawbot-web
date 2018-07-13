package com.lawbot.reco;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

/**
 * 
 * @author Cloud Lau
 *
 */
@SpringBootApplication
@EnableFeignClients
public class RecoApp {

	public static void main(String[] args) {
		SpringApplication.run(RecoApp.class, args);
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
