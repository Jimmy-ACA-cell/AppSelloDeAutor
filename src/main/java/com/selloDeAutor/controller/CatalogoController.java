package com.selloDeAutor.controller;

import com.selloDeAutor.model.Producto;
import com.selloDeAutor.service.CategoriaService;
import com.selloDeAutor.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CatalogoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public CatalogoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/catalogo";
    }

    @GetMapping("/catalogo")
    public String catalogo(@RequestParam(value = "categoriaId", required = false) Long categoriaId,
                           @RequestParam(value = "q", required = false) String q,
                           Model model) {

        List<Producto> productos;

        boolean hasQuery = q != null && !q.isBlank();
        boolean hasCategoria = categoriaId != null;

        if (hasQuery && hasCategoria) {
            productos = productoService.searchByCategoria(q, categoriaId);
        } else if (hasQuery) {
            productos = productoService.search(q);
        } else if (hasCategoria) {
            productos = productoService.findByCategoria(categoriaId);
        } else {
            productos = productoService.findActive();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaService.findAll());
        model.addAttribute("categoriaSeleccionada", categoriaId);
        model.addAttribute("q", q);
        return "catalogo";
    }

    @GetMapping("/carrito")
    public String carrito() {
        return "carrito";
    }
}

