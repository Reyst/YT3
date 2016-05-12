package gsihome.reyst.y3t.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.adapters.IssueListAdapter;
import gsihome.reyst.y3t.domain.IssueEntity;


public class Invoker implements IssueListAdapter.OnItemClickListener, AdapterView.OnItemClickListener {

    private Context mContext;

    public Invoker(Context context) {
        this.mContext = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IssueEntity entity = (IssueEntity) parent.getAdapter().getItem(position);
        invoke(entity);
    }

    @Override
    public void onItemClick(IssueEntity entity) {
        invoke(entity);
    }

    private void invoke(IssueEntity entity) {
//        Intent intent = new Intent(mContext, DetailActivity.class);
//        intent.putExtra(mContext.getString(R.string.key_for_entity), entity);
//        mContext.startActivity(intent);
    }
}
