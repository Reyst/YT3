package gsihome.reyst.y3t.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.adapters.ImageGalleryAdapter;
import gsihome.reyst.y3t.mvp.DetailDataContract;

public class DetailDataPresenter implements DetailDataContract.Presenter,
        ImageGalleryAdapter.OnItemClickListener {

    private Context mContext;
    private DetailDataContract.View mView;

    private DetailDataContract.Model mModel;

    public DetailDataPresenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate(Intent intent, DetailDataContract.View dataView) {

        mView = dataView;

        Serializable data = intent.getSerializableExtra(mContext.getString(R.string.key_for_entity));

        if (data != null && data instanceof DetailDataContract.Model) {
            mModel = (DetailDataContract.Model) data;
            setEntityData();
        } else {
            mView.closeView();
        }
    }

    @Override
    public void onDestroy() {
        mModel = null;
    }

    private void setEntityData() {

        mView.setTitle(mModel.getTitle());
        mView.setDateValues(mModel.getCreatedDate(),
                mModel.getStartDate(),
                mModel.getDeadline());
        mView.setStringValues(mModel.getCategory(),
                mModel.getResponsible(),
                mModel.getDescription(),
                mModel.getState());

        List<String> urlList = mModel.getUrlList();
        RecyclerView.Adapter adapter = new ImageGalleryAdapter(mContext, urlList, this);

        mView.setAdapter(adapter);
    }

    @Override
    public void onClick(android.view.View view) {
        mView.onClick(view);
    }
}
