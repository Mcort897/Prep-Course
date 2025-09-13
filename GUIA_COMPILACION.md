# 📱 SafeGuard Parental - Guía de Compilación Paso a Paso

## 🎯 Introducción

Esta guía te ayudará a compilar e instalar la aplicación **SafeGuard Parental** en tu dispositivo Android. La aplicación incluye funciones avanzadas de control parental como monitoreo de aplicaciones, seguimiento GPS, control de tiempo de pantalla y filtros de contenido web.

## 📋 Requisitos Previos

### 🛠️ Software Necesario

1. **Android Studio** (versión 2022.3.1 o superior)
   - Descargar desde: https://developer.android.com/studio
   
2. **Java Development Kit (JDK) 11 o superior**
   - Android Studio incluye un JDK, pero puedes usar uno externo
   
3. **Git** (para clonar el repositorio)
   - Descargar desde: https://git-scm.com/

### 📱 Dispositivo Android

- **Android 7.0 (API 24)** o superior
- **Espacio disponible**: Al menos 50MB
- **Permisos de desarrollador** habilitados

## 🚀 Paso 1: Configuración del Entorno

### 1.1 Instalar Android Studio

1. Descarga Android Studio desde el sitio oficial
2. Ejecuta el instalador y sigue las instrucciones
3. Al abrir por primera vez, instala los componentes recomendados:
   - Android SDK
   - Android SDK Platform-Tools
   - Android Virtual Device (AVD)

### 1.2 Configurar SDK

1. Abre Android Studio
2. Ve a **File > Settings** (o **Android Studio > Preferences** en Mac)
3. Navega a **Appearance & Behavior > System Settings > Android SDK**
4. En la pestaña **SDK Platforms**, instala:
   - Android 14 (API 34)
   - Android 7.0 (API 24)
5. En la pestaña **SDK Tools**, asegúrate de tener instalado:
   - Android SDK Build-Tools
   - Google Play Services
   - Google Repository

## 🔥 Paso 2: Configurar Firebase

### 2.1 Crear Proyecto Firebase

1. Ve a https://console.firebase.google.com/
2. Haz clic en **"Crear un proyecto"**
3. Nombra tu proyecto: `safeguard-parental-app`
4. Habilita Google Analytics (opcional pero recomendado)
5. Completa la configuración

### 2.2 Agregar App Android

1. En la consola de Firebase, haz clic en **"Agregar app"**
2. Selecciona el ícono de Android
3. Configura los detalles:
   - **Nombre del paquete de Android**: `com.safeguard.parental`
   - **Nombre de la app**: `SafeGuard Parental`
   - **Certificado de firma SHA-1**: (opcional para desarrollo)

### 2.3 Descargar google-services.json

1. Descarga el archivo `google-services.json`
2. Reemplaza el archivo existente en `/workspace/app/google-services.json`

### 2.4 Configurar Servicios Firebase

En la consola de Firebase, habilita los siguientes servicios:

#### Authentication
1. Ve a **Authentication > Sign-in method**
2. Habilita **Email/Password**
3. Configura dominios autorizados si es necesario

#### Firestore Database
1. Ve a **Firestore Database**
2. Haz clic en **"Crear base de datos"**
3. Selecciona **"Comenzar en modo de prueba"**
4. Elige una ubicación (preferiblemente cerca de tus usuarios)

#### Cloud Storage
1. Ve a **Storage**
2. Haz clic en **"Comenzar"**
3. Configura las reglas de seguridad básicas

## 📂 Paso 3: Preparar el Código

### 3.1 Obtener el Código Fuente

El código ya está disponible en `/workspace/`. Si necesitas copiarlo a otro lugar:

```bash
# Copiar todo el proyecto a tu directorio deseado
cp -r /workspace/ /ruta/a/tu/directorio/SafeGuardParental/
```

### 3.2 Estructura del Proyecto

```
SafeGuardParental/
├── app/
│   ├── src/main/
│   │   ├── java/com/safeguard/parental/
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── google-services.json
├── gradle/wrapper/
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## 🔧 Paso 4: Compilación en Android Studio

### 4.1 Abrir el Proyecto

1. Abre Android Studio
2. Selecciona **"Open an existing Android Studio project"**
3. Navega a la carpeta del proyecto y selecciónala
4. Espera a que Android Studio indexe el proyecto

### 4.2 Sincronizar Gradle

1. Android Studio debería mostrar una notificación para sincronizar
2. Haz clic en **"Sync Now"**
3. Si no aparece, ve a **File > Sync Project with Gradle Files**
4. Espera a que termine la sincronización (puede tomar varios minutos la primera vez)

### 4.3 Resolver Dependencias

Si hay errores de dependencias:

1. Ve a **File > Project Structure**
2. En **Project**, asegúrate de que:
   - **Gradle Version**: 8.0 o superior
   - **Android Gradle Plugin Version**: 8.2.0
   - **Compile SDK Version**: 34

### 4.4 Verificar Configuración

1. Ve a **Build > Clean Project**
2. Luego **Build > Rebuild Project**
3. Si hay errores, revisa:
   - Que `google-services.json` esté en la carpeta correcta
   - Que todas las dependencias estén actualizadas
   - Que el SDK esté instalado correctamente

## 📱 Paso 5: Preparar Dispositivo Android

### 5.1 Habilitar Opciones de Desarrollador

1. Ve a **Configuración > Acerca del teléfono**
2. Toca **"Número de compilación"** 7 veces
3. Aparecerá un mensaje confirmando que eres desarrollador

### 5.2 Habilitar Depuración USB

1. Ve a **Configuración > Opciones de desarrollador**
2. Habilita **"Depuración USB"**
3. Habilita **"Instalar apps vía USB"** (si está disponible)

### 5.3 Conectar Dispositivo

1. Conecta tu dispositivo Android via USB
2. En el dispositivo, autoriza la depuración USB cuando aparezca el diálogo
3. En Android Studio, deberías ver tu dispositivo en la lista de dispositivos

## 🚀 Paso 6: Compilar e Instalar

### 6.1 Compilación Debug (Para Desarrollo)

1. En Android Studio, selecciona tu dispositivo en la lista desplegable
2. Haz clic en el botón **"Run"** (▶️) o presiona **Shift + F10**
3. Android Studio compilará e instalará automáticamente la app
4. La app se abrirá automáticamente en tu dispositivo

### 6.2 Compilación Release (Para Distribución)

#### 6.2.1 Generar Keystore (Primera vez)

```bash
# En la terminal de Android Studio
keytool -genkey -v -keystore safeguard-release-key.keystore -alias safeguard -keyalg RSA -keysize 2048 -validity 10000
```

Guarda el archivo keystore en un lugar seguro y recuerda la contraseña.

#### 6.2.2 Configurar Signing

1. Ve a **Build > Generate Signed Bundle / APK**
2. Selecciona **APK**
3. Crea o selecciona tu keystore
4. Completa la información de firma
5. Selecciona **release** como build type
6. Haz clic en **Finish**

### 6.3 Instalación Manual

Si prefieres instalar manualmente el APK:

```bash
# Usando ADB (Android Debug Bridge)
adb install app-release.apk
```

## ⚙️ Paso 7: Configuración Post-Instalación

### 7.1 Permisos Necesarios

Al abrir la app por primera vez, necesitarás conceder los siguientes permisos:

1. **Administración del dispositivo**
   - Ve a Configuración > Seguridad > Administradores del dispositivo
   - Habilita SafeGuard Parental

2. **Acceso a estadísticas de uso**
   - Ve a Configuración > Apps > Acceso especial > Acceso a datos de uso
   - Habilita SafeGuard Parental

3. **Ubicación**
   - Permite acceso a ubicación precisa y aproximada
   - Permite en segundo plano

4. **Notificaciones**
   - Permite todas las categorías de notificaciones

### 7.2 Configuración Firebase (Producción)

Para uso en producción, actualiza las reglas de Firebase:

#### Firestore Rules
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Usuarios solo pueden acceder a sus propios datos
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Padres pueden acceder a datos de sus hijos
    match /children/{childId} {
      allow read, write: if request.auth != null && 
        resource.data.parentId == request.auth.uid;
    }
    
    // Otras colecciones con reglas similares
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

#### Storage Rules
```javascript
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    match /{allPaths=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

## 🐛 Solución de Problemas Comunes

### Error: "google-services.json not found"
- Asegúrate de que el archivo esté en `app/google-services.json`
- Verifica que el nombre del paquete coincida

### Error: "SDK not found"
- Ve a SDK Manager y descarga las versiones necesarias
- Actualiza las rutas en local.properties

### Error: "Build failed"
- Limpia el proyecto: **Build > Clean Project**
- Invalida caché: **File > Invalidate Caches and Restart**

### App se cierra inmediatamente
- Revisa los logs en **Logcat**
- Verifica que todos los permisos estén concedidos
- Asegúrate de que Firebase esté configurado correctamente

### Problemas de permisos
- Algunos permisos requieren configuración manual en Configuración del sistema
- Revisa que el dispositivo tenga Android 7.0 o superior

## 📊 Funcionalidades Principales

Una vez instalada, la app incluye:

### 🎛️ Dashboard
- Resumen de actividad de todos los hijos
- Estadísticas de tiempo de pantalla
- Alertas recientes
- Acciones rápidas

### 👥 Gestión de Hijos
- Agregar/editar perfiles de hijos
- Ver estado en tiempo real
- Configurar límites individuales

### 📱 Control de Apps
- Ver apps instaladas
- Bloquear/desbloquear aplicaciones
- Establecer límites de tiempo
- Categorización automática

### 📍 Ubicación GPS
- Seguimiento en tiempo real
- Zonas seguras configurables
- Historial de ubicaciones
- Alertas de entrada/salida

### 📊 Reportes
- Reportes diarios, semanales y mensuales
- Estadísticas detalladas de uso
- Exportación a PDF/Excel
- Análisis de patrones

### ⚙️ Configuración
- Personalización de notificaciones
- Configuración de privacidad
- Gestión de cuenta
- Preferencias del sistema

## 🔒 Consideraciones de Seguridad

### Para Desarrollo
- Usa el modo debug solo para pruebas
- No distribuyas APKs debug
- Mantén las credenciales Firebase seguras

### Para Producción
- Usa certificados de firma seguros
- Configura reglas Firebase restrictivas
- Implementa autenticación robusta
- Monitorea el uso de la API

## 📞 Soporte y Ayuda

Si encuentras problemas durante la compilación:

1. **Revisa los logs** en Android Studio
2. **Verifica la configuración** de Firebase
3. **Asegúrate** de que todos los permisos estén concedidos
4. **Consulta la documentación** de Android y Firebase

## 🎉 ¡Felicitaciones!

Si has seguido todos los pasos correctamente, ahora tienes SafeGuard Parental funcionando en tu dispositivo Android. La aplicación está lista para proteger a tu familia con tecnología avanzada de control parental.

---

**Nota**: Esta aplicación está diseñada para fines educativos y de demostración. Para uso comercial, asegúrate de cumplir con todas las regulaciones locales de privacidad y protección de datos.