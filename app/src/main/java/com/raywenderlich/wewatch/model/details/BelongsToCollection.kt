package com.raywenderlich.wewatch.data.model.details

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class BelongsToCollection {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null

    override fun toString(): String {
        return "BelongsToCollection(id=$id, name=$name, posterPath=$posterPath, backdropPath=$backdropPath)"
    }
}