package com.aosp.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aosp.R
import com.aosp.exts.click
import com.aosp.exts.paddingHorizontal
import com.aosp.exts.paddingVertical
import java.util.*
import javax.swing.JFileChooser


@Composable
fun SimpleAction(title: String = "", onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(R.Dimens.dp56)
            .background(R.Colors.white)
            .padding(start = R.Dimens.dp16, end = R.Dimens.dp16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = R.Icon.backArrow,
                contentDescription = "Back"
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = R.Dimens.dp16)
        )
    }
}


@Composable
fun LogCatView(
    list: List<String>, modifier: Modifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight(0.5f)
            .border(R.Dimens.dp1, R.Colors.black, RoundedCornerShape(5.dp))
            .padding(10.dp)
) {
    val scrollState = rememberLazyListState()

    LaunchedEffect(list) {
        if (list.isNotEmpty()) {
            scrollState.scrollToItem(list.lastIndex)
        }
    }

    Box(
        modifier.then(Modifier.fillMaxSize())
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState,
        ) {
            items(list) { item ->
                SelectionContainer {
                    Text(
                        text = item,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }

        VerticalScrollbar(
            rememberScrollbarAdapter(scrollState),
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}


@Composable
fun LoadFile(
    btnText: String = "加载项目",
    pathHints: String = "选择项目路径",
    path: String = "",
    selectFileMode: Int = JFileChooser.FILES_ONLY,
    modifier: Modifier = Modifier.wrapContentSize(),
    onPathChange: (String) -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = path.ifEmpty { pathHints },
            style = if (path.isEmpty()) {
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
            btnText,
            modifier = Modifier
                .click {
                    val chooser = JFileChooser().also {
                        it.fileSelectionMode = selectFileMode
                        it.dialogTitle = btnText
                        it.locale = Locale.SIMPLIFIED_CHINESE
                        val returnVal = it.showOpenDialog(ComposeWindow())
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            val selectedFolder = it.selectedFile
                            onPathChange.invoke(selectedFolder.absolutePath)
                        }
                    }
                }
                .border(1.dp, Color.Black, RoundedCornerShape(6.dp))
                .paddingHorizontal(8.dp)
                .paddingVertical(5.dp)
                .wrapContentSize()
                .fillMaxHeight()
        )
    }
}