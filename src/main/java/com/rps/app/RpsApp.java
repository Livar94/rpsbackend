package com.rps.app;

import com.rps.app.player.SpelareRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpsApp implements CommandLineRunner {
	@Autowired
	SpelareRep playerRepository;
	public static void main(String[] args) {
		SpringApplication.run(RpsApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
