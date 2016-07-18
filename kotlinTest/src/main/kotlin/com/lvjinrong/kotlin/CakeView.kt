package com.lvjinrong.kotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast

import java.text.DecimalFormat
import java.util.ArrayList
import org.jetbrains.anko.toast
/**
 * Created by Administrator on 4/20 0020.
 */
class CakeView: View {

    constructor(context: Context, attrs: AttributeSet ? , defStyleAttr: Int = 0):super(context,attrs,defStyleAttr)
    constructor(context: Context , attrs: AttributeSet?): super(context,attrs,0)
    constructor(context: Context): super(context)

    private var ctx: Context? = null
    private var format: DecimalFormat? = null
    private var mList: MutableList<BaseMessage>? = null

    private var arcPaint: Paint? = null
    private var linePaint: Paint? = null
    private var textPaint: Paint? = null

    private var centerX: Float = 0.toFloat()
    private var centerY: Float = 0.toFloat()
    private var radius: Float = 0.toFloat()
    private var total: Float = 0.toFloat()
    private var startAngle: Float = 0.toFloat()
    private var textAngle: Float = 0.toFloat()

    private var lineList: MutableList<Array<PointF>>? = null
    private var textList: MutableList<PointF>? = null

    private var eventChild :MotionEvent? = null

    init {
        init(context)
    }

    private fun init(context: Context) {
        this.ctx = context
        this.lineList = ArrayList<Array<PointF>>()
        this.textList = ArrayList<PointF>()
        this.mList = ArrayList<BaseMessage>()
        this.format = DecimalFormat("##0.00")

        this.arcPaint = Paint()
        this.arcPaint!!.isAntiAlias = true
        this.arcPaint!!.isDither = true
        this.arcPaint!!.style = Paint.Style.STROKE

        this.linePaint = Paint()
        this.linePaint!!.isAntiAlias = true
        this.linePaint!!.isDither = true
        this.linePaint!!.style = Paint.Style.STROKE
        this.linePaint!!.strokeWidth = dip2px(ctx!!, 2f).toFloat()
        this.linePaint!!.color = Color.parseColor("#FFFFFF")

        this.textPaint = Paint()
        this.textPaint!!.isAntiAlias = true
        this.textPaint!!.isDither = true
        this.textPaint!!.style = Paint.Style.FILL
        this.textPaint!!.color = Color.parseColor("#FFFFFF")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width: Int
        val height: Int
        val widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)

        if (widthSpecMode == View.MeasureSpec.AT_MOST && heightSpecMode == View.MeasureSpec.EXACTLY) {
            height = heightSpecSize
            width = Math.min(heightSpecSize, Math.min(getScreenSize(ctx!!)[0], getScreenSize(ctx!!)[1]))
        } else if (widthSpecMode == View.MeasureSpec.EXACTLY && heightSpecMode == View.MeasureSpec.AT_MOST) {
            width = widthSpecSize
            height = Math.min(widthSpecSize, Math.min(getScreenSize(ctx!!)[0], getScreenSize(ctx!!)[1]))
        } else if (widthSpecMode == View.MeasureSpec.AT_MOST && heightSpecMode == View.MeasureSpec.AT_MOST) {
            width = Math.min(getScreenSize(ctx!!)[0], getScreenSize(ctx!!)[1])
            height = width
        } else {
            width = widthSpecSize
            height = heightSpecSize
        }

        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
        radius = Math.min(centerX, centerY) * 0.725f
        arcPaint!!.strokeWidth = radius / 3 * 2
        textPaint!!.textSize = radius / 7
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textList!!.clear()
        lineList!!.clear()
        lineList = ArrayList<Array<PointF>>()
        textList = ArrayList<PointF>()

        if (mList != null) {
            val mRectF = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
            for (i in mList!!.indices) {
                arcPaint!!.color = mList!![i].color
                canvas.drawArc(mRectF, startAngle, mList!![i].percent / total * 360f, false, arcPaint!!)
                lineList!!.add(linePointFs)//获取直线 开始坐标 结束坐标
                textAngle = startAngle + mList!![i].percent / total * 360f / 2
                textList!!.add(textPointF)   //获取文本文本
                startAngle += mList!![i].percent / total * 360f
            }
            //绘制间隔空白线
            drawSpacingLine(canvas, lineList!!)
            //绘制文字
            drawText(canvas)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        eventChild = event
        Toast.makeText(context , "${event!!.x},${event!!.y}" , Toast.LENGTH_LONG).show()
        invalidate()
        return super.onTouchEvent(event)
    }


    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        val mPaint = Paint()
        mPaint.color = Color.RED
        if (eventChild != null){
            canvas!!.drawCircle(eventChild!!.x , eventChild!!.y , 10.toFloat() , mPaint )
        }
    }

    /**
     * 获取文本文本

     * @return
     */
    private val textPointF: PointF
        get() {
            val textPointX = (centerX + radius * Math.cos(Math.toRadians(textAngle.toDouble()))).toFloat()
            val textPointY = (centerY + radius * Math.sin(Math.toRadians(textAngle.toDouble()))).toFloat()
            return PointF(textPointX, textPointY)
        }

    /**
     * 获取直线 开始坐标 结束坐标
     */
    private val linePointFs: Array<PointF>
        get() {
            val stopX = (centerX + (radius + arcPaint!!.strokeWidth / 2) * Math.cos(Math.toRadians(startAngle.toDouble()))).toFloat()
            val stopY = (centerY + (radius + arcPaint!!.strokeWidth / 2) * Math.sin(Math.toRadians(startAngle.toDouble()))).toFloat()
            val startX = (centerX + (radius - arcPaint!!.strokeWidth / 2) * Math.cos(Math.toRadians(startAngle.toDouble()))).toFloat()
            val startY = (centerY + (radius - arcPaint!!.strokeWidth / 2) * Math.sin(Math.toRadians(startAngle.toDouble()))).toFloat()
            val startPoint = PointF(startX, startY)
            val stopPoint = PointF(stopX, stopY)
            return arrayOf(startPoint, stopPoint)
        }

    /**
     * 画间隔线

     * @param canvas
     */
    private fun drawSpacingLine(canvas: Canvas, pointFs: List<Array<PointF>>) {
        for (fp in pointFs) {
            canvas.drawLine(fp[0].x, fp[0].y, fp[1].x, fp[1].y, linePaint!!)
        }
    }

    /**
     * 画文本

     * @param canvas
     */
    private fun drawText(canvas: Canvas) {
        for (i in textList!!.indices) {
            textPaint!!.textAlign = Paint.Align.CENTER
            val text = mList!![i].content
            canvas.drawText(text!!, textList!![i].x, textList!![i].y, textPaint!!)

            val fm = textPaint!!.fontMetrics
            canvas.drawText(format!!.format((mList!![i].percent * 100 / total).toDouble()) + "%", textList!![i].x, textList!![i].y + (fm.descent - fm.ascent), textPaint!!)
        }
    }

    /**
     * 设置间隔线的颜色

     * @param color
     */
    fun setSpacingLineColor(color: Int) {
        linePaint!!.color = color
    }

    /**
     * 设置文本颜色

     * @param color
     */
    fun setTextColor(color: Int) {
        textPaint!!.color = color
    }

    /**
     * 设置开始角度

     * @param startAngle
     */
    fun setStartAngle(startAngle: Float) {
        this.startAngle = startAngle
    }

    /**
     * 设置饼的宽度

     * @param width
     */
    fun setCakeStrokeWidth(width: Int) {
        arcPaint!!.strokeWidth = dip2px(ctx!!, width.toFloat()).toFloat()
    }

    /**
     * 设置饼的数据

     * @param mList
     */
    fun setCakeData(mList: MutableList<BaseMessage>?) {
        total = 0f
        if (mList == null) {
            return
        }
        for (i in mList.indices!!) {
            total += mList[i].percent
        }
        this.mList!!.clear()
        this.mList = mList
        invalidate()
    }

    companion object {

        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        fun px2dip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun getScreenSize(context: Context): IntArray {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return intArrayOf(outMetrics.widthPixels, outMetrics.heightPixels)
        }
    }


}
