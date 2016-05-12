package gsihome.reyst.y3t.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class IssueEntity implements RealmModel {

    @PrimaryKey
    @SerializedName("id")
    private long id;

    @SerializedName("user")
    private User user;

    @SerializedName("address")
    private Address address;

    @SerializedName("geo_address")
    private GeoAddress geoAddress;

    @SerializedName("category")
    private Category category;

    @SerializedName("type")
    private Type type;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    @SerializedName("created_date")
    private Date createdDate;

    @SerializedName("start_date")
    private Date startDate;

    @SerializedName("state")
    private State state;

    @SerializedName("ticket_id")
    private String ticketId;

    @SerializedName("files")
    private RealmList<File> files;

    @SerializedName("performers")
    private RealmList<Performer> performers;

    @SerializedName("deadline")
    private Date deadline;

    @SerializedName("likes_counter")
    private int likesCounter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public GeoAddress getGeoAddress() {
        return geoAddress;
    }

    public void setGeoAddress(GeoAddress geoAddress) {
        this.geoAddress = geoAddress;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public RealmList<File> getFiles() {
        return files;
    }

    public void setFiles(RealmList<File> files) {
        this.files = files;
    }

    public RealmList<Performer> getPerformers() {
        return performers;
    }

    public void setPerformers(RealmList<Performer> performers) {
        this.performers = performers;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getLikesCounter() {
        return likesCounter;
    }

    public void setLikesCounter(int likesCounter) {
        this.likesCounter = likesCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueEntity that = (IssueEntity) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public int getDaysAmount() {
        int result = -1;
        Date now = new Date();
        if (createdDate != null) {
            result = (int) ((now.getTime() - createdDate.getTime()) / (1000 * 24 * 3600));
        }
        return result;
    }
}
