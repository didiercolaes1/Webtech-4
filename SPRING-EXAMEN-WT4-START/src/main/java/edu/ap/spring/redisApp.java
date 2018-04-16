package edu.ap.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.ap.spring.redis.RedisService;

@SpringBootApplication
public class redisApp {

	private RedisService service;

	
	@Autowired
	public void setRedisService(RedisService service) {
		this.service = service;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(redisApp.class, args);
	}

}
