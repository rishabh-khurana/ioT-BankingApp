package com.example.bankassist

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bluecats.sdk.*
import com.bluecats.sdk.BCBeacon.BCBeaconState
import com.bluecats.sdk.BCSite.BCSiteState
import kotlinx.android.synthetic.main.activity_select_service.*
import java.sql.Struct
import java.util.*


class SelectService : AppCompatActivity() {
    val REQUEST_CODE_ENABLE_BLUETOOTH = 1001;
    var SERVICE_SELECTED:CharSequence=""

    // init details
    var TICKET_NUMBER=""

    var KIOSK_NUMBER=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_service)

        // get details from main
        val CUSTOMER_ID:String = MainActivity().getCustomerID()

        val SERVICE_GROUP = findViewById<RadioGroup>(R.id.serviceGroup)

        // get ticket details

        formButton.setOnClickListener {

            // get selected radio button from radioGroup
            val selectedId:Int = SERVICE_GROUP.checkedRadioButtonId

            // find the radiobutton by returned id
            val radioButton = findViewById<RadioButton>(selectedId)

            // get user selection
            val SERVICE_INPUT:CharSequence = radioButton.getText()
            // update service selected
            SERVICE_SELECTED = SERVICE_INPUT

            // get ticket details and send it to Display Details Page
            val details = getTicketNumber()
            TICKET_NUMBER=details[1]
            KIOSK_NUMBER=details[0]

            // Switch on bluetooth
            if(!BlueCatsSDK.isBluetoothEnabled()){
                SwitchOnBlueTooth()
            }
            // start the Bluecats SDK
            val APP_TOKEN="4d37f3ac-b672-49d2-a58c-81ce5a1dafba"
            val Bluecats = BlueCatsSDK.startPurringWithAppToken(applicationContext,APP_TOKEN)

            // Send signals to beacon
            var mBCBeaconManager = BCBeaconManager()
            mBCBeaconManager.registerCallback(mCallback)
            // Once Signal is recieved make an API call
            // Move to diplay details page

            if (mBCBeaconManager.isAttached) {
                SwitchToDisplayDetails()
            }
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

    var mCallback: BCBeaconManagerCallback = object : BCBeaconManagerCallback() {
        override fun didEnterSite(site: BCSite) {
            super.didEnterSite(site)
            Log.d("ServiceSelect", "didEnterSite " + site.name)
        }

        override fun didExitSite(site: BCSite) {
            super.didExitSite(site)
            Log.d("ServiceSelect", "didExitSite " + site.name)
        }

        override fun didDetermineState(state: BCSiteState, forSite: BCSite) {
            super.didDetermineState(state, forSite)
            Log.d("ServiceSelect", "didDetermineState " + forSite.name)
        }

        override fun didEnterBeacons(beacons: List<BCBeacon>) {
            super.didEnterBeacons(beacons)
            Log.d("ServiceSelect", "didEnterBeacons " + getBeaconNames(beacons))
        }

        override fun didExitBeacons(beacons: List<BCBeacon>) {
            super.didExitBeacons(beacons)
            Log.d("ServiceSelect", "didExitBeacons " + getBeaconNames(beacons))
        }

        override fun didDetermineState(state: BCBeaconState, forBeacon: BCBeacon) {
            super.didDetermineState(state, forBeacon)
            Log.d("ServiceSelect", "didDetermineState " + forBeacon.serialNumber)
        }

        override fun didRangeBeacons(beacons: List<BCBeacon>) {
            super.didRangeBeacons(beacons)
            Log.d("ServiceSelect", "didRangeBeacons " + getBeaconNames(beacons))
        }

        override fun didRangeBlueCatsBeacons(beacons: List<BCBeacon>) {
            super.didRangeBlueCatsBeacons(beacons)
            Log.d("ServiceSelect", "didRangeBlueCatsBeacons " + getBeaconNames(beacons))
        }

        override fun didRangeNewbornBeacons(newBornBeacons: List<BCBeacon>) {
            super.didRangeNewbornBeacons(newBornBeacons)
            Log.d("ServiceSelect", "didRangeNewbornBeacons " + getBeaconNames(newBornBeacons))
        }
    }

    private fun getBeaconNames(beacons: List<BCBeacon>): String? {
        val sb = StringBuilder()
        sb.append('[')
        for (beacon in beacons) {
            sb.append(beacon.serialNumber)
            sb.append(',')
        }
        sb.append(']')
        return sb.toString()
    }

    fun getTicketNumber():Array<String>{
        val queue = Volley.newRequestQueue(this)
        // get customer_id from Main activity and service from ServiceSelect
        val customer_id="31111"
        val service ="loans"
        // api call details
        val API_URL="http://34.87.233.248:5000/queue?queue_data={\"customer_id\": \"${customer_id}\", \"service\": \"loans\"}"
        val request = StringRequest(
            Request.Method.POST, API_URL,
            Response.Listener { response ->
                // TODO:Navigate to the Services page if Authenticated
                // Move to next page if response is correct
                Log.d("Hello","1 is executed ${response}")
            },
            Response.ErrorListener { error ->
                Log.d("Hello","That didn't work! ${error}")
            })
        queue.add(request)
        // TODO: DO the API call here
        val queueNumber:String = "1F".toString()
        val ticketNumber:String = "135".toString()
        val details = arrayOf(queueNumber,ticketNumber)
        return details
    }

    fun getServiceSelected():CharSequence{
        return SERVICE_SELECTED
    }

    fun getKioskNumber():String{
        return KIOSK_NUMBER
    }

    fun getTicketNumnber():String{
        return TICKET_NUMBER
    }


}
