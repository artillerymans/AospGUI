package com.aosp.ui.presenters

import androidx.compose.runtime.*
import com.aosp.base.BaseAction
import com.aosp.exts.rememberMutableStateOf
import com.aosp.ui.OptionsSelect
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction


@Composable
fun HomePagePresenter(
    action: Flow<BaseAction>
): HomePageState {

    var listPrintState by rememberMutableStateOf(emptyList<String>())

    var pathState by rememberMutableStateOf("")
    var csvPathState by rememberMutableStateOf("")
    var showLoadDialogState by rememberMutableStateOf(false)
    var outPathState by rememberMutableStateOf("")
    var optConfigState by rememberMutableStateOf(OptionsSelect(androidManifest = true))

    action.collectAction {
        when (this) {
            is PageAction.Print -> {
                listPrintState = mutableListOf<String>().apply {
                    addAll(listPrintState.toList())
                    add(this@collectAction.msg)
                }.toList()
            }

            is PageAction.SUCCESS -> {

            }

            is PageAction.Error -> {

            }

            is PageAction.Loading -> {
                showLoadDialogState = true
            }

            is PageAction.LoadFinish -> {
                showLoadDialogState = false
            }

            is HomePageAction.LoadProject -> {   //加载需要处理的目录路径
                pathState = path
            }
            is HomePageAction.LoadCSVFile -> {  //加载CSV文件
                csvPathState = path
            }

            is HomePageAction.SelectOutPath ->{  //输出目录选择
                outPathState = path
            }
            is HomePageAction.OptSelectConfigChange -> {
                optConfigState = opt
            }
        }
    }
    return HomePageState(
        prints = listPrintState,
        path = pathState,
        csvPath = csvPathState,
        showLoadDialog = showLoadDialogState,
        outPath = outPathState,
        opt = optConfigState
    )
}

sealed interface HomePageAction: BaseAction{


    /*项目路径加载*/
    data class LoadProject(val path: String): HomePageAction
    data class LoadCSVFile(val path: String): HomePageAction

    data class SelectOutPath(val path: String): HomePageAction

    data class OptSelectConfigChange(val opt: OptionsSelect): HomePageAction


}

sealed interface PageAction: BaseAction{
    object Loading: PageAction

    object SUCCESS: PageAction

    object FAIL: PageAction

    object CANCEL: PageAction

    object LoadFinish: PageAction

    /*日志打印*/
    data class Print(val msg: String) : PageAction

    data class Error(val msg: String, val type: Int): PageAction
}


data class HomePageState(
    /*日志列表*/
    var prints: List<String> = emptyList(), //日志输出

    var path: String = "", //项目路径

    var csvPath: String = "", //CSV项目文件

    var showLoadDialog: Boolean = false,

    var outPath: String = "",

    var opt: OptionsSelect = OptionsSelect(androidManifest = true)


)



