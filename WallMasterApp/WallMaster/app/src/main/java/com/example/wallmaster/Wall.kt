package com.example.wallmaster

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class Wall(val id: Int, val imgId: Bitmap?, val title: String)
