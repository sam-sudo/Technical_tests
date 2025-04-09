# CASFID Challenge - Android Developer (Java)

Este repositorio contiene la solución a la prueba técnica para el puesto de Android Developer en Java.

## 📋 Descripción

La aplicación consume datos de la API pública [Random User](https://randomuser.me/) para mostrar una lista de 100 usuarios. Cada uno puede visualizarse con toda su información asociada y añadirse a la agenda de contactos del dispositivo.

Adicionalmente, se ha implementado la funcionalidad de generar un código QR con la información del contacto, que puede ser escaneado para guardarlo.

## 🚀 Funcionalidades

- 📥 Consumo de API REST (Retrofit)
- 📄 Lista de 100 usuarios
- 📱 Detalle completo del usuario
- ➕ Opción para guardar contacto en el teléfono
- 📷 Generación y escaneo de códigos QR
- 📦 Almacenamiento local opcional
- 👓 UI amigable y adaptada a Material Design

## 🧱 Estructura del proyecto

```bash
📦 app
 ┗ 📂 src
    ┗ 📂 main
       ┣ 📂 java
       ┃  ┗ 📂 com
       ┃     ┗ 📂 ck
       ┃        ┗ 📂 casfid_challenge
       ┃           ┣ 📂 data              # Acceso a datos (API, modelos remotos/locales)
       ┃           ┣ 📂 domain            # Casos de uso y entidades del dominio
       ┃           ┣ 📂 infrastructure    # Repositorios y servicios concretos
       ┃           ┣ 📂 presentation      # Actividades, fragmentos y lógica de UI
       ┃           ┗ 📜 MainActivity.java
       ┗ 📂 res
```

## 📸 Capturas de pantalla

<p align="center">
  <img src="./casfid_challenge/screenshots/user_list.jpg" alt="Lista de usuarios" width="200"/>
  <img src="./casfid_challenge/screenshots/user_detail.jpg" alt="Detalle del usuario" width="200"/>
  <img src="./casfid_challenge/screenshots/qr_code.jpg" alt="Código QR generado" width="200"/>
</p>
<p align="center">
  <img src="./casfid_challenge/screenshots/qr_scanner.jpg" alt="Escáner QR" width="200"/>
  <img src="./casfid_challenge/screenshots/save_contact.jpg" alt="Guardar contacto" width="200"/>
</p>

## 🛠 Tecnologías usadas

- Java
- Android SDK
- Retrofit
- Gson
- ZXing
- MVVM
- ViewModel + LiveData

## ✅ Requisitos

- Desarrollado en Java
- Compatible con Android 5.0 (API 21) o superior
- APK compilada incluida en el envío

## 📦 Instalación

Clona este repositorio y ábrelo con Android Studio:

```bash
git clone https://github.com/tu_usuario/tu_repositorio.git
```

Luego compílalo y ejecútalo en un emulador o dispositivo.

## ✍️ Notas personales

El enunciado permite tomar decisiones a nivel de diseño y estructura. En este proyecto me he enfocado en una UI clara, código limpio y modular, y una buena experiencia de usuario, incluyendo accesibilidad y escaneabilidad del QR.

## 🕒 Tiempo estimado de desarrollo

La prueba fue completada en menos de 3 días como se solicitó.

---

¡Gracias por revisar este proyecto!
