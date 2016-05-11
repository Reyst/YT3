package gsihome.reyst.y3t.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.text.DateFormat;
import java.util.Date;

import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.adapters.ImageGalleryAdapter;
import gsihome.reyst.y3t.data.IssueEntity;
import gsihome.reyst.y3t.mvp.view.DetailDataView;

public class DetailDataPresenterImpl implements DetailDataPresenter, ImageGalleryAdapter.OnItemClickListener {

    private Context mContext;
    private DetailDataView mDataView;
    private IssueEntity mEntity;

    private String mEmptyString;

    public DetailDataPresenterImpl(Context context) {
        this.mContext = context;

        mEmptyString = context.getString(R.string.emptyString);

    }

    @Override
    public void onCreate(Intent intent, DetailDataView dataView) {

        mDataView = dataView;

        mEntity = (IssueEntity) intent.getSerializableExtra(mContext.getString(R.string.key_for_entity));

        if (mEntity != null) {
            setEntityData();
        } else {
            mDataView.closeView();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onModelChange() {
        setEntityData();
    }

    private void initDates() {

        DateFormat dateFormat = android.text.format.DateFormat.getMediumDateFormat(mContext);

        Date tempDt = mEntity.getRegistered();
        String valueRegistered = (tempDt != null) ? dateFormat.format(tempDt) : mEmptyString;

        tempDt = mEntity.getCreated();
        String valueCreated = (tempDt != null) ? dateFormat.format(tempDt) : mEmptyString;

        tempDt = mEntity.getDeadline();
        String valueDeadline = (tempDt != null) ? dateFormat.format(tempDt) : mEmptyString;

        mDataView.setDateValues(valueCreated, valueRegistered, valueDeadline);

    }


    private void setEntityData() {

        mDataView.setTitle(mEntity.getNumber());
        initDates();

        String strCategory = mEntity.getCategory();
        String strResponsible = mEntity.getResponsible();
        String strDescription = mEntity.getFullText();

        String strState;
        switch (mEntity.getState()) {
            case IN_WORK:
                strState = mContext.getString(R.string.str_in_work);
                break;
            case DONE:
                strState = mContext.getString(R.string.str_done);
                break;
            case WAIT:
                strState = mContext.getString(R.string.str_wait);
                break;
            default:
                strState = mEmptyString;
                break;
        }

        mDataView.setStringValues(strCategory, strResponsible, strDescription, strState);

        RecyclerView.Adapter adapter = new ImageGalleryAdapter(mContext, mEntity.getImages(), this);

        mDataView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        mDataView.onClick(view);
    }
}
