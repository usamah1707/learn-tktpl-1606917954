package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import id.ac.ui.mobileprogramming.usamahnashirulhaq.helloworld.R
import kotlinx.android.synthetic.main.activity_main.view.*


import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import java.text.SimpleDateFormat
import java.util.*


class MainActivityInstrumentedTest {
    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)
    private lateinit var mActivity: MainActivity

    @Before
    fun setUp() {
        mActivity = activityTestRule.activity
    }
    @Test
    fun test_mainActivity() {
        assertNotNull(mActivity)
    }
    @Test
    fun test_nameInLayout() {
        var name = mActivity.getNama()
        assertEquals("Usamah Nashirul Haq", name)
    }
    @Test
    fun test_buttonUpdateTime() {
        onView(withId(R.id.buttonCheckTime)).perform(click())
        val FORMAT: SimpleDateFormat = SimpleDateFormat("'Its' HH:mm:ss, EEEE, d MMM yyyy")

        var date = FORMAT.format(Calendar.getInstance().getTime()).toString()

        onView(withId(R.id.dateView)).check(matches(withText(date)))
    }
}
