package com.aosp.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeDialog
import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.rememberDialogState
import com.aosp.R
import com.aosp.exts.*
import com.aosp.ui.presenters.HomePageAction
import com.aosp.ui.presenters.HomePagePresenter
import com.aosp.ui.presenters.PageAction
import com.aosp.ui.viewModles.HomePageViewModel
import com.aosp.utils.CustomPopupAlertDialogProvider
import com.aosp.utils.LoadingDialog
import com.aosp.views.LoadFile
import com.aosp.views.LogCatView
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import moe.tlaster.precompose.molecule.collectAction
import moe.tlaster.precompose.molecule.rememberPresenter
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.ui.viewModel
import java.awt.event.KeyEvent
import javax.swing.JFileChooser


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage(navigator: Navigator) {
    val mHomePageViewModel = viewModel {
        HomePageViewModel()
    }

    val (state, bus) = rememberPresenter { HomePagePresenter(it) }

    if (state.showLoadDialog) {
        LoadingDialog("加载中") {
            bus.loadFinish()
        }
    }

    Column(
        modifier = Modifier.background(R.Colors.white)
            .fillMaxSize()
            .padding(R.Dimens.dp5)
    ) {

        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
        ) {
            /*加载项目路径*/
            LoadFile(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                btnText = "加载项目",
                pathHints = "选择项目路径",
                path = state.path,
                selectFileMode = JFileChooser.DIRECTORIES_ONLY
            ) {
                bus.print("加载项目: $it")
                bus.post(HomePageAction.LoadProject(it))
            }

            LoadFile(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                btnText = "加载迁移AndroidX CSV文件",
                pathHints = "AndroidX CSV文件",
                path = state.csvPath
            ) {
                bus.print("选择CSV路径: $it")
                mHomePageViewModel.readCSVFile(
                    bus,
                    it
                )
            }

            LoadFile(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                btnText = "输出路径",
                pathHints = "转换后输出的路径",
                path = state.outPath,
                selectFileMode = JFileChooser.DIRECTORIES_ONLY
            ) {
                bus.print("输出路径: $it")
                bus.post(HomePageAction.SelectOutPath(it))
            }

            OptionsContainer(opt = state.opt) {
                bus.post(HomePageAction.OptSelectConfigChange(it))
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    "开始",
                    style = MaterialTheme.typography.h3.copy(color = Color.White),
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                        .click {
                            mHomePageViewModel.start(state, bus)
                        }
                        .background(Color.Blue)
                        .padding(30.dp)
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


/*处理的选型*/
@Preview
@Composable
fun OptionsContainer(modifier: Modifier = Modifier, opt: OptionsSelect, onConfigChange: (OptionsSelect) -> Unit) {
    var mSelectOptState by rememberMutableStateOf(opt)
    Row(modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        SelectOptItem("AndroidManifest合并", mSelectOptState.androidManifest){
            mSelectOptState = mSelectOptState.copy(androidManifest = !mSelectOptState.androidManifest)
            onConfigChange.invoke(mSelectOptState)
        }

        SelectOptItem("String.xml资源命名去重", mSelectOptState.stringNameDeduplication){
            mSelectOptState = mSelectOptState.copy(stringNameDeduplication = !mSelectOptState.stringNameDeduplication)
            onConfigChange.invoke(mSelectOptState)
        }

        SelectOptItem("代码转换AndroidX", mSelectOptState.androidSupport2AndroidX){
            mSelectOptState = mSelectOptState.copy(androidSupport2AndroidX = !mSelectOptState.androidSupport2AndroidX)
            onConfigChange.invoke(mSelectOptState)
        }
    }
}


@Composable
fun SelectOptItem(text: String, state: Boolean = false, onSelect: (Boolean) -> Unit) {
    Row(Modifier
        .wrapContentSize()
        .clip(RoundedCornerShape(4.dp))
        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        .click(500L) {
            onSelect.invoke(!state)
        }
        .paddingHorizontal(6.dp)
        .paddingVertical(4.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Checkbox(state, onCheckedChange = null, enabled = false)
        Text(text, style = MaterialTheme.typography.body2)
    }
}


data class OptionsSelect(
    var androidManifest: Boolean = false,
    var stringNameDeduplication: Boolean = false,
    var androidSupport2AndroidX: Boolean = false
)

