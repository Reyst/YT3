package gsihome.reyst.y3t.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import gsihome.reyst.y3t.mvp.IssueListContract;
import gsihome.reyst.y3t.mvp.presenter.IssueListPresenter;

public class IssueListPresenterHolder {

    private static Map<IssueListContract.View, IssueListContract.Presenter> sPresenters;

    public static IssueListContract.Presenter getPresenter(Context context,
                                                           IssueListContract.View view, String filter) {

        if (sPresenters == null) {
            sPresenters = new HashMap<>();
        }

        IssueListContract.Presenter result = sPresenters.get(view);

        if (result == null) {
            result = new IssueListPresenter(context, view, filter);
            sPresenters.put(view, result);
        }

        return result;
    }

    public static IssueListContract.Presenter remove(IssueListContract.View view) {
        return sPresenters.remove(view);
    }
}
