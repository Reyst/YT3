package gsihome.reyst.y3t.mvp.model;

import android.content.Context;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.domain.File;
import gsihome.reyst.y3t.domain.IssueEntity;
import gsihome.reyst.y3t.domain.Performer;
import gsihome.reyst.y3t.mvp.DetailDataContract;

public class DetailDataModelImpl implements DetailDataContract.DetailDataModel {

    private long mId;
    private String mCategory;
    private String mTitle;
    private String mDescription;
    private Date mCreatedDate;
    private Date mStartDate;
    private Date mDeadline;
    private String mState;
    private List<String> mImageUrls;
    private String mResponsible;

    private DateFormat mFormatter;

    private String mEmptyString;

    public DetailDataModelImpl(Context context, IssueEntity entity) {

        mEmptyString = context.getString(R.string.emptyString);
        String basePictureUrl = context.getString(R.string.base_picture_url);

        mId = entity.getId();
        mCategory = entity.getCategory().getName();
        mTitle = entity.getTicketId();
        mDescription = entity.getBody();
        mCreatedDate = entity.getCreatedDate();
        mStartDate = entity.getStartDate();
        mDeadline = entity.getDeadline();
        mState = entity.getState().getName();
        mImageUrls = new ArrayList<>();

        for (File f : entity.getFiles()) {
            mImageUrls.add(basePictureUrl + f.getFilename().trim());
        }

        List<Performer> performerList = entity.getPerformers();
        mResponsible = (!performerList.isEmpty()) ? performerList.get(0).getOrganization() : mEmptyString;

        mFormatter = android.text.format.DateFormat.getMediumDateFormat(context);

    }

    @Override
    public long getId() {
        return mId;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getState() {
        return mState;
    }

    @Override
    public List<String> getUrlList() {
        return mImageUrls;
    }

    @Override
    public String getDescription() {
        return mDescription;
    }

    @Override
    public String getResponsible() {
        return mResponsible;
    }

    @Override
    public String getCategory() {
        return mCategory;
    }

    @Override
    public String getStartDate() {
        return (mStartDate != null) ? mFormatter.format(mStartDate) : mEmptyString;
    }

    @Override
    public String getCreatedDate() {
        return (mCreatedDate != null) ? mFormatter.format(mCreatedDate) : mEmptyString;
    }

    @Override
    public String getDeadline() {
        return (mDeadline != null) ? mFormatter.format(mDeadline) : mEmptyString;
    }
}
