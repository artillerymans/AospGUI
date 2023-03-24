package com.aosp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.aosp.R
import com.aosp.Route
import com.aosp.exts.rememberMutableStateOf
import com.aosp.ui.presenters.HomePagePresenter
import com.aosp.ui.presenters.LogCatAction
import com.aosp.ui.presenters.LogCatState
import com.aosp.views.LogCatView
import moe.tlaster.precompose.molecule.rememberPresenter
import moe.tlaster.precompose.navigation.Navigator
import java.lang.StringBuilder

@Composable
fun HomePage(navigator: Navigator) {

    Column(
        modifier = Modifier.background(R.Colors.white)
            .fillMaxSize()
            .padding(R.Dimens.dp5)
    ) {
        val (state, channel) = rememberPresenter { HomePagePresenter(it) }
        Box(
            modifier = Modifier.clickable {
                channel.trySend(LogCatAction.Out(LogCatState(System.currentTimeMillis().toString())))
            }.fillMaxWidth().height(IntrinsicSize.Min)
                .defaultMinSize(minHeight = 100.dp)
                .border(R.Dimens.dp1, R.Colors.black, RoundedCornerShape(5.dp))
        ) {
            Text("输出日志信息")
        }

        LogCatView(state.msg)

    }


}


@Composable
fun MyTextField() {
    TextField(
        value = "",
        onValueChange = { },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.Black),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(5.dp),

        )
}
