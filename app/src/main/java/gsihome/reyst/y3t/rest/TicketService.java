package gsihome.reyst.y3t.rest;

import java.util.List;

import gsihome.reyst.y3t.domain.IssueEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TicketService {

    @GET("tickets")
    Call<List<IssueEntity>> getListByStateFilter(@Query("state") String filter);

    @GET("tickets")
    Call<List<IssueEntity>> getListByStateFilter(@Query("state") String filter,
                                                 @Query("amount") int amount,
                                                 @Query("offset") int offset);

    @GET("tickets")
    Call<List<IssueEntity>> getAll();

    @GET("tickets")
    Call<List<IssueEntity>> getAll(@Query("amount") int amount, @Query("offset") int offset);

}
