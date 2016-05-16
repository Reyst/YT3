package gsihome.reyst.y3t.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.mvp.FacebookAccountContract;
import gsihome.reyst.y3t.mvp.presenter.FacebookAccountPresenter;

public class FacebookAccountActivity extends AppCompatActivity
        implements FacebookAccountContract.View {

    @BindView(R.id.logout_btn)
    Button mBtnLogout;

    @BindView(R.id.profile_picture)
    ProfilePictureView mProfilePictureView;

    @BindView(R.id.user_name)
    TextView mTvName;

    private FacebookAccountContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_account);
        ButterKnife.bind(this);

        mPresenter = new FacebookAccountPresenter(this, this);
        mPresenter.onCreate();
    }

    @Override
    public void updateViews(Profile profile) {
        mProfilePictureView.setProfileId(profile.getId());
        mTvName.setText(profile.getName());
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.logout_btn)
    public void onClickBtnLogout(View view) {
        mPresenter.logOut();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
