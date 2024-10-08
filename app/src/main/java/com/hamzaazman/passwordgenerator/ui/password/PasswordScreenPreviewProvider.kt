package com.hamzaazman.passwordgenerator.ui.password

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class PasswordScreenPreviewProvider : PreviewParameterProvider<PasswordContract.UiState> {
    override val values: Sequence<PasswordContract.UiState>
        get() = sequenceOf(
            PasswordContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            PasswordContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            PasswordContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}