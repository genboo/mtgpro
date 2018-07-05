package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.LibraryColorState
import ru.spcm.apps.mtgpro.model.dto.LibraryManaState

class LibraryStateView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val mTextPaint: Paint
    private val mColorPaint: Paint

    private var mManaState: List<LibraryManaState>? = null
    private var mColorState: List<LibraryColorState>? = null
    private val mMargin: Float
    private val mColWidth: Float
    private val mColorGraphSize: Float
    private val mColorGraphStrokeWidth: Float
    private val mMainColor: Int
    private val mColorCreatures: Int

    private val fullSize: Int
        get() {
            var full = 0
            for (state in mColorState!!) {
                full += state.count
            }
            return if (full == 0) {
                1
            } else full
        }

    private val maximum: Int
        get() {
            var max = 1
            for (state in mManaState!!) {
                if (state.count > max) {
                    max = state.count
                }
            }
            return max
        }

    init {
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.LibraryStateView,
                0, 0)

        mMainColor = a.getColor(R.styleable.LibraryStateView_color, getColor(R.color.colorPrimary))
        mColorCreatures = a.getColor(R.styleable.LibraryStateView_colorCreatures, getColor(R.color.colorAccent))
        mMargin = a.getDimension(R.styleable.LibraryStateView_manaGraphMargin, 0f)
        mColWidth = a.getDimension(R.styleable.LibraryStateView_manaColWidth, 0f)
        mColorGraphSize = a.getDimension(R.styleable.LibraryStateView_colorGraphSize, 45f)
        mColorGraphStrokeWidth = a.getDimension(R.styleable.LibraryStateView_colorGraphStrokeWidth, 7f)

        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.color = mMainColor
        mTextPaint.textSize = TEXT_SIZE.toFloat()
        mTextPaint.textAlign = Paint.Align.CENTER

        mColorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mColorPaint.style = Paint.Style.STROKE

        if (isInEditMode) {
            val manaState = ArrayList<LibraryManaState>()
            for (i in 1..6) {
                val state = LibraryManaState(i, i, i / 2)
                manaState.add(state)
            }
            mManaState = manaState

            val colorState = ArrayList<LibraryColorState>()
            for (i in 0..5) {
                val state = LibraryColorState(getEditColor(i), 10)
                colorState.add(state)
            }
            mColorState = colorState
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mManaState != null && !mManaState!!.isEmpty()) {
            drawManaState(canvas)
        }
        if (mColorState != null && !mColorState!!.isEmpty()) {
            drawColorState(canvas)
        }
    }

    /**
     * Отрисовка распределения по цветам
     *
     * @param canvas Канвас
     */
    private fun drawColorState(canvas: Canvas) {
        val full = fullSize
        var angle = 0f

        mColorPaint.color = mMainColor
        mColorPaint.strokeWidth = mColorGraphStrokeWidth * 1.3f
        canvas.drawCircle(width.toFloat() - paddingRight.toFloat() - mColorGraphSize - mColorGraphStrokeWidth,
                height / 2f + paddingBottom, mColorGraphSize, mColorPaint)

        mColorPaint.strokeWidth = mColorGraphStrokeWidth
        for (state in mColorState!!) {
            val percent = state.count / full.toFloat()
            mColorPaint.color = getStateColor(state)
            val rect = RectF(
                    width.toFloat() - paddingRight.toFloat() - 2 * mColorGraphSize - mColorGraphStrokeWidth,
                    height / 2f - mColorGraphSize + paddingBottom,
                    width.toFloat() - paddingRight.toFloat() - mColorGraphStrokeWidth,
                    height / 2f + mColorGraphSize + paddingBottom.toFloat())
            canvas.drawArc(rect, angle, percent * 360f, false, mColorPaint)
            mColorPaint.color = mMainColor
            angle += percent * 360f
            canvas.drawArc(rect, angle - 2f, 2f, false, mColorPaint)
        }

    }

    /**
     * Отрисовка кривой маны
     *
     * @param canvas Канвас
     */
    private fun drawManaState(canvas: Canvas) {
        val max = maximum
        mTextPaint.color = mMainColor
        for (i in mManaState!!.indices) {
            val state = mManaState!![i]
            canvas.drawText(Integer.toString(state.cmc),
                    paddingLeft.toFloat() + i * mColWidth + mColWidth / 2 - mMargin / 2 + mMargin,
                    height - paddingBottom.toFloat(),
                    mTextPaint)

            val percent = state.count / max.toFloat()
            val height = height - paddingBottom - paddingTop - (height - paddingBottom - paddingTop) * percent
            val rect = Rect(
                    paddingLeft + i * Math.round(mColWidth) + Math.round(mMargin),
                    paddingTop + Math.round(height),
                    paddingLeft + i * Math.round(mColWidth) + Math.round(mColWidth),
                    getHeight() - paddingBottom - TEXT_SIZE)
            canvas.drawRect(rect, mTextPaint)
        }
        mTextPaint.color = mColorCreatures
        for (i in mManaState!!.indices) {
            val state = mManaState!![i]
            if (state.creatures > 0) {
                val percent = state.creatures / max.toFloat()
                val height = height - paddingBottom - paddingTop - (height - paddingBottom - paddingTop) * percent
                val rect = Rect(
                        paddingLeft + i * Math.round(mColWidth) + Math.round(mMargin),
                        paddingTop + Math.round(height),
                        paddingLeft + i * Math.round(mColWidth) + Math.round(mColWidth),
                        getHeight() - paddingBottom - TEXT_SIZE)
                canvas.drawRect(rect, mTextPaint)
            }
        }
    }

    private fun getStateColor(state: LibraryColorState): Int {
        return when (state.color) {
            "Red" -> getColor(R.color.colorRed)
            "Green" -> getColor(R.color.colorGreen)
            "Blue" -> getColor(R.color.colorBlue)
            "Black" -> getColor(R.color.colorBlack)
            else -> getColor(R.color.colorWhite)
        }
    }

    fun setManaState(states: MutableList<LibraryManaState>) {
        mManaState = states
        invalidate()
    }

    fun setColorState(states: MutableList<LibraryColorState>) {
        mColorState = states
        invalidate()
    }

    private fun getEditColor(color: Int): String {
        return when (color) {
            0 -> "Red"
            1 -> "Blue"
            2 -> "Green"
            3 -> "Black"
            4 -> "White"
            else -> ""
        }
    }

    private fun getColor(color: Int): Int {
        return ContextCompat.getColor(context, color)
    }

    companion object {
        private const val TEXT_SIZE = 22
    }

}