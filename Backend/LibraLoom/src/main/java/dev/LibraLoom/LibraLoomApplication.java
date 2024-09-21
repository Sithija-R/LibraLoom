package dev.LibraLoom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.LibraLoom.Models.Library;
import dev.LibraLoom.Services.LibraryService;

@RequestMapping
@SpringBootApplication
@RestController
public class LibraLoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraLoomApplication.class, args);
	}

	 @Bean
    public CommandLineRunner dataInitializer(LibraryService libraryService) {
        return args -> {
           
            if (!libraryService.libraryExists("library01")) {
                
                Library library = new Library();
                library.setId("library01");  
                library.setName("Library");
                library.setLocation("Sri Lanka");

                libraryService.createLibrary(library);
                System.out.println("Library created!");
            }
        };
    }

	@GetMapping("api/root")
	public String hello(){
		return "hello from backend";
	}

}
