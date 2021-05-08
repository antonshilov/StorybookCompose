package com.antonshilov.storybook.ui

import androidx.compose.runtime.Composable
import com.antonshilov.storybook.model.Storybook

@Composable
expect fun Storybook(storybook: Storybook)