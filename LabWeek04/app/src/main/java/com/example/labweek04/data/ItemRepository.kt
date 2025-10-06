package com.example.labweek04.data

import kotlinx.coroutines.flow.Flow

class ItemRepository(private val dao: ItemDao) {

    val allItems: Flow<List<Item>> = dao.getAll()

    suspend fun add(name: String, qty: Int) {
        dao.insert(Item(name = name, quantity = qty))
    }

    suspend fun remove(item: Item) {
        dao.delete(item)
    }

    suspend fun clear() {
        dao.deleteAll()
    }
}
