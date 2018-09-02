package soup.movie.ui.theater.edit

import android.view.View
import kotlinx.android.synthetic.main.activity_theater_edit.*
import soup.movie.R
import soup.movie.ui.BaseActivity
import timber.log.Timber
import javax.inject.Inject

class TheaterEditActivity :
        BaseActivity<TheaterEditContract.View, TheaterEditContract.Presenter>(),
        TheaterEditContract.View {

    @Inject
    override lateinit var presenter: TheaterEditContract.Presenter

    private lateinit var listAdapter: TheaterEditListAdapter

    override val layoutRes: Int
        get() = R.layout.activity_theater_edit

    override fun render(viewState: TheaterEditViewState) {
        Timber.d("render: %s", viewState)
        listAdapter = TheaterEditListAdapter(
                viewState.allTheaters,
                viewState.selectedTheaters)
        listView.adapter = listAdapter
    }

    fun onCancelClicked(view: View) {
        finish()
    }

    fun onConfirmClicked(view: View) {
        presenter.onConfirmClicked(listAdapter.selectedTheaters)
        finish()
    }
}