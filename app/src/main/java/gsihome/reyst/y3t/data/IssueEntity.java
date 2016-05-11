package gsihome.reyst.y3t.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gsihome.reyst.y3t.R;

public class IssueEntity implements Serializable {

    @SerializedName("id")
    private long _ID;

    @SerializedName("ticket_id")
    private String mNumber;

    @SerializedName("title")
    private String mCategory;

    @SerializedName("state")
    private State mState;

    @SerializedName("created_date")
    private Date mCreated;
    @SerializedName("start_date")
    private Date mRegistered;
    @SerializedName("deadline")
    private Date mDeadline;

    private String mResponsible;

    @SerializedName("body")
    private String mFullText;

    @SerializedName("files")
    private List<String> mImages;

    private int mIconId = R.drawable.building_and_upgrade; // todo

    @SerializedName("likes_counter")
    private int mLikeAmount;


    public IssueEntity() {
        mImages = new ArrayList<>();
    }

    public IssueEntity(long id, String number, String category, State state,
                       Date created, Date registered, Date deadline, String responsible,
                       int iconId, int likeAmount,
                       String fullText,
                       List<String> images) {
        this._ID = id;
        this.mNumber = number;
        this.mCategory = category;
        this.mState = state;
        this.mCreated = created;
        this.mRegistered = registered;
        this.mDeadline = deadline;
        this.mResponsible = responsible;
        this.mIconId = iconId;
        this.mLikeAmount = likeAmount;

        mImages = images;

        this.mFullText = fullText;
    }


    public long getID() {
        return _ID;
    }

    public void setID(long id) {
        this._ID = id;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        this.mNumber = number;
    }

    public String getFullText() {
        return mFullText;
    }

    public void setFullText(String fullText) {
        mFullText = fullText;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        this.mState = state;
    }

    public Date getCreated() {
        return mCreated;
    }

    public void setCreated(Date created) {
        this.mCreated = created;
    }

    public Date getRegistered() {
        return mRegistered;
    }

    public void setRegistered(Date registered) {
        this.mRegistered = registered;
    }

    public Date getDeadline() {
        return mDeadline;
    }

    public void setDeadline(Date deadline) {
        this.mDeadline = deadline;
    }

    public String getResponsible() {
        return mResponsible;
    }

    public void setResponsible(String responsible) {
        this.mResponsible = responsible;
    }

    public int getIconId() {
        return mIconId;
    }

    public void setIconId(int iconId) {
        this.mIconId = iconId;
    }

    public int getLikeAmount() {
        return mLikeAmount;
    }

    public void setLikeAmount(int likeAmount) {
        this.mLikeAmount = likeAmount;
    }

    public List<String> getImages() {
        return mImages;
    }

    public void setImages(List<String> images) {
        this.mImages = images;
    }

    public int getDaysAmount() {
        Date now = new Date();
        return (int) ((now.getTime() - mCreated.getTime()) / (1000 * 24 * 3600));
    }
}
