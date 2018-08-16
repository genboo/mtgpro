package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.GraphDot

class GraphView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var data: List<GraphDot> = ArrayList()
    private var maxValue = 0f
    private var topBound = 0f
    private var minValue = 0f
    private var bottomBound = 0f

    private var paddedWidth = 0
    private var paddedHeight = 0
    private var cellWidth = 0
    private var daysTextHeight = 0f

    private var countRadius = 1f
    private var countSelectedRadius = 4f
    private var legendOffset = 4f

    private val curvePath = Path()
    private var curvePositiveColor: Int = ContextCompat.getColor(context, R.color.colorGreen)
    private var curveNegativeColor: Int = ContextCompat.getColor(context, R.color.colorRed)
    private var curveNeutralColor: Int = ContextCompat.getColor(context, R.color.colorNoColor)

    private val monthPaint = Paint()
    private val minMaxValues = Paint()
    private val selectedPointPaint = Paint()
    private val selectedPointBackgroundPaint = Paint()
    private val curvePaint = Paint()

    private var selectedPoint = -1

    init {
        monthPaint.isAntiAlias = true
        monthPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        monthPaint.textAlign = Paint.Align.CENTER
        monthPaint.strokeWidth = context.resources.getDimensionPixelSize(R.dimen.graph_line_width).toFloat()
        monthPaint.textSize = context.resources.getDimensionPixelSize(R.dimen.graph_days_text_size).toFloat()

        minMaxValues.isAntiAlias = true
        minMaxValues.color = ContextCompat.getColor(context, R.color.colorAccent)
        minMaxValues.textAlign = Paint.Align.LEFT
        minMaxValues.textSize = context.resources.getDimensionPixelSize(R.dimen.graph_days_text_size).toFloat()

        selectedPointPaint.isAntiAlias = true
        selectedPointPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        selectedPointPaint.textAlign = Paint.Align.CENTER
        selectedPointPaint.strokeWidth = context.resources.getDimensionPixelSize(R.dimen.graph_line_width).toFloat()
        selectedPointPaint.textSize = context.resources.getDimensionPixelSize(R.dimen.graph_days_text_size).toFloat()
        selectedPointPaint.style = Paint.Style.STROKE

        selectedPointBackgroundPaint.color = ContextCompat.getColor(context, android.R.color.white)
        selectedPointBackgroundPaint.style = Paint.Style.FILL

        curvePaint.isAntiAlias = true
        curvePaint.strokeWidth = context.resources.getDimensionPixelSize(R.dimen.graph_line_width).toFloat()
        curvePaint.style = Paint.Style.STROKE

        countRadius = context.resources.getDimensionPixelSize(R.dimen.graph_count_radius).toFloat()
        countSelectedRadius = context.resources.getDimensionPixelSize(R.dimen.graph_count_selected_radius).toFloat()
        legendOffset = context.resources.getDimensionPixelSize(R.dimen.graph_legend_offset).toFloat()

        daysTextHeight = monthPaint.descent() - monthPaint.ascent()

        if (isInEditMode) {
            data = arrayListOf(GraphDot("2018-11-01", 1f),
                    GraphDot("2018-11-02", 2f),
                    GraphDot("2018-11-03", 1.5f),
                    GraphDot("2018-11-04", 1.2f),
                    GraphDot("2018-11-05", 5f),
                    GraphDot("2018-11-06", 4.2f),
                    GraphDot("2018-11-07", 3.1f)
            )
            selectedPoint = data.size - 3
            updateMinMaxValue()
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (data.isNotEmpty()) {
            val daysLineY = paddingTop + paddedHeight.toFloat()

            if (data.size <= 15) {
                monthPaint.color = curveNeutralColor
                canvas.drawLine(paddingLeft.toFloat() + legendOffset, paddingTop.toFloat(),
                        paddingLeft.toFloat() + legendOffset, daysLineY,
                        monthPaint)
                monthPaint.color = selectedPointPaint.color
            }

            canvas.drawLine(paddingLeft.toFloat(), daysLineY - daysTextHeight,
                    paddingLeft + paddedWidth.toFloat() + legendOffset, daysLineY - daysTextHeight,
                    monthPaint)

            var col = 1
            var prevX = 0f
            var prevY = 0f
            data.forEach { dot ->
                val x = paddingLeft.toFloat() + legendOffset + col * cellWidth - cellWidth / 2
                val y = countToOffset(dot.count)
                curvePath.reset()
                if (col == 1) {
                    curvePath.moveTo(x, y)
                } else {
                    curvePath.moveTo(prevX, prevY)
                    curvePath.cubicTo(prevX + cellWidth / 2, prevY, prevX + cellWidth / 2, y, x, y)
                    curvePaint.color = when {
                        (prevY > y) -> curvePositiveColor
                        (prevY < y) -> curveNegativeColor
                        else -> curveNeutralColor
                    }
                }
                canvas.drawPath(curvePath, curvePaint)
                col++
                prevX = x
                prevY = y
            }

            col = 1
            data.forEach { dot ->
                val x = paddingLeft.toFloat() + legendOffset + col * cellWidth - cellWidth / 2
                val y = countToOffset(dot.count)

                if (data.size > 15 && (col == 1 || col == data.size - 1 || col % 5 == 0) && col + 1 != data.size - 1 || data.size <= 15) {
                    val day = dot.date.substringAfterLast("-")
                    canvas.drawText(day, x, daysLineY, monthPaint)
                }

                if (col - 1 == selectedPoint) {
                    canvas.drawCircle(x, y, countSelectedRadius, selectedPointBackgroundPaint)
                    canvas.drawCircle(x, y, countSelectedRadius, selectedPointPaint)
                    canvas.drawText(dot.count.toString(), x, y - daysTextHeight, monthPaint)
                } else {
                    canvas.drawCircle(x, y, countRadius, monthPaint)
                }

                col++
                prevX = x
                prevY = y
            }

            var minOffset = countToOffset(minValue)
            var maxOffset = countToOffset(maxValue)
            if (maxOffset - minOffset < daysTextHeight && maxValue != minValue) {
                maxOffset -= daysTextHeight / 2
                minOffset += daysTextHeight / 2
            }
            canvas.drawText(maxValue.toString(), paddingLeft.toFloat(), maxOffset, minMaxValues)
            if(maxValue != minValue) {
                canvas.drawText(minValue.toString(), paddingLeft.toFloat(), minOffset, minMaxValues)
            }
        } else {
            canvas.drawText("Пока нечего показывать", width.toFloat() / 2, height.toFloat() / 2, monthPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = (event.x + 0.5f).toInt()
        val action = event.action
        when (action) {
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> {
                val touchedPoint = getNearestPoint(x)
                if (selectedPoint != touchedPoint) {
                    selectedPoint = touchedPoint
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                performClick()
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun getNearestPoint(x: Int): Int {
        val col = (x - paddingStart - legendOffset.toInt()) / cellWidth
        return when {
            (col >= data.size) -> data.size - 1
            (col < 0) -> 0
            else -> col
        }
    }

    private fun countToOffset(count: Float): Float {
        val offset = (count - bottomBound) * ((paddedHeight - daysTextHeight) / (topBound - bottomBound))
        return paddingTop + paddedHeight - daysTextHeight - offset
    }

    fun setData(data: List<GraphDot>) {
        this.data = data
        if (data.isNotEmpty()) {
            if(selectedPoint == -1) {
                selectedPoint = data.size - 1
            }
            updateMinMaxValue()
            cellWidth = paddedWidth / data.size
            invalidate()
        }
    }

    private fun updateMinMaxValue() {
        maxValue = data[0].count
        minValue = data[0].count

        data.forEach { dot ->
            if (dot.count > maxValue) {
                maxValue = dot.count
            }
            if (dot.count < minValue) {
                minValue = dot.count
            }
        }

        //15% снизу и сверху плюс небольшой запас, если все значения одинаковые
        val diff = (maxValue - minValue + .01f) * .15f
        topBound = maxValue + diff
        bottomBound = minValue - diff
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val w = right - left
        val h = bottom - top

        paddedWidth = w - paddingEnd - paddingStart - legendOffset.toInt()
        paddedHeight = h - paddingBottom - paddingTop

        cellWidth = if (data.isEmpty()) {
            paddedWidth / COLS
        } else {
            paddedWidth / data.size
        }
    }

    companion object {
        const val COLS = 30
    }

}