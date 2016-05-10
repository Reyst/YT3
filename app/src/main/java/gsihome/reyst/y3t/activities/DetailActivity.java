package gsihome.reyst.y3t.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.adapters.ImageGalleryAdapter;
import gsihome.reyst.y3t.data.IssueEntity;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, ImageGalleryAdapter.OnItemClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        IssueEntity entity = (IssueEntity) intent.getSerializableExtra(getString(R.string.key_for_entity));

        if (entity != null) {
            setEntityData(entity, actionBar);
        } else {
            finish();
        }
    }

    private void setEntityData(IssueEntity entity, ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setTitle(entity.getNumber());
        }

        initRecyclerView(entity);
        initDates(entity);

        mTextViewValueCategory.setText(entity.getCategory());
        mTextViewValueResponsible.setText(entity.getResponsible());
        mTextViewDescription.setText(entity.getFullText());

        switch (entity.getState()) {
            case IN_WORK:
                mTextViewValueStatus.setText(R.string.str_in_work);
                break;
            case DONE:
                mTextViewValueStatus.setText(R.string.str_done);
                break;
            case WAIT:
                mTextViewValueStatus.setText(R.string.str_wait);
                break;
            default:
                mTextViewValueStatus.setText(R.string.emptyString);
                break;
        }
    }

    private void initDates(IssueEntity entity) {

        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(getApplicationContext());

        mTextViewValueRegistered.setText(dateFormat.format(entity.getRegistered()));
        mTextViewValueCreated.setText(dateFormat.format(entity.getCreated()));
        mTextViewValueDeadline.setText(dateFormat.format(entity.getDeadline()));
    }

    private void initRecyclerView(IssueEntity entity) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_images);

        if (recyclerView != null) {
            ViewGroup.LayoutParams lp = recyclerView.getLayoutParams();
            lp.height = getResources().getDisplayMetrics().widthPixels / 2;
            recyclerView.setLayoutParams(lp);

            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            RecyclerView.Adapter adapter = new ImageGalleryAdapter(this, entity.getImages(), this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, v.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

