package com.hytx.jcxfd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@ServletComponentScan 
//因配置多数据源，需在配置文件中配置@MapperScan("com.hytx.jcxfd.dao")
public class ChapterApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ChapterApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return  new RestTemplate();
	}
	
}
