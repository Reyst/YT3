package gsihome.reyst.y3t.mvp.view;

import android.support.v4.app.FragmentManager;

import gsihome.reyst.y3t.adapters.PagerAdapter;

public interface MainActivityView {

    FragmentManager getSupportFragmentManager();

    void setPagerAdapter(PagerAdapter adapter);
}
