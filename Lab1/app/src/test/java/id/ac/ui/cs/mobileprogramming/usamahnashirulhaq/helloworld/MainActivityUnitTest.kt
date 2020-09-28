package id.ac.ui.cs.mobileprogramming.usamahnashirulhaq.helloworld

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivityUnitTest {

    private lateinit var mainActivityClass: MainActivity

    @Before
    fun setUp() {
        mainActivityClass = MainActivity()
    }

    @Test
    fun test_getTime() {
        val FORMAT: SimpleDateFormat = SimpleDateFormat("'Its' HH:mm:ss, EEEE, d MMM yyyy")

        var date = FORMAT.format(Calendar.getInstance().getTime()).toString()

        assertEquals(date, mainActivityClass.getTime())
    }

    @Test
    fun test_getTime_not_null() {
        assertNotNull(mainActivityClass.getTime())
    }
}