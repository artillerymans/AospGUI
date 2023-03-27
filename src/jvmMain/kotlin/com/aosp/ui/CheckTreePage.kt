package com.aosp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cn.hutool.core.io.file.FileNameUtil
import cn.hutool.core.io.file.PathUtil
import com.aosp.exts.rememberMutableStateOf
import moe.tlaster.precompose.navigation.Navigator
import java.io.File
import kotlin.io.path.Path


/**
 * 检查当前目录下有多少上Androidmainifest文件
 * Src目录几个
 * Res目录几个
 * Android.bp文件几个
 * */
@Composable
fun CheckFileTreePage(nav: Navigator, parameter: CheckFileTreeParameter) {
    var listView by rememberMutableStateOf( mapOf<String, Int>() )
    LaunchedEffect(parameter){
        val map = hashMapOf<String, Int>()
        PathUtil.loopFiles(Path(parameter.path)){
            val name = FileNameUtil.getPrefix(it)
            val extName = FileNameUtil.getSuffix(it)
            if (name.equals("android", true) && extName.equals("bp", true)){
                map["bp"] = if (map.contains("bp")) (map["bp"]!!.plus(1)) else 1
            }
            if (name.equals("AndroidManifest", true) && extName.equals("xml", true)){
                map["AndroidManifest"] = if (map.contains("AndroidManifest")) (map["AndroidManifest"]!!.plus(1)) else 1
            }
            true
        }
        listView = map
    }

    Column(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        listView.forEach { (key, value) ->
            Text(
                text = "$key => $value",
                style = MaterialTheme.typography.body2
            )
        }
    }
}

data class CheckFileTreeParameter(
    var path: String
)