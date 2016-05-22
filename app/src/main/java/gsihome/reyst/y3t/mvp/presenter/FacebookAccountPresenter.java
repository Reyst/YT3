package gsihome.reyst.y3t.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Collections;

import gsihome.reyst.y3t.domain.FBProfile;
import gsihome.reyst.y3t.mvp.FacebookAccountContract;
import gsihome.reyst.y3t.utils.ServiceApiHolder;
import io.realm.Realm;

public class FacebookAccountPresenter implements FacebookAccountContract.Presenter {

    private Context mContext;

    private FacebookAccountContract.View mView;

    private String mToken;
    private String mProfileId;
    private CallbackManager mCallbackManager;
    private ProfileTracker mProfileTracker;

    public FacebookAccountPresenter(Context mContext, FacebookAccountContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void onCreate() {
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                mToken = loginResult.getAccessToken().getToken();
                mProfileId = loginResult.getAccessToken().getUserId();

                Profile profile = Profile.getCurrentProfile();
                if (profile == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            stopTracking();
                            saveProfile(currentProfile);
                            mView.updateViews(currentProfile);
                        }
                    };
                    mProfileTracker.startTracking();
                } else {
                    saveProfile(profile);
                    mView.updateViews(profile);
                }
            }

            @Override
            public void onCancel() {
                logOut();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        LoginManager.getInstance()
                .logInWithReadPermissions(mView.getActivity(), Collections.singletonList("public_profile"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void logOut() {
        LoginManager.getInstance().logOut();
        mView.finish();
    }

    @Override
    public void onDestroy() {
        if (mProfileTracker != null && mProfileTracker.isTracking()){
            mProfileTracker.stopTracking();
        }
    }

    private void saveProfile(Profile profile) {

        Realm realm = ServiceApiHolder.getRealmService(mContext);

        if (profile != null) {
            realm.beginTransaction();
            FBProfile fbProfile = realm.where(FBProfile.class).equalTo("id", mProfileId).findFirst();
            if (fbProfile == null) {
                fbProfile = realm.createObject(FBProfile.class);
                fbProfile.setId(mProfileId);
            }
            fbProfile.setFirstName(profile.getFirstName());
            fbProfile.setMiddleName(profile.getMiddleName());
            fbProfile.setLastName(profile.getLastName());
            fbProfile.setName(profile.getName());
            fbProfile.setLinkUri(profile.getLinkUri().toString());
            fbProfile.setToken(mToken);
            realm.commitTransaction();
        }
    }
}
