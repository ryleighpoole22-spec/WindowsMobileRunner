package com.example.windowsmobilerunner

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*

class MainActivity : Activity() {
    private lateinit var status: TextView
    private lateinit var editor: EditText
    private val pickExe = 41
    private val pickCode = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 28, 24, 24)
            setBackgroundColor(0xFF101827.toInt())
        }
        root.addView(TextView(this).apply {
            text = "WINDOWS MOBILE RUNNER"
            textSize = 22f; setTextColor(0xFFFFFFFF.toInt())
        })
        root.addView(TextView(this).apply {
            text = "A Pixel-friendly hub for Windows apps and programming"
            textSize = 14f; setTextColor(0xFFB8C4D6.toInt())
            setPadding(0, 8, 0, 20)
        })
        status = TextView(this).apply {
            text = "Ready. Install a compatible Windows compatibility layer to run .exe files."
            setTextColor(0xFFFFD166.toInt()); textSize = 13f
            setPadding(0, 0, 0, 16)
        }
        root.addView(status)
        button(root, "OPEN WINDOWS APP (.EXE)") { chooseFile(pickExe) }
        button(root, "LAUNCH WINLATOR") { launchWinlator() }
        root.addView(TextView(this).apply {
            text = "CODE STUDIO"; textSize = 18f; setTextColor(0xFFFFFFFF.toInt())
            setPadding(0, 24, 0, 8)
        })
        editor = EditText(this).apply {
            hint = "Write code here...\nExample: print('Hello, Pixel 9!')"
            setTextColor(0xFFFFFFFF.toInt()); setHintTextColor(0xFF8996AA.toInt())
            setBackgroundColor(0xFF1D293B.toInt()); minLines = 7; gravity = Gravity.TOP
            setPadding(14, 14, 14, 14)
        }
        root.addView(editor, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f))
        button(root, "INSERT PYTHON TEMPLATE") {
            editor.setText("print(\\"Hello from Pixel 9!\\")\\n")
            status.text = "Python starter code inserted. A Python runtime is not bundled in this prototype."
        }
        button(root, "INSERT C TEMPLATE") {
            editor.setText("#include <stdio.h>\\nint main(void) {\\n    printf(\\"Hello from Pixel 9!\\\\n\\");\\n    return 0;\\n}\\n")
            status.text = "C starter code inserted. A compiler is not bundled in this prototype."
        }
        button(root, "SAVE SOURCE FILE") { chooseFile(pickCode) }
        root.addView(TextView(this).apply {
            text = "Prototype only: this editor saves source text. Compilers, Wine/Box64 integration, x86/x64 translation and a managed Windows container are not included yet."
            textSize = 12f; setTextColor(0xFFB8C4D6.toInt()); setPadding(0, 14, 0, 0)
        })
        setContentView(root)
    }

    private fun button(root: LinearLayout, label: String, action: () -> Unit) {
        root.addView(Button(this).apply {
            text = label
            setOnClickListener { action() }
        }, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { bottomMargin = 8 })
    }

    private fun chooseFile(request: Int) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = if (request == pickExe) "*/*" else "text/*"
        }
        startActivityForResult(intent, request)
    }

    private fun launchWinlator() {
        val launch = packageManager.getLaunchIntentForPackage("com.winlator")
        if (launch != null) startActivity(launch)
        else {
            status.text = "Winlator isn't installed. Install it from its official project source, then return here."
            val web = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/brunodev85/Winlator"))
            startActivity(web)
        }
    }

    @Deprecated("Legacy result callback kept for broad Android compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data?.data == null) return
        val uri = data.data!!
        if (requestCode == pickExe) {
            status.text = "Selected: ${displayName(uri)}. Android cannot execute this file directly; open Winlator and import it there."
            launchWinlator()
        } else if (requestCode == pickCode) {
            try {
                contentResolver.openOutputStream(uri, "wt")?.bufferedWriter()?.use { it.write(editor.text.toString()) }
                status.text = "Source file saved."
            } catch (e: Exception) {
                status.text = "Couldn't save file: ${e.message}"
            }
        }
    }

    private fun displayName(uri: Uri): String {
        var name = uri.lastPathSegment ?: "Windows program"
        contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use {
            if (it.moveToFirst()) name = it.getString(0) ?: name
        }
        return name
    }
}
