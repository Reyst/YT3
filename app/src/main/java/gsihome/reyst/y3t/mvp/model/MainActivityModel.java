package gsihome.reyst.y3t.mvp.model;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.fragments.RecyclerViewFragment;
import gsihome.reyst.y3t.mvp.MainActivityContract;

public class MainActivityModel implements MainActivityContract.Model {

    private Context mContext;

    private List<Fragment> mFragments;
    private List<String> mFragmentNames;

    public MainActivityModel(Context context) {
        mContext = context;
        initFragments();
    }

    private void initFragments() {

        mFragments = new ArrayList<>(3);
        mFragmentNames = new ArrayList<>(3);

        mFragments.add(RecyclerViewFragment.getInstance(mContext.getString(R.string.in_progress_filter)));
        mFragmentNames.add(mContext.getString(R.string.first_tab));

        mFragments.add(RecyclerViewFragment.getInstance(mContext.getString(R.string.done_filter)));
        mFragmentNames.add(mContext.getString(R.string.second_tab));

        mFragments.add(RecyclerViewFragment.getInstance(mContext.getString(R.string.wait_filter)));
        mFragmentNames.add(mContext.getString(R.string.third_tab));

    }

    @Override
    public List<Fragment> getFragments() {
        return mFragments;
    }

    @Override
    public List<String> getFragmentNames() {
        return mFragmentNames;
    }

}
