package com.codingwithmitch.mvvmrecipeapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * See Recipe example: https://food2fork.ca/
 */
@Parcelize
class Recipe (
    var id: Int? = null,
    var title: String? = null,
    var publisher: String? = null,
    var featuredImage: String? = null,
    var rating: Int? = 0,
    var sourceUrl: String? = null,
    var description: String? = null,
    var cookingInstructions: String? = null,
    var ingredients: List<String>? = null,
    var dateAdded: String? = null,
    var dateUpdated: String? = null,
): Parcelable