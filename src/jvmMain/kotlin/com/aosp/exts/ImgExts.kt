package com.aosp.exts

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource



@Composable
fun loadIconByResources(str: String): Painter {
    return painterResource(str)
}

