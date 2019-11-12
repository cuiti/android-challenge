package es.npatarino.android.gotchallenge.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Character(
        @PrimaryKey @SerializedName("name") val name: String,
        @SerializedName("imageUrl") val imageUrl: String,
        @SerializedName("description") val description: String,
        @SerializedName("houseImageUrl") val houseImageUrl: String,
        @SerializedName("houseName") val houseName: String,
        @SerializedName("houseId") val houseId: String) {

    val house get() = House(houseImageUrl, houseName, houseId)
    fun belongsTo(house: House) = houseId == house.id
}