package dev.LibraLoom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@SpringBootApplication
@RestController
public class LibraLoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraLoomApplication.class, args);
	}

	@GetMapping("api/root")
	public String hello(){
		return "hello from backend";
	}

}
