package com.hamzaazman.passwordgenerator.ui.password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class PasswordSettings(
    val passwordLength: Int = 4,
    val useUpperCase: Boolean = false,
    val useLowerCase: Boolean = false,
    val useDigits: Boolean = true,
    val useSpecialChars: Boolean = false
)


@HiltViewModel
class PasswordViewModel @Inject constructor() : ViewModel() {

    private val _passwordSettings = MutableStateFlow(PasswordSettings())
    val passwordSettings: StateFlow<PasswordSettings> get() = _passwordSettings

    private val _password = mutableStateOf(generateNumericPassword(6))
    val password: State<String> get() = _password

    /*
    private val _passwordLength = mutableIntStateOf(6)
    val passwordLength: State<Int> get() = _passwordLength

    private val _useUpperCase = mutableStateOf(false)
    val useUpperCase: State<Boolean> get() = _useUpperCase

    private val _useLowerCase = mutableStateOf(false)
    val useLowerCase: State<Boolean> get() = _useLowerCase

    private val _useDigits = mutableStateOf(true)
    val useDigits: State<Boolean> get() = _useDigits

    private val _useSpecialChars = mutableStateOf(false)
    val useSpecialChars: State<Boolean> get() = _useSpecialChars


     */
    private val _copyState = MutableStateFlow(false)
    val copyState: StateFlow<Boolean> get() = _copyState


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
        //_passwordLength.intValue = length
        updatePassword()
    }

    fun setUseUpperCase(value: Boolean) {
        _passwordSettings.value = _passwordSettings.value.copy(useUpperCase = value)
        //_useUpperCase.value = value
        ensureAtLeastOneOptionSelected()
        updatePassword()
    }

    fun setUseLowerCase(value: Boolean) {
        _passwordSettings.value = _passwordSettings.value.copy(useLowerCase = value)
        //_useLowerCase.value = value
        ensureAtLeastOneOptionSelected()
        updatePassword()
    }

    fun setUseDigits(value: Boolean) {
        _passwordSettings.value = _passwordSettings.value.copy(useDigits = value)
        // _useDigits.value = value
        ensureAtLeastOneOptionSelected()
        updatePassword()
    }

    fun setUseSpecialChars(value: Boolean) {
        _passwordSettings.value = _passwordSettings.value.copy(useSpecialChars = value)
        // _useSpecialChars.value = value
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