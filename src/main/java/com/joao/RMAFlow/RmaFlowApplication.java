package com.joao.RMAFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import me.paulschwarz.springdotenv.spring.DotenvApplicationInitializer;

@SpringBootApplication
public class RmaFlowApplication{

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RmaFlowApplication.class);
		app.addInitializers(new DotenvApplicationInitializer());
		app.run(args);
	}

}
