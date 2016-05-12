package gsihome.reyst.y3t.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.adapters.ImageGalleryAdapter;
import gsihome.reyst.y3t.domain.File;
import gsihome.reyst.y3t.domain.IssueEntity;
import gsihome.reyst.y3t.domain.Performer;
import gsihome.reyst.y3t.mvp.view.DetailDataView;

public class DetailDataPresenterImpl implements DetailDataPresenter, ImageGalleryAdapter.OnItemClickListener {

    private Context mContext;
    private DetailDataView mDataView;
    private IssueEntity mEntity;

    private String mEmptyString;
    private String mBasePictureUrl;

    public DetailDataPresenterImpl(Context context) {
        this.mContext = context;

        mEmptyString = context.getString(R.string.emptyString);
        mBasePictureUrl = mContext.getString(R.string.base_picture_url);

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

        Date tempDt = mEntity.getStartDate();
        String valueRegistered = (tempDt != null) ? dateFormat.format(tempDt) : mEmptyString;

        tempDt = mEntity.getCreatedDate();
        String valueCreated = (tempDt != null) ? dateFormat.format(tempDt) : mEmptyString;

        tempDt = mEntity.getDeadline();
        String valueDeadline = (tempDt != null) ? dateFormat.format(tempDt) : mEmptyString;

        mDataView.setDateValues(valueCreated, valueRegistered, valueDeadline);

    }


    private void setEntityData() {

        mDataView.setTitle(mEntity.getTicketId());
        initDates();

        String strCategory = mEntity.getCategory().getName();

        List<Performer> performerList = mEntity.getPerformers();
        String strResponsible = (!performerList.isEmpty()) ?  performerList.get(0).getOrganization() : mEmptyString;
        String strDescription = mEntity.getBody();

        String strState = mEntity.getState().getName();
//        switch (mEntity.getState()) {
//            case IN_WORK:
//                strState = mContext.getString(R.string.str_in_work);
//                break;
//            case DONE:
//                strState = mContext.getString(R.string.str_done);
//                break;
//            case WAIT:
//                strState = mContext.getString(R.string.str_wait);
//                break;
//            default:
//                strState = mEmptyString;
//                break;
//        }

        mDataView.setStringValues(strCategory, strResponsible, strDescription, strState);

        List<String> urlList = new ArrayList<>();
        for (File f : mEntity.getFiles()) {
            urlList.add(mBasePictureUrl + f.getFilename().trim());
        }

        RecyclerView.Adapter adapter = new ImageGalleryAdapter(mContext, urlList, this);

        mDataView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        mDataView.onClick(view);
    }
}
