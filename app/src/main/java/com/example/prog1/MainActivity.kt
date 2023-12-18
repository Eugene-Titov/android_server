package com.example.prog1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.InterruptedIOException
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket
import javax.net.ServerSocketFactory

class MainActivity : AppCompatActivity() {

    lateinit var server : ServerSocket
    lateinit var client : Socket
    lateinit var reader : BufferedReader
    lateinit var writer : BufferedWriter
    var isConnected : Boolean = false
    lateinit var userData : EditText
    lateinit var button: Button
    lateinit var incomeText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val label = findViewById<TextView>(R.id.main_label)
        userData = findViewById<EditText>(R.id.user_data)
        button = findViewById<Button>(R.id.button)
        incomeText = findViewById<EditText>(R.id.incoming_text)
        try {
//            server = ServerSocket(18000)
            server = ServerSocketFactory.getDefault().createServerSocket(8000)
        } catch(s : SecurityException) {
            Toast.makeText(this, "security exception", Toast.LENGTH_LONG).show()
        } catch (e : IOException) {
            Toast.makeText(this, "error to create server", Toast.LENGTH_LONG).show()
        }

//        button.setOnClickListener {
//            if(isConnected) {
//                writer.write(userData.text.toString().trim())
//            }
//        }

        Thread {
            try {
                client = server.accept()
                reader = BufferedReader(InputStreamReader(client.getInputStream()))
                writer = BufferedWriter(OutputStreamWriter(client.getOutputStream()))
                isConnected = true
                Toast.makeText(this, "someone has connected", Toast.LENGTH_LONG).show()
            } catch (e: InterruptedIOException) {
                isConnected = false
                e.printStackTrace()
            } catch (e : IOException) {
                isConnected = false
                e.printStackTrace()
            }
        }.start()
    }
}