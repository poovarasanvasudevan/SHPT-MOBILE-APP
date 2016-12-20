package `in`.shpt.widget

/**
 * Created by poovarasanv on 19/12/16.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 19/12/16 at 2:39 PM
 */

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.view.View
import java.util.*

class JustifiedTextView(context: Context, var text: String) : View(context) {
    internal var linesCollection = ArrayList<Line>()
    lateinit var textPaint: TextPaint
    var font: Typeface? = null
    internal var textColor: Int = 0
    internal var textSize = 42f
    var lineHeight = 57f
    var wordSpacing = 15f
    var lineSpacing = 15f
    internal var onBirim: Float = 0.toFloat()
    internal var w: Float = 0.toFloat()
    internal var h: Float = 0.toFloat()
    var leftPadding: Float = 0.toFloat()
    var rightPadding: Float = 0.toFloat()

    init {
        init()
    }

    private fun init() {
        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        textColor = Color.BLACK
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (font != null) {
            font = Typeface.createFromAsset(context.assets, "font/Trykker-Regular.ttf")
            textPaint.typeface = font
        }
        textPaint.color = textColor

        val minw = paddingLeft + paddingRight + suggestedMinimumWidth
        w = View.resolveSizeAndState(minw, widthMeasureSpec, 1).toFloat()
        h = View.MeasureSpec.getSize(widthMeasureSpec).toFloat()

        onBirim = 0.009259259f * w
        lineHeight = textSize + lineSpacing
        leftPadding = 3 * onBirim + paddingLeft
        rightPadding = 3 * onBirim + paddingRight

        textPaint.textSize = textSize

        wordSpacing = 15f
        var lineBuffer = Line()
        this.linesCollection.clear()
        val lines = text.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (line in lines) {
            val words = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            lineBuffer = Line()
            var lineWidth = leftPadding + rightPadding
            var totalWordWidth = 0f
            for (word in words) {
                val ww = textPaint.measureText(word) + wordSpacing
                if (lineWidth + ww + lineBuffer.words.size * wordSpacing > w) {// is
                    lineBuffer.addWord(word)
                    totalWordWidth += textPaint.measureText(word)
                    lineBuffer.spacing = (w - totalWordWidth - leftPadding - rightPadding) / (lineBuffer.words.size - 1)
                    this.linesCollection.add(lineBuffer)
                    lineBuffer = Line()
                    totalWordWidth = 0f
                    lineWidth = leftPadding + rightPadding
                } else {
                    lineBuffer.spacing = wordSpacing
                    lineBuffer.addWord(word)
                    totalWordWidth += textPaint.measureText(word)
                    lineWidth += ww
                }
            }
            this.linesCollection.add(lineBuffer)
        }
        setMeasuredDimension(w.toInt(), ((this.linesCollection.size + 1) * lineHeight + 10 * onBirim).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //canvas.drawLine(0f, 10f, measuredWidth.toFloat(), 10f, textPaint)
        var x: Float
        var y = lineHeight + onBirim
        for (line in linesCollection) {
            x = leftPadding
            for (s in line.words) {
                canvas.drawText(s, x, y, textPaint)
                x += textPaint.measureText(s) + line.spacing
            }
            y += lineHeight
        }
    }

    internal inner class Line {
        var words = ArrayList<String>()
        var spacing = 15f

        constructor() {
        }

        constructor(words: ArrayList<String>, spacing: Float) {
            this.words = words
            this.spacing = spacing
        }

        fun addWord(s: String) {
            words.add(s)
        }
    }
}