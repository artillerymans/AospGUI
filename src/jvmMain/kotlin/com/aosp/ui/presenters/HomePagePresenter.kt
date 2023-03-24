package com.aosp.ui.presenters

import androidx.compose.runtime.*
import com.aosp.exts.rememberMutableStateOf
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction


@Composable
fun HomePagePresenter(
    action: Flow<LogCatAction>
): LogCatState {
    var logcatOut by rememberMutableStateOf<LogCatState>(null)

    action.collectAction {
        when(this){
            is LogCatAction.Out -> {
                logcatOut = this.state
            }
            is LogCatAction.In -> {
                logcatOut = LogCatState(this.msg)
            }
        }
    }
    return LogCatState("")
}

sealed interface LogCatAction{
    data class Out(val state: LogCatState) : LogCatAction
    data class In(val msg: String) : LogCatAction
}


data class LogCatState(
    val msg: String,
    val time: Long = System.currentTimeMillis()

)