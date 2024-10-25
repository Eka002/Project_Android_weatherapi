package com.example.pogoda_2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pogoda_2.databinding.ActivityMainBinding
import org.json.JSONObject


const val API_KEY = "57c3fa11504a48a5b64161707242410"

class MainActivity : AppCompatActivity() {
private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bGet.setOnClickListener {
            getResult(name = "Moscow")
        }
    }
    private fun getResult(name: String) {
        val url = "http://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$name&aqi=no"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val obj = JSONObject(response)
                val temp = obj.getJSONObject("current")
                val temperature = temp.getString("temp_c")
                binding.tvResult.text = "Температура в $name: $temperature °C"
                Log.d("MyLog", "Response: $temperature")
            },
            { error ->
                binding.tvResult.text = "Ошибка: ${error.message}"
                Log.d("MyLog", "Volley error: $error")
            }
        )
        queue.add(stringRequest)
    }
}
