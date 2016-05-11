package gsihome.reyst.y3t.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface DetailDataView {


    void closeView();

    void setTitle(String title);

    void setDateValues(String valueCreated, String valueRegistered, String valueDeadline);

    void setStringValues(String strCategory, String strResponsible, String strDescription, String strState);

    void setAdapter(RecyclerView.Adapter adapter);

    void onClick(View view);
}
