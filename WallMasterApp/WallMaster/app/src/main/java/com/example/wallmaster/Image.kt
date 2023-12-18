package com.example.wallmaster

import android.graphics.Bitmap

data class Image(val id: String, val imgId: Bitmap?, val byteArr: ByteArray, val tag: String)