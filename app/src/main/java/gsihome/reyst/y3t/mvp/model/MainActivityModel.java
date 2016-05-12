package gsihome.reyst.y3t.mvp.model;

import android.support.v4.app.Fragment;

import java.util.List;

public interface MainActivityModel {

    List<Fragment> getFragments();

    List<String> getFragmentNames();

}
