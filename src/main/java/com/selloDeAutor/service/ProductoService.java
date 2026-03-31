package com.selloDeAutor.service;


import com.selloDeAutor.model.Producto;
import com.selloDeAutor.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

   private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public List<Producto> findActive(){
        return productoRepository.findByIsActiveTrue();
    }

    public Optional<Producto> findById(Long id){
        return productoRepository.findById(id);
    }

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    public void delete(Long id){
        Optional<Producto> producto = productoRepository.findById(id);
        if(producto.isPresent()){
            producto.get().setIsActive(false);
            productoRepository.save(producto.get());
        }
    }
}
