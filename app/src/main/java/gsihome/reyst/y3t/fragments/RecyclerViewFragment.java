package gsihome.reyst.y3t.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.activities.Invoker;
import gsihome.reyst.y3t.adapters.IssueListAdapter;
import gsihome.reyst.y3t.data.DataUtil;
import gsihome.reyst.y3t.data.IssueEntity;
import gsihome.reyst.y3t.data.State;

public class RecyclerViewFragment extends Fragment {

    private static final String STR_KEY_STATE = "state";
    private IssueListAdapter mAdapter;

    public static Fragment getInstance(State state) {

        Fragment fragment = new RecyclerViewFragment();

        Bundle params = new Bundle();
        params.putInt(STR_KEY_STATE, state.getValue());

        fragment.setArguments(params);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        State state = State.WAIT;
        Bundle params = getArguments();
        if (params != null) {
            state = State.getByValue(params.getInt(STR_KEY_STATE, -1));
        }

        List<IssueEntity> issueList = DataUtil.getModel(getContext(), state);
        mAdapter = new IssueListAdapter(getContext(), issueList, new Invoker(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recycler, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        recyclerView.setAdapter(mAdapter);


        return v;
    }
}
