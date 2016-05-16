package gsihome.reyst.y3t.mvp;

import android.app.Activity;
import android.content.Intent;

import com.facebook.Profile;

public interface FacebookAccountContract {

    interface Presenter {

        void onCreate();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void logOut();

        void onDestroy();
    }

    interface View {

        void updateViews(Profile profile);

        void finish();

        Activity getActivity();
    }

}
