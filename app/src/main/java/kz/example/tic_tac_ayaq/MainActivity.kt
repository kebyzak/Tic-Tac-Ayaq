package kz.example.tic_tac_ayaq

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var board: Array<Array<Button>>
    private lateinit var tvStatus: TextView
    private lateinit var btnReset: Button

    private var playerXturn = true
    private var turnsPlayed = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = Array(3) { row ->
            Array(3) { column ->
                val boardItemID = "board_item_${row}_${column}"
                val boardItem =
                    findViewById<View>(resources.getIdentifier(boardItemID, "id", packageName))
                boardItem.setOnClickListener(this)
                boardItem as Button
            }
        }

        tvStatus = findViewById(R.id.tv_status)
        btnReset = findViewById(R.id.btn_reset)
        btnReset.setBackgroundColor(getColor(R.color.xBg))
        btnReset.setOnClickListener { resetGame() }
    }

    override fun onClick(view: View) {
        if (view !is Button || view.text.isNotEmpty()) return

        val symbol = if (playerXturn) {
            view.setBackgroundColor(getColor(R.color.xBg))
            "X"
        } else {
            view.setBackgroundColor(getColor(R.color.oBg))
            "O"
        }
        view.setTextColor(Color.WHITE)
        view.text = symbol

        if (checkForWin()) {
            tvStatus.text = "$symbol жеңді!"
            btnReset.visibility = View.VISIBLE
            disableBoard()
        } else if (turnsPlayed == 8) {
            tvStatus.text = "Тең ойын!"
            btnReset.visibility = View.VISIBLE
        } else {
            playerXturn = !playerXturn
            tvStatus.text = "${if (playerXturn) "X" else "O"} жүреді"
            turnsPlayed++
        }
    }

    private fun checkForWin(): Boolean {
        for (i in 0..2) {
            if (board[i][0].text == board[i][1].text &&
                board[i][0].text == board[i][2].text &&
                board[i][0].text.isNotEmpty()
            ) return true

            if (board[0][i].text == board[1][i].text &&
                board[0][i].text == board[2][i].text &&
                board[0][i].text.isNotEmpty()
            ) return true
        }

        if (board[0][0].text == board[1][1].text &&
            board[0][0].text == board[2][2].text &&
            board[0][0].text.isNotEmpty()
        ) return true

        if (board[0][2].text == board[1][1].text &&
            board[0][2].text == board[2][0].text &&
            board[0][2].text.isNotEmpty()
        ) return true


        return false
    }


    private fun disableBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j].isEnabled = false
            }
        }
    }

    private fun resetGame() {
        playerXturn = true
        turnsPlayed = 0
        tvStatus.text = "X жүреді"

        for (i in board.indices) {
            for (j in board[i].indices) {
                val button = board[i][j]
                button.text = ""
                button.isEnabled = true
                button.setBackgroundResource(R.drawable.btn_bg)
                btnReset.visibility = View.INVISIBLE
            }
        }
    }
}
