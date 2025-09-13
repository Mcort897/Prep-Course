Guardian (Firebase + Usage + Location)
=====================================

Proyecto base funcional para Android con:
- Firebase (Auth anónima, Firestore, Crashlytics, Analytics, FCM)
- Servicio en primer plano de ubicación
- WorkManager para estadísticas de uso cada 15 min
- Receiver de arranque

Requisitos
---------
- Android Studio Jellyfish o superior
- Java 17
- Proyecto en Firebase

Configurar Firebase
-------------------
1. En Firebase Console, crea proyecto y agrega app Android con `applicationId`: `com.example.guardian`.
2. Descarga `google-services.json` y colócalo en `app/google-services.json`.
3. Habilita Authentication (anónima), Firestore, Cloud Messaging, Crashlytics.

Permisos
--------
- Ubicación precisa y en segundo plano
- Notificaciones (Android 13+)
- Acceso al uso de aplicaciones (debe activarse manualmente en Ajustes)

Compilar e instalar
-------------------
1. Abrir Android Studio > Open > carpeta `guardian`.
2. Esperar sincronización de Gradle.
3. Copiar `app/google-services.json`.
4. Conectar dispositivo o abrir emulador.
5. Presionar Run.

Uso básico
---------
- En la app, concede permisos, activa el acceso de uso, inicia ubicación y agenda la sincronización.

