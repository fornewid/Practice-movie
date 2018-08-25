package soup.movie.di.module;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import soup.movie.di.scope.FragmentScoped;
import soup.movie.ui.main.now.NowContract;
import soup.movie.ui.main.now.NowFragment;
import soup.movie.ui.main.now.NowPresenter;

@Module
public abstract class NowModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract NowFragment nowFragment();

    @FragmentScoped
    @Binds
    abstract NowContract.Presenter nowPresenter(NowPresenter presenter);
}
