package net.inferno.ygocardmaster.utils

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light",
    group = "Portrait",
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light - Landscape",
    widthDp = 720,
    heightDp = 360,
    group = "Landscape",
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark",
    group = "Portrait",
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark - Landscape",
    widthDp = 720,
    heightDp = 360,
    group = "Landscape",
)
annotation class CustomPreview