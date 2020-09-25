package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import android.view.View
import androidx.test.rule.ActivityTestRule

import id.ac.ui.mobileprogramming.usamahnashirulhaq.helloworld.R
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class MainActivityAndroidTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var mActivity: MainActivity

    @Before
    fun setUp() {
        mActivity = activityTestRule.activity
    }

    @Test
    fun test_onCreate() {
        mActivity
        var view = mActivity.findViewById<View>(R.id.activity_main)

        assertNotNull(view)
    }

    @After
    fun teardown(){
    }
}