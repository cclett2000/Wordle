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

        val guessesField = findViewById<TextView>(R.id.guesses_view)        // guess results display
        val failView = findViewById<TextView>(R.id.fail_view)               // fail view -- basically info view, just hate refactoring
        val debugView = findViewById<TextView>(R.id.DEBUG_view_word)        // guess word display -- used to be debug, still hate refactoring
        val getUserInput = findViewById<Button>(R.id.btn_get_user_input);   // button to get user input
        val editText = findViewById<EditText>(R.id.et_user_input);          // user input text

        var guessLimit = 3                                                  // set number of attempts
        var checkGuessDisplay = ""                                          // used to display check results
        var userInputDisplay = ""                                           // used to display user input

        // button logic
        getUserInput.setOnClickListener {
            // guesses remaining check
            if (guessLimit != 0) {
                var isCorrect = false       // correct guess flag
                val editTextString = editText.text.toString().uppercase(Locale.getDefault());   // to string for edit text
                if (editTextString.length == 4) {
                    failView.text = ""
                    val result = checkGuess(editTextString, wordToGuess)
                    checkGuess(editTextString, wordToGuess)

                    // display conditional
                    // first guess
                    if (checkGuessDisplay == "") {
                        checkGuessDisplay = result
                        userInputDisplay = editTextString
                    }
                    // following guesses
                    else {
                        checkGuessDisplay = checkGuessDisplay + "\n" + result
                        userInputDisplay = userInputDisplay + "\n" + editTextString
                    }

                    guessesField.text = checkGuessDisplay
                    debugView.text = userInputDisplay

                    // on win conditional
                    if (result == "OOOO") {
                        failView.text = "Congratz! You've Guessed Correctly!\nAnswer: $wordToGuess"
                        isCorrect = true
                        guessLimit = 0
                    } else {
                        guessLimit--

                        // on lost conditional
                        if (guessLimit == 0 && !isCorrect)
                            failView.text = "Oops! You're Out of Attempts!\nAnswer: $wordToGuess"
                    }
                }
                // invalid input conditional
                else
                    failView.text = "Oops, Gotta enter a Four Letter Word!"
            }
        }
    }

    // method for checking guess
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