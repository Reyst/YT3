package gsihome.reyst.y3t.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.activities.DetailActivity;
import gsihome.reyst.y3t.adapters.IssueListAdapter;
import gsihome.reyst.y3t.domain.IssueEntity;
import gsihome.reyst.y3t.mvp.DetailDataContract;
import gsihome.reyst.y3t.mvp.IssueListContract;
import gsihome.reyst.y3t.mvp.model.DetailDataModelImpl;
import gsihome.reyst.y3t.mvp.model.IssueListModelImpl;
import io.realm.RealmList;

public class IssueListPresenterImpl implements IssueListContract.IssueListPresenter, IssueListAdapter.OnItemClickListener {

    IssueListContract.IssueListModel mModel;

    IssueListContract.IssueListView mView;
    Context mContext;

    IssueListAdapter mIssueAdapter;


    public IssueListPresenterImpl(Context context, IssueListContract.IssueListView view, String filter) {
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
        mModel.getData(new IssueListContract.IssueListModel.Callback() {
            @Override
            public void getResult(List<IssueEntity> data) {

//                if (mIssueAdapter.size() == 0) {
//                    mIssueAdapter.setModel(data);
//                }
                mIssueAdapter.addAll(data);
                mIssueAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(IssueEntity entity) {

        DetailDataContract.DetailDataModel model = new DetailDataModelImpl(mContext, entity);

        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(mContext.getString(R.string.key_for_entity), model);
        mContext.startActivity(intent);
    }
}
