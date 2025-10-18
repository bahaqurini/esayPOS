package org.aasoft.easypos.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ItemsViewModel: ViewModel() {
    private val _items = MutableStateFlow(mutableListOf<SellItem>())
    val items = _items.asStateFlow()
    fun addItem(item: SellItem) {
        println("Adding item: $item ,size: ${_items.value.size}")
        _items.value.add(item)
    }
    fun removeItem(item: SellItem) {
        _items.value.remove(item)
    }
    fun clearItems() {
        _items.value.clear()
    }
    fun getItem(index: Int): SellItem {
        return _items.value[index]
    }
    fun setItem(index: Int, item: SellItem) {
        _items.value[index] = item
    }
    fun getItems(): List<SellItem> {
        return _items.value
    }
    fun setItems(items: List<SellItem>) {
        _items.value = items.toMutableList()
    }
    fun size(): Int {
        return _items.value.size
    }
    fun isEmpty(): Boolean {
        return _items.value.isEmpty()
    }
    fun contains(item: SellItem): Boolean {
        return _items.value.contains(item)
    }
    fun indexOf(item: SellItem): Int {
        return _items.value.indexOf(item)
    }
}




