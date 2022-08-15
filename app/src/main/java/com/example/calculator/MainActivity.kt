package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric = true // The last entry is a number
        lastDot = false // The last entry isnt a dot
    }

    fun onClear(view: View){
        tvInput?.text = "0"
    }

    fun onDecimalPoint(view: View){
        //We want to avoid using more than one dot
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false // The last entry isnt a number
            lastDot = true // Now the last entry would be a dot
        }
    }

    /* the "let" is going to use the inputted numbers as an implicit parameter
     * with the default name "it". you can change the name of the parameter
     * by declaring a word inside the let, followed by an arrow (->)
     */
    fun onOperator(view: View){
        tvInput?.text?.let { number ->
            if (lastNumeric && !isOPeratorAdded(number.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }

    }

    fun onEqual(view: View){
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring  (1)
                }
                if(tvValue.contains("-")){

                val splitValue = tvValue.split("-")

                var one = splitValue[0]
                var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if(tvValue.startsWith("+")){
                    prefix = "+"
                    tvValue = tvValue.substring  (1)
                }
                if(tvValue.contains("+")) {

                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if(tvValue.startsWith("/")){
                    prefix = "/"
                    tvValue = tvValue.substring  (1)
                }
                if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if(tvValue.startsWith("x")){
                    prefix = "x"
                    tvValue = tvValue.substring  (1)
                }
                if(tvValue.contains("x")) {

                    val splitValue = tvValue.split("x")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0,result.length - 2)

        return value
    }

    private fun isOPeratorAdded(value: String) : Boolean {
        return if (value.startsWith("-")){
            false
        } else {
            value.contains("/")
                    || value.contains("x")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}