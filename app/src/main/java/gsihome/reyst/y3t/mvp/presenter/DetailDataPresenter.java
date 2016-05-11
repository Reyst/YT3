package gsihome.reyst.y3t.mvp.presenter;

import android.content.Intent;

import gsihome.reyst.y3t.mvp.view.DetailDataView;

public interface DetailDataPresenter {

    void onCreate(Intent intent, DetailDataView dataView);

    void onDestroy();

    void onModelChange();

}
