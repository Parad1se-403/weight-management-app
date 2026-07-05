package com.example.weightmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.weightmanagement.mapper")
public class WeightManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeightManagementAppApplication.class, args);
	}

}
