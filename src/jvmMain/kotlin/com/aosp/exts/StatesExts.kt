package com.aosp.exts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun <T> rememberMutableStateOf(value: T?) = remember { mutableStateOf(value) }

@Composable
fun <T> rememberMutableSaveStateOf(value: T) = rememberSaveable() {
    mutableStateOf(value)
}