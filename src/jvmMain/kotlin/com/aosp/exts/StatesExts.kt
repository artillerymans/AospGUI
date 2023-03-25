package com.aosp.exts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.aosp.base.BaseAction
import com.aosp.ui.presenters.HomePageAction
import com.aosp.ui.presenters.PageAction
import kotlinx.coroutines.channels.Channel

@Composable
fun <T> rememberMutableStateOf(value: T) = remember { mutableStateOf(value) }

@Composable
fun <T> rememberMutableSaveStateOf(value: T) = rememberSaveable() {
    mutableStateOf(value)
}

fun Channel<BaseAction>.success(){
    post(PageAction.SUCCESS)
}

fun Channel<BaseAction>.fail(){
    post(PageAction.FAIL)
}

fun Channel<BaseAction>.loading(){
    post(PageAction.Loading)
}

fun Channel<BaseAction>.loadFinish(){
    post(PageAction.LoadFinish)
}

fun Channel<BaseAction>.cancel(){
    post(PageAction.CANCEL)
}

fun Channel<BaseAction>.print(str: String){
    post(PageAction.Print(str))
}

fun Channel<BaseAction>.error(str: String, type: Int = -1){
    post(PageAction.Error(str, type))
}

fun Channel<BaseAction>.errorAndLog(str: String, type: Int = -1){
    error(str, type)
    print(str)
}


fun <T: BaseAction> Channel<T>.post(any: T){
    this.trySend(any)
}

