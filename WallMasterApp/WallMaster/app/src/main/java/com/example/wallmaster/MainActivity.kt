package com.example.wallmaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.wallmaster.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindingClass: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        supportActionBar?.hide()

        val delayMillis = 1500 // задержка в миллисекундах (1.5 секунда)

        // создаем Handler
        val handler = Handler()

        handler.postDelayed({
            // создаем интент для перехода в другую активность
            val intent = Intent(this, MainMenu::class.java)

            // Запускаем активность
            startActivity(intent)

            // Завершаем текущую активность
            finish()
        }, delayMillis.toLong())
    }
}