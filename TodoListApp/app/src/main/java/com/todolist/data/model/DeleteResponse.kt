package com.todolist.data.model

import com.google.gson.annotations.SerializedName

data class DeleteResponse(
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean
)
