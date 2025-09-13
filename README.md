# ParentGuard Pro - Aplicación de Control Parental para Android

## Descripción
ParentGuard Pro es una aplicación avanzada de control parental para Android que ofrece funcionalidades similares a Qustodio pero con mejoras significativas. La aplicación utiliza Firebase para la sincronización de datos y ofrece un sistema completo de monitoreo, filtrado y control parental.

## Características Principales

### 🔒 Control Parental Avanzado
- **Monitoreo de Aplicaciones**: Rastrea el uso de aplicaciones en tiempo real
- **Filtrado Web**: Bloquea contenido inapropiado en la web
- **Límites de Tiempo**: Establece límites de tiempo para aplicaciones específicas
- **Seguimiento de Ubicación**: Rastrea la ubicación de tus hijos
- **Sistema de Recompensas**: Motiva a tus hijos con recompensas y logros

### 📱 Funcionalidades Técnicas
- **Interfaz Moderna**: Diseño con Jetpack Compose
- **Base de Datos Local**: Room Database para almacenamiento local
- **Sincronización en Tiempo Real**: Firebase para sincronización entre dispositivos
- **Notificaciones Inteligentes**: Sistema de notificaciones personalizable
- **Servicios en Segundo Plano**: Monitoreo continuo sin afectar el rendimiento

## Requisitos del Sistema

### Android Studio
- **Versión**: Android Studio Hedgehog (2023.1.1) o superior
- **SDK**: Android API 24 (Android 7.0) o superior
- **Target SDK**: Android API 34 (Android 14)

### Dispositivo Android
- **Versión**: Android 7.0 (API 24) o superior
- **RAM**: Mínimo 2GB recomendado
- **Almacenamiento**: 100MB de espacio libre

## Instalación y Configuración

### Paso 1: Configurar Android Studio

1. **Descargar Android Studio**
   - Ve a [developer.android.com](https://developer.android.com/studio)
   - Descarga la última versión de Android Studio
   - Instala siguiendo las instrucciones del instalador

2. **Configurar el SDK**
   - Abre Android Studio
   - Ve a `File > Settings > Appearance & Behavior > System Settings > Android SDK`
   - Instala las siguientes versiones del SDK:
     - Android 14 (API 34) - Target SDK
     - Android 7.0 (API 24) - Minimum SDK
     - Android SDK Build-Tools 34.0.0
     - Android SDK Platform-Tools
     - Android SDK Tools

### Paso 2: Configurar Firebase

1. **Crear Proyecto Firebase**
   - Ve a [console.firebase.google.com](https://console.firebase.google.com)
   - Haz clic en "Crear un proyecto"
   - Nombra tu proyecto: "ParentGuard Pro"
   - Habilita Google Analytics (opcional)

2. **Agregar Aplicación Android**
   - En la consola de Firebase, haz clic en "Agregar app"
   - Selecciona el ícono de Android
   - **Nombre del paquete**: `com.parentguard.pro`
   - **Apodo de la app**: ParentGuard Pro
   - **Certificado de firma**: Deja en blanco por ahora

3. **Descargar google-services.json**
   - Descarga el archivo `google-services.json`
   - Colócalo en la carpeta `app/` del proyecto

4. **Habilitar Servicios Firebase**
   - En la consola de Firebase, habilita los siguientes servicios:
     - **Authentication**: Para autenticación de usuarios
     - **Firestore Database**: Para almacenamiento de datos
     - **Cloud Storage**: Para archivos multimedia
     - **Cloud Messaging**: Para notificaciones push
     - **Analytics**: Para estadísticas de uso

### Paso 3: Configurar el Proyecto

1. **Clonar o Descargar el Proyecto**
   ```bash
   git clone [URL_DEL_REPOSITORIO]
   cd parentguard-pro
   ```

2. **Abrir en Android Studio**
   - Abre Android Studio
   - Selecciona "Open an existing project"
   - Navega a la carpeta del proyecto y selecciónala

3. **Sincronizar Gradle**
   - Android Studio detectará automáticamente los archivos de configuración
   - Haz clic en "Sync Now" cuando aparezca la notificación
   - Espera a que se descarguen todas las dependencias

### Paso 4: Configurar Permisos

1. **Permisos del Sistema**
   - La aplicación requiere varios permisos especiales
   - Al ejecutar por primera vez, la app te guiará para configurar:
     - **Estadísticas de Uso**: Ve a Configuración > Aplicaciones > Acceso especial > Acceso a estadísticas de uso
     - **Servicio de Accesibilidad**: Ve a Configuración > Accesibilidad > Servicios instalados
     - **Notificaciones**: Permite notificaciones de la aplicación
     - **Ubicación**: Permite acceso a la ubicación

2. **Configuración Manual**
   - Algunos permisos requieren configuración manual en el dispositivo
   - La aplicación te guiará paso a paso para configurar cada permiso

### Paso 5: Compilar y Ejecutar

1. **Compilar la Aplicación**
   - En Android Studio, haz clic en `Build > Build Bundle(s) / APK(s) > Build APK(s)`
   - Espera a que termine la compilación
   - El APK se generará en `app/build/outputs/apk/debug/`

2. **Instalar en Dispositivo**
   - Conecta tu dispositivo Android al ordenador
   - Habilita "Depuración USB" en las opciones de desarrollador
   - En Android Studio, haz clic en el botón "Run" (▶️)
   - Selecciona tu dispositivo y haz clic en "OK"

3. **Instalar APK Manualmente**
   - Transfiere el archivo APK al dispositivo
   - Habilita "Instalar aplicaciones de orígenes desconocidos"
   - Abre el archivo APK y sigue las instrucciones de instalación

## Configuración Inicial

### Primera Ejecución

1. **Abrir la Aplicación**
   - Busca "ParentGuard Pro" en el launcher
   - Toca el ícono para abrir la aplicación

2. **Configurar Permisos**
   - La aplicación te pedirá varios permisos
   - Acepta todos los permisos necesarios
   - Para permisos especiales, sigue las instrucciones en pantalla

3. **Crear Cuenta**
   - Selecciona "Registrarse"
   - Elige el tipo de usuario: "Padre/Madre" o "Hijo/Hija"
   - Completa el formulario de registro
   - Verifica tu correo electrónico si es necesario

4. **Configurar Familia**
   - Si eres padre/madre, crea un código de familia
   - Comparte el código con tus hijos
   - Si eres hijo/hija, ingresa el código de familia

### Configuración Avanzada

1. **Panel de Control para Padres**
   - Accede al panel de control
   - Configura límites de tiempo para cada hijo
   - Establece filtros web personalizados
   - Configura zonas de ubicación permitidas/restringidas

2. **Configuración para Hijos**
   - Los hijos pueden ver su progreso y recompensas
   - Pueden solicitar extensiones de tiempo
   - Tienen acceso a un botón de pánico para emergencias

## Funcionalidades Detalladas

### Monitoreo de Aplicaciones
- **Tiempo de Uso**: Rastrea cuánto tiempo se usa cada aplicación
- **Categorización**: Clasifica automáticamente las aplicaciones por categoría
- **Bloqueo**: Bloquea aplicaciones específicas o por categoría
- **Reportes**: Genera reportes detallados de uso

### Filtrado Web
- **Listas de Bloqueo**: Bloquea sitios web específicos
- **Filtros por Categoría**: Bloquea categorías de contenido
- **Filtros por Palabras Clave**: Bloquea contenido con palabras específicas
- **Modo de Aprendizaje**: Aprende de los patrones de uso

### Límites de Tiempo
- **Límites Diarios**: Establece límites de tiempo por día
- **Límites por Aplicación**: Límites específicos para cada app
- **Horarios de Uso**: Restringe el uso en ciertos horarios
- **Extensiones**: Permite extensiones de tiempo con aprobación

### Seguimiento de Ubicación
- **Ubicación en Tiempo Real**: Rastrea la ubicación actual
- **Historial de Ubicaciones**: Guarda el historial de movimientos
- **Zonas de Seguridad**: Define zonas permitidas y restringidas
- **Alertas de Ubicación**: Notifica cuando se entra/sale de zonas

### Sistema de Recompensas
- **Puntos**: Sistema de puntos por buen comportamiento
- **Logros**: Desbloquea logros por cumplir objetivos
- **Recompensas**: Canjea puntos por recompensas
- **Metas**: Establece metas personalizadas

## Solución de Problemas

### Problemas Comunes

1. **Error de Compilación**
   - Verifica que todas las dependencias estén sincronizadas
   - Limpia el proyecto: `Build > Clean Project`
   - Reconstruye el proyecto: `Build > Rebuild Project`

2. **Error de Permisos**
   - Verifica que todos los permisos estén configurados correctamente
   - Reinicia la aplicación después de otorgar permisos
   - Verifica la configuración en Configuración > Aplicaciones

3. **Error de Firebase**
   - Verifica que el archivo `google-services.json` esté en la carpeta correcta
   - Verifica que los servicios de Firebase estén habilitados
   - Verifica la conexión a internet

4. **Error de Base de Datos**
   - Verifica que la aplicación tenga permisos de almacenamiento
   - Limpia los datos de la aplicación si es necesario
   - Verifica que haya espacio suficiente en el dispositivo

### Logs y Depuración

1. **Ver Logs en Android Studio**
   - Abre la pestaña "Logcat"
   - Filtra por "ParentGuard" para ver solo los logs de la aplicación
   - Busca errores en rojo o advertencias en amarillo

2. **Modo de Depuración**
   - Habilita el modo de depuración en las opciones de desarrollador
   - Conecta el dispositivo y ejecuta la aplicación
   - Los logs aparecerán en tiempo real en Android Studio

## Personalización

### Temas y Colores
- La aplicación soporta temas claro y oscuro
- Los colores se pueden personalizar en `colors.xml`
- Los estilos se pueden modificar en `themes.xml`

### Configuraciones Avanzadas
- Modifica los límites de tiempo por defecto
- Personaliza las categorías de aplicaciones
- Configura las notificaciones según tus preferencias

## Contribuir

### Reportar Bugs
- Si encuentras un bug, crea un issue en el repositorio
- Incluye pasos para reproducir el problema
- Adjunta logs y capturas de pantalla si es posible

### Sugerir Funcionalidades
- Las sugerencias son bienvenidas
- Crea un issue con la etiqueta "enhancement"
- Describe la funcionalidad que te gustaría ver

### Contribuir al Código
- Fork el repositorio
- Crea una rama para tu funcionalidad
- Envía un pull request con tu contribución

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Contacto

- **Email**: support@parentguardpro.com
- **Website**: https://parentguardpro.com
- **GitHub**: https://github.com/parentguardpro

## Agradecimientos

- Google Firebase por los servicios en la nube
- Jetpack Compose por el framework de UI
- Room Database por el almacenamiento local
- La comunidad de Android por el soporte y contribuciones

---

**Nota**: Esta aplicación está diseñada para uso familiar y educativo. Siempre respeta la privacidad y los derechos de tus hijos. Usa la aplicación de manera responsable y transparente.