package gsihome.reyst.y3t.mvp;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import gsihome.reyst.y3t.domain.IssueEntity;

public interface IssueListContract {
    interface Model {

        interface Callback {

            void onSuccess(List<IssueEntity> data);

            void onFailure(Throwable throwable);
        }

        void getDataPage(boolean first, Callback callback);

        void getCachedData(Callback callback);

        void onDestroy();
    }

    interface Presenter {

        void init();

        void getNextPage();

        void getFirstPage();

        boolean isLoading();

        void onDestroy();
    }

    interface View {

        void setAdapter(RecyclerView.Adapter adapter);

        void setRefreshing(boolean flag);

        void showMessage(String message);
    }
}
