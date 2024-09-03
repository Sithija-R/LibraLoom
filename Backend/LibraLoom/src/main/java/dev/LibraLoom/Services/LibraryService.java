package dev.LibraLoom.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.LibraLoom.Repositories.LibraryRepo;

@Service
public class LibraryService {
    
    @Autowired
    private LibraryRepo libraryRepo;
}
