package com.selloDeAutor.service;

import com.selloDeAutor.model.Categoria;
import com.selloDeAutor.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> findAll() {
        return this.categoriaRepository.findAll();
    }

    public  Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }
}

