package com.aosp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object R {
    object Colors {
        val white = Color(0xffffffff)
        val black = Color(0xff000000)
    }

    object Dimens {
        val dp5 = 5.dp
        val window_withe = 1280.dp
        val window_height = 960.dp

        val dp1 = 1.dp
        val dp0_5 = 0.5.dp
        val dp6 = 6.dp
        val dp8 = 8.dp
        val dp16 = 16.dp
        val dp18 = 18.dp
        val dp48 = 48.dp
        val dp56 = 56.dp


        val sp12 = 12.sp
        val sp13 = 13.sp
        val sp14 = 14.sp
        val sp15 = 15.sp
        val sp16 = 16.sp
        val sp17 = 17.sp
        val sp18 = 18.sp
        val sp19 = 19.sp
        val sp20 = 20.sp
    }

    object Strings {
        const val app_name: String = "Aosp项目助手"
        const val settings_title = "设置"
        const val return_home = "返回首页"
    }

    object Icon {
        val backArrow = Icons.Rounded.ArrowBack
    }

}