package rickykuang.com.homies

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mPagerAdapter: MyPagerAdapter
    private lateinit var mViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        mPagerAdapter = MyPagerAdapter(supportFragmentManager)
        mViewPager = findViewById(R.id.pager)
        mViewPager.adapter = mPagerAdapter
        var tabLayout = findViewById<TabLayout>(R.id.tabLayout)
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

        var currentUser: FirebaseUser? = mAuth.currentUser
        Log.d(TAG, "Logged in as: ${currentUser?.displayName}")

        if (currentUser == null) {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.signout -> {
                AuthUI.getInstance()
                        .signOut(this).addOnCompleteListener( OnCompleteListener {
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        })
                return true
            }
            else -> return true
        }
    }
}
