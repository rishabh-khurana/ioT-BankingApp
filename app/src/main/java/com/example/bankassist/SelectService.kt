package com.example.bankassist
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_service.*


class SelectService : AppCompatActivity() {
    val REQUEST_CODE_ENABLE_BLUETOOTH = 1001;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_service)
        // get data from main
        val i:Intent = getIntent()
        val CUSTOMER_ID:String = i.getStringExtra("customer_ID")

        println("Customer ID is ${CUSTOMER_ID}")

        formButton.setOnClickListener {
            // Switch on bluetooth

            // start the Bluecats SDK

            // Send signals to beacon

            // Once Signal is recieved make an API call
            // Move to diplay details page

                SwitchToDisplayDetails()

        }

    }

    fun SwitchToDisplayDetails(){
        val selectDisplayDetails = Intent(this , DisplayDetails::class.java)
        startActivity(selectDisplayDetails)
    }

    fun SwitchOnBlueTooth(){
        val selectBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(selectBluetooth,REQUEST_CODE_ENABLE_BLUETOOTH)
    }

}
