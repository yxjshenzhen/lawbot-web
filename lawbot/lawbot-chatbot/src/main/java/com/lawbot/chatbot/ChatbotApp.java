package com.lawbot.chatbot;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

/**
 * 
 * @author Cloud Lau
 *
 */
@SpringBootApplication
@EnableZuulProxy
@ServletComponentScan(basePackages = "com.lawbot.chatbot.web.filter")
public class ChatbotApp {

	public static void main(String[] args){
		SpringApplication.run(ChatbotApp.class, args);
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
