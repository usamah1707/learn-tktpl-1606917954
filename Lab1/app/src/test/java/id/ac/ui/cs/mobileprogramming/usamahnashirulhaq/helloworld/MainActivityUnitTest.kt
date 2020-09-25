package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivityUnitTest {

    private lateinit var mainActivityClass : MainActivity

    @Before
    fun setUp() {
        mainActivityClass = MainActivity()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test_getTime() {
        val FORMAT: SimpleDateFormat = SimpleDateFormat("EEEE, MMMM d, yyyy 'at' ")

        var date = FORMAT.format(Calendar.getInstance().getTime()).toString()

        assertEquals(date, mainActivityClass.getTime())
    }

    @Test
    fun test_getTime_not_null() {
        assertNotNull(mainActivityClass.getTime())
    }
}