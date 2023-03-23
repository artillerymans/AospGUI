package com.aosp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aosp.R
import com.aosp.Route
import com.aosp.views.SimpleAction
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo

@Composable
fun SettingsPage(navigator: Navigator) {

    Column(
        modifier = Modifier.fillMaxSize().background(R.Colors.white)
    ) {
        SimpleAction(R.Strings.settings_title){
            navigator.goBack()
        }

        Button(onClick = {
            navigator.navigate(Route.MAIN, NavOptions(includePath = true, popUpTo = PopUpTo.First()))
        }) {
            Text(
                R.Strings.return_home,
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }


}