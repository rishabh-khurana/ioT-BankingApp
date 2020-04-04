package com.example.bankassist
// TODO:install a native database

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun showError(Message:String){
            // TODO: Show error message on view
        }

        fun switchToSelectService(){
            val selectServiceScreen = Intent(this , SelectService::class.java)
            startActivity(selectServiceScreen)
        }

        loginButton.setOnClickListener {
            // customer details from database for testing
            val cutomerNumber = 123456.toString()
            val pass:String =  "password".toString()

            // customer number entered by user
            val customerIDInput = findViewById<EditText>(R.id.customerIDInput)
            // password entered by user
            val passwordInput = findViewById<EditText>(R.id.passwordInput)
            // TODO : check if customer ID exists in database and then password
            // TODO : if password or ID does not exist show error messaage
            val IDVal=customerIDInput.text.toString()
            val passwordVal=passwordInput.text.toString()

            if(IDVal == cutomerNumber && passwordVal == pass){
                // Navigate to the Services page
                println("Navigate to next View")
                switchToSelectService()
            }
        }
    }
}
