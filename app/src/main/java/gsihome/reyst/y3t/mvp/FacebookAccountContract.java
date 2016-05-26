package gsihome.reyst.y3t.mvp;

import android.app.Activity;
import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.Profile;

public interface FacebookAccountContract {

    interface Presenter {

        void onCreate();

        void onActivityResult(int requestCode, int resultCode, Intent data);

        void logOut();

        void onDestroy();
    }

    interface View {

        void updateTextInfo(Profile profile);

        void updatePicture(String url);

        void finish();

        Activity getActivity();

        void showMessage(String message);
    }

    interface Model {

        Profile getCachedProfile(String id);

        void saveProfile(Profile profile, AccessToken token, OnSaveCallback callback);

        String getProfileImageUrl(String id);

        interface OnSaveCallback {

            void onSuccess();

            void onFailure(Throwable error);
        }


    }

}
