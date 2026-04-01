# Validación de Requerimientos y Mejoras Implementadas

**Proyecto:** Sello de Autor - E-commerce de Café de Especialidad (Desarrollo y Ajustes Finales)

Este documento detalla las sugerencias de retroalimentación recibidas a lo largo del curso, así como la manera en que se han solucionado y aplicado al proyecto para obtener la versión final del portafolio.

## 1. Retroalimentación Recibida

Durante el desarrollo de la aplicación se identificaron los siguientes puntos de mejora:
1. **Ausencia de Vista de Carrito**: El botón "Añadir al carrito" y el controlador mapaban a la ruta `/carrito`, la cual no contaba con la plantilla `carrito.html` correspondiente. Esto desencadenaba un error tipo `TemplateInputException`.
2. **Deficiencias de Estilos y Presentación**: El diseño de las páginas (`catalogo`, `login`, `register`, y paneles de administración) lucía genérico debido a que los estilos CSS eran en línea y se dependía exclusivamente de clases básicas de Bootstrap. Se recomendó aplicar un enfoque estético más "premium", con características modernas para retener al cliente (Hero section, glassmorphism, etc.).
3. **Mantenibilidad CSS**: Se detectó una alta dependencia de etiquetas `<style>` incrustadas directamente en `layout.html`, dificultando la mantenibilidad técnica del frontend.

## 2. Acciones y Mejoras Aplicadas

### Resolución de Funcionalidad Incompleta
- Se generó la plantilla estática `carrito.html` vinculada al controlador. Ahora cuando el cliente hace clic en el enlace del menú principal, navega con seguridad hacia esta interfaz preventiva sin experimentar caídas en la ejecución de la aplicación.
- Se configuró el botón "Añadir al carrito" en el catálogo para generar un vínculo navegable funcional (etiqueta `<a>`) en reemplazo del botón simulado original.

### Refactorización Arquitectónica del Frontend
- Se separó el diseño gráfico incorporando la estructura de diseño preaprobada del frontend original (Módulo 2).
- Múltiples recursos visuales estáticos (`estilos.css` y carpeta `/image/` con *assets* optimizados) fueron copiados al directorio de recursos de Spring Boot `src/main/resources/static/`.

### Mejora de Experiencia de Usuario (UX)
Para entregar un proyecto listo para portafolio laboral, se implementó un diseño unificado y altamente inmersivo:
- **Tema Oscuro & Glassmorphism**: Se introdujo una textura de fondo elegante con un difuminado (*blur*) adaptado a cada componente.
- **Micro-interacciones**: Las tarjetas de productos del catálogo se destacan al pasar el ratón (efectos *hover* de traslación en Y y aumento de sombras) proporcionando naturalidad de uso.
- **Identidad de Marca**: Implementación de una barra superior global moderna (transparente pero dinámica), y tipografías consistentes (Google Fonts Inter), para proyectar la marca *Sello de Autor*.
- **Cobertura de Vistas**: El rediseño dinámico fue aplicado de manera generalizada, impactando positivamente en las vistas de Catálogo, Registro, Iniciar Sesión de Clientes, así como las pasarelas de administración de CRUD de Productos.
