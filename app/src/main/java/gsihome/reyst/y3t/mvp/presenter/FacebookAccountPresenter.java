package gsihome.reyst.y3t.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Collections;

import gsihome.reyst.y3t.mvp.FacebookAccountContract;
import gsihome.reyst.y3t.mvp.model.FacebookProfileModel;

public class FacebookAccountPresenter implements FacebookAccountContract.Presenter {

    private FacebookAccountContract.View mView;
    private FacebookAccountContract.Model mModel;

    private CallbackManager mCallbackManager;
    private ProfileTracker mProfileTracker;
    private AccessToken mAccessToken;

    public FacebookAccountPresenter(Context context, FacebookAccountContract.View mView) {
        this.mView = mView;
        this.mModel = new FacebookProfileModel(context);
    }

    @Override
    public void onCreate() {

        mCallbackManager = CallbackManager.Factory.create();
        mAccessToken = AccessToken.getCurrentAccessToken();

        if (mAccessToken == null || mAccessToken.isExpired()) {
            LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

                @Override
                public void onSuccess(LoginResult loginResult) {
                    mAccessToken = loginResult.getAccessToken();
                    Profile profile = Profile.getCurrentProfile();
                    if (profile == null) {
                        mProfileTracker = new ProfileTracker() {
                            @Override
                            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                                stopTracking();
                                saveProfile(currentProfile);
                            }
                        };
                        mProfileTracker.startTracking();
                    } else {
                        saveProfile(profile);
                    }
                }

                @Override
                public void onCancel() {
                    logOut();
                }

                @Override
                public void onError(FacebookException error) {
                    mView.showMessage(error.getLocalizedMessage());
                    logOut();
                }
            });

            LoginManager.getInstance().logInWithReadPermissions(
                    mView.getActivity(),
                    Collections.singletonList("public_profile")
            );

        } else {
            mView.updateTextInfo(mModel.getCachedProfile(mAccessToken.getUserId()));
            mView.updatePicture(mModel.getProfileImageUrl(mAccessToken.getUserId()));
        }
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
        if (mProfileTracker != null && mProfileTracker.isTracking()) {
            mProfileTracker.stopTracking();
        }
    }

    private void saveProfile(final Profile profile) {
        mModel.saveProfile(profile, mAccessToken, new FacebookAccountContract.Model.OnSaveCallback() {
            @Override
            public void onSuccess() {
                mView.updateTextInfo(profile);
                mView.updatePicture(mModel.getProfileImageUrl(profile.getId()));
            }

            @Override
            public void onFailure(Throwable error) {
                mView.showMessage(error.getLocalizedMessage());
            }
        });
    }
}
