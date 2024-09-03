package dev.LibraLoom.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.LibraLoom.Repositories.LibrarianRepo;

@Service
public class LibrarianService {
   
    @Autowired
    private LibrarianRepo librarianRepo;
}
