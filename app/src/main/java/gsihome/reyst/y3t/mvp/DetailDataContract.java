package gsihome.reyst.y3t.mvp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public interface DetailDataContract {
    interface Model extends Serializable {

        long getId();

        String getTitle();

        String getState();

        List<String> getUrlList();

        String getDescription();

        String getResponsible();

        String getCategory();

        String getStartDate();

        String getCreatedDate();

        String getDeadline();
    }

    interface Presenter {

        void onCreate(Intent intent, View dataView);

        void onDestroy();
    }

    interface View {

        void closeView();

        void setTitle(String title);

        void setDateValues(String valueCreated, String valueRegistered, String valueDeadline);

        void setStringValues(String strCategory, String strResponsible, String strDescription, String strState);

        void setAdapter(RecyclerView.Adapter adapter);

        void onClick(android.view.View view);
    }
}
