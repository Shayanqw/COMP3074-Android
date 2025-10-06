package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen(
                    message = "Hi 👋  Welcome to my App",
                    name = "Shayan Pourahmad",
                    studentId = "101474651",
                    campus = "Casa Loma",
                    onGoSecond = { startActivity(Intent(this, SecondPage::class.java)) }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    message: String,
    name: String,
    studentId: String,
    campus: String,
    onGoSecond: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(message, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(24.dp))
            Text("My Name is : $name", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("Student ID: $studentId", fontSize = 20.sp)
            Spacer(Modifier.height(8.dp))
            Text("Campus: $campus", fontSize = 18.sp)
            Spacer(Modifier.height(24.dp))
            Button(onClick = onGoSecond, modifier = Modifier.fillMaxWidth()) {
                Text("Go to Second Activity")
            }
        }
    }
}
