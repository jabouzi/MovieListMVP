package com.raywenderlich.wewatch.data.model.details

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SpokenLanguage {
    @SerializedName("iso_639_1")
    @Expose
    var iso6391: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    override fun toString(): String {
        return "SpokenLanguage(iso6391=$iso6391, name=$name)"
    }
}