package ru.spcm.apps.mtgpro.view.components

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.widget.*
import android.util.TypedValue
import android.view.Gravity
import ru.spcm.apps.mtgpro.R


class NumberCounterView : LinearLayout {

    private lateinit var counter: TextView
    private lateinit var plus: ImageButton
    private lateinit var minus: ImageButton
    private var change: (Int) -> Unit = { }

    private var count = 0

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        gravity = Gravity.CENTER

        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.NumberCounterView,
                0, 0)

        plus = ImageButton(context)
        minus = ImageButton(context)
        counter = TextView(context)

        val plusIcon = a.getDrawable(R.styleable.NumberCounterView_icon_plus)
                ?: resources.getDrawable(R.drawable.ic_plus, context.theme)
        val minusIcon = a.getDrawable(R.styleable.NumberCounterView_icon_minus)
        val defaultCount = a.getInt(R.styleable.NumberCounterView_default_count, 0)
        val size = a.getDimensionPixelSize(R.styleable.NumberCounterView_size, 48)
        val counterSize = a.getDimensionPixelSize(R.styleable.NumberCounterView_counter_size, 48)
        val counterTextSize = a.getDimension(R.styleable.NumberCounterView_text_size, 18f) /
                resources.displayMetrics.scaledDensity
        val color = ContextCompat.getColor(context, R.color.colorTextMain)
        a.recycle()

        count = defaultCount

        prepareImageButton(plus, plusIcon, color, size)
        prepareImageButton(minus, minusIcon, color, size)

        with(counter) {
            text = "$count"
            layoutParams = LayoutParams(counterSize, LayoutParams.WRAP_CONTENT)
            gravity = Gravity.CENTER
            textSize = counterTextSize
        }

        minus.setOnClickListener {
            if (count > defaultCount) {
                count--
                counter.text = "$count"
                change(count)
            }
        }

        plus.setOnClickListener {
            count++
            counter.text = "$count"
            change(count)
        }

        addView(minus)
        addView(counter)
        addView(plus)
    }

    private fun prepareImageButton(view: ImageButton, drawableResource: Drawable?, tintColor: Int, size: Int) {
        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true)
        if (drawableResource != null) {
            DrawableCompat.setTint(drawableResource, tintColor)
        }
        with(view) {
            setImageDrawable(drawableResource)
            layoutParams = LayoutParams(size, size)
            scaleType = ImageView.ScaleType.CENTER_CROP
            background = resources.getDrawable(outValue.resourceId, context.theme)
        }
    }

    fun setCount(count: Int) {
        this.count = count
        counter.text = "$count"
    }

    fun getCount(): Int {
        return this.count
    }

    fun setOnChangeListener(event: (Int) -> Unit) {
        change = event
    }
}