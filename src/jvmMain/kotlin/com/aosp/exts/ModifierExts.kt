package com.aosp.exts

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import com.aosp.utils.debouncedAction

fun Modifier.paddingHorizontal(horizontal: Dp): Modifier {
    return padding(horizontal = horizontal)
}

fun Modifier.paddingVertical(vertical: Dp): Modifier {
    return padding(vertical = vertical)
}


/**
 * Create by zhiweizhu on 2022/5/14
 */

/*防抖*/
fun Modifier.click(
    delay: Long = 1000L, enabled: Boolean = true, block: () -> Unit
) = composed {
    clickable(
        onClick = debouncedAction(waitMillis = delay, action = block), enabled = enabled
    )
}

/*防抖 没有点击效果*/
fun Modifier.clickNoEffect(
    indication: Indication? = null,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    enabled: Boolean = true,
    waitMillis: Long = 1000,
    onClick: () -> Unit
) = composed {
    clickable(
        onClick = debouncedAction(waitMillis = waitMillis, action = onClick),
        indication = indication,
        interactionSource = interactionSource,
        enabled = enabled
    )
}

