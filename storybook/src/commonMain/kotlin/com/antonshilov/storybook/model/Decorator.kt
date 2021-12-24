package com.antonshilov.storybook.model

import androidx.compose.runtime.Composable

abstract class Decorator {

    @Composable
    fun decorate(nextDecorators: List<Decorator>, content: @Composable () -> Unit) {
        if (nextDecorators.isNotEmpty()) {
            decorate {
                nextDecorators.first().decorate(nextDecorators.drop(1), content)
            }
        } else {
            decorate { content() }
        }
    }

    @Composable
    abstract fun decorate(content: @Composable () -> Unit)

    @Composable
    abstract fun controlUi()
}