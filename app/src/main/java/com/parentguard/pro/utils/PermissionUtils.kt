package com.parentguard.pro.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.parentguard.pro.R

object PermissionUtils {
    
    private const val PERMISSION_REQUEST_CODE = 1001
    
    // Required permissions for the app
    val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        Manifest.permission.FOREGROUND_SERVICE,
        Manifest.permission.FOREGROUND_SERVICE_LOCATION,
        Manifest.permission.SYSTEM_ALERT_WINDOW,
        Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
        Manifest.permission.WAKE_LOCK,
        Manifest.permission.VIBRATE,
        Manifest.permission.RECEIVE_BOOT_COMPLETED,
        Manifest.permission.QUERY_ALL_PACKAGES,
        Manifest.permission.PACKAGE_USAGE_STATS,
        Manifest.permission.BIND_ACCESSIBILITY_SERVICE,
        Manifest.permission.WRITE_SETTINGS,
        Manifest.permission.DISABLE_KEYGUARD,
        Manifest.permission.MODIFY_SYSTEM_SETTINGS,
        Manifest.permission.REQUEST_DELETE_PACKAGES,
        Manifest.permission.INSTALL_PACKAGES,
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.SCHEDULE_EXACT_ALARM,
        Manifest.permission.USE_EXACT_ALARM,
        Manifest.permission.ACCESS_NOTIFICATION_POLICY,
        Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_SMS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.MANAGE_EXTERNAL_STORAGE
    )
    
    // Critical permissions that are required for core functionality
    val CRITICAL_PERMISSIONS = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.PACKAGE_USAGE_STATS,
        Manifest.permission.BIND_ACCESSIBILITY_SERVICE,
        Manifest.permission.SYSTEM_ALERT_WINDOW,
        Manifest.permission.POST_NOTIFICATIONS
    )
    
    fun checkAndRequestPermissions(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val missingPermissions = getMissingPermissions(context)
            
            if (missingPermissions.isNotEmpty()) {
                requestPermissions(context, missingPermissions)
            }
        }
    }
    
    fun getMissingPermissions(context: Context): List<String> {
        return REQUIRED_PERMISSIONS.filter { permission ->
            ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
        }
    }
    
    fun getMissingCriticalPermissions(context: Context): List<String> {
        return CRITICAL_PERMISSIONS.filter { permission ->
            ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
        }
    }
    
    fun hasAllCriticalPermissions(context: Context): Boolean {
        return getMissingCriticalPermissions(context).isEmpty()
    }
    
    fun hasAllPermissions(context: Context): Boolean {
        return getMissingPermissions(context).isEmpty()
    }
    
    fun requestPermissions(context: Context, permissions: List<String>) {
        if (context is Activity) {
            ActivityCompat.requestPermissions(
                context,
                permissions.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }
    }
    
    fun requestPermissionsWithDexter(
        context: Context,
        onPermissionsGranted: () -> Unit,
        onPermissionsDenied: (List<String>) -> Unit
    ) {
        Dexter.withContext(context)
            .withPermissions(*REQUIRED_PERMISSIONS)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        onPermissionsGranted()
                    } else {
                        val deniedPermissions = report.deniedPermissionResponses.map { it.permissionName }
                        onPermissionsDenied(deniedPermissions)
                    }
                }
                
                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .check()
    }
    
    fun openAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }
    
    fun openUsageStatsSettings(context: Context) {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        context.startActivity(intent)
    }
    
    fun openAccessibilitySettings(context: Context) {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        context.startActivity(intent)
    }
    
    fun openNotificationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        }
        context.startActivity(intent)
    }
    
    fun openBatteryOptimizationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
            data = Uri.parse("package:${context.packageName}")
        }
        context.startActivity(intent)
    }
    
    fun openOverlaySettings(context: Context) {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
            data = Uri.parse("package:${context.packageName}")
        }
        context.startActivity(intent)
    }
    
    fun openSystemAlertWindowSettings(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            context.startActivity(intent)
        }
    }
    
    fun openWriteSettings(context: Context) {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
            data = Uri.parse("package:${context.packageName}")
        }
        context.startActivity(intent)
    }
    
    fun openModifySystemSettings(context: Context) {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
            data = Uri.parse("package:${context.packageName}")
        }
        context.startActivity(intent)
    }
    
    fun openNotificationListenerSettings(context: Context) {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        context.startActivity(intent)
    }
    
    fun openManageExternalStorageSettings(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            context.startActivity(intent)
        }
    }
    
    fun getPermissionExplanation(permission: String): String {
        return when (permission) {
            Manifest.permission.INTERNET -> "Necesario para sincronizar datos con el servidor"
            Manifest.permission.ACCESS_NETWORK_STATE -> "Necesario para verificar el estado de la conexión"
            Manifest.permission.ACCESS_FINE_LOCATION -> "Necesario para rastrear la ubicación de tu hijo"
            Manifest.permission.ACCESS_COARSE_LOCATION -> "Necesario para rastrear la ubicación aproximada"
            Manifest.permission.ACCESS_BACKGROUND_LOCATION -> "Necesario para rastrear la ubicación en segundo plano"
            Manifest.permission.PACKAGE_USAGE_STATS -> "Necesario para monitorear el uso de aplicaciones"
            Manifest.permission.BIND_ACCESSIBILITY_SERVICE -> "Necesario para controlar el acceso a aplicaciones"
            Manifest.permission.SYSTEM_ALERT_WINDOW -> "Necesario para mostrar ventanas de bloqueo"
            Manifest.permission.POST_NOTIFICATIONS -> "Necesario para enviar notificaciones importantes"
            Manifest.permission.WAKE_LOCK -> "Necesario para mantener la aplicación activa"
            Manifest.permission.VIBRATE -> "Necesario para notificaciones táctiles"
            Manifest.permission.RECEIVE_BOOT_COMPLETED -> "Necesario para iniciar automáticamente al arrancar"
            Manifest.permission.QUERY_ALL_PACKAGES -> "Necesario para listar todas las aplicaciones instaladas"
            Manifest.permission.WRITE_SETTINGS -> "Necesario para modificar configuraciones del sistema"
            Manifest.permission.DISABLE_KEYGUARD -> "Necesario para desbloquear la pantalla cuando sea necesario"
            Manifest.permission.MODIFY_SYSTEM_SETTINGS -> "Necesario para modificar configuraciones del sistema"
            Manifest.permission.REQUEST_DELETE_PACKAGES -> "Necesario para gestionar aplicaciones"
            Manifest.permission.INSTALL_PACKAGES -> "Necesario para instalar actualizaciones"
            Manifest.permission.SCHEDULE_EXACT_ALARM -> "Necesario para programar alarmas exactas"
            Manifest.permission.USE_EXACT_ALARM -> "Necesario para usar alarmas exactas"
            Manifest.permission.ACCESS_NOTIFICATION_POLICY -> "Necesario para gestionar políticas de notificaciones"
            Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE -> "Necesario para escuchar notificaciones"
            Manifest.permission.READ_PHONE_STATE -> "Necesario para verificar el estado del teléfono"
            Manifest.permission.READ_CALL_LOG -> "Necesario para monitorear llamadas"
            Manifest.permission.READ_SMS -> "Necesario para monitorear mensajes"
            Manifest.permission.SEND_SMS -> "Necesario para enviar mensajes de emergencia"
            Manifest.permission.READ_CONTACTS -> "Necesario para gestionar contactos de emergencia"
            Manifest.permission.CAMERA -> "Necesario para capturar fotos de emergencia"
            Manifest.permission.RECORD_AUDIO -> "Necesario para grabar audio de emergencia"
            Manifest.permission.READ_EXTERNAL_STORAGE -> "Necesario para acceder a archivos"
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> "Necesario para guardar archivos"
            Manifest.permission.MANAGE_EXTERNAL_STORAGE -> "Necesario para gestionar archivos externos"
            else -> "Necesario para el funcionamiento de la aplicación"
        }
    }
    
    fun getPermissionTitle(permission: String): String {
        return when (permission) {
            Manifest.permission.INTERNET -> "Acceso a Internet"
            Manifest.permission.ACCESS_NETWORK_STATE -> "Estado de Red"
            Manifest.permission.ACCESS_FINE_LOCATION -> "Ubicación Precisa"
            Manifest.permission.ACCESS_COARSE_LOCATION -> "Ubicación Aproximada"
            Manifest.permission.ACCESS_BACKGROUND_LOCATION -> "Ubicación en Segundo Plano"
            Manifest.permission.PACKAGE_USAGE_STATS -> "Estadísticas de Uso"
            Manifest.permission.BIND_ACCESSIBILITY_SERVICE -> "Servicio de Accesibilidad"
            Manifest.permission.SYSTEM_ALERT_WINDOW -> "Ventanas del Sistema"
            Manifest.permission.POST_NOTIFICATIONS -> "Notificaciones"
            Manifest.permission.WAKE_LOCK -> "Mantener Despierto"
            Manifest.permission.VIBRATE -> "Vibración"
            Manifest.permission.RECEIVE_BOOT_COMPLETED -> "Inicio Automático"
            Manifest.permission.QUERY_ALL_PACKAGES -> "Listar Aplicaciones"
            Manifest.permission.WRITE_SETTINGS -> "Escribir Configuraciones"
            Manifest.permission.DISABLE_KEYGUARD -> "Desbloquear Pantalla"
            Manifest.permission.MODIFY_SYSTEM_SETTINGS -> "Modificar Configuraciones"
            Manifest.permission.REQUEST_DELETE_PACKAGES -> "Eliminar Aplicaciones"
            Manifest.permission.INSTALL_PACKAGES -> "Instalar Aplicaciones"
            Manifest.permission.SCHEDULE_EXACT_ALARM -> "Programar Alarmas"
            Manifest.permission.USE_EXACT_ALARM -> "Usar Alarmas Exactas"
            Manifest.permission.ACCESS_NOTIFICATION_POLICY -> "Política de Notificaciones"
            Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE -> "Escuchar Notificaciones"
            Manifest.permission.READ_PHONE_STATE -> "Estado del Teléfono"
            Manifest.permission.READ_CALL_LOG -> "Registro de Llamadas"
            Manifest.permission.READ_SMS -> "Leer Mensajes"
            Manifest.permission.SEND_SMS -> "Enviar Mensajes"
            Manifest.permission.READ_CONTACTS -> "Leer Contactos"
            Manifest.permission.CAMERA -> "Cámara"
            Manifest.permission.RECORD_AUDIO -> "Grabar Audio"
            Manifest.permission.READ_EXTERNAL_STORAGE -> "Leer Almacenamiento"
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> "Escribir Almacenamiento"
            Manifest.permission.MANAGE_EXTERNAL_STORAGE -> "Gestionar Almacenamiento"
            else -> "Permiso Especial"
        }
    }
}