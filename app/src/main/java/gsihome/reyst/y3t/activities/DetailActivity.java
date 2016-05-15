package gsihome.reyst.y3t.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.adapters.ImageGalleryAdapter;
import gsihome.reyst.y3t.mvp.DetailDataContract;
import gsihome.reyst.y3t.mvp.presenter.DetailDataPresenter;

public class DetailActivity extends AppCompatActivity implements
        android.view.View.OnClickListener, ImageGalleryAdapter.OnItemClickListener, DetailDataContract.View {

    @BindView(R.id.tv_value_created)
    TextView mTextViewValueCreated;

    @BindView(R.id.tv_value_registered)
    TextView mTextViewValueRegistered;

    @BindView(R.id.tv_value_deadline)
    TextView mTextViewValueDeadline;

    @BindView(R.id.tv_section)
    TextView mTextViewValueCategory;

    @BindView(R.id.tv_value_responsible)
    TextView mTextViewValueResponsible;

    @BindView(R.id.tv_status)
    TextView mTextViewValueStatus;

    @BindView(R.id.tv_description)
    TextView mTextViewDescription;

    @BindView(R.id.rv_images)
    RecyclerView mRecyclerView;

    ActionBar mActionBar;

    private DetailDataContract.Presenter mDataPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDataPresenter = new DetailDataPresenter(this);
        mDataPresenter.onCreate(getIntent(), this);

        initRecyclerView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataPresenter.onDestroy();
    }

    private void initRecyclerView() {
        if (mRecyclerView != null) {
            ViewGroup.LayoutParams lp = mRecyclerView.getLayoutParams();
            lp.height = getResources().getDisplayMetrics().widthPixels / 2;
            mRecyclerView.setLayoutParams(lp);
            mRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }

    @Override
    public void onClick(android.view.View v) {
        Toast.makeText(this, v.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void closeView() {
        finish();
    }

    @Override
    public void setTitle(String title) {
        if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
    }

    @Override
    public void setDateValues(String valueCreated, String valueRegistered, String valueDeadline) {
        mTextViewValueCreated.setText(valueCreated);
        mTextViewValueRegistered.setText(valueRegistered);
        mTextViewValueDeadline.setText(valueDeadline);
    }

    @Override
    public void setStringValues(String strCategory, String strResponsible, String strDescription, String strState) {
        mTextViewValueCategory.setText(strCategory);
        mTextViewValueResponsible.setText(strResponsible);
        mTextViewValueStatus.setText(strState);
        mTextViewDescription.setText(strDescription);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

}

