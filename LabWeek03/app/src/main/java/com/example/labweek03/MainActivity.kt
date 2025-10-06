package com.example.labweek03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    // light pink background like your preview
                    color = Color(0xFFEBD8D8)
                ) {
                    ChatScreen(messages = demoMessages)
                }
            }
        }
    }
}

/*=== The Logic ===*/
data class ChatMessage(val sender: String, val text: String)

// Small list, repeated to make the screen scroll.
private val demoMessages: List<ChatMessage> = buildList {
    val block = listOf(
        ChatMessage("Shayan", "Hi!"),
        ChatMessage("Hamed", "hey Shayan How are you?"),
        ChatMessage("Shayan", "Test..1..2...3"),
        ChatMessage("Shayan", "i'm fine but ! \n I hate coding!!!")
    )
    repeat(4) { addAll(block) }
}

/*===simple UI like background and messaghe bobble ===*/
@Composable
fun ChatScreen(messages: List<ChatMessage>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues()),
        contentPadding = PaddingValues(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(messages) { msg ->
            ChatBubble(message = msg)
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        ChipAvatar()

        Spacer(Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = message.sender,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(4.dp))

            Surface(
                shape = RoundedCornerShape(14.dp),
                tonalElevation = 1.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun ChipAvatar() {
    Surface(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape),
        shape = CircleShape,
        border = BorderStroke(2.dp, Color(0xFFFF3B30)),
        color = Color(0xFFF3F3F3)
    ) {
        Image(
            painter = painterResource(id = R.drawable.scriptorbit512),
            contentDescription = "User Avatar",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        )
    }
}
