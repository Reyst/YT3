package gsihome.reyst.y3t.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ProfilePictureView;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.domain.FBProfile;
import gsihome.reyst.y3t.utils.ServiceApiHolder;
import io.realm.Realm;

public class FacebookAccountActivity extends AppCompatActivity {

    @BindView(R.id.logout_btn)
    Button mBtnLogout;

    @BindView(R.id.profile_picture)
    ProfilePictureView mProfilePictureView;

    @BindView(R.id.user_name)
    TextView mTvName;

    private CallbackManager mCallbackManager;

    private boolean mLogined;

    private String mToken;
    private String mProfileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_account);

        ButterKnife.bind(this);

        mLogined = false;

        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                mLogined = true;
                mToken = loginResult.getAccessToken().getToken();
                mProfileId = loginResult.getAccessToken().getUserId();

                Profile profile = Profile.getCurrentProfile();
                if (profile == null) {
                    ProfileTracker tracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            stopTracking();
                            saveProfile(currentProfile);
                            updateViews(currentProfile);
                        }
                    };
                    tracker.startTracking();
                } else {
                    saveProfile(profile);
                    updateViews(profile);
                }
            }

            @Override
            public void onCancel() {
                finish();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("public_profile"));

    }

    private void updateViews(Profile currentProfile) {
        mProfilePictureView.setProfileId(mProfileId);
        mTvName.setText(currentProfile != null ? currentProfile.getName() : "");
    }

    private void saveProfile(Profile profile) {

        Realm realm = ServiceApiHolder.getRealmService(this);

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.logout_btn)
    public void onClickBtnLogout(View view) {
        LoginManager.getInstance().logOut();
        finish();
    }

}
