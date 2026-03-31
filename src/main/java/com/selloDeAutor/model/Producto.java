package com.selloDeAutor.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    private String codigo;
    private String nombreProducto;
    private String descripcion;
    private double precio;
    private int stock;
    private String imagenUrl;
    private Boolean isActive = true;

}
