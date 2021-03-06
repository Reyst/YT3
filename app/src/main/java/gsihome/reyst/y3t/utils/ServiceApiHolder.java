package gsihome.reyst.y3t.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import gsihome.reyst.y3t.R;
import gsihome.reyst.y3t.rest.TicketService;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceApiHolder {

    private static DateFormat sFormatter;
    private static Realm sRealm;
    private static TicketService sTicketService;

    public static DateFormat getFormatter(Context context) {
        if (sFormatter == null) {
            sFormatter = new SimpleDateFormat(context.getString(R.string.date_format_pattern),
                    Locale.getDefault());
        }
        return sFormatter;
    }

    public static TicketService getTicketService(Context context) {

        if (sTicketService == null) {
            initTicketAPI(context);
        }

        return sTicketService;
    }

    private static void initTicketAPI(Context context) {
        JsonDeserializer<Date> dateDeserializer = new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    long seconds = json.getAsLong();
                    if (seconds == 0) {
                        throw new ClassCastException();
                    }
                    return new Date(seconds * 1000);
                } catch (ClassCastException ex) {
                    return null;
                }
            }
        };

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, dateDeserializer)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_api_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        sTicketService = retrofit.create(TicketService.class);
    }

    public static Realm getRealmService(Context context) {
        if (sRealm == null || sRealm.isClosed()) {
            RealmConfiguration realmConfig = new RealmConfiguration.Builder(context)
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded() // need Migration
                    .build();
            sRealm = Realm.getInstance(realmConfig);
        }
        return sRealm;
    }


}
