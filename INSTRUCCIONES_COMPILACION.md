# 📱 Instrucciones de Compilación - Parental Control Pro

## 🚀 Pasos para Compilar en Android Studio

### 1. Preparación del Entorno

#### Requisitos Mínimos:
- **Android Studio**: Arctic Fox (2020.3.1) o superior
- **Android SDK**: API 24+ (Android 7.0)
- **Java**: JDK 8 o superior
- **RAM**: Mínimo 8GB recomendado
- **Espacio**: 10GB libres en disco

#### Verificar Instalación:
```bash
# Verificar Java
java -version

# Verificar Android SDK
sdkmanager --list
```

### 2. Configuración de Firebase

#### Paso 1: Crear Proyecto Firebase
1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Haz clic en "Crear un proyecto"
3. Nombre: `Parental Control Pro`
4. Habilita Google Analytics (recomendado)

#### Paso 2: Configurar Aplicación Android
1. En Firebase Console, haz clic en "Agregar app" → Android
2. **Package name**: `com.parentalcontrolpro`
3. **App nickname**: `Parental Control Pro`
4. **SHA-1**: (opcional para desarrollo)
5. Descarga el archivo `google-services.json`

#### Paso 3: Reemplazar Archivo de Configuración
1. Copia el archivo `google-services.json` descargado
2. Pégalo en: `app/google-services.json`
3. **IMPORTANTE**: Reemplaza el archivo de ejemplo

### 3. Configuración en Android Studio

#### Paso 1: Abrir Proyecto
1. Inicia Android Studio
2. Selecciona "Open an existing project"
3. Navega a la carpeta del proyecto
4. Selecciona la carpeta raíz (donde está `build.gradle`)

#### Paso 2: Sincronizar Gradle
1. Android Studio detectará automáticamente el proyecto
2. Aparecerá una notificación "Gradle sync needed"
3. Haz clic en "Sync Now" o presiona `Ctrl+Shift+O`
4. Espera a que termine la sincronización (puede tomar 5-10 minutos)

#### Paso 3: Verificar Configuración
1. Ve a `File` → `Project Structure`
2. Verifica que el SDK esté configurado correctamente
3. Asegúrate de que todas las dependencias estén resueltas

### 4. Configuración del Dispositivo

#### Opción A: Dispositivo Físico
1. **Habilitar Depuración USB**:
   - Ve a `Configuración` → `Acerca del teléfono`
   - Toca 7 veces en "Número de compilación"
   - Ve a `Configuración` → `Opciones de desarrollador`
   - Habilita "Depuración USB"

2. **Conectar Dispositivo**:
   - Conecta el dispositivo via USB
   - Autoriza la depuración cuando aparezca el diálogo
   - Verifica que aparezca en Android Studio

#### Opción B: Emulador
1. Ve a `Tools` → `AVD Manager`
2. Haz clic en "Create Virtual Device"
3. Selecciona un dispositivo (ej: Pixel 4)
4. Descarga una imagen del sistema (API 24+)
5. Configura el AVD y haz clic en "Finish"

### 5. Compilación y Ejecución

#### Paso 1: Limpiar Proyecto
```bash
# En Android Studio:
Build → Clean Project
```

#### Paso 2: Reconstruir Proyecto
```bash
# En Android Studio:
Build → Rebuild Project
```

#### Paso 3: Ejecutar Aplicación
1. Selecciona tu dispositivo en la barra de herramientas
2. Haz clic en el botón "Run" (▶️) o presiona `Shift+F10`
3. La aplicación se compilará e instalará automáticamente

### 6. Configuración de Permisos

#### Permisos Automáticos:
La aplicación solicitará automáticamente:
- Ubicación
- Notificaciones
- Almacenamiento

#### Permisos Manuales (Importante):
1. **Estadísticas de Uso**:
   - Ve a `Configuración` → `Seguridad` → `Acceso a estadísticas de uso`
   - Busca "Parental Control Pro"
   - Habilita el acceso

2. **Accesibilidad**:
   - Ve a `Configuración` → `Accesibilidad`
   - Busca "Parental Control Pro"
   - Habilita el servicio

3. **Superposición de Pantalla**:
   - Ve a `Configuración` → `Aplicaciones` → `Acceso especial`
   - Busca "Superposición de pantalla"
   - Habilita para "Parental Control Pro"

### 7. Solución de Problemas Comunes

#### Error: "Gradle sync failed"
```bash
# Solución:
1. File → Invalidate Caches and Restart
2. Build → Clean Project
3. Build → Rebuild Project
```

#### Error: "Firebase not initialized"
```bash
# Verificar:
1. Archivo google-services.json en app/
2. Plugin de Google Services en build.gradle
3. Sincronización de Gradle
```

#### Error: "Permission denied"
```bash
# Verificar:
1. Permisos en AndroidManifest.xml
2. Permisos otorgados en el dispositivo
3. Reiniciar la aplicación
```

#### Error: "App not installed"
```bash
# Solución:
1. Desinstalar versión anterior
2. Limpiar caché de Android Studio
3. Recompilar la aplicación
```

### 8. Generación de APK

#### APK de Debug:
1. `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
2. El APK se genera en: `app/build/outputs/apk/debug/`

#### APK de Release:
1. `Build` → `Generate Signed Bundle / APK`
2. Selecciona "APK"
3. Crea un keystore o usa uno existente
4. Configura la firma
5. Selecciona "release" build variant
6. Genera el APK

### 9. Verificación Final

#### Checklist de Verificación:
- [ ] Aplicación se instala correctamente
- [ ] Se abre sin errores
- [ ] Firebase se conecta correctamente
- [ ] Permisos se solicitan apropiadamente
- [ ] Navegación funciona entre pantallas
- [ ] Datos se guardan en Firebase

#### Pruebas Recomendadas:
1. **Registro de Usuario**: Crear cuenta de padre e hijo
2. **Autenticación**: Iniciar sesión y cerrar sesión
3. **Navegación**: Probar todas las pantallas
4. **Permisos**: Verificar que se soliciten correctamente
5. **Firebase**: Confirmar que los datos se guarden

### 10. Distribución

#### Para Pruebas:
1. Comparte el APK de debug
2. Instala en dispositivos de prueba
3. Verifica funcionalidad en diferentes dispositivos

#### Para Producción:
1. Genera APK firmado de release
2. Sube a Google Play Console
3. Configura metadatos de la aplicación
4. Envía para revisión

## 🆘 Soporte Adicional

### Recursos Útiles:
- [Documentación de Android Studio](https://developer.android.com/studio)
- [Guía de Firebase](https://firebase.google.com/docs)
- [Material Design](https://material.io/design)

### Contacto:
- Email: soporte@parentalcontrolpro.com
- GitHub Issues: [Crear issue](https://github.com/usuario/parental-control-pro/issues)

---

**¡Felicitaciones!** 🎉 Has compilado exitosamente Parental Control Pro. La aplicación está lista para usar y personalizar según tus necesidades.