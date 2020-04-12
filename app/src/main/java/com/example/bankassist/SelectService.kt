package com.example.bankassist
// Integrate Bluetooth Adapter
// Integrate Bluecats SDK

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bluecats.sdk.*
import com.bluecats.sdk.BCBeacon.BCBeaconState
import com.bluecats.sdk.BCSite.BCSiteState
import kotlinx.android.synthetic.main.activity_select_service.*
import java.net.URL


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

        override fun didRangeIBeacons(iBeacons: List<BCBeacon>) {
            super.didRangeIBeacons(iBeacons)
            Log.d("ServiceSelect", "didRangeIBeacons " + getBeaconNames(iBeacons))
        }

        override fun didRangeEddystoneBeacons(eddystoneBeacons: List<BCBeacon>) {
            super.didRangeEddystoneBeacons(eddystoneBeacons)
            Log.d(
                "ServiceSelect",
                "didRangeEddystoneBeacons " + getBeaconNames(eddystoneBeacons)
            )
        }

        override fun didDiscoverEddystoneURL(eddystoneUrl: URL) {
            super.didDiscoverEddystoneURL(eddystoneUrl)
            Log.d("ServiceSelect", "didDiscoverEddystoneURL " + eddystoneUrl.toString())
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


}
