package com.example.finalyearproject.util

import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import androidx.fragment.app.Fragment
import com.example.finalyearproject.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Blob
import java.io.ByteArrayOutputStream

// Usage: Show an error dialog from fragment
fun Fragment.errorDialog(text: String) {
    AlertDialog.Builder(context)
        .setIcon(R.drawable.ic_error)
        .setTitle("Error")
        .setMessage(text)
        .setPositiveButton("Dismiss", null)
        .show()
}

// Usage: Show an information dialog from fragment
fun Fragment.informationDialog(text: String) {
    AlertDialog.Builder(context)
        .setIcon(R.drawable.ic_info)
        .setTitle("Information")
        .setMessage(text)
        .setPositiveButton("Dismiss", null)
        .show()
}

fun Fragment.snackbar(text: String) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.hideKeyboard() {
    val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(requireView().windowToken, 0)
}

fun Bitmap.crop(width: Int, height: Int): Bitmap {
    // Source width, height and ratio
    val sw = this.width
    val sh = this.height
    val sratio = 1.0 * sw / sh

    val x: Int
    val y: Int
    val w: Int
    val h: Int
    val ratio = 1.0 * width / height

    if (ratio >= sratio) {
        w = sw
        h = (sw / ratio).toInt()
        x = 0
        y = (sh - h) / 2
    }
    else {
        w = (sh * ratio).toInt()
        h = sh
        x = (sw - w) / 2
        y = 0
    }

    return Bitmap
        .createBitmap(this, x, y, w, h) // Crop
        .scale(width, height) // Resize
}

@Suppress("DEPRECATION")
fun Bitmap.toBlob(): Blob {
    ByteArrayOutputStream().use {
        this.compress(Bitmap.CompressFormat.WEBP, 80, it)
        return Blob.fromBytes(it.toByteArray())
    }
}

fun Blob.toBitmap(): Bitmap? {
    val bytes = this.toBytes()
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

fun ImageView.cropToBlob(width: Int, height: Int): Blob {
    if (this.drawable == null)
        return Blob.fromBytes(ByteArray(0))
    else
        return this.drawable.toBitmap().crop(width, height).toBlob()
}