package com.hamzaazman.passwordgenerator.ui.password.components

import android.os.Vibrator
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PasswordLengthSlider(
    passwordLength: Int,
    onValueChange: (Int) -> Unit,
    onValueChangeFinished: (() -> Unit)? = null,
    vibrator: Vibrator,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Uzunluk",
            modifier = Modifier

                .padding(16.dp)
        )
        Text(text = passwordLength.toString())

        Slider(
            value = passwordLength.toFloat(),
            onValueChange = {
                onValueChange(it.toInt())
            },
            onValueChangeFinished = onValueChangeFinished,
            valueRange = 4f..128f,
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 16.dp
                )
                .weight(2f),
            steps = 124
        )
    }
}
