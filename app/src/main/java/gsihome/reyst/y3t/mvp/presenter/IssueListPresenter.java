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

        if (mIssueAdapter.size() == 0) {
            mLoading = true;

            mModel.getCachedData(new IssueListContract.Model.Callback() {
                @Override
                public void onGetResult(List<IssueEntity> data) {
                    addDataIntoAdapter(data);
                    mLoading = false;
                }

                @Override
                public void onFailure(Throwable error) {
                    getFirstPage();
                }
            });
        }

        mView.setAdapter(mIssueAdapter);
    }

    @Override
    public void getNextPage() {

        mLoading = true;

        mIssueAdapter.add(null);
        final int nullPosition = mIssueAdapter.size() - 1;
        mIssueAdapter.notifyItemInserted(nullPosition);

        mModel.getDataPage(false, new IssueListContract.Model.Callback() {
            @Override
            public void onGetResult(List<IssueEntity> data) {

                if (nullPosition < mIssueAdapter.size()) {
                    mIssueAdapter.remove(nullPosition);
                }

                addDataIntoAdapter(data);
                mLoading = false;
            }

            @Override
            public void onFailure(Throwable error) {
                if (nullPosition < mIssueAdapter.size()) {
                    mIssueAdapter.remove(nullPosition);
                    mIssueAdapter.notifyItemRemoved(nullPosition);
                }
                mLoading = false;
                mView.showMessage(error.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getFirstPage() {
        mLoading = true;
        mView.setRefreshing(true);

        mModel.getDataPage(true, new IssueListContract.Model.Callback() {
            @Override
            public void onGetResult(List<IssueEntity> data) {
                mIssueAdapter.clear();
                addDataIntoAdapter(data);
                mLoading = false;
                mView.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable error) {
                mLoading = false;
                mView.setRefreshing(false);

                mView.showMessage(error.getLocalizedMessage());
            }
        });

    }

    private void addDataIntoAdapter(List<IssueEntity> data) {
        data.removeAll(mIssueAdapter.getModel());
        if (data.size() > 0) {
            mIssueAdapter.addAll(data);
            mIssueAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean isLoading() {
        return mLoading;
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
