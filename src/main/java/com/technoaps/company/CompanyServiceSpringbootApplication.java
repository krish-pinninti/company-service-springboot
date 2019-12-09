package com.technoaps.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableAsync
@EncryptablePropertySource("file:companyservice.yml")
public class CompanyServiceSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyServiceSpringbootApplication.class, args);
	}

}
