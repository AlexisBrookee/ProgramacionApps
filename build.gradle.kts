// Archivo: build.gradle.kts (Project Level) - VERSIÓN LIMPIA

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // ⭐ NO LLEVA KSP AQUÍ EN LA VERSIÓN SIN ROOM ⭐
}