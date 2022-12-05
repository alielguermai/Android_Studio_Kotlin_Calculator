package com.example.calculator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Arrays

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var last : String  = " "
    var countDot : Int = 0
    val operators = ArrayList<Char>(listOf('+','-','÷','×'))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.equalButton.setOnClickListener{ calculateTip() }
    }
    fun clear(view: View){
        resultField.text="0"
        inputDisplay.text=""
        countDot=0
        last=" "
    }
    fun backSpace (view: View){
        if (inputDisplay.length()>0) {
            if ((inputDisplay).text[inputDisplay.length() - 1] == '.' || operators.contains((inputDisplay).text[inputDisplay.length() - 1])) {
                countDot = 0
                last=" "
            }
            inputDisplay.text = inputDisplay.text.subSequence(0, inputDisplay.length() - 1)
        }
    }
    fun numberInput(view: View) {
        if ((view as Button).text=="." && countDot==0){
            if (operators.contains(last[0])){
                inputDisplay.append("")
            }
            else if (inputDisplay.text.isEmpty()) {
                inputDisplay.text = "0."
                countDot++
                last = "."
            }
            else if (countDot<1){
                inputDisplay.append(".")
                countDot++
                last = "."
            }
        }
        else if ((view).text!=".") {
            inputDisplay.append((view as Button).text)
            last = (view).text as String
        }

    }
    fun operationInput(view: View) {
        if (last!="." && !inputDisplay.text.isEmpty()) {
            if (!operators.contains(last[0])) {
                inputDisplay.append((view as Button).text)
                countDot = 0
                last = (view).text as String
            }
        }
    }
    fun calculateTip() {
        if (!inputDisplay.text.isEmpty()) {
            if (!operators.contains(inputDisplay.text[inputDisplay.text.length-1])  && inputDisplay.text[inputDisplay.text.length-1]!='.') {
                var calcul = mutableListOf<Any>()
                var digit= ""
                for (i in 0..inputDisplay.text.length - 1) {
                    var x = inputDisplay.text[i]
                    if (!operators.contains(x))
                        digit += x
                    else {
                        calcul.add(digit.toDouble())
                        calcul.add(x)
                        digit=""
                    }
                    if (i==inputDisplay.text.length - 1)
                        calcul.add(digit.toDouble())
                }
                if (calcul.size==1)
                    resultField.text= inputDisplay.text
                else{
                      var calcul_arranged = mutableListOf<Any>()
                    var number : Double
                    while (calcul.contains('×')||calcul.contains('÷')||calcul.contains('-')||calcul.contains('+')){
                    if (calcul.contains('×')||calcul.contains('÷')){
                        for (i in calcul.indices) {
                            var x = calcul[i]
                            if (x=='×'){
                                number= (calcul[i-1] as Double) * (calcul[i+1] as Double)
                                for (j in calcul.indices){
                                    if (j==i-1)
                                        calcul_arranged.add(number)
                                    else if (j<i-1 || j>i+1)
                                        calcul_arranged.add(calcul[j])
                                    }
                                calcul=calcul_arranged
                                calcul_arranged = mutableListOf()
                                break
                                }
                            else if (x=='÷'){
                                number= (calcul[i-1] as Double) / (calcul[i+1] as Double)
                                for (j in calcul.indices){
                                    if (j==i-1)
                                        calcul_arranged.add(number)
                                    else if (j<i-1 || j>i+1)
                                        calcul_arranged.add(calcul[j])
                                }
                                calcul=calcul_arranged
                                calcul_arranged = mutableListOf()
                                break
                            }
                            }
                        }
                        else if (calcul.contains('+')||calcul.contains('-')){
                        for (i in calcul.indices) {
                            var x = calcul[i]
                            if (x=='+'){
                                number= (calcul[i-1] as Double) + (calcul[i+1] as Double)
                                for (j in calcul.indices){
                                    if (j==i-1)
                                        calcul_arranged.add(number)
                                    else if (j<i-1 || j>i+1)
                                        calcul_arranged.add(calcul[j])
                                }
                                calcul=calcul_arranged
                                calcul_arranged = mutableListOf()
                                break
                            }
                            else if (x=='-'){
                                number= (calcul[i-1] as Double) - (calcul[i+1] as Double)
                                for (j in calcul.indices){
                                    if (j==i-1)
                                        calcul_arranged.add(number)
                                    else if (j<i-1 || j>i+1)
                                        calcul_arranged.add(calcul[j])
                                }
                                calcul=calcul_arranged
                                calcul_arranged = mutableListOf()
                                break
                            }
                        }
                    }
                    }
                    var result : Double= calcul[0] as Double
                    if (!(result % 1.0==0.0))
                        resultField.text=result.toString()
                    else
                        resultField.text= result.toInt().toString()
                }
            }
        }
        else
            resultField.text="0"
    }
}
