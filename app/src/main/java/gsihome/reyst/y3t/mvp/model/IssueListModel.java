package gsihome.reyst.y3t.mvp.model;

import java.util.List;

import gsihome.reyst.y3t.domain.IssueEntity;

public interface IssueListModel {

    void getData(Callback callback);

    interface Callback {
        void getResult(List<IssueEntity> data);
    }

}
