package com.example.labweek04.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.labweek04.data.Item
import com.example.labweek04.data.ItemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ItemsViewModel(private val repo: ItemRepository) : ViewModel() {

    // Expose items as a StateFlow that the UI can collect
    val items: StateFlow<List<Item>> = repo.allItems
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun addItem(name: String, qty: Int) {
        viewModelScope.launch {
            if (name.isNotBlank()) repo.add(name.trim(), qty)
        }
    }

    fun removeItem(item: Item) {
        viewModelScope.launch {
            repo.remove(item)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repo.clear()
        }
    }
}

// Simple factory so we can pass the repository into the ViewModel
class ItemsViewModelFactory(private val repo: ItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemsViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
