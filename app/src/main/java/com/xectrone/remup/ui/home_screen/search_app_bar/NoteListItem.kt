package com.xectrone.remup.ui.home_screen.search_app_bar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xectrone.remup.ui.theme.LocalCustomColorPalette
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteListItem(item: NoteSelectionListItem, onClick: ()-> Unit, onLongClick: ()-> Unit)
{
    Card(
        modifier = Modifier
            .padding(4.5.dp)
            .height(150.dp)
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick() }
            ),
        backgroundColor = LocalCustomColorPalette.current.surface,
        shape = RoundedCornerShape(17.dp),
        border = if (item.isSelected) BorderStroke(1.dp,LocalCustomColorPalette.current.accent) else null
    )
    {
        Column(
            modifier = Modifier
                .padding(25.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start

        )
        {
            Text(
                modifier = Modifier
                    .padding(bottom = 15.dp),
                text = item.note.data,
                color = LocalCustomColorPalette.current.primary,
                softWrap = true,
                maxLines = 4,
            )

            Text(
                fontSize = 13.sp,
                text = item.note.created.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)),
                color = LocalCustomColorPalette.current.secondary,
                maxLines = 1,
                textAlign = TextAlign.Start
            )
        }
    }
}