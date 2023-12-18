package com.example.wallmaster

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallmaster.databinding.ActivityGroupImgBinding
import com.example.wallmaster.retrofit.GroupImgApi
import com.example.wallmaster.retrofit.ImageInfoApi
import com.example.wallmaster.retrofit.TitleApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class GroupImgActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityGroupImgBinding
    private val adapter = ImageAdapter {selectedImage ->
        // обработка нажатия, переход на картинку
        val intent = Intent(this@GroupImgActivity, ImageActivity::class.java)
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
        bindingClass = ActivityGroupImgBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        setSupportActionBar(bindingClass.toolbar)

        val receivedWall: String = intent.getStringExtra("selectedWall")!!.decapitalize()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://85.143.78.47:8080/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val tagsApi = retrofit.create(ImageInfoApi::class.java)
        val imgApi = retrofit.create(GroupImgApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val tagsDeferred = async { tagsApi.getTags("${receivedWall}Info")}
            val imagesDeferred = async {  imgApi.getImages("${receivedWall}Img")}

            val tag = tagsDeferred.await()
            val tags = arrayListOf<String>()
            val image = imagesDeferred.await()
            val images = arrayListOf<ByteArray>()

            runOnUiThread {
                tag.tags.forEach { tags.add(it) }
                image.img.forEach { images.add(it) }

                for (i in images.indices) {
                    val bitmap: Bitmap? = BitmapFactory.decodeByteArray(images[i], 0, images[i].size)

                    val img = Image("$receivedWall$i", bitmap, images[i], tags[i])
                    adapter.addWall(img)
                }
            }
        }


        init()

    }

    private fun init() {
        bindingClass.apply {
            rcImages.layoutManager = GridLayoutManager(this@GroupImgActivity, 3)
            rcImages.adapter = adapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }


}