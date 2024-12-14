package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val inputTime1 = findViewById<EditText>(R.id.inputTime1TV)
        val inputTime2 = findViewById<EditText>(R.id.inputTime2TV)
        val buttonAdd = findViewById<Button>(R.id.addBTN)
        val buttonSubtract = findViewById<Button>(R.id.subtractBTN)
        val resultView = findViewById<TextView>(R.id.result)

        buttonAdd.setOnClickListener {
            val time1 = parseTimeToSeconds(inputTime1.text.toString())
            val time2 = parseTimeToSeconds(inputTime2.text.toString())
            val result = time1 + time2
            resultView.text = "Результат: ${formatSecondsToTime(result)}"
        }
        buttonSubtract.setOnClickListener {
            val time1 = parseTimeToSeconds(inputTime1.text.toString())
            val time2 = parseTimeToSeconds(inputTime2.text.toString())
            val result = time1 - time2.coerceAtLeast(0)
            resultView.text = "Результат: ${formatSecondsToTime(result)}"
        }
    }

    private fun parseTimeToSeconds(time: String): Int {
        val regex = Regex("(\\d+h)?(\\d+m)?(\\d+s)?")
        val matchResult = regex.find(time)

        var hours = 0
        var minutes = 0
        var seconds = 0

        matchResult?.groupValues?.forEach { group ->
            when {
                group.contains("h") -> hours = group.replace("h", "").toInt()
                group.contains("m") -> minutes = group.replace("m", "").toInt()
                group.contains("s") -> seconds = group.replace("s", "").toInt()
                }
            }

            return hours * 3600 + minutes * 60 + seconds
        }

        private fun formatSecondsToTime(seconds: Int): String {
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            val remainingSeconds = seconds % 60

            return buildString {
                if (hours > 0) append("${hours}h")
                if (minutes > 0 || hours > 0) append("${minutes}m")
                append("${remainingSeconds}s")
            }.trim()
        }
    }
