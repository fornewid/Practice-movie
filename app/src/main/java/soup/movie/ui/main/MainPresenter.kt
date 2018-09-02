package soup.movie.ui.main

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.disposables.DisposableContainer
import soup.movie.settings.ui.MainTabSetting
import soup.movie.ui.BasePresenter
import soup.movie.ui.main.MainViewState.*
import soup.movie.settings.ui.MainTabSetting.Tab
import soup.movie.settings.ui.MainTabSetting.Tab.*

class MainPresenter(private val mainTabSetting: MainTabSetting) :
        BasePresenter<MainContract.View>(),
        MainContract.Presenter {

    override fun initObservable(disposable: DisposableContainer) {
        super.initObservable(disposable)
        disposable.add(mainTabSetting
                .asObservable()
                .distinctUntilChanged()
                .map { mapToViewState(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view?.render(it) })
    }

    private fun mapToViewState(tabMode: Tab): MainViewState =
            when (tabMode) {
                Now -> NowState
                Plan -> PlanState
                Settings -> SettingsState
            }

    override fun setCurrentTab(mode: Tab) {
        mainTabSetting.set(mode)
    }
}