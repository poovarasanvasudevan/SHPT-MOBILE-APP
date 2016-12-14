package `in`.shpt.widget

/**
 * Created by poovarasanv on 14/12/16.

 * @author poovarasanv
 * *
 * @project SHPT
 * *
 * @on 14/12/16 at 3:19 PM
 */
/*
 * Copyright (C) 2016 Dardle Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import `in`.shpt.R
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.TintTypedArray
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout

/**
 * LabelLayout provides a label at the corner to display text
 */
class LabelLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet = null!!, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private var mLabelDistance: Int = 0
    private var mLabelHeight: Int = 0
    private var mLabelBackground: Drawable? = null
    private var mLabelGravity: Gravity? = null

    private var mLabelText: String? = null
    private var mLabelTextSize: Int = 0
    private var mLabelTextColor: Int = 0
    private var mLabelTextDirection: TextDirection? = null

    private val mTextPaint: Paint

    init {

        // Enable 'onDraw()'
        setWillNotDraw(false)

        val tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.LabelLayout)
        mLabelDistance = tintTypedArray.getDimensionPixelSize(R.styleable.LabelLayout_labelDistance, 0)
        mLabelHeight = tintTypedArray.getDimensionPixelSize(R.styleable.LabelLayout_labelHeight, 0)
        mLabelBackground = tintTypedArray.getDrawable(R.styleable.LabelLayout_labelBackground)
        mLabelGravity = Gravity.values()[tintTypedArray.getInt(R.styleable.LabelLayout_labelGravity, Gravity.TOP_LEFT.ordinal)]
        mLabelText = tintTypedArray.getString(R.styleable.LabelLayout_labelText)
        mLabelTextSize = tintTypedArray.getDimensionPixelSize(R.styleable.LabelLayout_labelTextSize, Paint().textSize.toInt())
        mLabelTextColor = tintTypedArray.getColor(R.styleable.LabelLayout_labelTextColor, Color.BLACK)
        mLabelTextDirection = TextDirection.values()[tintTypedArray.getInt(R.styleable.LabelLayout_labelTextDirection, TextDirection.LEFT_TO_RIGHT.ordinal)]
        tintTypedArray.recycle()

        // Setup text paint
        mTextPaint = Paint()
        mTextPaint.isDither = true
        mTextPaint.isAntiAlias = true
        mTextPaint.strokeJoin = Paint.Join.ROUND
        mTextPaint.strokeCap = Paint.Cap.SQUARE
    }

    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)

        // Draw background
        val centerCoordinate = calculateCenterCoordinate(mLabelDistance, mLabelHeight, mLabelGravity!!)
        val labelHalfWidth = calculateWidth(mLabelDistance, mLabelHeight) / 2
        val labelHalfHeight = mLabelHeight / 2
        val labelRect = Rect(centerCoordinate[0] - labelHalfWidth, centerCoordinate[1] - labelHalfHeight, centerCoordinate[0] + labelHalfWidth, centerCoordinate[1] + labelHalfHeight)
        mLabelBackground!!.bounds = calculateBackgroundBounds(mLabelBackground!!, labelRect)

        canvas.save()
        canvas.rotate(calculateRotateDegree(mLabelGravity!!), centerCoordinate[0].toFloat(), centerCoordinate[1].toFloat())
        mLabelBackground!!.draw(canvas)
        canvas.restore()

        // Draw text
        val bisectorPath = Path()
        val bisectorCoordinates = calculateBisectorCoordinates(mLabelDistance, mLabelHeight, mLabelGravity!!)
        bisectorPath.moveTo(bisectorCoordinates[0].toFloat(), bisectorCoordinates[1].toFloat())
        bisectorPath.lineTo(bisectorCoordinates[2].toFloat(), bisectorCoordinates[3].toFloat())

        mTextPaint.textSize = mLabelTextSize.toFloat()
        mTextPaint.color = mLabelTextColor
        val offsets = calculateTextOffsets(mLabelText as String, mTextPaint, mLabelDistance, mLabelHeight, mLabelGravity!!)
        val displayText: String
        if (mLabelTextDirection == TextDirection.LEFT_TO_RIGHT) {
            displayText = mLabelText as String
        } else {
            displayText = StringBuffer(mLabelText).reverse().toString()
        }
        canvas.drawTextOnPath(displayText, bisectorPath, offsets[0], offsets[1], mTextPaint)

    }

    /**
     * Get the distance from vertex to label's edge

     * @return The distance from vertex to label's edge
     */
    /**
     * Set the distance from vertex to label's edge

     * @param labelDistance The distance from vertex to label's edge
     */
    var labelDistance: Int
        get() = mLabelDistance
        set(labelDistance) {
            mLabelDistance = labelDistance
            invalidate()
        }

    /**
     * Get the height of label

     * @return The height of label
     */
    /**
     * Set the height of label

     * @param labelHeight The height of label
     */
    var labelHeight: Int
        get() = mLabelHeight
        set(labelHeight) {
            mLabelHeight = labelHeight
            invalidate()
        }

    /**
     * Get the background of label

     * @return The background of label
     */
    /**
     * Set the background of label

     * @param labelBackground The background of label
     */
    var labelBackground: Drawable
        get() = mLabelBackground!!
        set(labelBackground) {
            mLabelBackground = labelBackground
            invalidate()
        }

    /**
     * Get the gravity of label

     * @return The gravity of label
     * *
     * @see Gravity
     */
    /**
     * Set the gravity of label

     * @param labelGravity The gravity of label
     */
    var labelGravity: Gravity
        get() = mLabelGravity!!
        set(labelGravity) {
            mLabelGravity = labelGravity
            invalidate()
        }

    /**
     * Get the text of label

     * @return The text of label
     */
    /**
     * Set the text of label

     * @param labelText The text of label
     */
    var labelText: String
        get() = mLabelText!!
        set(labelText) {
            mLabelText = labelText
            invalidate()
        }

    /**
     * Get the text size of label

     * @return The text size of label
     */
    /**
     * Set the text size of label

     * @param labelTextSize The text size of label
     */
    var labelTextSize: Int
        get() = mLabelTextSize
        set(labelTextSize) {
            mLabelTextSize = labelTextSize
            invalidate()
        }

    /**
     * Get the text color of label

     * @return The text color of label
     */
    /**
     * Set the text color of label

     * @param labelTextColor The text color of label
     */
    var labelTextColor: Int
        get() = mLabelTextColor
        set(labelTextColor) {
            mLabelTextColor = labelTextColor
            invalidate()
        }

    /**
     * Get text direction

     * @return The text direction
     */
    /**
     * Set text direction

     * @param textDirection The text direction
     */
    var labelTextDirection: TextDirection
        get() = mLabelTextDirection!!
        set(textDirection) {
            mLabelTextDirection = textDirection
            invalidate()
        }

    // Calculate the absolute position of point intersecting between canvas edge and bisector
    private fun calculateBisectorIntersectAbsolutePosition(distance: Int, height: Int): Int {
        return (Math.sqrt(2.0) * (distance + height / 2)).toInt()
    }

    // Calculate the starting and ending points coordinates of bisector line
    private fun calculateBisectorCoordinates(distance: Int, height: Int, gravity: Gravity): IntArray {
        val bisectorIntersectAbsolutePosition = calculateBisectorIntersectAbsolutePosition(distance, height)
        val results = IntArray(4)

        val bisectorStartX: Int
        val bisectorStartY: Int
        val bisectorEndX: Int
        val bisectorEndY: Int
        when (gravity) {
            LabelLayout.Gravity.TOP_RIGHT -> {
                bisectorStartY = 0
                bisectorStartX = measuredWidth - bisectorIntersectAbsolutePosition
                bisectorEndX = measuredWidth
                bisectorEndY = bisectorIntersectAbsolutePosition
            }

            LabelLayout.Gravity.BOTTOM_RIGHT -> {
                bisectorStartX = measuredWidth - bisectorIntersectAbsolutePosition
                bisectorStartY = measuredHeight
                bisectorEndX = measuredWidth
                bisectorEndY = measuredHeight - bisectorIntersectAbsolutePosition
            }

            LabelLayout.Gravity.BOTTOM_LEFT -> {
                bisectorStartX = 0
                bisectorStartY = measuredHeight - bisectorIntersectAbsolutePosition
                bisectorEndX = bisectorIntersectAbsolutePosition
                bisectorEndY = measuredHeight
            }

            else -> {
                bisectorStartX = 0
                bisectorStartY = bisectorIntersectAbsolutePosition
                bisectorEndX = bisectorIntersectAbsolutePosition
                bisectorEndY = 0
            }
        }

        results[0] = bisectorStartX
        results[1] = bisectorStartY
        results[2] = bisectorEndX
        results[3] = bisectorEndY

        return results
    }

    // Calculate text horizontal and vertical offset
    private fun calculateTextOffsets(text: String, paint: Paint, distance: Int, height: Int, gravity: Gravity): FloatArray {
        val offsets = FloatArray(2)

        val textBounds = Rect()
        paint.getTextBounds(text, 0, text.length, textBounds)

        val hOffset = (calculateBisectorIntersectAbsolutePosition(distance, height) / Math.sqrt(2.0) - textBounds.width() / 2.0).toFloat()
        val vOffset: Float
        if (distance >= height) {
            vOffset = textBounds.height() * 0.5f
        } else {
            if (gravity == Gravity.TOP_LEFT || gravity == Gravity.TOP_RIGHT) {
                vOffset = textBounds.height() * (0.5f + (height - distance) / height.toFloat() * 0.5f)
            } else {
                vOffset = textBounds.height() * (0.5f - (height - distance) / height.toFloat() * 0.5f)
            }
        }

        Log.d(LabelLayout::class.java.simpleName, String.format("%d, %d, %f", distance, height, vOffset))

        offsets[0] = hOffset
        offsets[1] = vOffset

        return offsets
    }

    private fun calculateCenterCoordinate(distance: Int, height: Int, gravity: Gravity): IntArray {
        val results = IntArray(2)
        val x: Int
        val y: Int

        val centerAbsolutePosition = calculateCenterAbsolutePosition(distance, height)
        when (gravity) {

            LabelLayout.Gravity.TOP_RIGHT -> {
                x = measuredWidth - centerAbsolutePosition
                y = centerAbsolutePosition
            }

            LabelLayout.Gravity.BOTTOM_RIGHT -> {
                x = measuredWidth - centerAbsolutePosition
                y = measuredHeight - centerAbsolutePosition
            }

            LabelLayout.Gravity.BOTTOM_LEFT -> {
                x = centerAbsolutePosition
                y = measuredHeight - centerAbsolutePosition
            }
            else -> {
                x = centerAbsolutePosition
                y = centerAbsolutePosition
            }
        }

        results[0] = x
        results[1] = y
        return results
    }

    private fun calculateCenterAbsolutePosition(distance: Int, height: Int): Int {
        return ((distance + height / 2) / Math.sqrt(2.0)).toInt()
    }

    private fun calculateWidth(distance: Int, height: Int): Int {
        return 2 * (distance + height)
    }

    private fun calculateBackgroundBounds(drawable: Drawable, labelRect: Rect): Rect {
        val rect: Rect

        if (drawable is ColorDrawable) {
            rect = Rect(labelRect)
        } else {
            rect = Rect()

            if (drawable.intrinsicWidth <= labelRect.width() && drawable.intrinsicHeight <= labelRect.height()) {
                // No need to scale
                rect.left = labelRect.centerX() - drawable.intrinsicWidth / 2
                rect.top = labelRect.centerY() - drawable.intrinsicHeight / 2
                rect.right = labelRect.centerX() + drawable.intrinsicWidth / 2
                rect.bottom = labelRect.centerY() + drawable.intrinsicHeight / 2
            } else {
                // Need to scale
                val width: Int
                val height: Int
                val ratio = drawable.intrinsicWidth / drawable.intrinsicHeight
                if (drawable.intrinsicWidth / drawable.intrinsicHeight >= labelRect.width() / labelRect.height()) {
                    // Scale to fill width
                    width = labelRect.width()
                    height = labelRect.width() / ratio
                } else {
                    // Scale to fill height
                    width = labelRect.height() * ratio
                    height = labelRect.height()
                }

                rect.left = labelRect.centerX() - width / 2
                rect.top = labelRect.centerY() - height / 2
                rect.right = labelRect.centerX() + width / 2
                rect.bottom = labelRect.centerY() + height / 2
            }
        }

        return rect
    }

    private fun calculateRotateDegree(gravity: Gravity): Float {
        val degree: Float

        when (gravity) {
            LabelLayout.Gravity.BOTTOM_RIGHT,

            LabelLayout.Gravity.TOP_RIGHT, LabelLayout.Gravity.BOTTOM_LEFT -> degree = 45f
            else -> degree = -45f
        }

        return degree
    }

    enum class Gravity {
        TOP_LEFT, TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT
    }

    enum class TextDirection {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }
}