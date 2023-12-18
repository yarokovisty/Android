package com.example.wallmaster

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallmaster.databinding.ActivityHistoryBinding
import com.example.wallmaster.databinding.ActivityMainMenuBinding
import com.example.wallmaster.db.Favorites
import com.example.wallmaster.db.History
import com.google.android.material.navigation.NavigationView

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyRepository: History
    private val adapter = ImageAdapter {selectedImage ->
        // обработка нажатия, переход на картинку
        val intent = Intent(this@HistoryActivity, ImageActivity::class.java)
        intent.putExtra("selectedImage", selectedImage.byteArr)
        setResult(RESULT_OK, intent)
        intent.putExtra("selectedTag", selectedImage.tag)
        setResult(RESULT_OK, intent)
        intent.putExtra("selectedId", selectedImage.id)
        setResult(RESULT_OK, intent)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Navigation bar
        val drawerConstraint = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(this, drawerConstraint, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerConstraint.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.main -> {
                    val intent = Intent(this, MainMenu::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                }
                R.id.favorites -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                }
                R.id.history -> {
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                }

            }
            true
        }

    }

    override fun onStart() {
        super.onStart()
        adapter.clearWalls()

        historyRepository = History(this)

        val listHistory = historyRepository.getAllImages()

        for (i in listHistory.indices) {
            val idImg = listHistory[i][0] as String
            val ba = listHistory[i][1] as ByteArray
            val tag = listHistory[i][2] as String
            val bitmap: Bitmap? = BitmapFactory.decodeByteArray(ba, 0, ba.size)

            val img = Image(idImg, bitmap, ba, tag)
            adapter.addWall(img)
        }

        init()
    }

    private fun init(){
        binding.apply {
            rcGroups.layoutManager = GridLayoutManager(this@HistoryActivity, 3)
            rcGroups.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            R.id.icon_delete -> {
                historyRepository.clearHistory()
            }
        }

        return true
    }
}