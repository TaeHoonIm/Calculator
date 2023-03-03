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
    //소수점이 연속으로 오는 것을 방지
    var lastNumeric : Boolean = false
    //마지막에 소수점이 오는 것을 방지
    var lastDot : Boolean = false

    //버튼에 onClick을 사용하지 않으면 모든 버튼들을 listener함수를 이용하여
    //구현해줘야함. 큰 프로젝트에서는 onClick을 사용하지 않는 것이 권장됨.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        //null인 경우 코드가 실행되지 않고 null이 아니면 코드가 실행됨
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false

        //contains -> 변수 안에 example이라는 단어가 포함됐는지 확인함.
//        var sentence = "example"
//        if(sentence.contains("example")){
//            tvInput?.append("example")
//        }
    }

    fun onClear(view: View){
        tvInput?.text = ""
    }
    //소수점을 위한 조건
    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
    //연산을 수행하기 위한 조건
    //앱 안전성을 위해 연산자를 눌렀을 때 텍스트 뷰가 비어있는지 확인.
    fun onOperator(view: View){
        tvInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                //음수 부분 처리
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){

                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }
                else if(tvValue.contains("+")){

                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }

                else if(tvValue.contains("*")){

                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }else if(tvValue.contains("/")){

                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
            }catch (e: ArithmeticException){
                //산술 오류 catch
                e.printStackTrace()
            }
        }
    }
    
    //결과가 .0인 경우 처리
    private fun removeZeroAfterDot(result: String) : String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length-2)
        return value
    }
    //음수인 경우를 체크
    private fun isOperatorAdded(value : String) : Boolean{
        //startsWith->특정 문자로 시작되는지 체크
        return if(value.startsWith("-")){
            false
        }
        else{
            value.contains("/") || value.contains("*") || value.contains("+")
                    || value.contains("-")

        }
    }

}