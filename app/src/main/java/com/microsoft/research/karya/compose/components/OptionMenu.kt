package com.microsoft.research.karya.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.microsoft.research.karya.compose.theme.KaryaTheme

/**
 * A Horizontal option menu.
 * Displays a list of options in a row from which user can select any one.
 * Overflow items will automatically become scrollable.
 * It is a stateless composable, its state needs to be hoisted by the parent.
 *
 * @param options list of options
 * @param onSelect lambda to be executed when an option is clicked
 * @param selectedIndex index of the selected option from the [options] list
 * @param style text style of the options
 *
 * @author Divyansh Kushwaha <divyanshdxn@gmail.com>
 */
// https://m3.material.io/components/segmented-buttons/overview
@Composable
fun OptionMenu(
    options: List<String>,
    onSelect: (index: Int, option: String) -> Unit,
    selectedIndex: Int = -1,
    style: TextStyle = MaterialTheme.typography.labelSmall
) {
    Row(
        Modifier
            .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .clip(CircleShape)
    ) {
        LazyRow {
            itemsIndexed(options) { index, option ->
                val background = if (selectedIndex == index) {
                    MaterialTheme.colorScheme.secondaryContainer
                } else {
                    Color.Transparent
                }
                Text(
                    modifier = Modifier
                        .background(background)
                        .border((0.5).dp, MaterialTheme.colorScheme.primary, RectangleShape)
                        .clickable { onSelect(index, option) }
                        .padding(16.dp),
                    text = option,
                    textAlign = TextAlign.Center,
                    style = style
                )
            }
        }
    }
}

@Preview
@Composable
fun OptionMenuPreview() {
    var selectedIndex by remember { mutableStateOf(0) }
    var selectedIndex0 by remember { mutableStateOf(0) }
    KaryaTheme {
        Surface {
            Column(Modifier.padding(8.dp)) {
                OptionMenu(
                    options = listOf(
                        "Excellent",
                        "Good",
                        "Okay",
                        "Bad",
                        "Unacceptable",
                        "One",
                        "Two",
                        "Three",
                        "Four"
                    ),
                    onSelect = { idx, _ -> selectedIndex = idx },
                    selectedIndex = selectedIndex
                )
                HorizontalSpacer()
                OptionMenu(
                    options = listOf("Excellent", "Good", "Okay"),
                    onSelect = { idx, _ -> selectedIndex0 = idx },
                    selectedIndex = selectedIndex0
                )
            }
        }
    }
}
