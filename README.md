# 🎬 Sistema de Alquiler de Películas - Spring Boot

Mi proyecto final de **Spring Boot** para la universidad. Es una aplicación web que maneja un videoclub con clientes, películas y alquileres.

## 📋 ¿Qué hace esta aplicación?

Básicamente es un sistema para manejar un videoclub. Puedes:
- Registrar clientes
- Agregar películas al inventario 
- Crear alquileres de películas
- Ver todo desde una página web bonita

## 🛠️ Tecnologías que usé

### **Backend**
- **Spring Boot** - El framework principal
- **Spring Data JPA** - Para conectar con la base de datos
- **PostgreSQL** - Base de datos (uso Supabase gratis)
- **Maven** - Para manejar las dependencias

### **Frontend**
- **Thymeleaf** - Para generar las páginas HTML
- **Bootstrap** - Para que se vea bonito
- **JavaScript** - Para algunas cosas dinámicas

## ✨ Funcionalidades

### **Gestión de Clientes**
- Agregar nuevos clientes
- Editar información de clientes
- Eliminar clientes
- Validación de email

### **Gestión de Películas**
- Agregar películas al catálogo
- Control de stock (cuántas copias hay)
- Organizar por género
- Editar y eliminar películas

### **Gestión de Alquileres**
- Crear nuevos alquileres
- Agregar varias películas a un alquiler
- Cambiar estado (activo, devuelto, retrasado)
- Editar alquileres existentes
- Calcular total automáticamente

## 🗄️ Base de Datos

Tengo 4 tablas principales:

```
Cliente
├── id_cliente 
├── nombre
└── email

Pelicula
├── id_pelicula
├── titulo
├── genero
└── stock

Alquiler
├── id_alquiler
├── fecha
├── id_cliente (cliente que alquila)
├── total
├── estado
└── detalles (las películas del alquiler)

DetalleAlquiler
├── id
├── id_alquiler (a qué alquiler pertenece)
├── id_pelicula (qué película)
└── cantidad
```

## 📚 Lo que aprendí haciendo este proyecto

### **Spring Boot**
- Cómo configurar un proyecto desde cero
- Usar anotaciones como @Controller, @Service, @Repository
- Configurar la base de datos con application.properties

### **Spring Data JPA**
- Crear repositorios que se conectan solos a la BD
- Hacer relaciones entre tablas (OneToMany, ManyToOne)
- Usar @Transactional para operaciones complejas

### **Thymeleaf**
- Generar HTML dinámico desde el servidor
- Usar formularios que se conectan con Java
- Mostrar listas de datos en tablas

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/cibertec/t2/
│   │   ├── T2Application.java           # Archivo principal
│   │   ├── controller/                  # Controladores (manejan las páginas web)
│   │   │   ├── AlquilerController.java
│   │   │   ├── ClienteController.java
│   │   │   ├── PeliculaController.java
│   │   │   └── HomeController.java
│   │   ├── service/                     # Servicios (lógica de negocio)
│   │   │   ├── AlquilerService.java
│   │   │   ├── ClienteService.java
│   │   │   └── PeliculaService.java
│   │   ├── repository/                  # Repositorios (conexión con BD)
│   │   │   ├── AlquilerRepository.java
│   │   │   ├── ClienteRepository.java
│   │   │   └── PeliculaRepository.java
│   │   └── model/                       # Modelos (las tablas de la BD)
│   │       ├── Alquiler.java
│   │       ├── DetalleAlquiler.java
│   │       ├── Cliente.java
│   │       └── Pelicula.java
│   └── resources/
│       ├── templates/                   # Páginas HTML
│       │   ├── alquileres.html
│       │   ├── clientes.html
│       │   ├── peliculas.html
│       │   └── layout.html
│       └── application.properties       # Configuración
```

## 🚀 Cómo ejecutar el proyecto

### **Lo que necesitas tener instalado:**
- **Java 21** 
- **Maven**
- Un IDE como IntelliJ o VS Code

### **Pasos para ejecutar:**

1. **Clonar el proyecto**
   ```bash
   git clone <url-del-repositorio>
   cd t2
   ```

2. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

3. **Abrir en el navegador**
   - Ir a: `http://localhost:8081`
   - Usar el menú lateral para navegar

## 🌐 Páginas disponibles

- `/` - Página principal (redirige a alquileres)
- `/alquileres` - Gestión de alquileres
- `/clientes` - Gestión de clientes  
- `/peliculas` - Gestión de películas

## 🔧 Problemas que tuve y cómo los solucioné

- **Puerto ocupado**: Cambié de 8080 a 8081 en application.properties
- **Errores de validación**: Agregué spring-boot-starter-validation al pom.xml
- **Problemas con JSON**: Usé @JsonIgnore para evitar referencias circulares
- **Formularios de edición**: Tuve que hacer JavaScript para cargar los datos existentes

## 💭 Cosas que podría mejorar

- Agregar login de usuarios
- Hacer una API REST para móviles
- Agregar búsqueda de películas
- Enviar emails cuando se vence un alquiler

---

*Proyecto hecho para aprender Spring Boot y desarrollo web con Java* 🎓 