package hu.bme.aut.android.tictactoe.view

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.bme.aut.android.tictactoe.GameActivity
import androidx.core.content.ContextCompat.startActivity
import hu.bme.aut.android.tictactoe.model.TicTacToeModel
import kotlin.math.min

class TicTacToeView : View {

    private val paintBg = Paint()
    private val paintLine = Paint()
    private val gameActivity: GameActivity = this.context as GameActivity

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        paintBg.color = Color.BLACK
        paintBg.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5F
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paintBg)

        drawGameArea(canvas)
        drawPlayers(canvas)
    }

    private fun drawGameArea(canvas: Canvas) {
        val widthFloat: Float = width.toFloat()
        val heightFloat: Float = height.toFloat()

        // border
        canvas.drawRect(0F, 0F, widthFloat, heightFloat, paintLine)

        // two horizontal lines
        canvas.drawLine(0F, heightFloat / 3, widthFloat, widthFloat / 3, paintLine)
        canvas.drawLine(0F, 2 * heightFloat / 3, widthFloat, 2 * heightFloat / 3, paintLine)

        // two vertical lines
        canvas.drawLine(widthFloat / 3, 0F, widthFloat / 3, heightFloat, paintLine)
        canvas.drawLine(2 * widthFloat / 3, 0F, 2 * widthFloat / 3, heightFloat, paintLine)
    }

    private fun drawPlayers(canvas: Canvas) {
        // draw a circle at the center of the field
        // X coordinate: left side of the square + half width of the square
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                when (TicTacToeModel.getFieldContent(i, j)) {
                    TicTacToeModel.CIRCLE -> {
                        val centerX = i * width / 3 + width / 6
                        val centerY = j * height / 3 + height / 6
                        val radius = height / 6 - 2
                        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paintLine)
                    }
                    TicTacToeModel.CROSS -> {
                        canvas.drawLine(
                            (i * width / 3).toFloat(),
                            (j * height / 3).toFloat(),
                            ((i + 1) * width / 3).toFloat(),
                            ((j + 1) * height / 3).toFloat(),
                            paintLine
                        )
                        canvas.drawLine(
                            ((i + 1) * width / 3).toFloat(),
                            (j * height / 3).toFloat(),
                            (i * width / 3).toFloat(),
                            ((j + 1) * height / 3).toFloat(),
                            paintLine
                        )
                    }
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        val d: Int

        when {
            w == 0 -> { d = h }
            h == 0 -> { d = w }
            else -> { d = min(w, h) }
        }

        setMeasuredDimension(d, d)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val tX: Int = (event.x / (width / 3)).toInt()
                val tY: Int = (event.y / (height / 3)).toInt()
                if (tX < 3 && tY < 3 && TicTacToeModel.getFieldContent(tX, tY) == TicTacToeModel.EMPTY) {
                    TicTacToeModel.setFieldContent(tX, tY, TicTacToeModel.nextPlayer)
                    invalidate()
                    if (TicTacToeModel.checkEnd()){
                        gameActivity.endGame(TicTacToeModel.getWinner())
                        gameActivity.finish()
                    }
                }
                return true
            }
            else -> return super.onTouchEvent(event)
        }
    }

}
