package com.example.tbc_academy_android_task_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val generateNumberButton: Button = findViewById(R.id.generateNumberButton)
        val numberInput: TextView = findViewById(R.id.numberInput)
        val generatedResultText: TextView = findViewById(R.id.generatedResultText)

        generateNumberButton.setOnClickListener{
            val generatedText: String
            val enteredNumber:Int? = numberInput.text.toString().toIntOrNull()

            if(enteredNumber == null){
                showToast("Make sure you enter a number")
            }else if((enteredNumber < 1) || (enteredNumber > 1000)){
                showToast("Make sure you enter a number Between 1-1000 range")
            }else{
                generatedText = calculateNumberText(enteredNumber)
                generatedResultText.text = generatedText;
            }
        }
    }

    private fun calculateNumberText(value:Int):String {
        val first9 = arrayOf("","ერთი", "ორი", "სამი", "ოთხი", "ხუთი", "ექვსი", "შვიდი", "რვა", "ცხრა")
        val elevenToNineteen  = arrayOf("","თერთმეტი", "თორმეტი", "ცამეტი", "თოთხმეტი", "თხუთმეტი", "თექვსმეტი", "ჩვიდმეტი", "თვრამეტი", "ცხრამეტი")
        val tens = arrayOf("","ათი", "ოცი", "ოცდაათი", "ორმოცი", "ორმოცდაათი", "სამოცი", "სამოცდაათი", "ოთხმოცი", "ოთხმოცდაათი");
        val indexedValueDigits = mutableMapOf<Int, Int>()
        val stringValue: String = value.toString().reversed();

        repeat(stringValue.length){
            indexedValueDigits[it+1] = stringValue[it].digitToInt()
        }
        var result = "";

//      სიტუაციები სადაც სტრინგის აწყობა არ არის საჭირო
//      თუ რიცხვი არის ათასი
        if (value == 1000){
            return "ათასი"
        }

//      თუ რიცხვი უნაშთოდ იყოფა 100-ზე 100დან - 1000მდე
        if (value % 100 == 0 && value < 1000){
            return when(value / 100){
                1 -> "ასი"
                8, 9 -> first9[value/100] + "ასი"
                else -> first9[value/100].substring(0, first9[value/100].length - 1) + "ასი"
            }
        }

//      თუ რიცხვი უნაშთოდ იყოფა 10-ზე 10დან - 100მდე
        if (value % 10 == 0 && value < 100){
            return tens[value / 10]
        }

//      თუ რიცხვი არის 11 სა და 20-ს შორის
        if(value in 11..19){
            return elevenToNineteen[value-10]
        }
//      თუ ნაკლებია ათზე
        if(value < 10){
            return first9[value];
        }

//      დავიწყოთ სტრინგის აწყობა


//      თუ რიცხვი მეტია 100-ზე
        if (value > 100){
            result = when(val thirdRowDigit:Int = indexedValueDigits[3].toString().toInt()){
                1 -> "ას "
                8, 9 -> "${first9[thirdRowDigit]}ას "
                else -> "${first9[thirdRowDigit].substring(0, first9[thirdRowDigit].length - 1)}ას "
            }
        }

//      თუ რიცხვი მეტია 20ზე
        if (value > 20){
            val secondRowDigit: Int = indexedValueDigits[2].toString().toInt();
            val firstRowDigit: Int = indexedValueDigits[1].toString().toInt();

            if(firstRowDigit == 0 ){
                result = "$result${tens[secondRowDigit]}"
            }else{
                if(secondRowDigit != 0){
                    result = when(secondRowDigit){
                        9,7,5,3 -> "$result${tens[secondRowDigit].substring(0, tens[secondRowDigit].length - 3)}";
                        else -> "$result${tens[secondRowDigit].substring(0, tens[secondRowDigit].length - 1)}და"
                    }
                }
                result = when(secondRowDigit % 2){
                    0 -> "$result${first9[firstRowDigit]}";
                    else -> "$result${elevenToNineteen[firstRowDigit]}";
                }
            }

        }

        return result;
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

