# 🔥 Configuración Firebase para SafeGuard Parental

## 📋 Guía Completa de Configuración

Esta guía te ayudará a configurar Firebase correctamente para que SafeGuard Parental funcione perfectamente.

## 🚀 Paso 1: Crear Proyecto Firebase

### 1.1 Acceder a Firebase Console
1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Inicia sesión con tu cuenta de Google
3. Haz clic en **"Crear un proyecto"**

### 1.2 Configurar Proyecto
1. **Nombre del proyecto**: `safeguard-parental-app` (o el que prefieras)
2. **Habilitar Google Analytics**: ✅ Recomendado
3. **Cuenta de Analytics**: Selecciona o crea una cuenta
4. Haz clic en **"Crear proyecto"**

## 📱 Paso 2: Agregar App Android

### 2.1 Registrar App
1. En la consola de Firebase, haz clic en el ícono de Android
2. Completa la información:
   - **Nombre del paquete**: `com.safeguard.parental`
   - **Nombre de la app**: `SafeGuard Parental`
   - **Certificado SHA-1**: (opcional para desarrollo)

### 2.2 Obtener SHA-1 (Para producción)
```bash
# Para debug keystore
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android

# Para release keystore
keytool -list -v -keystore tu-keystore.keystore -alias tu-alias
```

### 2.3 Descargar google-services.json
1. Descarga el archivo `google-services.json`
2. Colócalo en `/workspace/app/google-services.json`
3. **¡MUY IMPORTANTE!** Reemplaza el archivo existente

## 🔐 Paso 3: Configurar Authentication

### 3.1 Habilitar Authentication
1. En Firebase Console, ve a **Authentication**
2. Haz clic en **"Comenzar"**
3. Ve a la pestaña **"Sign-in method"**

### 3.2 Configurar Proveedores
1. **Email/Password**:
   - Haz clic en "Email/Password"
   - Habilita "Email/Password" ✅
   - Habilita "Email link (passwordless sign-in)" ✅ (opcional)
   - Guarda los cambios

2. **Google Sign-In** (opcional):
   - Haz clic en "Google"
   - Habilita el proveedor ✅
   - Configura el email de soporte
   - Agrega tu SHA-1 si no lo hiciste antes

### 3.3 Configurar Dominios Autorizados
1. Ve a la pestaña **"Settings"**
2. En **"Authorized domains"**, agrega:
   - `localhost` (para desarrollo)
   - Tu dominio personalizado (si tienes)

## 🗄️ Paso 4: Configurar Firestore Database

### 4.1 Crear Base de Datos
1. Ve a **Firestore Database**
2. Haz clic en **"Crear base de datos"**
3. **Modo de seguridad**: Selecciona "Comenzar en modo de prueba"
4. **Ubicación**: Elige la más cercana a tus usuarios (ej: `us-central1`)

### 4.2 Configurar Reglas de Seguridad
Reemplaza las reglas por defecto con:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Reglas para usuarios
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Reglas para niños - solo padres pueden acceder
    match /children/{childId} {
      allow read, write: if request.auth != null && 
        (resource == null || resource.data.parentId == request.auth.uid);
    }
    
    // Reglas para uso de aplicaciones
    match /app_usage/{usageId} {
      allow read, write: if request.auth != null;
      // TODO: Agregar validación de que el usuario es el padre del niño
    }
    
    // Reglas para ubicaciones
    match /locations/{locationId} {
      allow read, write: if request.auth != null;
      // TODO: Agregar validación de que el usuario es el padre del niño
    }
    
    // Reglas para zonas seguras
    match /safe_zones/{zoneId} {
      allow read, write: if request.auth != null && 
        (resource == null || resource.data.parentId == request.auth.uid);
    }
    
    // Reglas para reportes
    match /reports/{reportId} {
      allow read, write: if request.auth != null;
      // TODO: Agregar validación de que el usuario es el padre
    }
    
    // Reglas para alertas de ubicación
    match /location_alerts/{alertId} {
      allow read, write: if request.auth != null && 
        (resource == null || resource.data.parentId == request.auth.uid);
    }
  }
}
```

## 📁 Paso 5: Configurar Cloud Storage

### 5.1 Habilitar Storage
1. Ve a **Storage**
2. Haz clic en **"Comenzar"**
3. **Modo de seguridad**: Selecciona "Comenzar en modo de prueba"
4. **Ubicación**: Usa la misma que Firestore

### 5.2 Configurar Reglas de Storage
```javascript
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    // Reglas para avatares de usuarios
    match /avatars/{userId}/{fileName} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Reglas para reportes exportados
    match /reports/{userId}/{fileName} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Reglas para capturas de pantalla (si se implementa)
    match /screenshots/{userId}/{fileName} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Regla general para usuarios autenticados
    match /{allPaths=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

## 📊 Paso 6: Configurar Analytics (Opcional)

### 6.1 Verificar Configuración
1. Ve a **Analytics**
2. Verifica que esté vinculado correctamente
3. Los eventos se comenzarán a registrar automáticamente

### 6.2 Eventos Personalizados
La app ya incluye eventos como:
- `child_added`
- `app_blocked`
- `safe_zone_created`
- `report_generated`

## 🔔 Paso 7: Configurar Cloud Messaging

### 7.1 Configuración Automática
- Cloud Messaging se configura automáticamente con `google-services.json`
- No requiere configuración adicional para funcionalidad básica

### 7.2 Configuración Avanzada (Opcional)
Si quieres notificaciones desde el servidor:

1. Ve a **Cloud Messaging**
2. Guarda la **Clave del servidor** para uso futuro
3. Configura temas para diferentes tipos de notificaciones

## 🛡️ Paso 8: Configurar Crashlytics

### 8.1 Habilitar Crashlytics
1. Ve a **Crashlytics**
2. Haz clic en **"Configurar Crashlytics"**
3. Sigue las instrucciones (ya está integrado en el código)

### 8.2 Verificar Integración
- Compila la app en modo release
- Los crashes se reportarán automáticamente

## ⚙️ Paso 9: Configuraciones Adicionales

### 9.1 Configurar Índices de Firestore
Para consultas complejas, es posible que necesites crear índices:

```javascript
// Ejemplo de consultas que requieren índices
// children where parentId == "user123" order by createdAt desc
// app_usage where childId == "child123" and date >= "2023-01-01" order by usageTimeMillis desc
```

### 9.2 Configurar Cuotas y Límites
1. Ve a **Usage and billing**
2. Configura alertas de uso
3. Establece límites de presupuesto si es necesario

## 🧪 Paso 10: Probar la Configuración

### 10.1 Verificar Authentication
```kotlin
// En la app, verificar que la autenticación funcione
FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
```

### 10.2 Verificar Firestore
```kotlin
// Probar escritura en Firestore
FirebaseFirestore.getInstance()
    .collection("test")
    .add(mapOf("timestamp" to FieldValue.serverTimestamp()))
```

### 10.3 Verificar Storage
```kotlin
// Probar subida de archivo
FirebaseStorage.getInstance()
    .reference
    .child("test/test.txt")
    .putBytes("Hello Firebase".toByteArray())
```

## 🔧 Configuración para Producción

### 10.1 Reglas de Seguridad Estrictas
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Solo usuarios autenticados
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
    
    // Validaciones específicas por colección
    match /users/{userId} {
      allow read, write: if request.auth != null && 
        request.auth.uid == userId &&
        validateUserData(request.resource.data);
    }
    
    function validateUserData(data) {
      return data.keys().hasAll(['email', 'displayName', 'role']) &&
             data.role in ['PARENT', 'CHILD', 'ADMIN'];
    }
  }
}
```

### 10.2 Configurar Backup
1. Ve a **Firestore > Backup**
2. Programa backups automáticos
3. Configura retención de backups

### 10.3 Monitoreo y Alertas
1. Configura alertas de uso en **Usage and billing**
2. Habilita monitoreo de rendimiento
3. Configura alertas de errores en Crashlytics

## 📱 Configuración Específica por Entorno

### Desarrollo
```json
// google-services.json para desarrollo
{
  "project_info": {
    "project_id": "safeguard-parental-dev"
  }
}
```

### Producción
```json
// google-services.json para producción
{
  "project_info": {
    "project_id": "safeguard-parental-prod"
  }
}
```

## 🚨 Problemas Comunes y Soluciones

### Error: "Default app has not been initialized"
- Verifica que `google-services.json` esté en `app/`
- Asegúrate de que el plugin está aplicado en `app/build.gradle`

### Error: "Permission denied" en Firestore
- Revisa las reglas de seguridad
- Verifica que el usuario esté autenticado

### Error: "Network request failed"
- Verifica la conexión a internet
- Revisa la configuración de red en el emulador

### Notificaciones no llegan
- Verifica que FCM esté configurado
- Revisa los permisos de notificación en el dispositivo

## 🎯 Mejores Prácticas

### Seguridad
- ✅ Nunca hardcodees API keys en el código
- ✅ Usa reglas de seguridad estrictas
- ✅ Valida datos en el cliente y servidor
- ✅ Implementa rate limiting

### Rendimiento
- ✅ Usa índices compuestos para consultas complejas
- ✅ Implementa paginación en listas grandes
- ✅ Usa cache local cuando sea posible
- ✅ Optimiza el tamaño de documentos

### Costos
- ✅ Monitorea el uso regularmente
- ✅ Implementa límites de uso
- ✅ Usa cache para reducir lecturas
- ✅ Optimiza consultas para reducir operaciones

---

## 🎉 ¡Configuración Completa!

Si has seguido todos los pasos, Firebase está configurado correctamente para SafeGuard Parental. La aplicación ahora puede:

- ✅ Autenticar usuarios
- ✅ Almacenar datos en Firestore
- ✅ Subir archivos a Storage
- ✅ Enviar notificaciones
- ✅ Reportar crashes
- ✅ Analizar uso

¡Tu aplicación está lista para proteger familias! 🛡️👨‍👩‍👧‍👦