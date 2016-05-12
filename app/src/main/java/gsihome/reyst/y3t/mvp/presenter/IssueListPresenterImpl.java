package gsihome.reyst.y3t.mvp.presenter;

import android.content.Context;

import java.util.List;

import gsihome.reyst.y3t.adapters.IssueListAdapter;
import gsihome.reyst.y3t.domain.IssueEntity;
import gsihome.reyst.y3t.mvp.model.IssueListModel;
import gsihome.reyst.y3t.mvp.model.IssueListModelImpl;
import gsihome.reyst.y3t.mvp.view.IssueListView;

public class IssueListPresenterImpl implements IssueListPresenter, IssueListAdapter.OnItemClickListener {

    IssueListModel mModel;

    IssueListView mView;
    Context mContext;

    IssueListAdapter mIssueAdapter;


    public IssueListPresenterImpl(Context context, IssueListView view, String filter) {
        mView = view;
        mContext = context;
        mIssueAdapter = new IssueListAdapter(context, this);
        mModel = new IssueListModelImpl(context, filter);
    }


    @Override
    public void init() {
        mView.setAdapter(mIssueAdapter);
        if (mIssueAdapter.size() == 0) {
            getNextPage();
        }
    }

    @Override
    public void getNextPage() {
        mModel.getData(new IssueListModel.Callback() {
            @Override
            public void getResult(List<IssueEntity> data) {
                mIssueAdapter.addAll(data);
                mIssueAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(IssueEntity entity) {

    }
}
