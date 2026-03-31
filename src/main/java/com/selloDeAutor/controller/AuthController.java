package com.selloDeAutor.controller;

import com.selloDeAutor.model.Usuario;
import com.selloDeAutor.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("usuario") Usuario usuarioDTO, Model model) {
        try {
            usuarioService.registerNewUserAccount(usuarioDTO);
            return "redirect:/login?success=true";
        }catch (Exception e){
            model.addAttribute("error","Ocurrio un error al agregar usuario");
            return "register";
        }
    }
}
