package com.aosp

import com.alibaba.fastjson2.to
import com.alibaba.fastjson2.toJSONString
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.query

object Route {
    const val SETTINGS = "settings"
    const val MAIN = "main"
    //const val CHECK = "check/{json}?"
    const val CHECK = "check"
}

/**设置跳转参数*/
fun String.navAction(any: Any? = null): String {
    return if (any == null){
        this
    }else {
        "$this?json=${any.toJSONString()}"
    }
}

/**获取跳转过来的参数*/
inline fun <reified T> BackStackEntry.navParameter(key: String = "json"): T {
    val jsonStr = query(key, "")
    return jsonStr.to()
}