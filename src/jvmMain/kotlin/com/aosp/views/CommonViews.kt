package com.aosp.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.aosp.R
import com.aosp.exts.rememberMutableStateOf
import kotlin.math.log

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
            items(list, key = { item -> item }) { item ->
                Text(
                    text = item,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.body2
                )
            }
        }

        VerticalScrollbar(
            rememberScrollbarAdapter(scrollState),
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}