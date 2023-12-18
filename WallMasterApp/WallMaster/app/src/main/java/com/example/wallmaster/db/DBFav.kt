package com.example.wallmaster.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "DataBase.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME_F = "favorites"
        const val TABLE_NAME_H = "history"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME_F (_id INTEGER PRIMARY KEY, idImg TEXT, img BLOB, tag TEXT)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME_H (_id INTEGER PRIMARY KEY, idImg TEXT, img BLOB, tag TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_F")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_H")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }



}