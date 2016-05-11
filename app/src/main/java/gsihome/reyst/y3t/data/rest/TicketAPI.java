package gsihome.reyst.y3t.data.rest;

import java.util.List;

import gsihome.reyst.y3t.data.IssueEntity;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TicketAPI {

    @GET("tickets?state=0,9,5,7,8")
    Call<List<IssueEntity>> getInProgress();

    @GET("tickets?state=10,6")
    Call<List<IssueEntity>> getDone();

    @GET("tickets?state=1,3,4")
    Call<List<IssueEntity>> getPending();

    @GET("tickets")
    Call<List<IssueEntity>> getAll();

}
