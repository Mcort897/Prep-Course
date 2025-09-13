package com.safeguard.parental.utils

sealed class Screen(val route: String) {
    object Setup : Screen("setup")
    object Auth : Screen("auth")
    object Main : Screen("main")
    object Dashboard : Screen("dashboard")
    object Children : Screen("children")
    object Apps : Screen("apps")
    object Location : Screen("location")
    object Reports : Screen("reports")
    object Settings : Screen("settings")
    
    // Detail screens
    object ChildDetail : Screen("child_detail/{childId}") {
        fun createRoute(childId: String) = "child_detail/$childId"
    }
    object AppDetail : Screen("app_detail/{packageName}") {
        fun createRoute(packageName: String) = "app_detail/$packageName"
    }
    object LocationDetail : Screen("location_detail/{childId}") {
        fun createRoute(childId: String) = "location_detail/$childId"
    }
    object ReportDetail : Screen("report_detail/{reportType}") {
        fun createRoute(reportType: String) = "report_detail/$reportType"
    }
}