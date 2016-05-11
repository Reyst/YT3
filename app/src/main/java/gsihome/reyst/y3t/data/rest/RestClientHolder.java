package gsihome.reyst.y3t.data.rest;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.data.State;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClientHolder {

    private static TicketAPI sTicketService;

    public static TicketAPI getService(Context context) {

        if (sTicketService == null) {
            initService(context);
        }

        return sTicketService;
    }

    private static void initService(Context context) {

        JsonDeserializer<Date> dateDeserializer = new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return new Date(json.getAsLong());
                } catch (Exception ex) {
                    Log.d("ERROR Date", ex.getLocalizedMessage());
                    return new Date(0);
                }
            }
        };

        JsonDeserializer<State> stateDeserializer = new JsonDeserializer<State>() {
            @Override
            public State deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                int stateId = ((JsonObject) json).get("id").getAsInt();

                int stateValue;

                switch (stateId) {
                    case 0:
                    case 9:
                    case 5:
                    case 7:
                    case 8:
                        stateValue = 1;
                        break;
                    case 10:
                    case 6:
                        stateValue = 9;
                        break;
                    default:
                        stateValue = 0;
                        break;
                }

                return State.getByValue(stateValue);
            }
        };

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, dateDeserializer)
                .registerTypeAdapter(State.class, stateDeserializer)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_api_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        sTicketService = retrofit.create(TicketAPI.class);
    }

}
