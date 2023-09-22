package org.deiverbum.app.util

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Esta clase crea un TextView Zoomable, cuyo contenido se puede agrandar o disminuir juntando o separando los dedos en la pantalla.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2018.1
 */
class ZoomTextView : AppCompatTextView, OnTouchListener {
    private var mRatio = 13.0f
    private var mBaseDist = 0
    private var mBaseRatio = 0f

    constructor(context: Context?) : super(context!!) {
        setTextIsSelectable(true)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        setTextIsSelectable(true)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        setTextIsSelectable(true)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.pointerCount == 2) {
            val action = event.action
            val pureaction = action and MotionEvent.ACTION_MASK
            if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                mBaseDist = getDistance(event)
                mBaseRatio = mRatio
            } else {
                val delta = (getDistance(event) - mBaseDist) / STEP
                val multi = 2.0.pow(delta.toDouble()).toFloat()
                mRatio = min(1024.0f, max(0.1f, mBaseRatio * multi))
                this.textSize = mRatio + 13
            }
        } else {
            performClick()
            //return super.onTouchEvent(event);
            return super.onTouchEvent(event)
        }
        return true
    }

    private fun getDistance(event: MotionEvent): Int {
        val dx = (event.getX(0) - event.getX(1)).toInt()
        val dy = (event.getY(0) - event.getY(1)).toInt()
        return sqrt((dx * dx + dy * dy).toDouble()).toInt()
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return false
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        // FIXME simple workaround to https://code.google.com/p/android/issues/detail?id=191430
        val startSelection = selectionStart
        val endSelection = selectionEnd
        if (startSelection != endSelection) {
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                val text = text
                setText(null)
                setText(text)
            }
        }
        return super.dispatchTouchEvent(event)
    }

    companion object {
        const val STEP = 200f
    }
}