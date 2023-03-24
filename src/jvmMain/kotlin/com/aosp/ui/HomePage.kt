package com.aosp.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.unit.dp
import com.aosp.R
import com.aosp.exts.*
import com.aosp.ui.presenters.HomePageAction
import com.aosp.ui.presenters.HomePagePresenter
import com.aosp.ui.viewModles.HomePageViewModel
import com.aosp.views.LoadFile
import com.aosp.views.LogCatView
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction
import moe.tlaster.precompose.molecule.rememberPresenter
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.ui.viewModel
import java.util.*
import javax.swing.JFileChooser


@Composable
fun HomePage(navigator: Navigator) {
    val mHomePageViewModel = viewModel {
        HomePageViewModel()
    }
    Column(
        modifier = Modifier.background(R.Colors.white)
            .fillMaxSize()
            .padding(R.Dimens.dp5)
    ) {
        val (state, channel) = rememberPresenter { HomePagePresenter(it) }
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {
            /*加载项目路径*/
            LoadFile(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                btnText = "加载项目",
                pathHints = "选择项目路径",
                path = state.path,
                selectFileMode = JFileChooser.DIRECTORIES_ONLY
            ) {
                logCat(channel, "加载项目: $it")
                channel.trySend(HomePageAction.LoadProject(it))
            }

            Spacer(Modifier.fillMaxWidth().height(10.dp))

            LoadFile(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                btnText = "加载迁移AndroidX CSV文件",
                pathHints = "AndroidX CSV文件",
                path = state.csvPath
            ) {
                logCat(channel, "选择CSV路径: $it")
                channel.trySend(HomePageAction.LoadCSVFile(it))
            }
        }

        Spacer(Modifier.fillMaxWidth().height(2.dp))

        LogCatView(
            state.prints,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(10.dp)
        )



    }


}

fun logCat(channel: Channel<HomePageAction>, msg: String){
    channel.trySend(HomePageAction.Print(msg))
}


@Composable
fun Presenter(
    action: Flow<Action>,
): State {
    var count by remember { mutableStateOf(0) }

    action.collectAction {
        when (this) {
            Action.Increment -> count++
            Action.Decrement -> count--
        }
    }

    return State("Clicked $count times")
}

sealed interface Action {
    object Increment : Action
    object Decrement : Action
}

data class State(
    val count: String,
)
