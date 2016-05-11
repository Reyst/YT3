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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        JsonDeserializer<List<String>> stringListDeserializer = new JsonDeserializer<List<String>>() {
            @Override
            public List<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                List<String> result = new ArrayList<>();

                if (json.isJsonArray()) {
                    for (JsonElement jsonElement : json.getAsJsonArray()) {
                        result.add(jsonElement.getAsString());
                    }
                }

                return result;
            }
        };

        JsonDeserializer<Date> dateDeserializer = new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    long milliseconds = json.getAsLong();
                    if (milliseconds == 0) {
                        throw new ClassCastException();
                    }
                    return new Date(milliseconds);
                } catch (ClassCastException ex) {
                    return null;
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
                .registerTypeAdapter(List.class, stringListDeserializer)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_api_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        sTicketService = retrofit.create(TicketAPI.class);
    }

}
