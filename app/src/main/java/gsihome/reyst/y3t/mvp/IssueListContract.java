package gsihome.reyst.y3t.mvp;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import gsihome.reyst.y3t.domain.IssueEntity;

public interface IssueListContract {
    interface Model {

        //void getData(Callback callback);

        void getDataPage(boolean first, Callback callback);

        void getCachedData(Callback callback);

        interface Callback {
            void getResult(List<IssueEntity> data);
        }

    }

    interface Presenter {

        void init();

        void getNextPage();

        void getFirstPage();

        boolean isLoading();
    }

    interface View {

        void setAdapter(RecyclerView.Adapter adapter);

        void setRefreshing(boolean flag);
    }
}
