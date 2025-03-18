package org.example.adreessmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AdreessManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdreessManagementApplication.class, args);
	}

}
