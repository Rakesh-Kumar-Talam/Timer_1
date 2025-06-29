package com.example.timer

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var labelInput: EditText
    private lateinit var timeInput: EditText
    private lateinit var addTimerButton: Button
    private lateinit var buttonContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        labelInput = findViewById(R.id.editLabel)
        timeInput = findViewById(R.id.editTime)
        addTimerButton = findViewById(R.id.addTimerButton)
        buttonContainer = findViewById(R.id.buttonContainer)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addTimerButton.setOnClickListener {
            addTimerButton()
        }
    }

    private fun addTimerButton() {
        val label = labelInput.text.toString().trim()
        val timeText = timeInput.text.toString().trim()

        if (label.isEmpty() || timeText.isEmpty()) {
            Toast.makeText(this, "Please enter both label and time.", Toast.LENGTH_SHORT).show()
            return
        }

        val timeInMinutes = try {
            timeText.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid time entered.", Toast.LENGTH_SHORT).show()
            return
        }

        if (timeInMinutes <= 0) {
            Toast.makeText(this, "Time must be greater than zero.", Toast.LENGTH_SHORT).show()
            return
        }

        val newButton = Button(this).apply {
            text = label
            setOnClickListener {
                startTimer(label, timeInMinutes)
            }
        }

        buttonContainer.addView(newButton)

        labelInput.text.clear()
        timeInput.text.clear()
    }

    fun startTimer(message: String, seconds: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_LENGTH, seconds)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }
        startActivity(intent)

    }

}