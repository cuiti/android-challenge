package es.npatarino.android.gotchallenge.model

import com.google.gson.annotations.SerializedName

data class House(@SerializedName("houseImageUrl") val imageUrl: String,
                 @SerializedName("houseName") val name: String,
                 @SerializedName("houseId") val id: String)