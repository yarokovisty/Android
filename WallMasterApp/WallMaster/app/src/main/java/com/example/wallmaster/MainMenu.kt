package com.example.wallmaster

import android.annotation.SuppressLint
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
import com.example.wallmaster.databinding.ActivityMainMenuBinding
import com.example.wallmaster.retrofit.ImageApi
import com.example.wallmaster.retrofit.TitleApi
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainMenu : AppCompatActivity() {
    private lateinit var bindingClass: ActivityMainMenuBinding
    private val adapter = WallAdapter {selectedWall ->
        // обработка нажатия, переход на другую активность
        val intent = Intent(this@MainMenu, GroupImgActivity::class.java)
        intent.putExtra("selectedWall", selectedWall.title)
        setResult(RESULT_OK, intent)
        startActivity(intent)
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainMenuBinding.inflate(layoutInflater)
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

        val retrofit = Retrofit.Builder()
            .baseUrl("http://85.143.78.47:8080/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val titleApi = retrofit.create(TitleApi::class.java)
        val imageApi = retrofit.create(ImageApi::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val titleDeferred = async {titleApi.getTitle()}
            val imageDeferred = async { imageApi.getImage() }

            val title = titleDeferred.await()
            val image = imageDeferred.await()
            val titles = arrayListOf<String>()
            val images = arrayListOf<ByteArray>()

            runOnUiThread{
                title.titles.forEach{ titles.add(it) }
                image.paths.forEach { images.add(it) }

                for (i in titles.indices) {
                    val bitmap: Bitmap? = BitmapFactory.decodeByteArray(images[i], 0, images[i].size)

                    val wall = Wall(i, bitmap, titles[i])
                    adapter.addWall(wall)
                }
            }

        }

        init()

    }

    private fun init(){
        bindingClass.apply {
            rcGroups.layoutManager = GridLayoutManager(this@MainMenu, 3)
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