package com.dustin.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

//@EnableAutoConfiguration(exclude = {WebMvcAutoConfiguration.class })
//@ImportResource(locations = { "classpath:spring-persist-tx.xml", "classpath:spring-web-mvc.xml" })
//@ImportResource("classpath:spring-persist-tx.xml")
@SpringBootApplication
public class Crowdfunding02AdminWebuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Crowdfunding02AdminWebuiApplication.class, args);
	}

}
