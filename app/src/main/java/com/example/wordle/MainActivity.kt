package com.example.wordle

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wordToGuess = FourLetterWordList.getRandomFourLetterWord();     // get word to check

        val guessesField = findViewById<TextView>(R.id.guesses_view)
        val failView = findViewById<TextView>(R.id.fail_view)
        val debugView = findViewById<TextView>(R.id.DEBUG_view_word)
        val getUserInput = findViewById<Button>(R.id.btn_get_user_input);   // button to get user input
        val editText = findViewById<EditText>(R.id.et_user_input);          // user input text

        var guessLimit = 3
        var checkGuessDisplay = ""
        var userInputDisplay = ""

        getUserInput.setOnClickListener {
            if (guessLimit != 0) {
                val editTextString = editText.text.toString().uppercase(Locale.getDefault());
                val result = checkGuess(editTextString, wordToGuess)
                checkGuess(editTextString, wordToGuess)

                if (checkGuessDisplay == "") {
                    checkGuessDisplay = result
                    userInputDisplay = editTextString
                } else {
                    checkGuessDisplay = checkGuessDisplay + "\n" + result
                    userInputDisplay = userInputDisplay + "\n" + editTextString
                }

                guessesField.text = checkGuessDisplay
                debugView.text = userInputDisplay
                guessLimit--

                if (guessLimit == 0) failView.text =
                    "Oops! You're Out of Attempts!\nCorrect Answer: $wordToGuess"
            }
        }
    }

    private fun checkGuess(guess: String, wordToGuess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }

}