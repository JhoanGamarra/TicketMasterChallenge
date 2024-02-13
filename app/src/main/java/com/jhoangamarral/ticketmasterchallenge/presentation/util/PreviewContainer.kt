package com.jhoangamarral.ticketmasterchallenge.presentation.util

import androidx.compose.runtime.Composable
import com.jhoangamarral.ticketmasterchallenge.presentation.ui.theme.AppTheme

@Composable
fun PreviewContainer(
    content: @Composable () -> Unit
) {
    AppTheme {
        content()
    }
}