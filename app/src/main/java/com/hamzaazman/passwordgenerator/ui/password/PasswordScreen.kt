package com.hamzaazman.passwordgenerator.ui.password

import android.content.Context
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hamzaazman.passwordgenerator.ui.password.components.PasswordDisplay
import com.hamzaazman.passwordgenerator.ui.password.components.PasswordLengthSlider
import com.hamzaazman.passwordgenerator.ui.password.components.SelectPasswordCharacter

@Composable
fun PasswordScreen(
    viewModel: PasswordViewModel = hiltViewModel()
) {

    val password by viewModel.password
    val passwordSettings by viewModel.passwordSettings.collectAsState()
    /*
      val passwordLength by viewModel.passwordLength
      val useUpperCase by viewModel.useUpperCase
      val useLowerCase by viewModel.useLowerCase
      val useDigits by viewModel.useDigits
      val useSpecialChars by viewModel.useSpecialChars
  */
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val copyState by viewModel.copyState.collectAsState()


    LaunchedEffect(copyState) {
        if (copyState) {
            viewModel.setPasswordHistory(password)
            viewModel.resetCopyState()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PasswordDisplay(
            password = password,
            onRefreshClicked = {
                viewModel.updatePassword()
            },
            onCopyClicked = {
                copyToClipboard(context, password)
                viewModel.setCopyState(true)
            }
        )


        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        PasswordLengthSlider(
            passwordLength = passwordSettings.passwordLength,
            onValueChange = { newValue ->
                viewModel.setPasswordLength(newValue)
            },
            onValueChangeFinished = {
                viewModel.updatePassword()
            },
            vibrator = vibrator
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        SelectPasswordCharacter(
            label = "Büyük Harf (A-Z)",
            checked = passwordSettings.useUpperCase,
            onCheckedChange = { value ->
                viewModel.setUseUpperCase(value)
            }
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        SelectPasswordCharacter(
            label = "Küçük Harf (a-z)",
            checked = passwordSettings.useLowerCase,
            onCheckedChange = { value ->
                viewModel.setUseLowerCase(value)
            })

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        SelectPasswordCharacter(label = "Rakam (0-9)",
            checked = passwordSettings.useDigits,
            onCheckedChange = { value ->
                viewModel.setUseDigits(value)
            })

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        SelectPasswordCharacter(
            label = "Özel Karakter (!@^$%)",
            checked = passwordSettings.useSpecialChars,
            onCheckedChange = { value ->
                viewModel.setUseSpecialChars(value)
            }
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.updatePassword()
        }) {
            Text("Parola Oluştur")
        }

    }


}


fun generateNumericPassword(length: Int): String {
    val digits = "0123456789"
    return (1..length)
        .map { digits.random() }
        .joinToString("")
}


fun copyToClipboard(context: android.content.Context, text: String) {
    val clipboard =
        context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    val clip = android.content.ClipData.newPlainText("password", text)
    clipboard.setPrimaryClip(clip)
}
