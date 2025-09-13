# 🛡️ SafeGuard Parental

## 📱 Control Parental Inteligente y Seguro

SafeGuard Parental es una aplicación avanzada de control parental para Android que supera las funcionalidades de Qustodio con características mejoradas y una interfaz moderna. Desarrollada con las últimas tecnologías de Android y Firebase.

## ✨ Características Principales

### 🎛️ Dashboard Inteligente
- **Resumen en tiempo real** de la actividad de todos los hijos
- **Estadísticas visuales** de tiempo de pantalla y uso de apps
- **Alertas inmediatas** sobre actividades importantes
- **Acciones rápidas** para control inmediato

### 👥 Gestión Avanzada de Hijos
- **Perfiles personalizados** para cada hijo
- **Monitoreo de estado** en tiempo real (online/offline)
- **Configuración individual** de límites y restricciones
- **Historial completo** de actividades

### 📱 Control de Aplicaciones Mejorado
- **Monitoreo en tiempo real** de todas las apps instaladas
- **Bloqueo inteligente** con categorización automática
- **Límites de tiempo flexibles** por app y categoría
- **Estadísticas detalladas** de uso y patrones

### 📍 Ubicación GPS Avanzada
- **Seguimiento preciso** con Google Location Services
- **Zonas seguras configurables** con alertas personalizadas
- **Historial de ubicaciones** con análisis de patrones
- **Alertas inteligentes** de entrada/salida de zonas

### 🌐 Filtros de Contenido Web
- **Bloqueo automático** de contenido inapropiado
- **Búsqueda segura** en todos los navegadores
- **Lista personalizable** de sitios bloqueados/permitidos
- **Categorización inteligente** de contenido

### 📊 Reportes Detallados
- **Reportes automáticos** diarios, semanales y mensuales
- **Análisis de tendencias** y patrones de uso
- **Exportación a PDF/Excel** para revisión offline
- **Gráficos interactivos** con estadísticas avanzadas

### 🔔 Sistema de Notificaciones
- **Alertas en tiempo real** para actividades críticas
- **Notificaciones personalizables** por tipo y prioridad
- **Resúmenes periódicos** de actividad
- **Integración con Firebase Messaging**

## 🚀 Tecnologías Utilizadas

### 📱 Frontend Android
- **Jetpack Compose** - UI moderna y reactiva
- **Material Design 3** - Interfaz intuitiva y accesible
- **Navigation Component** - Navegación fluida
- **ViewModel + LiveData** - Arquitectura MVVM

### ☁️ Backend Firebase
- **Firebase Authentication** - Autenticación segura
- **Cloud Firestore** - Base de datos en tiempo real
- **Cloud Storage** - Almacenamiento de archivos
- **Firebase Analytics** - Análisis de uso
- **Cloud Messaging** - Notificaciones push

### 🛠️ Herramientas y Librerías
- **Room Database** - Cache local eficiente
- **WorkManager** - Tareas en segundo plano
- **Retrofit** - Cliente HTTP robusto
- **Coil** - Carga de imágenes optimizada
- **MPAndroidChart** - Gráficos y estadísticas

## 📋 Requisitos del Sistema

### 📱 Dispositivo Android
- **Android 7.0 (API 24)** o superior
- **2GB RAM** mínimo (4GB recomendado)
- **100MB** espacio de almacenamiento
- **GPS y conexión a internet** requeridos

### 🛠️ Desarrollo
- **Android Studio** 2022.3.1 o superior
- **JDK 11** o superior
- **Gradle 8.0** o superior
- **Android SDK 34**

## 🚀 Instalación Rápida

### Para Usuarios
1. Descarga el APK desde las releases
2. Habilita "Fuentes desconocidas" en Configuración
3. Instala la aplicación
4. Sigue el asistente de configuración
5. Concede los permisos necesarios

### Para Desarrolladores
```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/safeguard-parental.git

# Abrir en Android Studio
# Sincronizar Gradle
# Configurar Firebase (ver GUIA_COMPILACION.md)
# Compilar e instalar
```

## 📚 Documentación Completa

- **[📖 Guía de Compilación](GUIA_COMPILACION.md)** - Instrucciones detalladas paso a paso
- **[⚙️ Configuración Firebase](docs/firebase-setup.md)** - Setup completo de Firebase
- **[🔧 API Reference](docs/api-reference.md)** - Documentación técnica
- **[🐛 Troubleshooting](docs/troubleshooting.md)** - Solución de problemas

## 🏗️ Arquitectura del Proyecto

```
SafeGuardParental/
├── 📱 app/
│   ├── 🎨 src/main/
│   │   ├── 📄 java/com/safeguard/parental/
│   │   │   ├── 🏠 ui/                    # Interfaz de usuario
│   │   │   ├── 📊 data/                  # Repositorios y modelos
│   │   │   ├── 🔧 services/              # Servicios de fondo
│   │   │   ├── 👷 workers/               # Tareas programadas
│   │   │   ├── 📡 receivers/             # Broadcast receivers
│   │   │   └── 🛠️ utils/                 # Utilidades y constantes
│   │   ├── 🎨 res/                       # Recursos (layouts, strings, etc.)
│   │   └── 📋 AndroidManifest.xml        # Configuración de la app
│   ├── 🔧 build.gradle                   # Configuración de build
│   └── 🔥 google-services.json          # Configuración Firebase
├── 📁 gradle/wrapper/                    # Gradle wrapper
├── 🏗️ build.gradle                      # Configuración del proyecto
├── ⚙️ settings.gradle                    # Configuración de módulos
└── 📝 README.md                         # Este archivo
```

## 🔧 Configuración de Permisos

La aplicación requiere los siguientes permisos especiales:

### 🔐 Permisos Críticos
- **Administración del dispositivo** - Para bloquear apps
- **Acceso a estadísticas de uso** - Para monitorear tiempo de pantalla
- **Ubicación en segundo plano** - Para seguimiento GPS
- **Overlay del sistema** - Para mostrar alertas

### 📱 Permisos Estándar
- Internet, estado de red, WiFi
- Notificaciones, vibración
- Contactos, cámara (opcional)
- Arranque automático

## 🆚 Comparación con Qustodio

| Característica | SafeGuard | Qustodio |
|----------------|-----------|----------|
| **Interfaz** | ✅ Material Design 3 | ❌ Interfaz antigua |
| **Tiempo Real** | ✅ Actualizaciones instantáneas | ⚠️ Retraso en sync |
| **Personalización** | ✅ Altamente personalizable | ❌ Opciones limitadas |
| **Reportes** | ✅ Análisis avanzado | ⚠️ Reportes básicos |
| **Ubicación** | ✅ GPS preciso + zonas | ⚠️ Ubicación básica |
| **Precio** | ✅ Código abierto | ❌ Suscripción cara |
| **Privacidad** | ✅ Datos bajo tu control | ❌ Datos en servidores externos |

## 🤝 Contribuir al Proyecto

¡Las contribuciones son bienvenidas! Por favor:

1. **Fork** el repositorio
2. **Crea** una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. **Commit** tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. **Push** a la rama (`git push origin feature/nueva-funcionalidad`)
5. **Crea** un Pull Request

## 📄 Licencia

Este proyecto está bajo la **Licencia MIT**. Ver [LICENSE](LICENSE) para más detalles.

## 🙏 Agradecimientos

- **Material Design Team** por los componentes de UI
- **Firebase Team** por la infraestructura backend
- **Android Jetpack** por las librerías modernas
- **Comunidad Open Source** por las librerías utilizadas

## 📞 Soporte y Contacto

- **🐛 Issues**: [GitHub Issues](https://github.com/tu-usuario/safeguard-parental/issues)
- **💬 Discusiones**: [GitHub Discussions](https://github.com/tu-usuario/safeguard-parental/discussions)
- **📧 Email**: safeguard.parental@gmail.com
- **📱 Telegram**: @SafeGuardParental

## 🔄 Roadmap

### 📅 Próximas Versiones

#### v1.1.0 - Control Avanzado
- [ ] Bloqueo de llamadas y SMS
- [ ] Control de redes sociales mejorado
- [ ] Límites por horarios específicos
- [ ] Modo de estudio/tarea

#### v1.2.0 - IA y Machine Learning
- [ ] Detección automática de contenido inapropiado
- [ ] Predicción de patrones de uso
- [ ] Recomendaciones inteligentes
- [ ] Análisis de comportamiento

#### v1.3.0 - Multiplataforma
- [ ] Versión para iOS
- [ ] Panel web para padres
- [ ] Sincronización entre dispositivos
- [ ] API pública para integraciones

---

## ⭐ Si te gusta el proyecto, ¡dale una estrella!

**SafeGuard Parental** - *Protegiendo familias con tecnología inteligente* 🛡️👨‍👩‍👧‍👦