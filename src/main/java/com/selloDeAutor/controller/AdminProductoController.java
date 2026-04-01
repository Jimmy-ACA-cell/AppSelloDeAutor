package com.selloDeAutor.controller;

import com.selloDeAutor.model.Categoria;
import com.selloDeAutor.model.Producto;
import com.selloDeAutor.service.CategoriaService;
import com.selloDeAutor.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductoController {
    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public AdminProductoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listProducts(Model model) {
        List<Producto> productos = productoService.findAll();
        model.addAttribute("productos", productos);
        return "admin/productos_list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.findAll());
        return "admin/producto_form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("producto") Producto producto,
                         BindingResult result,
                         @RequestParam("categoria_id") Long categoriaId,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.findAll());
            return "admin/producto_form";
        }
        Categoria categoria = categoriaService.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Id de categoría inválido"));
        producto.setCategoria(categoria);
        productoService.save(producto);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Producto producto = productoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido"));
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.findAll());
        return "admin/producto_form";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("producto") Producto productoForm,
                                BindingResult result,
                                @RequestParam("categoria_id") Long categoriaId,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.findAll());
            return "admin/producto_form";
        }
        Producto productoDto = productoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido"));

        Categoria categoria = categoriaService.findById(categoriaId)
                        .orElseThrow(() -> new IllegalArgumentException("Id de categoría inválido"));

        productoDto.setCategoria(categoria);
        productoDto.setCodigo(productoForm.getCodigo());
        productoDto.setNombreProducto(productoForm.getNombreProducto());
        productoDto.setDescripcion(productoForm.getDescripcion());
        productoDto.setPrecio(productoForm.getPrecio());
        productoDto.setStock(productoForm.getStock());
        productoDto.setImagenUrl(productoForm.getImagenUrl());
        productoDto.setIsActive(productoForm.getIsActive());

        productoService.save(productoDto);
        return "redirect:/admin/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        productoService.delete(id);
        return "redirect:/admin/products";
    }
}
