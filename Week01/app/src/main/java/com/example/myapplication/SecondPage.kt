package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource


class SecondPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SecondScreen(
                    onOpenCollege = {
                        val uri = Uri.parse("https://www.georgebrown.ca")
                        startActivity(Intent(Intent.ACTION_VIEW, uri))
                    },
                    onBack = { finish() }   // 👈 this will go back to MainActivity
                )
            }
        }
    }
}

@Composable
fun SecondScreen(
    onOpenCollege: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.images), // replace with your file name
            contentDescription = "College Logo",
            modifier = Modifier
                .size(150.dp) // resize as needed
                .padding(bottom = 24.dp)
        )
        Button(
            onClick = onOpenCollege,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open College Website")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(   // 👈 Second button
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to First Page")
        }
    }
}
