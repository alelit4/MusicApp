package com.alexandra.musicapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.alexandra.musicapp.ui.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class FavoritesFragmentShould {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp(){
        val favoritesBottomNavigationItem = onView(
            withId(R.id.fragment_favorite_music)
        )
        favoritesBottomNavigationItem.perform(click())
    }

    @Test
    fun load_title_in_fragment_favorites() {

        val textView = onView(withId(R.id.favorite_music_title))
        textView.check(matches(withText("No favorite music")))
    }
}
