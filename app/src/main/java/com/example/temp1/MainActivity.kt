package com.example.temp1

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewConfiguration
import android.widget.TextView
import android.R.attr.start
import android.animation.ObjectAnimator
import android.app.PendingIntent.getActivity
import android.util.Log
import android.widget.ScrollView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.mood_diary -> {
                val intent = Intent(this, Diary::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.yoga -> {
                val intent = Intent(this, yoga::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.art -> {
                val intent = Intent(this, Art::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navView.selectedItemId = R.id.navigation_home

        val fab: View = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intent = Intent(this, AiBot::class.java)
            startActivity(intent)
        }
//        val animator = ObjectAnimator.ofInt(mainScrollView, "scrollY", mainScrollView.bottom)
//        animator.duration = 1000;
//        animator.start()
        shouldSmoothScrollBy()
//        Log.e("Terminator_MainActivity", "${mainScrollView.scrollX} ${mainScrollView.scrollY}")
    }
    @Throws(Exception::class)
    fun shouldSmoothScrollBy() {
        val scrollView = ScrollView(mainScrollView.context)
        scrollView.smoothScrollTo(7, 6)
        scrollView.smoothScrollBy(10, 20)

//        assertEquals(17, scrollView.scrollX)
//        assertEquals(26, scrollView.scrollY)
    }
    override fun onStart() {
        super.onStart()
        hide()
    }
    protected fun hide() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity();
        System.exit(0)

    }


}
