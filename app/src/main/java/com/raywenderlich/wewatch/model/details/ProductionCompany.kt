package com.raywenderlich.wewatch.data.model.details

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductionCompany {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("logo_path")
    @Expose
    var logoPath: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("origin_country")
    @Expose
    var originCountry: String? = null

    override fun toString(): String {
        return "ProductionCompany(id=$id, logoPath=$logoPath, name=$name, originCountry=$originCountry)"
    }
}