package net.inferno.ygocardmaster.utils

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.view.WindowInsetsControllerCompat

val LocalWindowInsetsController = staticCompositionLocalOf<WindowInsetsControllerCompat> {
    error("CompositionLocal LocalWindowInsetsController not present")
}