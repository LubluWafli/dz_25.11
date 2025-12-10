package com.example.dz_2511

data class ListItem(
    val id: Int,
    val title: String,
    val type: ItemType,
    val description: String = "Описание..."
)

enum class ItemType {
    NUMBER,
    IMAGE
}