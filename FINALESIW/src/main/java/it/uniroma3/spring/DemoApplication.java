package it.uniroma3.spring;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import it.uniroma3.spring.service.StorageService;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
 
	@Resource
	StorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
 
	@Override
	public void run(String... args) throws Exception {
		storageService.deleteAll();
		storageService.init();
	}
}