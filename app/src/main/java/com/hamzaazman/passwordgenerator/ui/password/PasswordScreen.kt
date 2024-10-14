package com.hamzaazman.passwordgenerator.ui.password

import android.content.Context
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hamzaazman.passwordgenerator.ui.password.components.PasswordDisplay
import com.hamzaazman.passwordgenerator.ui.password.components.PasswordLengthSlider
import com.hamzaazman.passwordgenerator.ui.password.components.SelectPasswordCharacter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: PasswordViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val password by viewModel.password

    val passwordSettings by viewModel.passwordSettings.collectAsState()
    val copyState by viewModel.copyState.collectAsState()

    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    LaunchedEffect(copyState) {
        if (copyState) {
            viewModel.setPasswordHistory(password)
            viewModel.resetCopyState()
        }
    }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Password Generator") },
                navigationIcon = {},
                actions = {},
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            PasswordContent(
                modifier = modifier.padding(innerPadding),
                password = password,
                passwordSettings = passwordSettings,
                viewModel = viewModel,
                context = context,
                vibrator = vibrator,
                scrollBehavior = scrollBehavior
            )
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordContent(
    modifier: Modifier,
    password: String,
    passwordSettings: PasswordSettings,
    viewModel: PasswordViewModel,
    context: Context,
    vibrator: Vibrator,
    scrollBehavior: TopAppBarScrollBehavior
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
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

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            viewModel.updatePassword()
        }) {
            Text("Parola Oluştur")
        }

    }

}


fun copyToClipboard(context: android.content.Context, text: String) {
    val clipboard =
        context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    val clip = android.content.ClipData.newPlainText("password", text)
    clipboard.setPrimaryClip(clip)
}
