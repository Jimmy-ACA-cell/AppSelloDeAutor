package com.selloDeAutor.repository;

import com.selloDeAutor.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByIsActiveTrue();

    List<Producto> findByIsActiveTrueAndCategoria_Id(Long categoriaId);

    @Query("SELECT p FROM Producto p WHERE p.isActive = true " +
           "AND LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Producto> searchByNombre(@Param("q") String q);

    @Query("SELECT p FROM Producto p WHERE p.isActive = true " +
           "AND p.categoria.id = :categoriaId " +
           "AND LOWER(p.nombreProducto) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Producto> searchByNombreAndCategoria(@Param("q") String q, @Param("categoriaId") Long categoriaId);
}
