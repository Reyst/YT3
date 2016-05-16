package gsihome.reyst.y3t.mvp.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.domain.IssueEntity;
import gsihome.reyst.y3t.mvp.IssueListContract;
import gsihome.reyst.y3t.rest.TicketService;
import gsihome.reyst.y3t.utils.ServiceApiHolder;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;

public class IssueListModel implements IssueListContract.Model {

    public static final String ERROR_TAG = "RETROFIT ERROR";
    private Context mContext;
    private String mFilter;

    private long[] mQueryFilter;

    private TicketService mTicketService;
    private Realm mRealmService;

    private int mPageSize;
    private int mOffset;

    public IssueListModel(Context context, String filter) {

        mContext = context;
        mFilter = filter;

        if (!TextUtils.isEmpty(mFilter)) {

            String[] filterParts = mFilter.split(context.getString(R.string.str_filter_delimiter));

            mQueryFilter = new long[filterParts.length];
            for (int i = 0; i < filterParts.length; i++) {
                mQueryFilter[i] = Long.parseLong(filterParts[i]);
            }

        }

        mTicketService = ServiceApiHolder.getTicketService(context);
        mRealmService = ServiceApiHolder.getRealmService(context);

        mPageSize = context.getResources().getInteger(R.integer.data_page_size);
        mOffset = 0;

    }

    @Override
    public void getDataPage(boolean first, final Callback callback) {
        if (first) {
            mRealmService.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmQuery<IssueEntity> query = getIssueEntityRealmQuery(realm);
                    query.findAll().deleteAllFromRealm();
                }
            });
            mOffset = 0;
        }

        Call<List<IssueEntity>> call = mTicketService.getListByStateFilter(mFilter, mPageSize, mOffset);

        call.enqueue(new retrofit2.Callback<List<IssueEntity>>() {
            @Override
            public void onResponse(Call<List<IssueEntity>> call, Response<List<IssueEntity>> response) {
                final List<IssueEntity> result = response.body();

                mOffset += result.size();

                mRealmService.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(result);
                    }
                });
                callback.getResult(result);
            }

            @Override
            public void onFailure(Call<List<IssueEntity>> call, Throwable t) {
                Log.d(ERROR_TAG, t.getLocalizedMessage());
                callback.getResult(new ArrayList<IssueEntity>());
            }
        });

    }

    @Override
    public void getCachedData(final Callback callback) {
//        mRealmService.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
                RealmQuery<IssueEntity> query = getIssueEntityRealmQuery(mRealmService);
                RealmResults<IssueEntity> result = query.findAll();
                mOffset = result.size();
                callback.getResult(result);
//            }
//        }, new Realm.Transaction.OnError() {
//            @Override
//            public void onError(Throwable error) {
//                callback.getResult(null);
//            }
//        });
    }

    @NonNull
    private RealmQuery<IssueEntity> getIssueEntityRealmQuery(Realm realm) {
        RealmQuery<IssueEntity> query = realm.where(IssueEntity.class);
        if (mQueryFilter != null) {
            boolean nonFirst = false;
            for (long param : mQueryFilter) {
                if (nonFirst) query.or();
                nonFirst = true;
                query.equalTo("state.id", param);
            }
        }

        return query;
    }
}
