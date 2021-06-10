package com.app.jetpacksubmissiontwo

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.jetpacksubmissiontwo.data.utils.EspressoIdlingResource
import com.app.jetpacksubmissiontwo.ui.movie.MovieAdapter
import com.app.jetpacksubmissiontwo.ui.tv.TvAdapter
import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsEqual
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val navController = TestNavHostController(
        ApplicationProvider.getApplicationContext()
    )

    @get:Rule
    var activity: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }


    @Test
    fun mainTest() {
//create TestNavigationHostController
        activity.scenario.onActivity { act ->
            navController.setGraph(R.navigation.bottom_navigation)
            Navigation.setViewNavController(act.findViewById(R.id.fragment_host), navController)
        }
        Espresso.onView(withId(R.id.id_bottom_naview)).check(matches(isDisplayed()))
//check navigation
        Espresso.onView(withId(R.id.id_bottom_naview)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.id_menu_movie)).perform(ViewActions.click())
        Assert.assertThat(navController.currentDestination?.id, IsEqual(R.id.id_menu_movie))
        activity.scenario.onActivity { navController.setCurrentDestination(R.id.id_menu_tv) }
        Espresso.onView(withId(R.id.id_menu_tv)).perform(ViewActions.click())
        Assert.assertThat(navController.currentDestination?.id, IsEqual(R.id.id_menu_tv))
//cek recycler view Movie
        Espresso.pressBack()
        Espresso.onView(withId(R.id.id_recview_movie)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.id_recview_movie)).perform(
            RecyclerViewActions.scrollToPosition<MovieAdapter.MovieHolder>(
                (10)
            )
        )
//select movie item
        Espresso.onView(withId(R.id.id_recview_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MovieAdapter.MovieHolder>(
                0,
                ViewActions.click()
            )
        )
//cek movie detail
        Espresso.onView(withId(R.id.id_detail_img)).check(matches(isDisplayed()))
            .check(matches(CoreMatchers.not(ViewMatchers.withText(""))))
        Espresso.onView(withId(R.id.id_detail_collap)).check(matches(isDisplayed()))
            .check(matches(CoreMatchers.not(ViewMatchers.withText(""))))
        Espresso.onView(withId(R.id.id_detail_rating)).check(matches(isDisplayed()))
            .check(matches(CoreMatchers.not(ViewMatchers.withText(""))))
        Espresso.onView(withId(R.id.id_detail_description)).check(matches(isDisplayed()))
            .check(matches(CoreMatchers.not(ViewMatchers.withText(""))))

//cek recycler view Tv show
        Espresso.pressBack()
        Espresso.onView(withId(R.id.id_menu_tv)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.id_recview_tv)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.id_recview_tv)).perform(
            RecyclerViewActions.scrollToPosition<TvAdapter.TvHolder>(
                (10)
            )
        )
//select tv item
        Espresso.onView(withId(R.id.id_recview_tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<TvAdapter.TvHolder>(
                0,
                ViewActions.click()
            )
        )
//cek tv detail
        Espresso.onView(withId(R.id.id_detail_img)).check(matches(isDisplayed()))
            .check(matches(CoreMatchers.not(ViewMatchers.withText(""))))
        Espresso.onView(withId(R.id.id_detail_collap)).check(matches(isDisplayed()))
            .check(matches(CoreMatchers.not(ViewMatchers.withText(""))))
        Espresso.onView(withId(R.id.id_detail_rating)).check(matches(isDisplayed()))
            .check(matches(CoreMatchers.not(ViewMatchers.withText(""))))
        Espresso.onView(withId(R.id.id_detail_description)).check(matches(isDisplayed()))
            .check(matches(CoreMatchers.not(ViewMatchers.withText(""))))
    }
}