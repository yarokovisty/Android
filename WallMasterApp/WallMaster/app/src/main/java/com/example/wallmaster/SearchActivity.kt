package com.example.wallmaster

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wallmaster.databinding.ActivitySearchBinding
import com.example.wallmaster.retrofit.SearchApi
import com.example.wallmaster.retrofit.SearchRequestRemote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://85.143.78.47:8080/").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val searchApi = retrofit.create(SearchApi::class.java)

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                adapter = ImageAdapter {selectedImage ->
                    // обработка нажатия, переход на картинку
                    val intent = Intent(this@SearchActivity, ImageActivity::class.java)
                    intent.putExtra("selectedImage", selectedImage.byteArr)
                    setResult(RESULT_OK, intent)
                    intent.putExtra("selectedTag", selectedImage.tag)
                    setResult(RESULT_OK, intent)
                    startActivity(intent)
                }

                CoroutineScope(Dispatchers.IO).launch {
                    val group = binding.etSearch.text.toString()
                    val search = searchApi.search(
                        SearchRequestRemote(
                            group
                        )
                    )

                    runOnUiThread{
                        if(search.search.isNotEmpty()) {
                            for (i in search.search.indices) {
                                val bitmap: Bitmap? = BitmapFactory.decodeByteArray(search.search[i].img, 0, search.search[i].img.size)
                                val img = Image("$group$i", bitmap, search.search[i].img, search.search[i].tags)
                                adapter.addWall(img)
                            }
                        }
                        else Toast.makeText(this@SearchActivity, getString(R.string.not_found), Toast.LENGTH_SHORT).show()

                    }

                }

                init()

                hideKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }
        binding.ibtnSearch.setOnClickListener {
            adapter = ImageAdapter {selectedImage ->
                // обработка нажатия, переход на картинку
                val intent = Intent(this@SearchActivity, ImageActivity::class.java)
                intent.putExtra("selectedImage", selectedImage.byteArr)
                setResult(RESULT_OK, intent)
                intent.putExtra("selectedTag", selectedImage.tag)
                setResult(RESULT_OK, intent)
                startActivity(intent)
            }

            CoroutineScope(Dispatchers.IO).launch {
                val group = binding.etSearch.text.toString()

                val search = searchApi.search(
                    SearchRequestRemote(
                        group
                    )
                )

                runOnUiThread{
                    if(search.search.isNotEmpty()) {
                        val count = 0
                        for (i in search.search.indices) {
                            val bitmap: Bitmap? = BitmapFactory.decodeByteArray(search.search[i].img, 0, search.search[i].img.size)
                            val img = Image("$group$", bitmap, search.search[i].img, search.search[i].tags)
                            adapter.addWall(img)
                        }
                    }
                    else Toast.makeText(this@SearchActivity, getString(R.string.not_found), Toast.LENGTH_SHORT).show()
                }
            }

            init()
            hideKeyboard()
        }


    }

    private fun init() {
        binding.apply {
            rvSearch.layoutManager = GridLayoutManager(this@SearchActivity, 3)
            rvSearch.adapter = adapter
        }
    }

    fun onClickBack(view: View) = finish()

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}