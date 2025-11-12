package org.example.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.example.app.R
import androidx.navigation.fragment.NavHostFragment

/**
 PUBLIC_INTERFACE
 MainActivity hosts the NavHostFragment for TV navigation.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Ensure NavHost is resolved; no additional setup required for simple graph
        val host = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        host.navController // initialize
    }
}
