package gsihome.reyst.y3t.mvp.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

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

    private Context mContext;

    private TicketService mTicketService;
    private Realm mRealmService;

    private String mFilter;
    private long[] mQueryFilter;

    private int mPageSize;

    public IssueListModel(Context context, String filter) {

        mContext = context;

        mFilter = filter;
        initQueryFilter();

        mTicketService = ServiceApiHolder.getTicketService(context);
        mRealmService = ServiceApiHolder.getRealmService(context);

        mPageSize = context.getResources().getInteger(R.integer.data_page_size);
    }

    private void initQueryFilter() {
        if (!TextUtils.isEmpty(mFilter)) {
            String[] filterParts = mFilter.split(mContext.getString(R.string.str_filter_delimiter));
            mQueryFilter = new long[filterParts.length];
            for (int i = 0; i < filterParts.length; i++) {
                mQueryFilter[i] = Long.parseLong(filterParts[i]);
            }
        }
    }

    @Override
    public void getDataPage(final int offset, final Callback callback) {

        final boolean first = offset == 0;
        Call<List<IssueEntity>> call;
        if (TextUtils.isEmpty(mFilter)) {
            call = mTicketService.getAll(mPageSize, offset);
        } else {
            call = mTicketService.getListByStateFilter(mFilter, mPageSize, offset);
        }

        call.enqueue(new retrofit2.Callback<List<IssueEntity>>() {
            @Override
            public void onResponse(Call<List<IssueEntity>> call, Response<List<IssueEntity>> response) {
                final List<IssueEntity> result = response.body();

                mRealmService.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        if (first) {
                            getIssueEntityRealmQuery(realm)
                                    .findAll().deleteAllFromRealm();
                        }

                        realm.copyToRealmOrUpdate(result);
                    }
                });
                callback.onGetResult(result);
            }

            @Override
            public void onFailure(Call<List<IssueEntity>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void getCachedData(final Callback callback) {

        RealmResults<IssueEntity> result = getIssueEntityRealmQuery(mRealmService).findAll();

        if (result.isEmpty()) {
            callback.onFailure(null);
        } else {
            List<IssueEntity> data = new ArrayList<>(result);
            callback.onGetResult(data);
        }
    }

    @NonNull
    private RealmQuery<IssueEntity> getIssueEntityRealmQuery(Realm realm) {
        RealmQuery<IssueEntity> query = realm.where(IssueEntity.class);
        if (mQueryFilter != null) {
            boolean nonFirst = false;
            for (long param : mQueryFilter) {
                if (nonFirst) {
                    query.or();
                }
                nonFirst = true;
                query.equalTo("state.id", param);
            }
        }
        return query;
    }
}
