package gsihome.reyst.y3t.mvp.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import gsihome.reyst.y3t.domain.IssueEntity;
import gsihome.reyst.y3t.mvp.IssueListContract;
import gsihome.reyst.y3t.rest.TicketService;
import gsihome.reyst.y3t.utils.ServiceApiHolder;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Response;

public class IssueListModelImpl implements IssueListContract.IssueListModel {

    private Context mContext;
    private String mFilter;

    private TicketService ticketService;
    private Realm realmService;

    public IssueListModelImpl(Context context, String filter) {

        mContext = context;
        mFilter = filter;

        ticketService = ServiceApiHolder.getTicketService(context);
        realmService = ServiceApiHolder.getRealmService(context);

    }


    @Override
    public void getData(final Callback callback) {

        Call<List<IssueEntity>> call;
        if (TextUtils.isEmpty(mFilter)) {
            call = ticketService.getAll();
        } else {
            call = ticketService.getListByStateFilter(mFilter);
        }

        call.enqueue(new retrofit2.Callback<List<IssueEntity>>() {
            @Override
            public void onResponse(Call<List<IssueEntity>> call, Response<List<IssueEntity>> response) {

                List<IssueEntity> result = response.body();

                realmService.beginTransaction();
                realmService.copyToRealmOrUpdate(result);
                realmService.commitTransaction();

                callback.getResult(result);
            }

            @Override
            public void onFailure(Call<List<IssueEntity>> call, Throwable t) {

                Log.d("ERROR", t.getLocalizedMessage());
            }
        });
    }
}
