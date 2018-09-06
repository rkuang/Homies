package com.rickykuang.homies.activities.main

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.rickykuang.homies.Homies
import com.rickykuang.homies.activities.main.adapters.MainPagerAdapter
import com.rickykuang.homies.R
import com.rickykuang.homies.activities.SignInActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var mPagerAdapter: MainPagerAdapter
    private lateinit var mViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Homies.mAuth = FirebaseAuth.getInstance()

        mPagerAdapter = MainPagerAdapter(supportFragmentManager)

        mViewPager = findViewById(R.id.pager)
        mViewPager.adapter = mPagerAdapter
        mViewPager.offscreenPageLimit = 2
        mViewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) { }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }
            override fun onPageSelected(position: Int) {
                Timber.d("page changed: $position")
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(currentFocus.windowToken,0)
            }
        })

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(mViewPager, true)

        val page_icons: IntArray = intArrayOf(
                R.drawable.baseline_chat_24,
                R.drawable.baseline_calendar_today_24,
                R.drawable.baseline_shopping_cart_24
        )
        for (i in 0..tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.setIcon(page_icons[i])
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.my_menu, menu)
        return true
    }

    override fun onStart() {
        super.onStart()

        if (Homies.mAuth.currentUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.signout -> {
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener{
                            startActivity(Intent(this, SignInActivity::class.java))
                            finish()
                        }
                return true
            }
            else -> return true
        }
    }
}
