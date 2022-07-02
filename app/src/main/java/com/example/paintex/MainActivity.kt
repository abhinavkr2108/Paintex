package com.example.paintex

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.brush_size_dialog.*

class MainActivity : AppCompatActivity() {
    lateinit var linearLayout: LinearLayout
    lateinit var drawingView: drawView
    lateinit var imageButtonColor: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        drawingView = findViewById(R.id.drawView)
        drawingView.setBrushSize(10f)

        brushButton.setOnClickListener {
            brushSizeDialog()
        }

        //Setting on-click listeners for changing colors:

        black.setOnClickListener {
            val blackTag = black.tag.toString()
            drawingView.setColor(blackTag)
        }
        white.setOnClickListener{
            val whiteTag = white.tag.toString()
            drawingView.setColor(whiteTag)
        }
        purple.setOnClickListener {
            val purpleTag = purple.tag.toString()
            drawingView.setColor(purpleTag)
        }
        green.setOnClickListener {
            val greenTag = green.tag.toString()
            drawingView.setColor(greenTag)
        }
        blue.setOnClickListener {
            val blueTag = blue.tag.toString()
            drawingView.setColor(blueTag)
        }
        brown.setOnClickListener {
            val brownTag = brown.tag.toString()
            drawingView.setColor(brownTag)
        }
        yellow.setOnClickListener {
            val yellowTag = yellow.tag.toString()
            drawingView.setColor(yellowTag)
        }
        orange.setOnClickListener {
            val orangeTag = orange.tag.toString()
            drawingView.setColor(orangeTag)
        }

        clrscr.setOnClickListener {
           drawingView.clearScreen()
        }

        galleryButton.setOnClickListener {
            requestPermission()
        }
    }

    fun checkStoragePermission(): Boolean{
       return ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }


    fun checkCameraPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(){
        var permissionRequest = mutableListOf<String>()
        if (!checkStoragePermission()){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!checkCameraPermission()){
            permissionRequest.add(Manifest.permission.CAMERA)
        }

        if (permissionRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(this, permissionRequest.toTypedArray(),0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==0 && grantResults.isNotEmpty()){
            for (i in grantResults.indices){
                if (grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "${permissions[i]} granted", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun brushSizeDialog() {
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.brush_size_dialog)
        brushDialog.setTitle("Select Brush Size: ")
        val smallButton = brushDialog.small
        val mediumButton = brushDialog.medium
        val largeButton = brushDialog.large
        smallButton.setOnClickListener {
            drawingView.setBrushSize(10f)
            brushDialog.dismiss()
        }
        mediumButton.setOnClickListener {
            drawingView.setBrushSize(20f)
            brushDialog.dismiss()
        }
        largeButton.setOnClickListener {
            drawingView.setBrushSize(30f)
            brushDialog.dismiss()
        }
        brushDialog.show()

    }


}




