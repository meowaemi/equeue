package com.churilin.equeue;

import org.springframework.boot.SpringApplication;

public class TestEqueueApplication {

	public static void main(String[] args) {
		SpringApplication.from(EqueueApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
