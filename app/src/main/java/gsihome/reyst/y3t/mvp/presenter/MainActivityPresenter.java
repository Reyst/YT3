package gsihome.reyst.y3t.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import gsihome.reyst.y3t.activities.FacebookAccountActivity;
import gsihome.reyst.y3t.adapters.PagerAdapter;
import gsihome.reyst.y3t.mvp.MainActivityContract;
import gsihome.reyst.y3t.mvp.model.MainActivityModel;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mView;
    private Context mContext;

    private MainActivityContract.Model mModel;

    public MainActivityPresenter(Context context, MainActivityContract.View view) {
        this.mContext = context;
        this.mView = view;

        mModel = new MainActivityModel(context);
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
