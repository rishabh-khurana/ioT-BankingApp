package com.example.bankassist
// Integrate Bluetooth Adapter
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
// Integrate Bluecats SDK
import com.bluecats.sdk.BlueCatsSDK
import kotlinx.android.synthetic.main.activity_select_service.*


class SelectService : AppCompatActivity() {

    val REQUEST_CODE_ENABLE_BLUETOOTH = 1001;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_service)

        formButton.setOnClickListener {
            // Switch on bluetooth
            if(!BlueCatsSDK.isBluetoothEnabled()){
                SwitchOnBlueTooth()
            }
            // start the Bluecats SDK
            val APP_TOKEN="4d37f3ac-b672-49d2-a58c-81ce5a1dafba"
            BlueCatsSDK.startPurringWithAppToken(applicationContext,APP_TOKEN)
            // TODO: Send signals to beacon

            // TODO: Once Signal is recieved make an API
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
