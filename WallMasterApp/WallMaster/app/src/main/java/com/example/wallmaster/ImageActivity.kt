package com.example.wallmaster

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.WallpaperManager
import android.content.ClipData.Item
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.wallmaster.databinding.ActivityImageBinding
import com.example.wallmaster.db.Favorites
import com.example.wallmaster.db.History
import java.io.OutputStream
import java.time.LocalTime

class ImageActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityImageBinding
    private lateinit var bitmap: Bitmap
    private lateinit var receivedTag: String
    private lateinit var receivedImage: ByteArray
    private lateinit var receivedId: String
    private lateinit var favRepository: Favorites
    private lateinit var historyRepository: History
    private var check = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityImageBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        favRepository = Favorites(this)
        historyRepository = History(this)

        setSupportActionBar(bindingClass.toolbar)

        receivedImage = intent.getByteArrayExtra("selectedImage")!!
        receivedTag = intent.getStringExtra("selectedTag")!!
        receivedId = intent.getStringExtra("selectedId")!!

        bitmap = BitmapFactory.decodeByteArray(receivedImage, 0, receivedImage.size)
        bindingClass.clImage.setBackgroundDrawable(BitmapDrawable(resources, bitmap))

        bindingClass.btnGallery.setOnClickListener {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                saveBitmapToGallery(bitmap, this, "WallMater${LocalTime.now()}", receivedTag)
                if (!historyRepository.checkExist(receivedId))
                    historyRepository.insertImage(receivedId, receivedImage, receivedTag)
            }

        }


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickBackground(view: View) = with(bindingClass){
        if (btnGroup.visibility == View.VISIBLE && toolbar.visibility == View.VISIBLE) {
            btnGroup.visibility = View.INVISIBLE
            toolbar.visibility = View.INVISIBLE
            tvTag.visibility = View.INVISIBLE
        }
        else {
            btnGroup.visibility = View.VISIBLE
            toolbar.visibility = View.VISIBLE
            val menuItemInfo = toolbar.menu.findItem(R.id.icon_info)
            menuItemInfo.icon = getDrawable(R.drawable.baseline_info_24)

        }

    }

    private fun saveBitmapToGallery(bitmap: Bitmap, context: Context, title: String, description: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, title)
            put(MediaStore.Images.Media.DESCRIPTION, description)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/wallmaster")
        }

        val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            context.contentResolver.openOutputStream(it).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream!!)
                outputStream.flush()
            }

            // Уведомить галерею о новом изображении
            Toast.makeText(this, getString(R.string.install), Toast.LENGTH_SHORT).show()
        }


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onClickInstallTelephone(view: View) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_box_install)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_box_install))

        val tvScreen = dialog.findViewById<TextView>(R.id.tvDownloadScr)
        val tvLock = dialog.findViewById<TextView>(R.id.tvDownloadLockScr)
        val tvBoth = dialog.findViewById<TextView>(R.id.tvDownloadBoth)

        tvScreen.setOnClickListener {
            WallpaperManager.getInstance(this).setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                saveBitmapToGallery(bitmap, this, "WallMater${LocalTime.now()}", receivedTag)
            }

            if (!historyRepository.checkExist(receivedId))
                historyRepository.insertImage(receivedId, receivedImage, receivedTag)
            dialog.dismiss()
        }
        tvLock.setOnClickListener {
            WallpaperManager.getInstance(this).setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                saveBitmapToGallery(bitmap, this, "WallMater${LocalTime.now()}", receivedTag)
            }

            if (!historyRepository.checkExist(receivedId))
                historyRepository.insertImage(receivedId, receivedImage, receivedTag)
            dialog.dismiss()
        }
        tvBoth.setOnClickListener {
            WallpaperManager.getInstance(this).setBitmap(bitmap)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                saveBitmapToGallery(bitmap, this, "WallMater${LocalTime.now()}", receivedTag)
            }

            if (!historyRepository.checkExist(receivedId))
                historyRepository.insertImage(receivedId, receivedImage, receivedTag)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_wall, menu)
        val customMenuItem = menu?.findItem(R.id.ic_acc)
        val customLayout = customMenuItem?.actionView as ConstraintLayout

        customLayout.setOnClickListener{
            Toast.makeText(this, "account", Toast.LENGTH_SHORT).show()
        }

        return true
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            R.id.icon_info -> {
                bindingClass.apply {
                    if (tvTag.visibility == View.INVISIBLE) {
                        item.icon = getDrawable(R.drawable.baseline_info_pink)
                        tvTag.visibility = View.VISIBLE
                    }
                    else {
                        item.icon = getDrawable(R.drawable.baseline_info_24)
                        tvTag.visibility = View.INVISIBLE
                    }
                }
            }
            R.id.icon_favorite -> {
                if (!favRepository.checkExist(receivedId)) {
                    item.icon = getDrawable(R.drawable.baseline_star_24)
                    favRepository.insertImage(receivedId, receivedImage, receivedTag)
                }
                else {
                    item.icon = getDrawable(R.drawable.baseline_star_border_24)
                    favRepository.deleteImages(receivedId)
                }

            }

        }


        return true
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (favRepository.checkExist(receivedId)) {
            val item = menu?.findItem(R.id.icon_favorite)
            item?.icon = getDrawable(R.drawable.baseline_star_24)
        }

        return true
    }
}