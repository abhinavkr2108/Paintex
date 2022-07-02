package com.example.paintex

import android.R.attr.path
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View


//Class drawView with its constructors
class drawView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle)

//Initializing all values to null at the beginning
{
    private var drawCanvas: Canvas? = null
    private var drawBitmap: Bitmap? = null

    private var drawPaint: Paint? = null
    private var canvasPaint: Paint? = null

    private var drawPath: customPath? = null

    private var brushSize: Float = 0.toFloat()
    private var color: Int = Color.RED

    private var mpaths =
        ArrayList<customPath>() // To make the lines persistent, we need to store all the values in an ArrayList

    class customPath(var color: Int, var brushThickness: Float) : Path() {

    }

    //Init Block - Used to initialize all the null values in the beginning
    init {
        drawPaint = Paint()
        canvasPaint = Paint(Paint.DITHER_FLAG)
        //Initializing all Paint Properties
        drawPaint!!.strokeWidth = 20f
        drawPaint!!.color = color
        drawPaint!!.style = Paint.Style.STROKE
        drawPaint!!.strokeJoin = Paint.Join.ROUND
        drawPaint!!.strokeCap = Paint.Cap.ROUND
        drawPaint!!.isAntiAlias = true

        drawPath = customPath(color, brushSize)
    }


    //Creating a Bitmap and initializing Canvas
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        drawBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(drawBitmap!!)
    }

    //Initializing onDraw Method:- This method is called when a stroke is drawn on the canvas
    //                            as a part of the painting.
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (path in mpaths) {
            drawPaint!!.strokeWidth = path.brushThickness
            drawPaint!!.color = path.color
            canvas!!.drawPath(path, drawPaint!!)
        }
        drawCanvas!!.drawBitmap(drawBitmap!!, 0f, 0f, canvasPaint)
        drawCanvas!!.drawPath(drawPath!!, drawPaint!!)
        drawPaint!!.strokeWidth = drawPath!!.brushThickness
        drawPaint!!.color = drawPath!!.color
        canvas!!.drawPath(drawPath!!, drawPaint!!)
    }


    //Initializing onTouch Events:-  This method acts as an event listener when a touch
    //                               event is detected on the device.

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var xpos = event?.x
        var ypos = event?.y
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                drawPath!!.color = color
                drawPath!!.brushThickness = brushSize
                drawPath!!.moveTo(xpos!!, ypos!!)
            }
            MotionEvent.ACTION_MOVE -> {
                drawPath?.lineTo(xpos!!, ypos!!)
            }
            MotionEvent.ACTION_UP -> {
                mpaths.add(drawPath!!)
                drawPath = customPath(color, brushSize)
            }
            else ->
                return false
        }
        invalidate()
        return true
    }

    //Function to set the brush size
    //TypedValue: Container for a dynamically typed data value. Primarily used with Resources for holding resource values.
    fun setBrushSize(newSize: Float) {
        brushSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            newSize,
            resources.displayMetrics
        )
        drawPaint!!.strokeWidth = brushSize
    }

    fun setColor(newColor: String) {
        color = Color.parseColor(newColor)
        drawPaint!!.color = color
    }

    fun clearScreen() {
        mpaths.removeAll(mpaths)
        drawBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        drawPath!!.reset()
        invalidate()
    }

}


