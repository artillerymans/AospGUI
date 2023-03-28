package com.aosp

import com.alibaba.fastjson2.toJSONString

object Route {
    const val SETTINGS = "settings"
    const val MAIN = "main"
    //const val CHECK = "check/{json}?"
    const val CHECK = "check"
}

fun String.navAction(any: Any? = null): String {
    return if (any == null){
        this
    }else {
        "$this?json=${any.toJSONString()}"
    }
}