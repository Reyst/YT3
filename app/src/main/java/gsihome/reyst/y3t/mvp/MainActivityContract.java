package gsihome.reyst.y3t.mvp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

import gsihome.reyst.y3t.adapters.PagerAdapter;

public interface MainActivityContract {

    interface Model {

        List<Fragment> getFragments();

        List<String> getFragmentNames();
    }

    interface Presenter {

        void onCreate();

        void onLoginClick();
    }

    interface View {

        FragmentManager getSupportFragmentManager();

        void setPagerAdapter(PagerAdapter adapter);
    }
}
