package com.example.labweek04.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.labweek04.data.Item

@Composable
fun ItemsScreen(
    vm: ItemsViewModel,
    modifier: Modifier = Modifier
) {
    val items by vm.items.collectAsState() // collect the StateFlow
    var name by remember { mutableStateOf("") }
    var qtyText by remember { mutableStateOf("1") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Room Items", style = MaterialTheme.typography.headlineSmall)

        // Input row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Item name") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = qtyText,
                onValueChange = { qtyText = it.filter { ch -> ch.isDigit() }.ifEmpty { "0" } },
                label = { Text("Qty") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.width(96.dp)
            )
            Button(
                onClick = {
                    val q = qtyText.toIntOrNull() ?: 0
                    vm.addItem(name, q)
                    name = ""
                    qtyText = "1"
                }
            ) { Text("Add") }
        }

        // List
        Divider()
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items, key = { it.id }) { item ->
                ItemRow(
                    item = item,
                    onDelete = { vm.removeItem(item) }
                )
            }
        }

        // Footer actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(onClick = { vm.clearAll() }) {
                Text("Clear All")
            }
        }
    }
}

@Composable
private fun ItemRow(
    item: Item,
    onDelete: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(item.name, style = MaterialTheme.typography.titleMedium)
                Text("Quantity: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
