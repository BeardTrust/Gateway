package com.beardtrust.webapp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * This class contains the entry point into the application.
 *
 * @author Matthew Crowell <Matthew.Crowell@Smoothstack.com>
 */
@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
