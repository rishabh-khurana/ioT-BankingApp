package com.example.bankassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // customer number from database
        val cutomerNumber = 123456
        // login button from login Page
        val loginButton = findViewById<Button>(R.id.loginButton)
        // customer number entered by user
        val customerInput = findViewById<View>(R.id.customerInput)

        loginButton.setOnClickListener {
            // TODO : check if customer ID exists in database
            // TODO : if not show error message
        }
    }
}
