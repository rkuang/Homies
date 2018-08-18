package rickykuang.com.homies

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MyPagerAdapter(fm : FragmentManager) :  FragmentPagerAdapter(fm) {

    override fun getCount(): Int  = 3

    override fun getItem(position: Int): Fragment {
        val fragment = MyPageFragment()
        fragment.arguments = Bundle().apply {
            putInt(PAGE, position+1)
        }
        return fragment
    }

}
