package soup.movie.ui.main

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.main_activity.*
import soup.movie.MainDirections
import soup.movie.R
import soup.movie.analytics.EventAnalytics
import soup.movie.spec.KakaoLink
import soup.movie.ui.base.BaseActivity
import soup.movie.ui.base.OnBackPressedListener
import soup.movie.ui.home.MovieSelectManager
import soup.movie.util.consume
import soup.movie.util.doOnApplyWindowInsets
import soup.movie.util.observeEvent
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModel()

    @Inject
    lateinit var analytics: EventAnalytics

    private val listener = NavController.OnDestinationChangedListener {
        _, destination, _ ->
        navigationView.setCheckedItem(destination.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Main)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        //TODO: Improve this please
        FirebaseMessaging.getInstance().isAutoInitEnabled = true

        intent?.handleDeepLink()

        viewModel.uiEvent.observeEvent(this) {
            execute(it)
        }

        navigationView.setNavigationItemSelectedListener {
            consume {
                drawerLayout.closeDrawer(GravityCompat.START)
                val navController = navHostFragment.findNavController()
                when (it.itemId) {
                    R.id.home -> navController.popBackStack(R.id.home, false)
                    else -> NavigationUI.onNavDestinationSelected(it, navController)
                }
            }
        }
        adaptSystemWindowInsets()
    }

    private fun adaptSystemWindowInsets() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        drawerLayout.doOnApplyWindowInsets { view, windowInsets, initialPadding ->
            navigationView.updatePadding(
                top = initialPadding.top + windowInsets.systemWindowInsetTop
            )
        }
    }

    override fun onResume() {
        super.onResume()
        navHostFragment.findNavController().addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navHostFragment.findNavController().removeOnDestinationChangedListener(listener)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.handleDeepLink()
    }

    private fun Intent.handleDeepLink() {
        when (action) {
            ACTION_VIEW -> {
                KakaoLink.extractMovieId(this)?.let {
                    viewModel.requestMovie(it)
                }
            }
        }
    }

    private fun execute(action: MainUiEvent) {
        when (action) {
            is OpenDrawerMenuUiEvent -> {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            is ShowDetailUiEvent -> {
                MovieSelectManager.select(action.movie)
                navHostFragment.findNavController().navigate(
                    MainDirections.actionToDetail()
                )
            }
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
            return
        }
        if (handleBackEventInChildFragment()) return
        super.onBackPressed()
    }

    private fun handleBackEventInChildFragment(): Boolean {
        val current = navHostFragment.childFragmentManager.fragments.elementAtOrNull(0)
        if (current is OnBackPressedListener) {
            return current.onBackPressed()
        }
        return false
    }
}
