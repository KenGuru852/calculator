package com.example.pritula_funtikov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val zero_text: TextView = findViewById(R.id.zero_button)
        val one_text: TextView = findViewById(R.id.one_button)
        val two_text: TextView = findViewById(R.id.two_button)
        val three_text: TextView = findViewById(R.id.three_button)
        val four_text: TextView = findViewById(R.id.four_button)
        val five_text: TextView = findViewById(R.id.five_button)
        val six_text: TextView = findViewById(R.id.six_button)
        val seven_text: TextView = findViewById(R.id.seven_button)
        val eight_text: TextView = findViewById(R.id.eight_button)
        val nine_text: TextView = findViewById(R.id.nine_button)
        val remove_text: TextView = findViewById(R.id.remove_button)
        val divide_text: TextView = findViewById(R.id.divide_button)
        val multiple_text: TextView = findViewById(R.id.multiple_button)
        val subtract_text: TextView = findViewById(R.id.subtract_button)
        val plus_text: TextView = findViewById(R.id.plus_button)
        val delete_text: TextView = findViewById(R.id.delete_button)
        val point_text: TextView = findViewById(R.id.point_button)
        val result_button: TextView = findViewById(R.id.result_button)
        var result_text: TextView = findViewById(R.id.result_text)
        var enter_text_code = ""
        zero_text.setOnClickListener{string_builder('0')}
        one_text.setOnClickListener{string_builder('1')}
        two_text.setOnClickListener{string_builder('2')}
        three_text.setOnClickListener{string_builder('3')}
        four_text.setOnClickListener{string_builder('4')}
        five_text.setOnClickListener{string_builder('5')}
        six_text.setOnClickListener{string_builder('6')}
        seven_text.setOnClickListener{string_builder('7')}
        eight_text.setOnClickListener{string_builder('8')}
        nine_text.setOnClickListener{string_builder('9')}
        plus_text.setOnClickListener{string_builder('+')}
        divide_text.setOnClickListener{string_builder('/')}
        subtract_text.setOnClickListener{string_builder('-')}
        multiple_text.setOnClickListener{string_builder('*')}
        point_text.setOnClickListener { string_builder(',') }
        delete_text.setOnClickListener { element_deleter() }
        remove_text.setOnClickListener { element_remover() }
        result_button.setOnClickListener { ALU() }
    }

    var for_check_to_transfer : Int = 0

    fun reverse_polish_notation(To_operate: String) : ArrayList<String>
    {
        var Operand_Stack = ArrayList<String>()
        var Operation_Stack = ArrayList<String>()
        var To_operand_temp = ""
        for (element in 0..To_operate.length-1)
        {
            if (To_operate.get(element) == '-' && element == 0)
            {
                To_operand_temp += '-'
                continue
            }
            // 3+5,14*6
            // to_operand_temp =
            // operand_stack = 3 5,14
            // operation_stack = + *
            else if (To_operate.get(element) == '*' || To_operate.get(element) == '/')
            {
                Operand_Stack.add(To_operand_temp)
                To_operand_temp = ""
                if (Operation_Stack.isEmpty())
                {
                    Operation_Stack.add(To_operate.get(element).toString())
                    continue
                }
                var i = Operation_Stack.size-1
                while(i>=0)
                {
                    var temp = Operation_Stack.get(i)
                    if (temp == "+" || temp == "-")
                    {
                        Operation_Stack.add(To_operate.get(element).toString())
                        break
                    }
                    else
                    {
                        var temp2 = Operation_Stack.get(i)
                        Operand_Stack.add(temp2)
                        Operation_Stack.removeAt(i)
                    }
                    i--
                }
            }
            else if (To_operate.get(element) == '-' || To_operate.get(element) == '+')
            {
                //Operand_Stack += To_operand_temp
                Operand_Stack.add(To_operand_temp)
                To_operand_temp = ""
                if (Operation_Stack.isEmpty())
                {
                    Operation_Stack.add(To_operate.get(element).toString())
                    continue
                }
                var i = Operation_Stack.size-1
                while (i >= 0)
                {
                    var temp2 = Operation_Stack.get(i)
                    Operand_Stack.add(temp2)
                    Operation_Stack.removeAt(i)
                    i--
                }
                Operation_Stack.add(To_operate.get(element).toString())
            }
            else
            {
                if (To_operate.get(element) == ',')
                {
                    To_operand_temp += '.'
                }
                else
                To_operand_temp += To_operate.get(element)
            }
        }
        Operand_Stack.add(To_operand_temp.toString())
        if (Operation_Stack.isNotEmpty()) {
            var i = Operation_Stack.size-1
            while(i >= 0)
            {
                Operand_Stack.add(Operation_Stack.get(i))
                i--
            }
        }
        return Operand_Stack
    }

    fun Calculator_func(Mas: ArrayList<String>) : String
    {
        // 3+5,14*6
        // 3 5,14 6 * +
        var Stack = ArrayList<String>()
        for (item in Mas)
        {
            if (item != "+" && item != "-" && item != "*" && item != "/")
            {
                Stack.add(item)
            }
            else
            {
                var first_operand = Stack.get(Stack.size-2).toDouble()
                var second_operand = Stack.get(Stack.size-1).toDouble()
                var temp_result = 0.0
                if (item == "+")
                {
                    temp_result = first_operand + second_operand
                }
                else if (item == "-")
                {
                    temp_result = first_operand - second_operand
                }
                else if (item == "*")
                {
                    temp_result = first_operand * second_operand
                }
                else if (item == "/")
                {
                    if (second_operand == 0.0)
                    {
                        for_check_to_transfer = 1
                        return "Error divide by zero"
                    }
                    temp_result = first_operand / second_operand
                }
                Stack.removeAt(Stack.size-1)
                Stack.removeAt(Stack.size-1)
                Stack.add(temp_result.toString())
            }
        }
        for_check_to_transfer = 1
        return Stack.get(0)
    }

    fun ALU()
    {
        var enter_text: TextView = findViewById(R.id.enter_text)
        if (enter_text.text.isEmpty())
        {
            return
        }
        var result_text: TextView = findViewById(R.id.result_text)
        if (enter_text.text.get(enter_text.text.length-1) == '+' || enter_text.text.get(enter_text.text.length-1) == '-'
            || enter_text.text.get(enter_text.text.length-1) == '*' || enter_text.text.get(enter_text.text.length-1) == '/'
            || enter_text.text.get(enter_text.text.length-1) == ',')
        {
            enter_text.text = enter_text.text.substring(0, enter_text.text.length - 1)
        }
        var temp_array = reverse_polish_notation(enter_text.text.toString())
        var Calc_answer = Calculator_func(temp_array)
        result_text.text = Calc_answer
//        var test_string = ""
//        for (element in 0..temp_array.size-1)
//        {
//            test_string += temp_array.get(element).toString()
//        }
//        result_text.text = test_string
    }

    fun element_remover()
    {
        for_check_to_transfer = 0
        var enter_text: TextView = findViewById(R.id.enter_text)
        var result_text: TextView = findViewById(R.id.result_text)
        enter_text.text = ""
        result_text.text = ""
    }


    fun element_deleter()
    {
        for_check_to_transfer = 0
        var enter_text: TextView = findViewById(R.id.enter_text)
        if (enter_text.text.isNotEmpty())
        {
            enter_text.text = enter_text.text.substring(0, enter_text.text.length - 1)
        }
    }

    fun string_builder(To_add_string: Char)
    {
        var enter_text: TextView = findViewById(R.id.enter_text)
        var result_text: TextView = findViewById(R.id.result_text)
        if (To_add_string == '*' || To_add_string == '+' ||
            To_add_string == '-' || To_add_string == '/')
        {
            if (for_check_to_transfer == 1)
            {
                if (result_text.text == "Error divide by zero")
                {
                    enter_text.text = ""
                    result_text.text = ""
                }
                enter_text.text = result_text.text
                result_text.text = ""
                for_check_to_transfer = 0
            }
        }
        if (To_add_string == ',' || To_add_string == '*' || To_add_string == '+' ||
            To_add_string == '-' || To_add_string == '/')
        {
            if (enter_text.text.isEmpty())
            {
                return
            }
        }
        //var temp = enter_text.text.substring(enter_text.text.length-2,enter_text.text.length-1)
        if (enter_text.text.isNotEmpty())
        {
            var temp: Char = enter_text.text.last()
            if (To_add_string == ',')
            {
                if (enter_text.text.get(enter_text.text.length-1) == '+' || enter_text.text.get(enter_text.text.length-1) == '-'
                    || enter_text.text.get(enter_text.text.length-1) == '*' || enter_text.text.get(enter_text.text.length-1) == '/')
                {
                    return
                }
                var element = enter_text.text.length-1
                while(element >= 0){
                        println(enter_text.text.get(element))
                        if (enter_text.text.get(element) == ',')
                        {

                            return
                        }
                        else if (enter_text.text.get(element) == '+' || enter_text.text.get(element) == '-'
                            || enter_text.text.get(element) == '*' || enter_text.text.get(element) == '/')
                        {
                            enter_text.append(To_add_string.toString())
                            return
                        }
                    element--;
                }
            }
            if ((To_add_string == '+' || To_add_string == ',' || To_add_string == '/' || To_add_string == '*' || To_add_string == '-')
                && (temp == '+' || temp == ',' || temp == '-' || temp == '/' || temp == '*'))
            {
                enter_text.text = enter_text.text.substring(0, enter_text.text.length-1)
                enter_text.append(To_add_string.toString())
                return
            }
            else
            {
                enter_text.append(To_add_string.toString())
                for_check_to_transfer = 0
            }
        }
        else
        {
            enter_text.append(To_add_string.toString())
            for_check_to_transfer = 0
        }
//        enter_text.append(To_add_string)
    }
}