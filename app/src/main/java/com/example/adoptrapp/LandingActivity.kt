package com.example.adoptrapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LandingActivity : AppCompatActivity(){

    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var toolbar: Toolbar? = null
    private var bottomNavigationView: BottomNavigationView? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_activity)

        // Setting up the sidebar
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.my_toolBar)
        bottomNavigationView = findViewById(R.id.navBottom_menu)

        // setting the initial fragment


        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_toolbar)

        bottomNavigationView?.selectedItemId = R.id.navBottom_home
        navigationView?.setNavigationItemSelectedListener {
            // need to load a different toolbar menu based on user log in status
            if (Firebase.auth.currentUser != null) {
                // User is signed in
                navigationView?.menu?.findItem(R.id.nav_login)?.isVisible = false       // hide login
                navigationView?.menu?.findItem(R.id.nav_register)?.isVisible = false    // hide register
                navigationView?.menu?.findItem(R.id.nav_signout)?.isVisible = true      // display signout
            }
            else {
                // No user signed in
                navigationView?.menu?.findItem(R.id.nav_login)?.isVisible = true        // display login
                navigationView?.menu?.findItem(R.id.nav_register)?.isVisible = true     // display register
                navigationView?.menu?.findItem(R.id.nav_signout)?.isVisible = false     // hide signout
            }

            when (it.itemId) {
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
                    Toast.makeText(this.baseContext, "Create Listing Selected", Toast.LENGTH_SHORT)
                        .show()
                    drawerLayout?.closeDrawers()
                    true
                }
                R.id.nav_help -> {
                    // onclick event
                    it.isChecked = true
                    // the toast is for testing purposes only
                    Toast.makeText(this.baseContext, "Help Selected", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this, "Contact Selected", Toast.LENGTH_SHORT).show()
                    drawerLayout?.closeDrawers()
                    true
                }
                R.id.nav_signout -> {
                    // onclick event
                    it.isChecked = true
                    // the toast is for testing purposes only
                    userLogout()
                    drawerLayout?.closeDrawers()
                    true
                }

                else -> false
            }
        }

        bottomNavigationView?.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                // Some activities are commented cause they are not implemented yet
                R.id.navBottom_article -> {

                    val articleFragment = FragmentArticle.newInstance()
                    openFragment(articleFragment)
                    true
                }
                R.id.navBottom_search -> {

                    val searchFragment = FragmentSearch.newInstance()
                    openFragment(searchFragment)
                    true
                }
                R.id.navBottom_home -> {

                    val homeFragment = FragmentHome.newInstance()
                    openFragment(homeFragment)
                    true
                }
                R.id.navBottom_mail -> {

                    val inboxFragment = FragmentInbox.newInstance()
                    openFragment(inboxFragment)
                    true
                }
                R.id.navBottom_profile -> {

                    val homeFragment = FragmentProfile.newInstance()
                    openFragment(homeFragment)
                    true
                    }
                else -> false    // else case this should not occur
            }
        }

        openFragment(FragmentHome.newInstance())
        // END OF onCreate
    }


    // sign the user out and sends them to the home screen to reload the drawer
    private fun userLogout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LandingActivity::class.java)
        finish()
        startActivity(intent)
    }

    // Makes it so the button to open the side menu works
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> drawerLayout?.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}