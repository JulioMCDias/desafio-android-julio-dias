package com.jlmcdeveloper.desafio_android_julio_dias.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.jlmcdeveloper.desafio_android_julio_dias.R
import com.jlmcdeveloper.desafio_android_julio_dias.data.api.ApiEndPoint
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)



    @Test
    fun mainActivityTest() {
        Thread.sleep(700)
        val viewGroup = Espresso.onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.cardView_repository),
                        childAtPosition(
                            withId(R.id.recyclerView),
                            0
                        )
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        viewGroup.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        val recyclerView = Espresso.onView(
            allOf(
                withId(R.id.recyclerView),
                childAtPosition(
                    ViewMatchers.withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    @Test
    fun qtItemList() {
        Thread.sleep(700)
        assert(getRVcount() == ApiEndPoint.perPage)
    }

    private fun getRVcount(): Int {
        val recyclerView =
            mActivityTestRule.activity.findViewById(R.id.recyclerView) as RecyclerView
        return recyclerView.adapter!!.itemCount
    }
}
