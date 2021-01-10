package com.alexandra.musicapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alexandra.musicapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavController()
        setupInformationShare()
    }

    private fun setupNavController() {
        navController = findNavController(R.id.fragment_navigation_host)
        val navBottomFragments = setOf(
            R.id.fragment_music_catalog, R.id.fragment_favorite_music
        )
        val appBarConfiguration = AppBarConfiguration(navBottomFragments)

        bottom_navigation.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupInformationShare() {
        when (intent?.action) {
            Intent.ACTION_SEND ->  handleSendText(intent)
        }
    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            Toast.makeText(this, "Someone love -> $it", Toast.LENGTH_LONG).show()
        }
    }

}