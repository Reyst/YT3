package gsihome.reyst.y3t.rest;

import java.util.List;

import gsihome.reyst.y3t.domain.IssueEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TicketService {

//    @GET("tickets?state=0,9,5,7,8")
//    Call<List<IssueEntity>> getInProgress();
//
//    @GET("tickets?state=10,6")
//    Call<List<IssueEntity>> getDone();
//
//    @GET("tickets?state=1,3,4")
//    Call<List<IssueEntity>> getPending();

    @GET("tickets")
    Call<List<IssueEntity>> getListByStateFilter(@Query("state") String filter);

    @GET("tickets")
    Call<List<IssueEntity>> getAll();

}
