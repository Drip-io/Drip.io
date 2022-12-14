package com.dripio.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dripio.domain.entity.Category

@Entity
data class EntityCategory(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    var id: Long? = null,
    @ColumnInfo(name = "name") var name: String
)

fun EntityCategory.toDomain() = Category(
    id = this.id ?: 0,
    name = this.name
)
