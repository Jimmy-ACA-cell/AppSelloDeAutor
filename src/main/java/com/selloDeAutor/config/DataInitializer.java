package com.selloDeAutor.config;

import com.selloDeAutor.model.Categoria;
import com.selloDeAutor.model.Producto;
import com.selloDeAutor.model.Rol;
import com.selloDeAutor.model.Usuario;
import com.selloDeAutor.repository.CategoriaRepository;
import com.selloDeAutor.repository.ProductoRepository;
import com.selloDeAutor.repository.RolRepository;
import com.selloDeAutor.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public DataInitializer(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder,
                           CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear rol de Administrador si no existe
        Optional<Rol> rolAdminOpt = rolRepository.findByNombre("ROLE_ADMIN");
        Rol rolAdmin;
        if (rolAdminOpt.isEmpty()) {
            rolAdmin = Rol.builder().nombre("ROLE_ADMIN").build();
            rolAdmin = rolRepository.save(rolAdmin);
        } else {
            rolAdmin = rolAdminOpt.get();
        }

        // Crear rol de Cliente si no existe
        Optional<Rol> rolClientOpt = rolRepository.findByNombre("ROLE_CLIENT");
        if (rolClientOpt.isEmpty()) {
            Rol rolClient = Rol.builder().nombre("ROLE_CLIENT").build();
            rolRepository.save(rolClient);
        }

        // Crear usuario Administrador inicial si no existe ninguno
        String adminEmail = "admin@sellodeautor.com";
        if (!usuarioRepository.existsByEmail(adminEmail)) {
            Usuario adminUser = Usuario.builder()
                    .nombre("Administrador")
                    .apellido("General")
                    .email(adminEmail)
                    .password(passwordEncoder.encode("admin123"))
                    .build();

            Set<Rol> roles = new HashSet<>();
            roles.add(rolAdmin);
            adminUser.setRoles(roles);

            usuarioRepository.save(adminUser);
            System.out.println("  Usuario Administrador creado exitosamente:");
            System.out.println("  Email:    " + adminEmail);
            System.out.println("  Password: admin123");
        }

        // Crear Categorias si no existen
        if (categoriaRepository.count() == 0) {
            Categoria catMaquinas = Categoria.builder().nombreCategoria("Máquinas").descripcion("Máquinas de café profesionales").build();
            Categoria catMolinos = Categoria.builder().nombreCategoria("Molinos").descripcion("Molinos de café profesionales").build();
            Categoria catTostadoras = Categoria.builder().nombreCategoria("Tostadoras").descripcion("Equipos para tostar café").build();
            Categoria catAccesorios = Categoria.builder().nombreCategoria("Accesorios").descripcion("Accesorios para baristas").build();
            Categoria catCafe = Categoria.builder().nombreCategoria("Café").descripcion("Granos de café de especialidad").build();

            categoriaRepository.saveAll(Arrays.asList(catMaquinas, catMolinos, catTostadoras, catAccesorios, catCafe));

            // Crear Productos de Prueba
            if (productoRepository.count() == 0) {
                Producto p1 = new Producto();
                p1.setCategoria(catMaquinas);
                p1.setCodigo("m1");
                p1.setNombreProducto("Máquina Espresso Pro 2 Grupos");
                p1.setDescripcion("Máquina profesional de 2 grupos, ideal para cafeterías con volumen medio-alto.");
                p1.setPrecio(3499000);
                p1.setStock(5);
                p1.setImagenUrl("/image/maquina1.png");
                p1.setIsActive(true);

                Producto p2 = new Producto();
                p2.setCategoria(catMolinos);
                p2.setCodigo("m2");
                p2.setNombreProducto("Molino Comercial 75mm");
                p2.setDescripcion("Molino con muelas de 75mm para molienda consistente y duradera.");
                p2.setPrecio(499900);
                p2.setStock(10);
                p2.setImagenUrl("/image/grinder1.png");
                p2.setIsActive(true);

                Producto p3 = new Producto();
                p3.setCategoria(catTostadoras);
                p3.setCodigo("t1");
                p3.setNombreProducto("Tostadora 5kg");
                p3.setDescripcion("Tostadora para pequeños lotes, control de perfil y refrigeración rápida.");
                p3.setPrecio(2899900);
                p3.setStock(2);
                p3.setImagenUrl("/image/roaster1.png");
                p3.setIsActive(true);

                Producto p4 = new Producto();
                p4.setCategoria(catAccesorios);
                p4.setCodigo("a1");
                p4.setNombreProducto("Tamper profesional 58mm");
                p4.setDescripcion("Tamper ergonómico con base niveladora y acabado en acero inoxidable.");
                p4.setPrecio(39990);
                p4.setStock(50);
                p4.setImagenUrl("/image/tamper.png");
                p4.setIsActive(true);

                Producto p5 = new Producto();
                p5.setCategoria(catAccesorios);
                p5.setCodigo("a2");
                p5.setNombreProducto("Balanza de precisión 0.1g");
                p5.setDescripcion("Balanza de precisión para pesar granos y dosis.");
                p5.setPrecio(89990);
                p5.setStock(30);
                p5.setImagenUrl("/image/balanza1.png");
                p5.setIsActive(true);

                Producto p6 = new Producto();
                p6.setCategoria(catCafe);
                p6.setCodigo("c1");
                p6.setNombreProducto("Café Especial - Colombia 1kg");
                p6.setDescripcion("Café de especialidad, tueste medio, notas a caramelo y cítricos.");
                p6.setPrecio(24990);
                p6.setStock(100);
                p6.setImagenUrl("/image/cafecolombia1.png");
                p6.setIsActive(true);

                Producto p7 = new Producto();
                p7.setCategoria(catCafe);
                p7.setCodigo("c2");
                p7.setNombreProducto("Café verde Especial - Colombia 1kg");
                p7.setDescripcion("Café verde de especialidad, grano sin tostar, notas a té verde y sutiles toques herbales.");
                p7.setPrecio(24990);
                p7.setStock(100);
                p7.setImagenUrl("/image/cafevcolombia1.png");
                p7.setIsActive(true);

                Producto p8 = new Producto();
                p8.setCategoria(catCafe);
                p8.setCodigo("c3");
                p8.setNombreProducto("Café Especialidad - Sello de autor 1kg");
                p8.setDescripcion("Café de especialidad, tueste oscuro, notas a chocolate amargo y nuez.");
                p8.setPrecio(24990);
                p8.setStock(100);
                p8.setImagenUrl("/image/cafeVerdeSA.png");
                p8.setIsActive(true);

                productoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8));

                System.out.println("  Productos de prueba cargados exitosamente ");
            }
        }
    }
}
