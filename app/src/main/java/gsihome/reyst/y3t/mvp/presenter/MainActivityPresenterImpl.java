package gsihome.reyst.y3t.mvp.presenter;

import android.content.Context;

import gsihome.reyst.y3t.adapters.PagerAdapter;
import gsihome.reyst.y3t.mvp.model.MainActivityModel;
import gsihome.reyst.y3t.mvp.model.MainActivityModelImpl;
import gsihome.reyst.y3t.mvp.view.MainActivityView;

public class MainActivityPresenterImpl implements MainActivityPresenter {

    private MainActivityView mView;
    private Context mContext;

    private MainActivityModel mModel;

    public MainActivityPresenterImpl(Context context, MainActivityView view) {
        this.mContext = context;
        this.mView = view;

        mModel = new MainActivityModelImpl(context);
    }

    @Override
    public void onCreate() {
        PagerAdapter adapter = new PagerAdapter(mView.getSupportFragmentManager(),
                mModel.getFragments(),
                mModel.getFragmentNames());
        mView.setPagerAdapter(adapter);
    }

    @Override
    public void onLoginClick() {
        // TODO: Implement FB Login
    }

}
