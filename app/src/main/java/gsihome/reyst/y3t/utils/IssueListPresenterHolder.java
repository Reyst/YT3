package gsihome.reyst.y3t.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import gsihome.reyst.y3t.mvp.IssueListContract;
import gsihome.reyst.y3t.mvp.presenter.IssueListPresenterImpl;

public class IssueListPresenterHolder {

    private static Map<IssueListContract.IssueListView, IssueListContract.IssueListPresenter> sPresenters;

    public static IssueListContract.IssueListPresenter getPresenter(Context context, IssueListContract.IssueListView view, String filter) {

        if (sPresenters == null) {
            sPresenters = new HashMap<>();
        }

        IssueListContract.IssueListPresenter result = sPresenters.get(view);

        if (result == null) {
            result = new IssueListPresenterImpl(context, view, filter);
            sPresenters.put(view, result);
        }

        return result;
    }


}
