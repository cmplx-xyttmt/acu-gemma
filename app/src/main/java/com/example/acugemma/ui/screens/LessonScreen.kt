package com.example.acugemma.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.acugemma.ai.GemmaAiService

import com.example.acugemma.ui.viewmodels.LessonViewModel
import com.example.acugemma.ui.viewmodels.LessonUiState
import com.example.acugemma.ui.viewmodels.Message
import com.example.acugemma.ui.viewmodels.Sender
import com.example.acugemma.ui.viewmodels.ViewModelFactory
import com.example.acugemma.ui.utils.MarkdownText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    topicId: String,
    onBackClick: () -> Unit
) {
    val viewModel: LessonViewModel = viewModel(factory = ViewModelFactory(GemmaAiService(LocalContext.current), topicId))
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember {
        mutableIntStateOf(0)
    } // Home tab selected
    var userMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lesson",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF181511)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF181511)
                )
            )
        },
        
        containerColor = Color.White
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            when (val state = uiState) {
                
                is LessonUiState.Processing -> {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .imePadding(),

                        verticalArrangement = Arrangement.Bottom
                    ) {
                        items(state.messages) { message ->
                            MessageBubble(message = message)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = userMessage,
                            onValueChange = { userMessage = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("Type your message...") },
                            enabled = false
                        )
                        IconButton(onClick = {}, enabled = false) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is LessonUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .imePadding(),

                        verticalArrangement = Arrangement.Bottom
                    ) {
                        items(state.messages) { message ->
                            MessageBubble(message = message)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = userMessage,
                            onValueChange = { userMessage = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("Type your message...") }
                        )
                        IconButton(onClick = {
                            if (userMessage.isNotBlank()) {
                                viewModel.sendMessage(userMessage)
                                userMessage = ""
                            }
                        }) {
                            Icon(Icons.Default.Send, contentDescription = "Send")
                        }
                    }
                }
                is LessonUiState.Error -> {
                    Text(text = state.message)
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if (message.sender == Sender.User) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = if (message.sender == Sender.User) Color(0xFFD3E0DC) else Color(0xFFF0F0F0),
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                        bottomStart = if (message.sender == Sender.User) 12.dp else 2.dp,
                        bottomEnd = if (message.sender == Sender.User) 2.dp else 12.dp
                    )
                )
                .padding(12.dp)
        ) {
            MarkdownText(
                markdown = message.text,
                color = Color(0xFF181511)
            )
        }
    }
}
