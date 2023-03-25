package com.aosp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.awtEventOrNull
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.aosp.exts.paddingHorizontal
import java.awt.event.KeyEvent


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoadingDialog(des: String, onDismissRequest: () -> Unit) {
    Box(
        Modifier
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            buttons = {},
            text = {
                Column(
                    Modifier.wrapContentSize().paddingHorizontal(30.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("", modifier = Modifier.wrapContentSize(), fontSize = 1.sp)
                    CircularProgressIndicator(
                        Modifier.size(60.dp),
                        color = Color.Gray
                    )
                    Text(
                        des,
                        style = TextStyle(color = Color.Black, fontSize = 18.sp),
                        modifier = Modifier.wrapContentSize()
                    )
                }
            },
            modifier = Modifier.wrapContentSize(),
            dialogProvider = CustomPopupAlertDialogProvider(RoundedCornerShape(10.dp)),
        )
    }


}


/**
 * Shows Alert dialog as popup in the middle of the window.
 * 复写 让Dialog弹框背景圆角显示
 */
@ExperimentalMaterialApi
class CustomPopupAlertDialogProvider(val roundedCornerShape: RoundedCornerShape = RoundedCornerShape(10.dp)) :
    AlertDialogProvider {
    @Composable
    override fun AlertDialog(
        onDismissRequest: () -> Unit,
        content: @Composable () -> Unit
    ) {
        // Popups on the desktop are by default embedded in the component in which
        // they are defined and aligned within its bounds. But an [AlertDialog] needs
        // to be aligned within the window, not the parent component, so we cannot use
        // [alignment] property of [Popup] and have to use [Box] that fills all the
        // available space. Also [Box] provides a dismiss request feature when clicked
        // outside of the [AlertDialog] content.
        Popup(
            popupPositionProvider = object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset = IntOffset.Zero
            },
            focusable = true,
            onDismissRequest = onDismissRequest,
            onKeyEvent = {
                if (it.type == KeyEventType.KeyDown && it.awtEventOrNull?.keyCode == KeyEvent.VK_ESCAPE) {
                    onDismissRequest()
                    true
                } else {
                    false
                }
            },
        ) {
            val scrimColor = Color.Black.copy(alpha = 0.32f) //todo configure scrim color in function arguments
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(scrimColor)
                    .pointerInput(onDismissRequest) {
                        detectTapGestures(onPress = { onDismissRequest() })
                    },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    Modifier
                        .pointerInput(onDismissRequest) {
                            detectTapGestures(onPress = {
                                // Workaround to disable clicks on Surface background https://github.com/JetBrains/compose-jb/issues/2581
                            })
                        }, elevation = 20.dp,
                    shape = roundedCornerShape,

                    contentColor = Color.Red
                ) {
                    content()
                }
            }
        }
    }
}