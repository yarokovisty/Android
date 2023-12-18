package com.example.wallmaster

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallmaster.databinding.ActivityFavoriteBinding
import com.example.wallmaster.databinding.ActivityMainMenuBinding
import com.example.wallmaster.db.Favorites
import com.google.android.material.navigation.NavigationView

class FavoriteActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityFavoriteBinding
    private lateinit var favRepository: Favorites
    private val adapter = ImageAdapter {selectedImage ->
        // обработка нажатия, переход на картинку
        val intent = Intent(this@FavoriteActivity, ImageActivity::class.java)
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
        bindingClass = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        setSupportActionBar(bindingClass.toolbar)

        // Navigation bar
        val drawerConstraint = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(this, drawerConstraint, bindingClass.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
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

        favRepository = Favorites(this)

        val listFav = favRepository.getAllImages()

        for (i in listFav.indices) {
            val idImg = listFav[i][0] as String
            val ba = listFav[i][1] as ByteArray
            val tag = listFav[i][2] as String
            val bitmap: Bitmap? = BitmapFactory.decodeByteArray(ba, 0, ba.size)

            val img = Image(idImg, bitmap, ba, tag)
            adapter.addWall(img)
        }

        init()
    }

    private fun init(){
        bindingClass.apply {
            rcGroups.layoutManager = GridLayoutManager(this@FavoriteActivity, 3)
            rcGroups.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val customMenuItem = menu?.findItem(R.id.ic_acc)
        val customLayout = customMenuItem?.actionView as ConstraintLayout

        customLayout.setOnClickListener{
            Toast.makeText(this, "account", Toast.LENGTH_SHORT).show()
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.ic_search ->{
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.ic_login ->{
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_login)
                dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.dialog_bg))
                dialog.setCancelable(true)

                dialog.show()
            }

        }

        return true
    }
}