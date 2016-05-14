package gsihome.reyst.y3t.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;
import java.util.List;

import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.adapters.ImageGalleryAdapter;
import gsihome.reyst.y3t.mvp.DetailDataContract;

public class DetailDataPresenterImpl implements DetailDataContract.DetailDataPresenter, ImageGalleryAdapter.OnItemClickListener {

    private Context mContext;
    private DetailDataContract.DetailDataView mDataView;

    private DetailDataContract.DetailDataModel mModel;

    public DetailDataPresenterImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate(Intent intent, DetailDataContract.DetailDataView dataView) {

        mDataView = dataView;

        Serializable data = intent.getSerializableExtra(mContext.getString(R.string.key_for_entity));

        if (data != null && data instanceof DetailDataContract.DetailDataModel) {
            mModel = (DetailDataContract.DetailDataModel) data;
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

    private void setEntityData() {

        mDataView.setTitle(mModel.getTitle());
        mDataView.setDateValues(mModel.getCreatedDate(),
                mModel.getStartDate(),
                mModel.getDeadline());
        mDataView.setStringValues(mModel.getCategory(),
                mModel.getResponsible(),
                mModel.getDescription(),
                mModel.getState());

        List<String> urlList = mModel.getUrlList();
        RecyclerView.Adapter adapter = new ImageGalleryAdapter(mContext, urlList, this);

        mDataView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        mDataView.onClick(view);
    }
}
