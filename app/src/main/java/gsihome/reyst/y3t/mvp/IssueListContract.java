package gsihome.reyst.y3t.mvp;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import gsihome.reyst.y3t.domain.IssueEntity;

public interface IssueListContract {
    interface IssueListModel {

        void getData(Callback callback);

        interface Callback {
            void getResult(List<IssueEntity> data);
        }

    }

    interface IssueListPresenter {

        void init();

        void getNextPage();

    }

    interface IssueListView {

        void setAdapter(RecyclerView.Adapter adapter);
    }
}
