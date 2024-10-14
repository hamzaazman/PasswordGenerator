package com.hamzaazman.passwordgenerator.ui.password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamzaazman.passwordgenerator.data.model.HistoryEntity
import com.hamzaazman.passwordgenerator.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PasswordSettings(
    val passwordLength: Int = 4,
    val useUpperCase: Boolean = false,
    val useLowerCase: Boolean = false,
    val useDigits: Boolean = true,
    val useSpecialChars: Boolean = false
)


@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _passwordSettings = MutableStateFlow(PasswordSettings())
    val passwordSettings: StateFlow<PasswordSettings> get() = _passwordSettings

    private val _password = mutableStateOf(generateNumericPassword(6))
    val password: State<String> get() = _password

    private val _copyState = MutableStateFlow(false)
    val copyState: StateFlow<Boolean> get() = _copyState


    fun setPasswordHistory(password: String) = viewModelScope.launch(Dispatchers.IO) {
        mainRepository.insertHistory(HistoryEntity(password = password))
    }

    fun setCopyState(value: Boolean) {
        _copyState.value = value
    }

    fun resetCopyState() {
        _copyState.value = false
    }

    fun updatePassword() {
        _password.value = generatePassword(
            length = _passwordSettings.value.passwordLength,
            useUpperCase = _passwordSettings.value.useUpperCase,
            useLowerCase = _passwordSettings.value.useLowerCase,
            useDigits = _passwordSettings.value.useDigits,
            useSpecialChars = _passwordSettings.value.useSpecialChars
        )
    }

    fun setPasswordLength(length: Int) {
        _passwordSettings.value = _passwordSettings.value.copy(passwordLength = length)
        updatePassword()
    }

    fun setUseUpperCase(value: Boolean) {
        _passwordSettings.value = _passwordSettings.value.copy(useUpperCase = value)
        ensureAtLeastOneOptionSelected()
        updatePassword()
    }

    fun setUseLowerCase(value: Boolean) {
        _passwordSettings.value = _passwordSettings.value.copy(useLowerCase = value)
        ensureAtLeastOneOptionSelected()
        updatePassword()
    }

    fun setUseDigits(value: Boolean) {
        _passwordSettings.value = _passwordSettings.value.copy(useDigits = value)
        ensureAtLeastOneOptionSelected()
        updatePassword()
    }

    fun setUseSpecialChars(value: Boolean) {
        _passwordSettings.value = _passwordSettings.value.copy(useSpecialChars = value)
        ensureAtLeastOneOptionSelected()
        updatePassword()
    }

    private fun ensureAtLeastOneOptionSelected() {
        val atLeastOneOptionSelected =
            _passwordSettings.value.useUpperCase ||
                    _passwordSettings.value.useLowerCase ||
                    _passwordSettings.value.useDigits ||
                    _passwordSettings.value.useSpecialChars

        if (!atLeastOneOptionSelected) {
            _passwordSettings.value = _passwordSettings.value.copy(
                useLowerCase = true,
            )
        }
    }

    private fun generateNumericPassword(length: Int): String {
        val digits = "0123456789"
        return (1..length)
            .map { digits.random() }
            .joinToString("")
    }

    private fun generatePassword(
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

}