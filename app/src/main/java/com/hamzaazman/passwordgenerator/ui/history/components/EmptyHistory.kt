package com.hamzaazman.passwordgenerator.ui.history.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.hamzaazman.passwordgenerator.R


@Composable
fun EmptyHistory(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.empty_box),
            contentDescription = "Empty Box",
            modifier = Modifier
                .padding(16.dp)
                .size(200.dp)
                .alpha(0.5f)
        )

        Text(
            text =  "No history found",
            modifier = Modifier
                .padding(16.dp)
                .alpha(0.5f),
            style = MaterialTheme.typography.titleLarge
        )

    }
}