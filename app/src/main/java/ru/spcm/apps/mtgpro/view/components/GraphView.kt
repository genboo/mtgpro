package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.GraphDot
import ru.spcm.apps.mtgpro.tools.format

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
    private var cloudPadding = 4

    private val curvePath = Path()
    private var curvePositiveColor: Int = ContextCompat.getColor(context, R.color.colorPositive)
    private var curveNegativeColor: Int = ContextCompat.getColor(context, R.color.colorNegative)
    private var curveNeutralColor: Int = ContextCompat.getColor(context, R.color.colorNoColor)

    private val monthPaint = Paint()
    private val dotsPaint = Paint()
    private val minMaxValues = Paint()
    private val selectedPointPaint = Paint()
    private val selectedPointBackgroundPaint = Paint()
    private val curvePaint = Paint()

    private var drawableCloud: Drawable? = null

    private var selectedPoint = -1

    private var isCubicCurve = true
        set(value) {
            field = value
            invalidate()
        }
    private var isLegendClick = false

    init {
        monthPaint.isAntiAlias = true
        monthPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        monthPaint.textAlign = Paint.Align.CENTER
        monthPaint.strokeWidth = context.resources.getDimensionPixelSize(R.dimen.graph_line_width).toFloat()
        monthPaint.textSize = context.resources.getDimensionPixelSize(R.dimen.graph_days_text_size).toFloat()

        dotsPaint.isAntiAlias = true
        dotsPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        dotsPaint.textAlign = Paint.Align.CENTER
        dotsPaint.strokeWidth = context.resources.getDimensionPixelSize(R.dimen.graph_curve_width).toFloat()

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
        curvePaint.strokeWidth = context.resources.getDimensionPixelSize(R.dimen.graph_curve_width).toFloat()
        curvePaint.style = Paint.Style.STROKE

        countRadius = context.resources.getDimensionPixelSize(R.dimen.graph_count_radius).toFloat()
        countSelectedRadius = context.resources.getDimensionPixelSize(R.dimen.graph_count_selected_radius).toFloat()
        legendOffset = context.resources.getDimensionPixelSize(R.dimen.graph_legend_offset).toFloat()
        cloudPadding = context.resources.getDimensionPixelSize(R.dimen.graph_cloud_padding)

        daysTextHeight = monthPaint.descent() - monthPaint.ascent()

        drawableCloud = ContextCompat.getDrawable(context, R.drawable.bg_rounded_cloud)

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
            drawDecors(canvas, daysLineY)
            drawCurve(canvas)
            drawDaysAndPointers(canvas, daysLineY)
        } else {
            canvas.drawText("Пока нечего показывать", width.toFloat() / 2, height.toFloat() / 2, monthPaint)
        }
    }

    private fun drawDecors(canvas: Canvas, daysLineY: Float) {
        //Вертикальная линия между графом и минимальным\максимальным значениями
        if (data.size <= 15) {
            monthPaint.color = curveNeutralColor
            canvas.drawLine(paddingLeft.toFloat() + legendOffset, paddingTop.toFloat(),
                    paddingLeft.toFloat() + legendOffset, daysLineY,
                    monthPaint)
            monthPaint.color = selectedPointPaint.color
        }

        //Горизонтальная линия над днями
        canvas.drawLine(paddingLeft.toFloat(), daysLineY - daysTextHeight,
                paddingLeft + paddedWidth.toFloat() + legendOffset, daysLineY - daysTextHeight,
                monthPaint)

        //Минимальное/максимальное знаячения
        var minOffset = countToOffset(minValue)
        var maxOffset = countToOffset(maxValue)
        if (maxOffset - minOffset < daysTextHeight && maxValue != minValue) {
            maxOffset -= daysTextHeight / 2
            minOffset += daysTextHeight / 2
        }
        canvas.drawText(maxValue.format(), paddingLeft.toFloat(), maxOffset, minMaxValues)
        if (maxValue != minValue) {
            canvas.drawText(minValue.format(), paddingLeft.toFloat(), minOffset, minMaxValues)
        }
    }

    private fun drawCurve(canvas: Canvas) {
        var prevX = 0f
        var prevY = 0f
        for (i in 1..data.size) {
            val dot = data[i - 1]
            val x = paddingLeft.toFloat() + legendOffset + i * cellWidth - cellWidth / 2
            val y = countToOffset(dot.count)
            curvePath.reset()
            if (i == 1) {
                curvePath.moveTo(x, y)
            } else {
                curvePath.moveTo(prevX, prevY)
                if (isCubicCurve) {
                    curvePath.cubicTo(prevX + cellWidth / 2, prevY, prevX + cellWidth / 2, y, x, y)
                } else {
                    curvePath.lineTo(x, y)
                }
                curvePaint.color = when {
                    (prevY > y) -> curvePositiveColor
                    (prevY < y) -> curveNegativeColor
                    else -> curveNeutralColor
                }
            }
            canvas.drawPath(curvePath, curvePaint)
            prevX = x
            prevY = y
        }
    }

    private fun drawDaysAndPointers(canvas: Canvas, daysLineY: Float) {
        for (i in 1..data.size) {
            val dot = data[i - 1]
            val x = paddingLeft.toFloat() + legendOffset + i * cellWidth - cellWidth / 2
            val y = countToOffset(dot.count)

            if (data.size > 15 && (i == 1 || i == data.size - 1 || i % 5 == 0) && i + 1 != data.size - 1 || data.size <= 15) {
                val day = dot.date.substringAfterLast("-")
                canvas.drawText(day, x, daysLineY, monthPaint)
            }

            if (i - 1 == selectedPoint) {
                val dotFormatted = dot.count.format()
                canvas.drawCircle(x, y, countSelectedRadius, selectedPointBackgroundPaint)
                canvas.drawCircle(x, y, countSelectedRadius, selectedPointPaint)

                val textBounds = Rect()
                monthPaint.getTextBounds(dotFormatted, 0, dotFormatted.length, textBounds)

                val rect = Rect(x.toInt() - textBounds.width() / 2 - cloudPadding,
                        (y - daysTextHeight - textBounds.height()).toInt() - cloudPadding,
                        x.toInt() + textBounds.width() / 2 + cloudPadding,
                        (y - daysTextHeight).toInt() + cloudPadding + cloudPadding / 3)
                drawableCloud?.bounds = rect
                drawableCloud?.draw(canvas)
                canvas.drawText(dotFormatted, x, y - daysTextHeight, monthPaint)
            } else {
                canvas.drawCircle(x, y, countRadius, dotsPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = (event.x + 0.5f).toInt()
        val action = event.action
        val touchedPoint = getNearestPoint(x)
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                if (x < paddingLeft + legendOffset) {
                    isLegendClick = true
                } else if (selectedPoint != touchedPoint) {
                    selectedPoint = touchedPoint
                    invalidate()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (!isLegendClick && selectedPoint != touchedPoint) {
                    selectedPoint = touchedPoint
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (isLegendClick) {
                    isLegendClick = false
                    isCubicCurve = !isCubicCurve
                    invalidate()
                }
                performClick()
            }
            MotionEvent.ACTION_CANCEL -> {
                isLegendClick = false
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
            if (selectedPoint == -1) {
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