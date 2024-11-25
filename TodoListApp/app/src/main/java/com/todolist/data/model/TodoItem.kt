package com.todolist.data.model

import com.google.gson.annotations.SerializedName

data class TodoItem(
    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String? = null,
    @SerializedName("isCompleted") var isCompleted: Boolean = false,
    var createdAt: String = ""
) {
    constructor(title: String) : this(
        title = title,
        description = "",
        isCompleted = false,
        createdAt = ""
    )
}
