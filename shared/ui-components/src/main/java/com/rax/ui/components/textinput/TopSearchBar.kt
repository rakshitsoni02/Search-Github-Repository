package com.rax.ui.components.textinput

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@ExperimentalMaterial3Api
fun TopSearchBar(
    placeholder: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var value by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = placeholder,
            modifier = Modifier.padding(start = 16.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            value = value,
            onValueChange = {
                value = it
                onValueChanged.invoke(it)
            },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            keyboardOptions = KeyboardOptions(KeyboardCapitalization.Sentences),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.outline,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                innerTextField()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TopSearchBarPreview() {
    TopSearchBar(
        placeholder = "placeholder",
        onValueChanged = {
            //no-op
        }
    )
}

