package com.example.appsinfo.ui.appInformation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appsinfo.R

@Composable
fun TextInfoItem(
    title: String,
    info: String
) {

    var showMore by remember {
        mutableStateOf(false)
    }

    var textWidth by remember {
        mutableStateOf(0.dp)
    }

    var containerWidth by remember {
        mutableStateOf(0.dp)
    }

    BoxWithConstraints {

        val textMeasurer = rememberTextMeasurer()
        textWidth = textMeasurer.measure(
            text = "$title $info",
            style = TextStyle(
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily(Font(R.font.poppins))
            ),
            TextOverflow.Ellipsis,
            maxLines = 1,
            constraints = constraints
        ).size.width.dp

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .onSizeChanged {
                    containerWidth = it.width.dp
                }
                .animateContentSize(animationSpec = tween(200)),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            if (showMore) {
                Text(
                    text = "$title $info",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                )
            } else {
                Text(
                    text = "$title $info",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (containerWidth < textWidth) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Text(
                        text = if (showMore) stringResource(R.string.hideInfo) else stringResource(R.string.showFullInfo),
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clickable(enabled = containerWidth < textWidth) {
                                showMore = !showMore
                            }
                    )
                }
            }
        }
    }
}