package com.antonshilov.storybook.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antonshilov.storybook.model.ControlType
import com.antonshilov.storybook.model.Story
import com.antonshilov.storybook.model.Storybook
import com.antonshilov.storybook.model.controls.BooleanControl
import com.antonshilov.storybook.model.controls.ColorControl
import com.antonshilov.storybook.model.controls.StringControl
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
actual fun Storybook(storybook: Storybook) {
    val scope = rememberCoroutineScope()
    var selected by remember(storybook) { mutableStateOf<Story>(storybook.groups.first().stories.first()) }

    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetContent = {
            ControlsAndActions(selected)
        },
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("${selected.group} - ${selected.label}") },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = "Localized description")
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        sheetPeekHeight = 64.dp,
        drawerContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                storybook.groups.forEach { group ->
                    stickyHeader(group.label) {
                        GroupItem(group.label)
                    }
                    items(group.stories, { it.label }) { story ->
                        StoryItem(story.label, story == selected) {
                            selected = story
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            selected.ui(selected, Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun GroupItem(label: String) {
    val style = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    )

    Text(
        text = label,
        style = style,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 4.dp,
                vertical = 2.dp
            )
    )
}

@Composable
fun StoryItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    val style = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    )

    Text(
        text = label,
        style = style,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(if (isSelected) Color.Blue.copy(alpha = 0.2f) else Color.Transparent)
            .padding(vertical = 2.dp)
            .padding(start = 12.dp, end = 4.dp)
    )
}

@Composable
fun ActionList(actions: List<String>) {
    LazyColumn(Modifier.fillMaxHeight()) {
        items(actions) { item ->
            Text(item)
        }
    }
}

@Composable
private fun Controls(controls: List<ControlType>) {
    LazyColumn(Modifier.fillMaxHeight()) {
        items(controls) {
            when (it) {
                is StringControl -> it.ui()
                is BooleanControl -> it.ui()
                is ColorControl -> it.ui()
            }
        }
    }
}

@Composable
fun ControlsAndActions(story: Story) {
    val selectedTab = remember { mutableStateOf(0) }

    Column(Modifier.fillMaxWidth().height(400.dp)) {
        TabRow(selectedTabIndex = selectedTab.value) {
            Tab(selectedTab.value == 0, { selectedTab.value = 0 }) {
                Text("Actions")
            }
            Tab(selectedTab.value == 1, { selectedTab.value = 1 }) {
                Text("Controls")
            }
        }
        when (selectedTab.value) {
            0 -> {
                Controls(story.controls.value)
            }
            1 -> {
                ActionList(story.actions)
            }
        }
    }
}