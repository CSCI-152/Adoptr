package com.example.adoptrapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class LandingActivity : AppCompatActivity() {

    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var toolBar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_activity)

        // Setting up the sidebar
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        toolBar = findViewById(R.id.my_toolBar)

        setSupportActionBar(toolBar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_toolbar)

        navigationView?.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_login -> {
                    // onclick event
                    it.isChecked = true
                    // the toast is for testing purposes only
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    drawerLayout?.closeDrawers()
                    true
                }
                R.id.nav_register -> {
                    // onclick event
                    it.isChecked = true
                    // the toast is for testing purposes only
                    val intent = Intent(this, RegistrationActivity::class.java)
                    startActivity(intent)
                    drawerLayout?.closeDrawers()
                    true
                }
                R.id.nav_createListing -> {
                    // onclick event
                    it.isChecked = true
                    // the toast is for testing purposes only
                    Toast.makeText(this.baseContext,"Create Listing Selected",Toast.LENGTH_SHORT).show()
                    drawerLayout?.closeDrawers()
                    true
                }
                R.id.nav_help -> {
                    // onclick event
                    it.isChecked = true
                    // the toast is for testing purposes only
                    Toast.makeText(this.baseContext,"Help Selected", Toast.LENGTH_SHORT).show()
                    drawerLayout?.closeDrawers()
                    true
                }
                R.id.nav_support_ticket -> {
                    // onclick event
                    it.isChecked = true
                    // the toast is for testing purposes only
                    val intent = Intent(this, SupportTicketActivity::class.java)
                    startActivity(intent)
                    drawerLayout?.closeDrawers()
                    true
                }
                R.id.nav_contact -> {
                    // onclick event
                    it.isChecked = true
                    // the toast is for testing purposes only
                    val intent = Intent(this, ContactDevs::class.java)
                    startActivity(intent)
                    drawerLayout?.closeDrawers()
                    true
                }
                R.id.nav_signout -> {
                    // onclick event
                    it.isChecked = true
                    // the toast is for testing purposes only
                    Toast.makeText(this, "Sign Out Selected", Toast.LENGTH_SHORT).show()
                    drawerLayout?.closeDrawers()
                    true
                }
                else -> false
            }

        }
    }

    // Makes it so the button to open the side menu works
    @SuppressLint("WrongConstant")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> drawerLayout?.openDrawer(Gravity.START)
        }
        return super.onOptionsItemSelected(item)
    }

}