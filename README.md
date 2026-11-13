# Un Finde Tranqui — Aplicación Android en Kotlin

Proyecto académico grupal desarrollado con Kotlin y Jetpack Compose. Permite iniciar sesión y gestionar productos mediante una API REST.

## Funcionalidades

- Pantalla de bienvenida e inicio de sesión.
- Autenticación JWT y cierre de sesión.
- Navegación entre Inicio, Productos y Ajustes.
- Consulta y visualización de productos.
- Registro, edición y eliminación de productos.
- Confirmación antes de eliminar.
- Manejo de estados de carga y mensajes de error.

## Tecnologías utilizadas

- Kotlin y Android Studio
- Jetpack Compose y Material 3
- Navigation Compose y ViewModel
- Retrofit, OkHttp y Gson
- Gradle
- Node.js y Express
- API REST y JWT

## Estructura del proyecto

- `data`: servicios HTTP, modelos y repositorios.
- `domain`: entidades y casos de uso.
- `presentation`: pantallas, navegación y ViewModels.

## Instalación

1. Clonar o descargar el repositorio.
2. Abrir la carpeta Android en Android Studio.
3. Sincronizar Gradle.
4. En la carpeta de la API ejecutar:

   npm install
   npm start

5. Configurar `BASE_URL` en `RetrofitClient.kt`.
6. Ejecutar la aplicación en un emulador o dispositivo Android.

Para conectar un emulador Android a la API local en el puerto 3000, normalmente se utiliza `http://10.0.2.2:3000/`.

## Capturas de pantalla

Pendientes de agregar capturas reales de la aplicación.

## Objetivo

Aplicar conocimientos de desarrollo móvil con Kotlin, programación orientada a objetos, interfaces con Jetpack Compose y consumo de servicios REST.

## Créditos

Proyecto académico desarrollado en equipo.

## Nota

Este proyecto fue desarrollado con fines educativos. La API y sus credenciales de demostración no están preparadas para un entorno de producción.
