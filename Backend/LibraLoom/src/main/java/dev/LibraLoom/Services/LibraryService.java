package dev.LibraLoom.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.LibraLoom.Models.Library;
import dev.LibraLoom.Repositories.LibraryRepo;

@Service
public class LibraryService {
    
    @Autowired
    private LibraryRepo libraryRepo;

     // Create a new library
     public Library createLibrary(Library library) {
        return libraryRepo.save(library);
    }

    // Check if library exists
    public boolean libraryExists(String id) {
        return libraryRepo.existsById(id);
    }
}
