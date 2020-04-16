package com.example.bankassist
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_select_service.*
import org.json.JSONObject


class SelectService : AppCompatActivity() {
    val REQUEST_CODE_ENABLE_BLUETOOTH = 1001;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_service)

        val SERVICE_GROUP = findViewById<RadioGroup>(R.id.serviceGroup)

        // get data from main
        val i:Intent = getIntent()
        val CUSTOMER_ID:String = i.getStringExtra("customer_ID")

        println("Customer ID is ${CUSTOMER_ID}")

        formButton.setOnClickListener {
            // Switch on bluetooth

            // start the Bluecats SDK

            // Send signals to beacon

            // Once Signal is recieved make an API call

            // get service selected by user
            // TODO:replace invalid radio options with valid ones
            // get selected radio button from radioGroup
            val selectedId:Int = SERVICE_GROUP.checkedRadioButtonId

            // find the radiobutton by returned id
            val radioButton = findViewById<RadioButton>(selectedId)

            // get user selection
            val SERVICE_INPUT:String = radioButton.text.toString()


            // get ticket details
            getTicketNumber(CUSTOMER_ID,SERVICE_INPUT)

        }

    }

    fun SwitchToDisplayDetails(cust_ID:String,ticketNumber:String,queueNumber:String){
        val selectDisplayDetails = Intent(this , DisplayDetails::class.java)
        selectDisplayDetails.putExtra("customerID",cust_ID)
        selectDisplayDetails.putExtra("ticketNumber",ticketNumber)
        selectDisplayDetails.putExtra("queueNumber",queueNumber)
        startActivity(selectDisplayDetails)
    }

    fun SwitchOnBlueTooth(){
        val selectBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(selectBluetooth,REQUEST_CODE_ENABLE_BLUETOOTH)
    }

    fun getTicketNumber(customer_id:String,service:String){
        val queue = Volley.newRequestQueue(this)
        // api call details
        val API_URL="http://34.87.233.248:5000/queue?queue_data={\"customer_id\": \"${customer_id}\", \"service\": \"${service}\"}"
        val request = StringRequest(
            Request.Method.POST, API_URL,
            Response.Listener { response:String ->
                // set values for next activity

                val cust_ID:String = customer_id
                val ticketNumber:String = "135"
                val queueNumber:String ="1F"

                // TODO:Navigate to the Services page if Authenticated
                // Move to next page if response is correct

                Log.d("Hello","1 is executed ${response}")

                // Move to diplay details page
                SwitchToDisplayDetails(cust_ID,ticketNumber,queueNumber)
            },
            Response.ErrorListener { error ->
                Log.d("Hello","That didn't work! ${error}")
            })
        queue.add(request)
    }

}
