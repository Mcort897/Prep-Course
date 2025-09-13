# Parental Control Pro - Aplicación de Control Parental Avanzada

Una aplicación de control parental completa para Android con funcionalidades mejoradas, desarrollada con Firebase y Android Studio.

## 🚀 Características Principales

### Para Padres:
- **Dashboard Completo**: Vista general de la actividad de todos los hijos
- **Monitoreo en Tiempo Real**: Seguimiento de aplicaciones, web, ubicación y llamadas
- **Control de Tiempo**: Límites diarios, horarios de estudio y descanso
- **Filtrado de Contenido**: Bloqueo de sitios web inapropiados
- **Geolocalización**: Zonas seguras y de peligro con alertas
- **Reportes Detallados**: Estadísticas diarias, semanales y mensuales
- **Sistema de Alertas**: Notificaciones instantáneas de actividades sospechosas

### Para Hijos:
- **Interfaz Amigable**: Diseño intuitivo y fácil de usar
- **Control de Tiempo**: Visualización del tiempo restante disponible
- **Aplicaciones Permitidas**: Acceso solo a apps autorizadas
- **Sistema de Solicitudes**: Pedir permisos para apps o sitios web
- **Modo de Emergencia**: Acceso rápido a contactos de emergencia

## 🛠️ Tecnologías Utilizadas

- **Android Studio**: IDE principal
- **Kotlin**: Lenguaje de programación
- **Firebase**: Backend y servicios en la nube
  - Authentication
  - Firestore Database
  - Cloud Storage
  - Cloud Messaging
  - Analytics
- **Room Database**: Base de datos local
- **Hilt**: Inyección de dependencias
- **Navigation Component**: Navegación entre pantallas
- **Material Design 3**: UI/UX moderna
- **Google Play Services**: Ubicación y mapas

## 📋 Requisitos del Sistema

- Android Studio Arctic Fox o superior
- Android SDK 24+ (Android 7.0)
- Java 8 o superior
- Cuenta de Google para Firebase
- Dispositivo Android para pruebas

## 🔧 Instalación y Configuración

### 1. Clonar el Proyecto
```bash
git clone [url-del-repositorio]
cd parental-control-pro
```

### 2. Configurar Firebase

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Crea un nuevo proyecto llamado "Parental Control Pro"
3. Agrega una aplicación Android con el package name: `com.parentalcontrolpro`
4. Descarga el archivo `google-services.json`
5. Reemplaza el archivo `google-services.json` en la carpeta `app/`

### 3. Configurar Android Studio

1. Abre Android Studio
2. Selecciona "Open an existing project"
3. Navega a la carpeta del proyecto y ábrela
4. Espera a que Gradle sincronice las dependencias

### 4. Configurar Permisos

La aplicación requiere varios permisos especiales. Asegúrate de:

1. Habilitar "Instalación desde fuentes desconocidas"
2. Otorgar permisos de "Estadísticas de uso"
3. Habilitar "Accesibilidad" para control de aplicaciones
4. Permitir "Superposición de pantalla" para notificaciones

### 5. Compilar y Ejecutar

1. Conecta tu dispositivo Android o inicia un emulador
2. Haz clic en "Run" (▶️) en Android Studio
3. Selecciona tu dispositivo
4. La aplicación se compilará e instalará automáticamente

## 📱 Uso de la Aplicación

### Primera Configuración

1. **Registro de Padre**:
   - Abre la aplicación
   - Selecciona "Crear Cuenta de Padre"
   - Completa el formulario de registro
   - Verifica tu correo electrónico

2. **Registro de Hijo**:
   - En el panel de padre, selecciona "Agregar Hijo"
   - Completa la información del niño
   - Configura límites y restricciones

3. **Configuración de Permisos**:
   - Otorga todos los permisos necesarios
   - Configura las zonas seguras en el mapa
   - Establece horarios y límites de tiempo

### Funcionalidades Principales

#### Panel de Padre:
- **Dashboard**: Vista general de todos los hijos
- **Hijos**: Gestión de cuentas de niños
- **Monitoreo**: Actividad en tiempo real
- **Reportes**: Estadísticas detalladas
- **Configuración**: Ajustes de la aplicación

#### Panel de Hijo:
- **Inicio**: Resumen del tiempo disponible
- **Aplicaciones**: Apps permitidas y bloqueadas
- **Tiempo**: Control de tiempo restante
- **Solicitudes**: Pedir permisos especiales
- **Perfil**: Información personal

## 🔒 Seguridad y Privacidad

- Todos los datos se almacenan de forma segura en Firebase
- Encriptación de datos sensibles
- Cumplimiento con COPPA (Children's Online Privacy Protection Act)
- No se recopila información personal innecesaria
- Los padres tienen control total sobre los datos de sus hijos

## 🐛 Solución de Problemas

### Error de Compilación
- Verifica que todas las dependencias estén sincronizadas
- Limpia el proyecto: Build → Clean Project
- Reconstruye el proyecto: Build → Rebuild Project

### Error de Firebase
- Verifica que el archivo `google-services.json` esté en la ubicación correcta
- Confirma que el package name coincida con Firebase
- Revisa la configuración de Firebase en la consola

### Error de Permisos
- Asegúrate de otorgar todos los permisos necesarios
- Algunos permisos requieren configuración manual en Ajustes del sistema
- Reinicia la aplicación después de otorgar permisos

## 📈 Próximas Características

- [ ] Integración con redes sociales
- [ ] Modo de estudio avanzado
- [ ] Análisis de sentimientos en mensajes
- [ ] Integración con dispositivos IoT
- [ ] Reportes automáticos por email
- [ ] Modo offline mejorado

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 📞 Soporte

Para soporte técnico o preguntas:
- Email: soporte@parentalcontrolpro.com
- GitHub Issues: [Crear un issue](https://github.com/usuario/parental-control-pro/issues)

## 🙏 Agradecimientos

- Google Firebase por los servicios en la nube
- Material Design por los componentes de UI
- Comunidad de Android por las librerías open source
- Todos los contribuidores del proyecto

---

**Nota**: Esta aplicación está diseñada para uso familiar y educativo. Los padres son responsables del uso apropiado y del cumplimiento de las leyes locales de privacidad.