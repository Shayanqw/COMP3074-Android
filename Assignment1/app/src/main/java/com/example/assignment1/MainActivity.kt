package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    private lateinit var etHours: EditText
    private lateinit var etRate: EditText
    private lateinit var etTax: EditText
    private lateinit var btnCalc: Button
    private lateinit var btnAbout: Button
    private lateinit var tvPay: TextView
    private lateinit var tvOT: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvTax: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etHours = findViewById(R.id.etHours)
        etRate  = findViewById(R.id.etRate)
        etTax   = findViewById(R.id.etTax)
        btnCalc = findViewById(R.id.btnCalc)
        btnAbout= findViewById(R.id.btnAbout)
        tvPay   = findViewById(R.id.tvPay)
        tvOT    = findViewById(R.id.tvOvertime)
        tvTotal = findViewById(R.id.tvTotal)
        tvTax   = findViewById(R.id.tvTax)

        btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        btnCalc.setOnClickListener {
            calculateAndShow()
        }
    }

    private fun parseDoubleOrZero(s: String): Double =
        s.trim().toDoubleOrNull() ?: 0.0

    private fun calculateAndShow() {
        val hours = parseDoubleOrZero(etHours.text.toString())
        val rate  = parseDoubleOrZero(etRate.text.toString())
        var taxRate = parseDoubleOrZero(etTax.text.toString())

        // Allow user to type 20 or 0.20
        if (taxRate > 1.0) taxRate /= 100.0

        // Spec:
        // if hours <= 40: pay = hours*rate, overtime = 0
        // else: pay = 40*rate, overtime = (hours-40)*rate*1.5
        val regularHours = minOf(hours, 40.0)
        val overtimeHours = max(0.0, hours - 40.0)

        val pay = regularHours * rate
        val overtimePay = overtimeHours * rate * 1.5
        val totalPay = pay + overtimePay

        // Spec says tax = pay * tax_rate (i.e., on base pay only)
        val tax = pay * taxRate

        val currency = NumberFormat.getCurrencyInstance()
        tvPay.text   = "Pay: ${currency.format(pay)}"
        tvOT.text    = "Overtime Pay: ${currency.format(overtimePay)}"
        tvTotal.text = "Total Pay: ${currency.format(totalPay)}"
        tvTax.text   = "Tax: ${currency.format(tax)}"
    }
}
