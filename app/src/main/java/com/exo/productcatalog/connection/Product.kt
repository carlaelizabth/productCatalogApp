package com.exo.productcatalog.connection

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Product(
    val id: String,
    @SerializedName("name")
    val productName: String,
    @SerializedName("thumbnail_url")
    val imageUrl: String,
    val price: String,
    @SerializedName("provider")
    val pSupplier: String,
    @SerializedName("delivery")
    val shippingCost: Double,
    @SerializedName("desc")
    val pDescription: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(productName)
        parcel.writeString(imageUrl)
        parcel.writeString(price)
        parcel.writeString(pSupplier)
        parcel.writeDouble(shippingCost)
        parcel.writeString(pDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
