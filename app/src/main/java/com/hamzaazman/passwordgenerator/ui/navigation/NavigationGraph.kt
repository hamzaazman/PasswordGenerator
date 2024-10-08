package com.hamzaazman.passwordgenerator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hamzaazman.passwordgenerator.ui.password.PasswordScreen
import com.hamzaazman.passwordgenerator.ui.password.PasswordViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = Modifier.then(modifier),
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("Password") {
            val viewModel: PasswordViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            PasswordScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }

    }
}