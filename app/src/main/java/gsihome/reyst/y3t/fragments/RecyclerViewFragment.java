package gsihome.reyst.y3t.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.mvp.IssueListContract;
import gsihome.reyst.y3t.utils.IssueListPresenterHolder;

public class RecyclerViewFragment extends Fragment implements IssueListContract.IssueListView {

    private static final String STR_KEY_FILTER = "filter";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private IssueListContract.IssueListPresenter mPresenter;
    private String mFilter;

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

        mFilter = null;
        Bundle params = getArguments();
        if (params != null) {
            mFilter = params.getString(STR_KEY_FILTER, null);
        }

        mPresenter = IssueListPresenterHolder.getPresenter(getContext(), this, mFilter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        ButterKnife.bind(this, v);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mPresenter.init();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }
}
