package com.selloDeAutor.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCategoria;
    private String descripcion;

    @OneToMany(mappedBy = "categoria",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto>  productos;
}
