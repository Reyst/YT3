package gsihome.reyst.y3t.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;

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
    ImageView mProfilePictureView;

    @BindView(R.id.user_name)
    TextView mTvName;

    @BindView(R.id.first_name)
    TextView mTvFirstName;

    @BindView(R.id.middle_name)
    TextView mTvMiddleName;

    @BindView(R.id.last_name)
    TextView mTvLastName;

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
    public void updateTextInfo(Profile profile) {

        if (profile != null) {

            mTvName.setText(profile.getName());

            if (!TextUtils.isEmpty(profile.getFirstName())) {
                mTvFirstName.setText(profile.getFirstName());
            }

            if (!TextUtils.isEmpty(profile.getMiddleName())) {
                mTvMiddleName.setText(profile.getMiddleName());
            }

            if (!TextUtils.isEmpty(profile.getLastName())) {
                mTvLastName.append(profile.getLastName());
            }
        }
    }

    @Override
    public void updatePicture(String url) {

        if (!TextUtils.isEmpty(url)) {
            Picasso.with(this)
                    .load(url)
                    .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                    .into(mProfilePictureView);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.logout_btn)
    public void onClickBtnLogout() {
        mPresenter.logOut();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
