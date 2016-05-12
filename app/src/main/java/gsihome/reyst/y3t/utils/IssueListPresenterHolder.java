package gsihome.reyst.y3t.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import gsihome.reyst.y3t.mvp.presenter.IssueListPresenter;
import gsihome.reyst.y3t.mvp.presenter.IssueListPresenterImpl;
import gsihome.reyst.y3t.mvp.view.IssueListView;

public class IssueListPresenterHolder {

    private static Map<IssueListView, IssueListPresenter> sPresenters;

    public static IssueListPresenter getPresenter(Context context, IssueListView view, String filter) {

        if (sPresenters == null) {
            sPresenters = new HashMap<>();
        }

        IssueListPresenter result = sPresenters.get(view);

        if (result == null) {
            result = new IssueListPresenterImpl(context, view, filter);
            sPresenters.put(view, result);
        }

        return result;
    }


}
