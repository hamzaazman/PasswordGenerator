package com.hamzaazman.passwordgenerator.ui.password

import android.content.Context
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hamzaazman.passwordgenerator.ui.components.EmptyScreen
import com.hamzaazman.passwordgenerator.ui.components.LoadingBar
import com.hamzaazman.passwordgenerator.ui.password.PasswordContract.UiAction
import com.hamzaazman.passwordgenerator.ui.password.PasswordContract.UiEffect
import com.hamzaazman.passwordgenerator.ui.password.PasswordContract.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
fun PasswordScreen(
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
) {
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> PasswordContent()
    }
}

@Composable
fun PasswordContent() {
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    var password by remember { mutableStateOf(generateNumericPassword(6)) }
    val passwordLength = remember { mutableIntStateOf(6) }

    var useUpperCase by remember { mutableStateOf(false) }
    var useLowerCase by remember { mutableStateOf(false) }
    var useDigits by remember { mutableStateOf(true) }
    var useSpecialChars by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    fun ensureAtLeastOneOptionIsSelected() {
        if (!useUpperCase && !useLowerCase && !useDigits && !useSpecialChars) {
            useLowerCase = true
        }
    }

    fun onCheckedChange(checked: Boolean, setOption: (Boolean) -> Unit) {
        setOption(checked)
        ensureAtLeastOneOptionIsSelected()
        password = generatePassword(
            passwordLength.intValue,
            useUpperCase,
            useLowerCase,
            useDigits,
            useSpecialChars
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                singleLine = false,
                maxLines = 6,
                readOnly = true,
                value = password,
                textStyle = TextStyle(fontSize = 20.sp),
                onValueChange = { password = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .animateContentSize(),
                trailingIcon = {
                    Row {
                        IconButton(onClick = {
                            password = generatePassword(
                                length = passwordLength.intValue,
                                useUpperCase = useUpperCase,
                                useLowerCase = useLowerCase,
                                useDigits = useDigits,
                                useSpecialChars = useSpecialChars
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
                        IconButton(onClick = {
                            copyToClipboard(context, password)
                            Toast.makeText(context, "Parola kopyalandı", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy, contentDescription = "Copy"
                            )
                        }

                    }
                }
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Uzunluk", modifier = Modifier
                    .weight(1F)
                    .padding(16.dp)
            )
            Text(text = passwordLength.intValue.toString())

            Slider(
                value = passwordLength.intValue.toFloat(),
                onValueChange = {
                    passwordLength.intValue = it.toInt()
                    vibrator.vibrate(15)

                    coroutineScope.launch(Dispatchers.Main) {
                        password = generatePassword(
                            length = passwordLength.intValue,
                            useUpperCase = useUpperCase,
                            useLowerCase = useLowerCase,
                            useDigits = useDigits,
                            useSpecialChars = useSpecialChars
                        )
                    }
                },
                valueRange = 4f..64f,
                steps = 32,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(2f)
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        SelectPasswordCharacter(
            label = "Büyük Harf (A-Z)",
            checked = useUpperCase,
            onCheckedChange = {
                onCheckedChange(it) { value ->
                    useUpperCase = value
                }
            })

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        SelectPasswordCharacter(
            label = "Küçük Harf (a-z)",
            checked = useLowerCase,
            onCheckedChange = {
                onCheckedChange(it) { value ->
                    useLowerCase = value
                }
            })

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        SelectPasswordCharacter(label = "Rakam (0-9)", checked = useDigits, onCheckedChange = {
            onCheckedChange(it) { value ->
                useDigits = value
            }
        })

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        SelectPasswordCharacter(
            label = "Özel Karakter (!@^$%)",
            checked = useSpecialChars,
            onCheckedChange = {
                onCheckedChange(it) { value ->
                    useSpecialChars = value
                }
            })

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            password = generatePassword(
                length = passwordLength.intValue,
                useUpperCase = useUpperCase,
                useLowerCase = useLowerCase,
                useDigits = useDigits,
                useSpecialChars = useSpecialChars
            )
        }) {
            Text("Parola Oluştur")
        }

    }


}


@Composable
fun SelectPasswordCharacter(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.scale(0.75f)
        )
    }
}


fun generateNumericPassword(length: Int): String {
    val digits = "0123456789"
    return (1..length)
        .map { digits.random() }
        .joinToString("")
}


fun generatePassword(
    length: Int,
    useUpperCase: Boolean = false,
    useLowerCase: Boolean = false,
    useDigits: Boolean = true,
    useSpecialChars: Boolean = false
): String {
    val upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz"
    val digits = "0123456789"
    val specialChars = "!@^$%*&#"

    val charPool = buildString {
        if (useUpperCase) append(upperCaseLetters)
        if (useLowerCase) append(lowerCaseLetters)
        if (useDigits) append(digits)
        if (useSpecialChars) append(specialChars)
    }



    return (1..length)
        .map { charPool.random() }
        .joinToString("")
}

fun copyToClipboard(context: android.content.Context, text: String) {
    val clipboard =
        context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    val clip = android.content.ClipData.newPlainText("password", text)
    clipboard.setPrimaryClip(clip)
}

@Preview(showBackground = true)
@Composable
fun PasswordScreenPreview(
    @PreviewParameter(PasswordScreenPreviewProvider::class) uiState: UiState,
) {
    PasswordScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
    )
}