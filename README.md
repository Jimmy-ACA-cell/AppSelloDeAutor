# App Sello de Autor

https://github.com/Jimmy-ACA-cell/AppSelloDeAutor

## Instrucciones de Ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Jimmy-ACA-cell/AppSelloDeAutor
   ```
2. Navega al directorio del proyecto:
   ```bash
   cd AppSelloDeAutor
   ```
3. Construye y ejecuta el proyecto con Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
4. La aplicación estará disponible en `http://localhost:8080`.

## Configuración del Datasource

La aplicación utiliza MySQL con la siguiente configuración en `application.properties`:

- **URL:** `jdbc:mysql://localhost:3306/sello_autor_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`
- **Username:** `root`
- **Password:** 
- **Driver:** `com.mysql.cj.jdbc.Driver`
- **Hibernate DDL Auto:** `update`

Asegúrate de tener una base de datos MySQL en ejecución en el puerto 3306 con el nombre `sello_autor_db` antes de iniciar la aplicación.

## Rutas y Roles

La seguridad de la aplicación está configurada con Spring Security. A continuación, las rutas accesibles según el rol del usuario:

- **Rutas Públicas (No requieren autenticación):**
  - `/`, `/login`, `/register`
  - Archivos estáticos: `/css/**`, `/js/**`, `/img/**`, `/assets/**`, `/webjars/**`

- **Rutas para Administradores (`ROLE_ADMIN`):**
  - `/admin/**` (incluye la gestión de productos, ej. `/admin/products`)

- **Rutas para Usuarios Autenticados (Cualquier rol):**
  - Todas las demás rutas, como el catálogo de productos (`/catalogo`).

Tras iniciar sesión exitosamente, los usuarios con rol `ADMIN` son redirigidos a `/admin/products`, mientras que los demás usuarios son redirigidos a `/catalogo`.
