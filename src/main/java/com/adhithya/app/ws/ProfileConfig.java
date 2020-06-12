package com.adhithya.app.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Bean
	@Profile("dev")
	public CommandLineRunner loadData() {
		
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				
				logger.info("This is profile based commandline runner");
				
			}
		};
	}
	
	@Bean
	@Profile("prod")
	public CommandLineRunner loadDataForProd() {
		
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				
				logger.info("This is profile based commandline runner for prod");
				
			}
		};
	}
}
