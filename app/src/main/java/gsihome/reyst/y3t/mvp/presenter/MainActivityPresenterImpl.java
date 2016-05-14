package gsihome.reyst.y3t.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import gsihome.reyst.y3t.activities.FacebookAccountActivity;
import gsihome.reyst.y3t.adapters.PagerAdapter;
import gsihome.reyst.y3t.mvp.MainActivityContract;
import gsihome.reyst.y3t.mvp.model.MainActivityModelImpl;

public class MainActivityPresenterImpl implements MainActivityContract.MainActivityPresenter {

    private MainActivityContract.MainActivityView mView;
    private Context mContext;

    private MainActivityContract.MainActivityModel mModel;

    public MainActivityPresenterImpl(Context context, MainActivityContract.MainActivityView view) {
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
        mContext.startActivity(new Intent(mContext, FacebookAccountActivity.class));
    }

}
