package com.example.diceroller

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val targetNumberEditText: EditText = findViewById(R.id.targetNumber)

        targetNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    rollDice()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun rollDice() {
        val dice1 = Dice(6)
        val dice2 = Dice(6)
        val diceRoll1 = dice1.roll()
        val diceRoll2 = dice2.roll()
        val sum = diceRoll1 + diceRoll2

        val diceImage1: ImageView = findViewById(R.id.diceImage1)
        val diceImage2: ImageView = findViewById(R.id.diceImage2)
        val targetNumberEditText: EditText = findViewById(R.id.targetNumber)

        diceImage1.setImageResource(getDiceImage(diceRoll1))
        diceImage2.setImageResource(getDiceImage(diceRoll2))

        val targetNumber = targetNumberEditText.text.toString().toIntOrNull()

        if (targetNumber != null) {
            if (sum == targetNumber) {
                Toast.makeText(this, "Félicitations ! Vous avez gagné !", Toast.LENGTH_SHORT).show()
                animateDice()
            } else {
                Toast.makeText(this, "Désolé, vous avez perdu. Essayez encore !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDiceImage(diceRoll: Int): Int {
        return when (diceRoll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }

    private fun animateDice() {
        val diceImage1: ImageView = findViewById(R.id.diceImage1)
        val diceImage2: ImageView = findViewById(R.id.diceImage2)
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        diceImage1.startAnimation(animation)
        diceImage2.startAnimation(animation)
    }
}

class Dice(private val numSides: Int) {

    fun roll(): Int {
        return (1..numSides).random()
    }
}