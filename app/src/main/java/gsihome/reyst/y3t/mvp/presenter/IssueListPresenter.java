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
import gsihome.reyst.y3t.mvp.model.DetailDataModel;
import gsihome.reyst.y3t.mvp.model.IssueListModel;

public class IssueListPresenter implements IssueListContract.Presenter,
        IssueListAdapter.OnItemClickListener {

    private Context mContext;

    private IssueListContract.Model mModel;
    private IssueListContract.View mView;

    private IssueListAdapter mIssueAdapter;
    private boolean mLoading;

    public IssueListPresenter(Context context, IssueListContract.View view, String filter) {
        mView = view;
        mContext = context;
        mIssueAdapter = new IssueListAdapter(context, this);
        mModel = new IssueListModel(context, filter);
    }

    @Override
    public void init() {

        mLoading = true;

        mModel.getCachedData(new IssueListContract.Model.Callback() {
            @Override
            public void onSuccess(List<IssueEntity> data) {
                mLoading = false;
                if (data == null || data.size() == 0) {
                    getFirstPage();
                } else {
                    mIssueAdapter.addAll(data);
                    mIssueAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                mView.showMessage(throwable.getLocalizedMessage());
                mLoading = false;
                getFirstPage();
            }
        });

        mView.setAdapter(mIssueAdapter);
//        if (mIssueAdapter.size() == 0) {
//            getNextPage();
//        }
    }

    @Override
    public void getNextPage() {

        mLoading = true;

        mIssueAdapter.add(null);
        final int nullPosition = mIssueAdapter.size() - 1;
        mIssueAdapter.notifyItemInserted(nullPosition);

        mModel.getDataPage(false, new IssueListContract.Model.Callback() {
            @Override
            public void onSuccess(List<IssueEntity> data) {

                if (nullPosition < mIssueAdapter.size()) {
                    mIssueAdapter.remove(nullPosition);
                }

                for (IssueEntity entity : data) {
                    if (!mIssueAdapter.contains(entity)) {
                        mIssueAdapter.add(entity);
                    }
                }
                mIssueAdapter.notifyDataSetChanged();
                loadingEnd();
            }

            @Override
            public void onFailure(Throwable throwable) {
                mView.showMessage(throwable.getLocalizedMessage());
                loadingEnd();
            }
        });
    }

    private void loadingEnd() {
        mLoading = false;
        mView.setRefreshing(false);
    }

    @Override
    public void getFirstPage() {
        mLoading = true;
        mView.setRefreshing(true);

        mModel.getDataPage(true, new IssueListContract.Model.Callback() {
            @Override
            public void onSuccess(List<IssueEntity> data) {
                mIssueAdapter.clear();
                mIssueAdapter.addAll(data);
                mIssueAdapter.notifyDataSetChanged();
                loadingEnd();
            }

            @Override
            public void onFailure(Throwable throwable) {
                mView.showMessage(throwable.getLocalizedMessage());
                loadingEnd();
            }
        });

    }

    @Override
    public boolean isLoading() {
        return mLoading;
    }

    @Override
    public void onDestroy() {
        mModel.onDestroy();
        mModel = null;
        mView = null;
    }

    @Override
    public void onItemClick(IssueEntity entity) {

        // The Id of the entity can be sent as a param for the Detail Activity,
        // but sending instance of the model is better.
        DetailDataContract.Model model = new DetailDataModel(mContext, entity);

        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(mContext.getString(R.string.key_for_entity), model);
        mContext.startActivity(intent);
    }
}
