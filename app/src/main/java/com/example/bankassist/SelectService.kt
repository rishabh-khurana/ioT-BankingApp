package com.example.bankassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bluecats.sdk.BlueCatsSDK
import kotlinx.android.synthetic.main.activity_select_service.*


class SelectService : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_service)

        formButton.setOnClickListener {
            // TODO: Switch on bluetooth
            // TODO: Integrate Bluecats SDK
            // TODO: Send signals to beacon
            val APP_TOKEN="4d37f3ac-b672-49d2-a58c-81ce5a1dafba"
            BlueCatsSDK.startPurringWithAppToken(applicationContext,APP_TOKEN)
            // TODO: Once Signal is recieved make an API
            // Move to diplay details page
            switchToDisplayDetails()
        }

    }

    fun switchToDisplayDetails(){
        val selectDisplayDetails = Intent(this , DisplayDetails::class.java)
        startActivity(selectDisplayDetails)
    }

}
