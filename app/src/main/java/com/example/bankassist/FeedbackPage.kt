package com.example.bankassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_feedback_page.*
import kotlin.system.exitProcess

class FeedbackPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_page)

        closeAppButton.setOnClickListener {
            // Register User Input
            // Close App
            exitProcess(-1)
        }
    }
}
