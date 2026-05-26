# Ecommerce USB — Frontend

Panel de gestión para el microservicio ecommerce de la Universidad de San Buenaventura.

## Requisitos

- Node.js 18+
- El microservicio Spring Boot corriendo en `http://localhost:8080`

## Instalación y uso

```bash
npm install
npm start
```

Abre `http://localhost:3000` en tu navegador.

## Configurar CORS en Spring Boot

Agrega esta configuración en tu proyecto Spring Boot para permitir peticiones desde el frontend:

```java
package co.edu.usbcali.ecommerceusb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
```

## Funcionalidades

- Ver todos los registros de cada entidad
- Buscar por ID exacto o filtrar en tabla
- Crear nuevos registros con formulario validado
- Editar registros existentes
- Eliminar con confirmación
- Notificaciones de éxito y error
- Link directo a Swagger UI

## Entidades disponibles

| Entidad             | GET all | GET id | POST | PUT | DELETE |
|---------------------|---------|--------|------|-----|--------|
| document-type       | ✅      | ✅     | ✅   | ✅  | ✅     |
| user                | ✅      | ✅     | ✅   | ✅  | ✅     |
| category            | ✅      | ✅     | ✅   | ✅  | ✅     |
| product             | ✅      | ✅     | ✅   | ✅  | ✅     |
| product-category    | ✅      | ✅     | ✅   | —   | ✅     |
| inventory           | ✅      | ✅     | ✅   | ✅  | ✅     |
| inventory-movement  | ✅      | ✅     | ✅   | ✅  | ✅     |
| cart                | ✅      | ✅     | ✅   | ✅  | ✅     |
| cart-item           | ✅      | ✅     | ✅   | ✅  | ✅     |
| order               | ✅      | ✅     | ✅   | ✅  | ✅     |
| order-item          | ✅      | ✅     | ✅   | ✅  | ✅     |
| payment             | ✅      | ✅     | ✅   | ✅  | ✅     |
