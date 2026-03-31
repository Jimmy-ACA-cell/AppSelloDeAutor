package com.selloDeAutor.controller;

import com.selloDeAutor.model.Producto;
import com.selloDeAutor.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CatalogoController {
    private final ProductoService productoService;

    public CatalogoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/catalogo";
    }

    @GetMapping("/catalogo")
    public String catalogo(Model model){
        List<Producto> productos = productoService.findActive();
        model.addAttribute("productos", productos);
        return "catalogo";
    }

    @GetMapping("/carrito")
    public String carrito(){
        return "carrito";
    }
}
