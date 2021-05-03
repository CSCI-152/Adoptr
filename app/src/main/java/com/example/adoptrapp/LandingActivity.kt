package com.example.adoptrapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObject
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

        setupNavigationView()
        setupBottomNavigationView()
        bottomNavigationView?.selectedItemId = R.id.navBottom_home


        openFragment(FragmentHome.newInstance())
        // END OF onCreate
    }

    private fun setupNavigationView() {

        val db = FirebaseFirestore.getInstance()
        navigationView?.setNavigationItemSelectedListener {
            // need to load a different toolbar menu based on user log in status
            when (it.itemId) {
                R.id.nav_admin -> {
                    // onclick event
                    it.isChecked = true
                    // the toast is for testing purposes only
                    val intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)
                    drawerLayout?.closeDrawers()
                    true
                }
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
                    finish()
                    drawerLayout?.closeDrawers()
                    setupNavigationView()
                    true
                }
                R.id.nav_createPost -> {
                    // onclick event
                    it.isChecked = true
                    // the toast is for testing purposes only
                    val intent = Intent(this, CreatePostActivity::class.java)
                    startActivity(intent)
                    drawerLayout?.closeDrawers()
                    true
                }
                R.id.nav_createArticle -> {
                    // onclick event
                    it.isChecked = true
                    val intent = Intent(this, CreateArticleActivity::class.java)
                    startActivity(intent)
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
                    userLogout()
                    drawerLayout?.closeDrawers()
                    true
                }

                else -> false
            } // END WHEN CASE
        } // END LISTENER CASE

        // This needs to be loaded after the event listener

        if (Firebase.auth.currentUser != null) {
            // User is signed in
            navigationView?.menu?.findItem(R.id.nav_login)?.isVisible = false       // hide login
            navigationView?.menu?.findItem(R.id.nav_register)?.isVisible = false    // hide register
            navigationView?.menu?.findItem(R.id.nav_signout)?.isVisible = true      // display signout

            val uid = Firebase.auth.currentUser!!.uid
            var currentUserRole: String?
            // Access the document assigned to the current user and allows them to grab the role
            db.collection("users").document(uid).get(Source.SERVER)
                .addOnSuccessListener { document ->
                    // converts the grabbed document to ClassUser object class and takes the role field
                    currentUserRole = document.toObject<ClassUser>()!!.role
                    when (currentUserRole) {
                        "admin" -> {
                            // things only the admin should see should go into here
                            navigationView?.menu?.findItem(R.id.nav_admin)?.isVisible = true
                            // admins should see createPost/Article for debugging
                            navigationView?.menu?.findItem(R.id.nav_createPost)?.isVisible = true
                            navigationView?.menu?.findItem(R.id.nav_createArticle)?.isVisible = true
                        }
                        "center" -> {
                            // things only the center should see should go into here
                            navigationView?.menu?.findItem(R.id.nav_admin)?.isVisible = false
                            navigationView?.menu?.findItem(R.id.nav_createPost)?.isVisible = true
                            navigationView?.menu?.findItem(R.id.nav_createArticle)?.isVisible = true

                        }
                        else -> {
                            // things only the user should NOT see should go into here
                            navigationView?.menu?.findItem(R.id.nav_admin)?.isVisible = false
                            navigationView?.menu?.findItem(R.id.nav_createPost)?.isVisible = false
                            navigationView?.menu?.findItem(R.id.nav_createArticle)?.isVisible = false
                        }
                }
            } // END when CASE
        } // END IF CASE
        else {
            // No user signed in
            navigationView?.menu?.findItem(R.id.nav_login)?.isVisible = true        // display login
            navigationView?.menu?.findItem(R.id.nav_register)?.isVisible = true     // display register
            navigationView?.menu?.findItem(R.id.nav_signout)?.isVisible = false     // hide signout
            navigationView?.menu?.findItem(R.id.nav_admin)?.isVisible = false       // hide admin
            navigationView?.menu?.findItem(R.id.nav_createPost)?.isVisible = false  // hide createPost
            navigationView?.menu?.findItem(R.id.nav_createArticle)?.isVisible = false  // hide createArticle
        } // END ELSE CASE

    } // END setupNavigationView FUNCTION

    private fun setupBottomNavigationView(){
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
                /*
                R.id.navBottom_mail -> {

                    val inboxFragment = FragmentInbox.newInstance()
                    openFragment(inboxFragment)
                    true
                }
                */
                R.id.navBottom_profile -> {

                    val homeFragment = FragmentProfile.newInstance()
                    openFragment(homeFragment)
                    true
                }
                else -> false    // else case this should not occur
            } // END WHEN CASE
        } // END LISTENER CASE
    } // END setupBottomNavigationView FUNCTION

    // sign the user out and sends them to the home screen to reload the drawer
    private fun userLogout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LandingActivity::class.java)
        finish()
        startActivity(intent)
    } // END userLogout FUNCTION

    // Makes it so the button to open the side menu works
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> drawerLayout?.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    } // END onOptionsItemSelected FUNCTION

    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    } // END openFragment FUNCTION
} // END LandingActivity Class