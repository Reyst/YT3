package gsihome.reyst.y3t.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.mvp.IssueListContract;
import gsihome.reyst.y3t.utils.IssueListPresenterHolder;

public class RecyclerViewFragment extends Fragment implements IssueListContract.View {

    private static final String STR_KEY_FILTER = "filter";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private IssueListContract.Presenter mPresenter;

    public static Fragment getInstance(String filter) {

        Fragment fragment = new RecyclerViewFragment();

        Bundle params = new Bundle();
        params.putString(STR_KEY_FILTER, filter);

        fragment.setArguments(params);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String filter = null;
        Bundle params = getArguments();
        if (params != null) {
            filter = params.getString(STR_KEY_FILTER, null);
        }

        mPresenter = IssueListPresenterHolder.getPresenter(getContext(), this, filter);
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        android.view.View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        ButterKnife.bind(this, v);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        initEvents();

        mPresenter.init();

        return v;
    }

    private void initEvents() {

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {

                    LinearLayoutManager lManager = (LinearLayoutManager) layoutManager;

                    int totalItemCount = lManager.getItemCount();
                    int lastVisibleItem = lManager.findLastVisibleItemPosition();

                    int visibleThreshold = 1;//lastVisibleItem - lManager.findFirstVisibleItemPosition();
                    if (!mPresenter.isLoading() && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        mPresenter.getNextPage();
                    }
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mPresenter.isLoading()) {
                    mPresenter.getFirstPage();
                } else {
                    setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setRefreshing(boolean flag) {
        mSwipeRefreshLayout.setRefreshing(flag);
    }
}
