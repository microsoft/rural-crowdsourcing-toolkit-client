package com.microsoft.research.karya.compose.components.v1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.research.karya.compose.theme.KaryaTheme

// TODO: Animate button select and unselect
@Composable
fun ToggleButtonGroup(
    options: List<String>,
    selectedIndices: List<Int>,
    buttonPadding: Dp = 16.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    onOptionSelected: (Int) -> Unit
) {
    FlowRow(
        horizontalGap = 4.dp,
        verticalGap = 4.dp,
    ) {
        options.forEachIndexed { idx, option ->
            val background = if (selectedIndices.contains(idx)) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                Color.Transparent
            }
            Text(
                modifier = Modifier
                    .clip(CircleShape)
                    .border((0.5).dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .background(background)
                    .clickable { onOptionSelected(idx) }
                    .padding(buttonPadding),
                text = option,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                style = textStyle
            )
        }
    }
}

/**
 * Multi Select Behaviour
 */
@Preview
@Composable
fun ToggleButtonGroupPrev() {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    val selectedListItem = remember { mutableStateListOf<Int>() }
    val maximumSelection = 3
    KaryaTheme {
        Surface {
            ToggleButtonGroup(options = options, selectedIndices = selectedListItem) {
                // if option is already select the unselect it
                if (selectedListItem.contains(it)) {
                    selectedListItem.remove(it)
                } else {
                    // selection limit has reached maximum, then unselect the first selected option
                    if (selectedListItem.size == maximumSelection) {
                        selectedListItem.removeAt(0)
                    }
                    // select the clicked option
                    selectedListItem.add(it)
                }
            }
        }
    }
}

/**
 * Single Select Behaviour
 */
@Preview
@Composable
fun ToggleButtonGroupPrev1() {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var selectedIndex by remember { mutableStateOf(-1) } // -1 means none selected
    KaryaTheme {
        Surface {
            ToggleButtonGroup(
                options = options,
                selectedIndices = listOf(selectedIndex)
            ) { optionIdx ->
                // toggling selection,
                // if option is selected the unselect it by changing selectedIndex = -1
                // else selected = optionIdx
                selectedIndex = if (selectedIndex == optionIdx) -1 else optionIdx
            }
        }
    }
}
