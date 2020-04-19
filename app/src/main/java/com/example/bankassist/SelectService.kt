package com.example.bankassist

import android.Manifest
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.android.synthetic.main.activity_select_service.*
import org.altbeacon.beacon.*


class SelectService : AppCompatActivity(), BeaconConsumer,MonitorNotifier {
    val REQUEST_CODE_ENABLE_BLUETOOTH = 1001
    val PERMISSION_REQUEST_FINE_LOCATION = 1
    val PERMISSION_REQUEST_BACKGROUND_LOCATION = 2
    private var mBeaconManager: BeaconManager? = null
    var BEACONDETECTED:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_service)

        val SERVICE_GROUP = findViewById<RadioGroup>(R.id.serviceGroup)

        // get data from main
        val i:Intent = getIntent()
        val CUSTOMER_ID:String = i.getStringExtra("customer_ID")

        println("Customer ID is ${CUSTOMER_ID}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("This app needs background location access")
                    builder.setMessage("Please grant location access so this app can detect beacons in the background.")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setOnDismissListener(DialogInterface.OnDismissListener() {
                        println("it works")
                        requestPermissions(
                            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                            PERMISSION_REQUEST_BACKGROUND_LOCATION)
                    })
                    builder.show()
                }
                else {
                    val builder:AlertDialog.Builder = AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons in the background.  Please go to Settings -> Applications -> Permissions and grant background location access to this app.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(DialogInterface.OnDismissListener() {
                    });
                    builder.show();
                }
            }
            else {
                if (this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        ),
                        PERMISSION_REQUEST_FINE_LOCATION
                    );
                } else {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.  Please go to Settings -> Applications -> Permissions and grant location access to this app.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(DialogInterface.OnDismissListener() {
                    });
                    builder.show();
                }
            }
        }



        formButton.setOnClickListener {
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
            if (BEACONDETECTED) {
                getTicketNumber(CUSTOMER_ID, SERVICE_INPUT)
            }

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
                val mapper = jacksonObjectMapper()

                val details:Details = mapper.readValue(response)

                // convert string response to map

                val cust_ID:String = customer_id
                val ticketNumber:String = details.ticket
                val queueNumber:String =  details.counter

                // TODO:Navigate to the Services page if Authenticated
                // Move to next page if response is correct

                Log.d("Hello","1 is executed ${response}")

                // Move to diplay details page

                    SwitchToDisplayDetails(cust_ID, ticketNumber, queueNumber)

            },
            Response.ErrorListener { error ->
                // network error
                Log.d("Hello","That didn't work! ${error}")
            })
        queue.add(request)
    }

    // edystone code for beacon integration

    public override fun onResume() {
        super.onResume()
        mBeaconManager = BeaconManager.getInstanceForApplication(this.applicationContext)
        // Detect the main Eddystone-UID frame!
        mBeaconManager!!.beaconParsers
            .add(BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT))
        mBeaconManager!!.bind(this)
    }

    override fun onBeaconServiceConnect() { // Set the two identifiers below to null to detect any beacon regardless of identifiers
        val myBeaconNamespaceId = Identifier.parse("61687109E602F514C96D")
        val myBeaconInstanceId = Identifier.parse("000000023692")
        val region = Region(
            "my-beacon-region",
            myBeaconNamespaceId,
            myBeaconInstanceId,
            null
        )


        mBeaconManager!!.addMonitorNotifier(this)
        try {
            mBeaconManager!!.startMonitoringBeaconsInRegion(region)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

    }

    override fun didEnterRegion(region: Region) {
        Log.d(
            "Found", "I detected a beacon in the region with namespace id " + region.id1 +
                    " and instance id: " + region.id2
        )
        BEACONDETECTED = true
        try {
            mBeaconManager!!.startRangingBeaconsInRegion(Region("myRangingUniqueId", null, null, null))
            //mBeaconManager!!.addRangeNotifier(this)

        } catch (e: RemoteException) {
        }

    }

    fun BeaconsInRegion(beacons: Collection<Beacon>, region: Region) {
        for (beacon in beacons) {
            if (beacon.getDistance() < 3.0) {
                Log.d("Close to beacon", "I see a beacon that is less than 3 meters away.")
                // Perform distance-specific action here
                BEACONDETECTED = true
            }
        }

    }

    override fun didExitRegion(region: Region) {
        Log.d(
            "Not Found", "I no longer see a beacon " + region.id2
        )
    }

    override fun didDetermineStateForRegion(
        state: Int,
        region: Region
    ) {
    }

    public override fun onPause() {
        super.onPause()
        mBeaconManager!!.unbind(this)
    }


}

data class Details(
    var ticket:String,
    var counter:String
)
