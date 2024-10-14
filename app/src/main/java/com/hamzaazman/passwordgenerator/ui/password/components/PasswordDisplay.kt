package com.hamzaazman.passwordgenerator.ui.password.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PasswordDisplay(
    password: String,
    onRefreshClicked: () -> Unit,
    onCopyClicked: () -> Unit
) {
    TextField(
        label = { Text("Parola") },
        singleLine = false,
        maxLines = Int.MAX_VALUE,
        readOnly = true,
        value = password,
        textStyle = TextStyle(fontSize = 24.sp),
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateContentSize(),
        trailingIcon = {
            Row {
                IconButton(onClick = onRefreshClicked) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                }
                IconButton(onClick = onCopyClicked) {
                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy")
                }
            }
        }
    )
}