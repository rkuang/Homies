package com.rickykuang.homies.activities.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.rickykuang.homies.Homies
import com.rickykuang.homies.activities.main.adapters.MainPagerAdapter
import com.rickykuang.homies.R
import com.rickykuang.homies.activities.SignInActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mPagerAdapter: MainPagerAdapter
    private lateinit var mViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Homies.mAuth = FirebaseAuth.getInstance()

        mPagerAdapter = MainPagerAdapter(supportFragmentManager)
        mViewPager = findViewById(R.id.pager)
        mViewPager.adapter = mPagerAdapter
        mViewPager.offscreenPageLimit = 2

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(mViewPager, true)

        val drawables: IntArray = intArrayOf(
                R.drawable.baseline_chat_24,
                R.drawable.baseline_calendar_today_24,
                R.drawable.baseline_shopping_cart_24
        )
        for (i in 0..tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.setIcon(drawables[i])
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
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.signout -> {
                AuthUI.getInstance()
                        .signOut(this).addOnCompleteListener{
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        }
                return true
            }
            else -> return true
        }
    }
}
