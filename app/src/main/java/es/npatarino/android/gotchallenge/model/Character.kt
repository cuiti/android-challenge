package es.npatarino.android.gotchallenge.model

import com.google.gson.annotations.SerializedName

data class Character(@SerializedName("name") val name: String,
                     @SerializedName("imageUrl") val imageUrl: String,
                     @SerializedName("description") val description: String,
                     @SerializedName("houseImageUrl") val houseImageUrl: String,
                     @SerializedName("houseName") val houseName: String,
                     @SerializedName("houseId") val houseId: String)