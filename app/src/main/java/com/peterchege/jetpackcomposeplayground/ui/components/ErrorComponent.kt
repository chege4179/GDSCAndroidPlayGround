
package com.peterchege.jetpackcomposeplayground.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ErrorComponent(
    modifier: Modifier = Modifier.fillMaxSize(),
    retryCallback:() -> Unit,
    errorMessage:String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = errorMessage,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { retryCallback() }
        ){
            Text(text = "Retry")
        }

    }
}