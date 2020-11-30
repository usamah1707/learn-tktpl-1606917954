package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import id.ac.ui.mobileprogramming.usamahnashirulhaq.helloworld.R
import id.ac.ui.mobileprogramming.usamahnashirulhaq.helloworld.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var wifiManager: WifiManager
    private lateinit var listView: ListView
    private lateinit var buttonScan: Button
    private lateinit var buttonAPI: Button
    private var listResultWifi: List<ScanResult> = emptyList()
    private var wifiArrayList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, wifiArrayList)
        listView = binding.listViewWifi
        listView.adapter = adapter

        checkPermission()
        scanWifi()

        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        buttonAPI = binding.buttonAPI
        buttonAPI.setOnClickListener {
            if (wifiArrayList.isNotEmpty()) {
                Toast.makeText(this, "Sending API", Toast.LENGTH_SHORT).show()
                postWifiApi()
            }
            else{
                Toast.makeText(this, "No Wifi Available", Toast.LENGTH_SHORT).show()
            }
        }
        buttonScan = binding.buttonScan
        buttonScan.setOnClickListener {
            scanWifi()
        }
    }

    val wifiReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            listResultWifi = wifiManager.scanResults
            unregisterReceiver(this)

            for (scan: ScanResult in listResultWifi) {
                wifiArrayList.add(scan.SSID)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(
                            this@MainActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) ==
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun checkWifiIsOn() {
        if (!wifiManager.isWifiEnabled) {
            Toast.makeText(applicationContext, "Turning On WIFI...", Toast.LENGTH_LONG).show()
            wifiManager.isWifiEnabled = true
        }
    }

    private fun checkPermission() {
        checkWifiIsOn()
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun scanWifi() {
        checkPermission()
        wifiArrayList.clear()
        Toast.makeText(this, "Scanning WiFi...", Toast.LENGTH_SHORT).show()
        wifiManager.startScan()
        registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun postWifiApi() {
        val url = URL("https://81326856f1545607d1e7aba4d8f379fb.m.pipedream.net")
        var param = ""
        var counter = 0

        for (output: String in wifiArrayList) {
            param += "${++counter}. ${output}; "
        }

        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.readTimeout = 10000
        connection.connectTimeout = 15000
        connection.doOutput = true

        var postParam = param.toByteArray(StandardCharsets.UTF_8)

        connection.setRequestProperty("charset", "UTF-8")
        connection.setRequestProperty("content-type", "application/json")
        connection.setRequestProperty("content-length", param.length.toString())

        try {
            val outputStream: DataOutputStream = DataOutputStream(connection.outputStream)
            outputStream.write(postParam)
            outputStream.flush()
        } catch (exception: Exception) {

        }

        if (connection.responseCode != HttpURLConnection.HTTP_OK && connection.responseCode != HttpURLConnection.HTTP_CREATED) {
            try {
                val inputStream: DataInputStream = DataInputStream(connection.inputStream)
                val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
                val output: String = reader.readLine()

                println("There was error while connecting the chat $output")
                System.exit(0)

            } catch (exception: Exception) {
                throw Exception("Exception while push the notification  $exception.message")
            }
        }
    }
}