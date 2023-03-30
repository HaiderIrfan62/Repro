package com.example.repro

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.repro.databinding.ActivityScanBinding
import com.example.repro.ml.LiteModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer


class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2
    lateinit var bitmap: Bitmap
    //var btnGallery = findViewById<Button>(R.id.btnGallery)
    //var imageView = findViewById<ImageView>(R.id.imageView)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_scan)
        supportActionBar?.hide()
        setContentView(binding.root)

        // handling permissions
        //checkandGetpermissions()

        val bundle = intent.extras
        val name = bundle!!.getString("name")
        val scannedTokens = bundle!!.getInt("scanned")
        val earnedTokens = bundle!!.getInt("earned")
        val plastic = bundle!!.getInt("plastic")
        val aluminum = bundle!!.getInt("aluminum")
        val cardboard = bundle!!.getInt("cardboard")

        binding.backScanBtn.setOnClickListener {
            val it = Intent(this, HomeActivity::class.java).also{
                it.putExtra("name", name)
                it.putExtra("scanned", scannedTokens)
                it.putExtra("earned", earnedTokens)
                it.putExtra("plastic", plastic)
                it.putExtra("aluminum", aluminum)
                it.putExtra("cardboard", cardboard)
                startActivity(it)
            }
        }

        val labels = application.assets.open("labels.txt").bufferedReader().use { it.readText() }.split("\n")

        binding.btnGallery.setOnClickListener(View.OnClickListener {
            var intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 250)
        })

        binding.scanScanBtn.setOnClickListener(View.OnClickListener {
            var tensorImage: TensorImage = TensorImage(DataType.FLOAT32)
            var resized = Bitmap.createScaledBitmap(bitmap, 256, 256, true)
            tensorImage.load(resized)
            val byteBuffer = tensorImage.buffer
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)

            //var resized = Bitmap.createScaledBitmap(bitmap, 256, 256, true)
            val model = LiteModel.newInstance(this)

            //var tbuffer = TensorImage.fromBitmap(resized)
            //var byteBuffer = tbuffer.buffer

            // Creates inputs for reference.
            //val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            var max = getMax(outputFeature0.floatArray)

            Toast.makeText(this, labels[max].toString(), Toast.LENGTH_SHORT).show()

            if(labels[max] == "Glass"){
                Log.e("Object", "_" + labels[max].toString() + "_")
            }

            val intent = Intent(this, ConfirmActivity::class.java).also{
                it.putExtra("index", max)
                it.putExtra("name", name)
                it.putExtra("object", labels[max].toString())
                it.putExtra("scanned", scannedTokens)
                it.putExtra("earned", earnedTokens)
                it.putExtra("plastic", plastic)
                it.putExtra("aluminum", aluminum)
                it.putExtra("cardboard", cardboard)
                startActivity(it)
            }

            // Releases model resources if no longer used.
            model.close()
        })

        binding.btnCamera.setOnClickListener(View.OnClickListener {
            var camera : Intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera, 200)
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 250){
            binding.imageView.setImageURI(data?.data)

            var uri : Uri ?= data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        }
        else if(requestCode == 200 && resultCode == Activity.RESULT_OK){
            bitmap = data?.extras?.get("data") as Bitmap
            binding.imageView.setImageBitmap(bitmap)
        }

    }

    fun getMax(arr:FloatArray) : Int{
        var ind = 0;
        var min = 0.0f;

        for(i in 0..8)
        {
            //Log.d(i.toString(), arr[i].toString())
            if(arr[i] > min)
            {
                min = arr[i]
                ind = i;
            }
        }
        return ind
    }
}

//        setContentView(binding.root)
//
//        var labels = application.assets.open("labels.txt").bufferedReader().readLines()
//
//        var imageProcessor = ImageProcessor.Builder()
//            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
//            .build()
//
//        binding.scanScanBtn.setOnClickListener {
//            val bitmap = (binding.imageView.getDrawable() as BitmapDrawable).bitmap
//
//            var tensorImage = TensorImage(DataType.UINT8)
//            tensorImage.load(bitmap)
//
//            tensorImage = imageProcessor.process(tensorImage)
//
//            val model = LiteModel.newInstance(this)
//
//            // Creates inputs for reference.
//            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 256, 256, 3), DataType.FLOAT32)
//            inputFeature0.loadBuffer(tensorImage.buffer)
//
//            // Runs model inference and gets result.
//            val outputs = model.process(inputFeature0)
//            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray
//
//            var maxIdx = 0
//            outputFeature0.forEachIndexed{index, fl ->
//                if(outputFeature0[maxIdx] < fl){
//                    maxIdx = index
//                }
//            }
//
//            Toast.makeText(this, labels[maxIdx].toString(), Toast.LENGTH_SHORT).show()
//
//            // Releases model resources if no longer used.
//            model.close()
//        }
//
//        binding.backScanBtn.setOnClickListener {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.btnCamera.setOnClickListener {
//            cameraCheckPermission()
//        }
//
//        binding.btnGallery.setOnClickListener {
//            galleryCheckPermission()
//        }
//
//        //when you click on the image
//        binding.imageView.setOnClickListener {
//            val pictureDialog = AlertDialog.Builder(this)
//            pictureDialog.setTitle("Select Action")
//            val pictureDialogItem = arrayOf("Select photo from Gallery",
//                "Capture photo from Camera")
//            pictureDialog.setItems(pictureDialogItem) { dialog, which ->
//
//                when (which) {
//                    0 -> gallery()
//                    1 -> camera()
//                }
//            }
//
//            pictureDialog.show()
//        }
//
//    }
//
//    private fun galleryCheckPermission() {
//
//        Dexter.withContext(this).withPermission(
//            android.Manifest.permission.READ_EXTERNAL_STORAGE
//        ).withListener(object : PermissionListener {
//            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
//                gallery()
//            }
//
//            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
//                Toast.makeText(
//                    this@ScanActivity,
//                    "You have denied the storage permission to select image",
//                    Toast.LENGTH_SHORT
//                ).show()
//                showRotationalDialogForPermission()
//            }
//
//            override fun onPermissionRationaleShouldBeShown(
//                p0: PermissionRequest?, p1: PermissionToken?) {
//                showRotationalDialogForPermission()
//            }
//        }).onSameThread().check()
//    }
//
//    private fun gallery() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, GALLERY_REQUEST_CODE)
//    }
//
//
//    private fun cameraCheckPermission() {
//
//        Dexter.withContext(this)
//            .withPermissions(
//                android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                android.Manifest.permission.CAMERA).withListener(
//
//                object : MultiplePermissionsListener {
//                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
//                        report?.let {
//
//                            if (report.areAllPermissionsGranted()) {
//                                camera()
//                            }
//
//                        }
//                    }
//
//                    override fun onPermissionRationaleShouldBeShown(
//                        p0: MutableList<PermissionRequest>?,
//                        p1: PermissionToken?) {
//                        showRotationalDialogForPermission()
//                    }
//
//                }
//            ).onSameThread().check()
//    }
//
//
//    private fun camera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, CAMERA_REQUEST_CODE)
//    }
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == Activity.RESULT_OK) {
//
//            when (requestCode) {
//
//                CAMERA_REQUEST_CODE -> {
//
//                    val bitmap = data?.extras?.get("data") as Bitmap
//
//                    //we are using coroutine image loader (coil)
//                    binding.imageView.load(bitmap) {
//                        crossfade(true)
//                        crossfade(1000)
//                    }
//                }
//
//                GALLERY_REQUEST_CODE -> {
//
//                    binding.imageView.load(data?.data) {
//                        crossfade(true)
//                        crossfade(1000)
//                    }
//
//                }
//            }
//
//        }
//
//    }
//
//
//    private fun showRotationalDialogForPermission() {
//        AlertDialog.Builder(this)
//            .setMessage("It looks like you have turned off permissions"
//                    + "required for this feature. It can be enable under App settings!!!")
//
//            .setPositiveButton("Go TO SETTINGS") { _, _ ->
//
//                try {
//                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                    val uri = Uri.fromParts("package", packageName, null)
//                    intent.data = uri
//                    startActivity(intent)
//
//                } catch (e: ActivityNotFoundException) {
//                    e.printStackTrace()
//                }
//            }
//
//            .setNegativeButton("CANCEL") { dialog, _ ->
//                dialog.dismiss()
//            }.show()
//    }
//
//
//}