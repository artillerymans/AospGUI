package com.aosp.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aosp.R
import com.aosp.exts.*
import com.aosp.ui.presenters.HomePageAction
import com.aosp.ui.presenters.HomePagePresenter
import com.aosp.views.LogCatView
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction
import moe.tlaster.precompose.molecule.rememberPresenter
import moe.tlaster.precompose.navigation.Navigator
import java.util.*
import javax.swing.JFileChooser


@Composable
fun HomePage(navigator: Navigator) {
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
            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                Text(
                    text = state.path.ifEmpty { "选择项目路径" },
                    style = if (state.path.isEmpty()) {
                        MaterialTheme.typography.body2.copy(color = Color.LightGray)
                    } else {
                        MaterialTheme.typography.body2
                    },
                    modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                        .weight(1f)
                        .height(IntrinsicSize.Min)
                        .paddingVertical(6.dp)
                        .paddingHorizontal(10.dp)
                )
                Spacer(modifier = Modifier.width(10.dp).height(IntrinsicSize.Min))
                Text(
                    "加载项目",
                    modifier = Modifier
                        .click {
                            val chooser = JFileChooser().also {
                                it.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                                it.dialogTitle = "加载项目"
                                it.locale = Locale.SIMPLIFIED_CHINESE
                                val returnVal = it.showOpenDialog(ComposeWindow())
                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                    val selectedFolder = it.selectedFile
                                    logCat(channel, "选择项目路径: ${selectedFolder.absolutePath}")
                                    channel.trySend(HomePageAction.LoadProject(selectedFolder.absolutePath))
                                }
                            }
                        }
                        .border(1.dp, Color.Black, RoundedCornerShape(6.dp))
                        .paddingHorizontal(8.dp)
                        .paddingVertical(5.dp)
                        .wrapContentSize()
                )
            }

            Spacer(Modifier.fillMaxWidth().height(10.dp))

            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                Text(
                    text = state.csvPath.ifEmpty { "AndroidX CSV文件" },
                    style = if (state.csvPath.isEmpty()) {
                        MaterialTheme.typography.body2.copy(color = Color.LightGray)
                    } else {
                        MaterialTheme.typography.body2
                    },
                    modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                        .weight(1f)
                        .height(IntrinsicSize.Min)
                        .paddingVertical(6.dp)
                        .paddingHorizontal(10.dp)
                )
                Spacer(modifier = Modifier.width(10.dp).height(IntrinsicSize.Min))
                Text(
                    "加载迁移AndroidX CSV文件",
                    modifier = Modifier
                        .click {
                            val chooser = JFileChooser().also {
                                it.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                                it.dialogTitle = "加载CSV"
                                it.locale = Locale.SIMPLIFIED_CHINESE
                                val returnVal = it.showOpenDialog(ComposeWindow())
                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                    val selectedFolder = it.selectedFile
                                    logCat(channel, "选择CSV路径: ${selectedFolder.absolutePath}")
                                    channel.trySend(HomePageAction.LoadCSVFile(selectedFolder.absolutePath))
                                }
                            }
                        }
                        .border(1.dp, Color.Black, RoundedCornerShape(6.dp))
                        .paddingHorizontal(8.dp)
                        .paddingVertical(5.dp)
                        .wrapContentSize()
                )
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
