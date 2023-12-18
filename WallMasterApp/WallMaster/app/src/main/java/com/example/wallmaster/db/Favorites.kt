package com.example.wallmaster.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class Favorites(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun insertImage(idImg: String, img: ByteArray, tag: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("idImg", idImg)
            put("img", img)
            put("tag", tag)
        }

        return db.insert("favorites", null, values)
    }

    @SuppressLint("Range")
    fun getAllImages(): List<List<Any>> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM favorites", null)
        val images = mutableListOf<List<Any>>()

        while (cursor.moveToNext()) {
            val idImg = cursor.getString(cursor.getColumnIndex("idImg"))
            val img = cursor.getBlob(cursor.getColumnIndex("img"))
            val tag = cursor.getString(cursor.getColumnIndex("tag"))

            images.add(listOf(idImg, img, tag))
        }

        cursor.close()

        return images
    }

    fun deleteImages(idImg: String): Int {
        val db = dbHelper.writableDatabase
        return db.delete("favorites", "idImg=?", arrayOf(idImg))
    }

    fun checkExist(idImg: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT 1 FROM favorites WHERE idImg=?", arrayOf(idImg))
        val check = cursor.count > 0
        cursor.close()
        return check
    }
}