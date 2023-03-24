package com.aosp.ui.presenters

import androidx.compose.runtime.*
import com.aosp.exts.rememberMutableStateOf
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction


@Composable
fun HomePagePresenter(
    action: Flow<HomePageAction>
): HomePageState {

    var listPrintState by rememberMutableStateOf(emptyList<String>())

    var pathState by rememberMutableStateOf("")
    var csvPathState by rememberMutableStateOf("")

    action.collectAction {
        when (this) {
            is HomePageAction.Print -> {
                listPrintState = mutableListOf<String>().apply {
                    addAll(listPrintState.toList())
                    add(this@collectAction.msg)
                }.toList()
            }
            is HomePageAction.LoadProject -> {
                pathState = path
            }
            is HomePageAction.LoadCSVFile -> {
                csvPathState = path
            }
        }
    }
    return HomePageState(
        listPrintState,
        pathState,
        csvPathState
    )
}

sealed interface HomePageAction {

    /*日志打印*/
    data class Print(val msg: String) : HomePageAction
    /*项目路径加载*/
    data class LoadProject(val path: String): HomePageAction
    data class LoadCSVFile(val path: String): HomePageAction
}


data class HomePageState(
    /*日志列表*/
    var prints: List<String> = emptyList(), //日志输出

    var path: String = "", //项目路径

    var csvPath: String = "" //CSV项目文件

)