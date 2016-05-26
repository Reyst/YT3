package gsihome.reyst.y3t.mvp.model;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import org.json.JSONObject;

import gsihome.reyst.y3t.domain.FBProfile;
import gsihome.reyst.y3t.mvp.FacebookAccountContract;
import gsihome.reyst.y3t.utils.ServiceApiHolder;
import io.realm.Realm;

public class FacebookProfileModel implements FacebookAccountContract.Model {

    private Realm mRealmService;

    public FacebookProfileModel(Context context) {
        mRealmService = ServiceApiHolder.getRealmService(context);
    }

    @Override
    public Profile getCachedProfile(String id) {
        Profile result = null;
        FBProfile cachedProfile = mRealmService.where(FBProfile.class).equalTo("id", id).findFirst();
        if (cachedProfile != null) {
            result = new Profile(cachedProfile.getId(),
                    cachedProfile.getFirstName(),
                    cachedProfile.getMiddleName(),
                    cachedProfile.getLastName(),
                    cachedProfile.getName(),
                    (cachedProfile.getLinkUri() != null) ? Uri.parse(cachedProfile.getLinkUri()) : null
            );
        }
        return result;
    }

    @Override
    public void saveProfile(final Profile profile, final AccessToken token, final OnSaveCallback callback) {

        if (profile == null || profile.getId() == null) {
            callback.onFailure(new NullPointerException("Profile or profile's id is null"));
        } else {

            mRealmService.executeTransactionAsync(
                    new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            FBProfile fbProfile = realm.where(FBProfile.class).equalTo("id", profile.getId()).findFirst();

                            if (fbProfile == null) {
                                fbProfile = realm.createObject(FBProfile.class, profile.getId());
                            }

                            fbProfile.setFirstName(profile.getFirstName());
                            fbProfile.setMiddleName(profile.getMiddleName());
                            fbProfile.setLastName(profile.getLastName());
                            fbProfile.setName(profile.getName());
                            fbProfile.setLinkUri(profile.getLinkUri().toString());
                            fbProfile.setToken(token.getToken());

                            Bundle params = new Bundle();
                            params.putString("fields", "id,picture.type(large)"); // "id,email,gender,cover,picture.type(large)"
                            GraphRequest request = new GraphRequest(token, "me", params, HttpMethod.GET);

                            GraphResponse response = request.executeAndWait();

                            if (response != null) {
                                try {
                                    JSONObject data = response.getJSONObject();
                                    if (data.has("picture")) {
                                        fbProfile.setImageUrl(data.getJSONObject("picture").getJSONObject("data").getString("url"));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    realm.cancelTransaction();
                                }
                            }
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            callback.onSuccess();
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            callback.onFailure(error);
                        }
                    }
            );
        }
    }

    @Override
    public String getProfileImageUrl(String id) {
        FBProfile cachedProfile = mRealmService.where(FBProfile.class).equalTo("id", id).findFirst();
        return (cachedProfile != null) ? cachedProfile.getImageUrl() : null;
    }
}
