# GuardianSuite (Parent/Child)

Proyecto Android multi-módulo (Kotlin, Compose) con integración preparada para Firebase (Auth/Firestore) y servicio de accesibilidad en el módulo `child`.

## Requisitos
- Android Studio Jellyfish+ (AGP 8.5+)
- JDK 17 (Android Studio ya lo incluye)

## Estructura
- `parent`: App para el padre/tutor. Incluye UI mínima y login anónimo de Firebase.
- `child`: App para el dispositivo a supervisar. Incluye `AccessibilityService` básico.

## Configurar Firebase
1. Crea un proyecto en Firebase.
2. Agrega dos apps Android en el mismo proyecto:
   - `com.guardiansuite.parent`
   - `com.guardiansuite.child`
3. Descarga los dos archivos `google-services.json`.
4. Colócalos en:
   - `parent/google-services.json`
   - `child/google-services.json`
5. En la consola de Firebase habilita:
   - Authentication → Sign-in method → Anonymous (y/o Email/Password si lo deseas)
   - Firestore Database → Create database (modo producción recomendado)

Nota: Los scripts Gradle aplican el plugin `google-services` solo si detectan `google-services.json`, por lo que el proyecto compila aún sin el archivo (pero Firebase no funcionará hasta agregarlo).

## Compilar e instalar (Android Studio)
1. Abre la carpeta del proyecto (`GuardianSuite`) en Android Studio.
2. Espera la sincronización de Gradle.
3. Menú Build → Make Project.
4. Selecciona la configuración de ejecución:
   - Para `parent`: elige el módulo `parent`.
   - Para `child`: elige el módulo `child`.
5. Click en Run ▶ para instalar en tu dispositivo.

## Permisos importantes
- `child` requiere:
  - Habilitar manualmente el servicio de accesibilidad: Ajustes → Accesibilidad → Guardian Child.
  - Permisos especiales como Uso de apps (`PACKAGE_USAGE_STATS`) según la funcionalidad a implementar.

## Próximos pasos
- UI en `parent` para gestionar lista de apps bloqueadas (Firestore).
- `child` observar app en primer plano y bloquear según políticas.

## Advertencia legal
Este proyecto es educativo y debe usarse con consentimiento explícito y conforme a las leyes aplicables de privacidad y monitoreo.