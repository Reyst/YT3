package gsihome.reyst.y3t.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mFragmentNames;

    public PagerAdapter(FragmentManager fm, @NonNull List<Fragment> pages, @NonNull List<String> names) {
        super(fm);
        mFragments = pages;
        mFragmentNames = names;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentNames.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}