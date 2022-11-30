package retrofit.di;

import com.google.gson.*;
import config.Configuracion;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit.llamadas.NewspaperApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDate;

public class RetrofitProduces {

    @Produces
    @Singleton
    public Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(LocalDate.class,
                (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString())).registerTypeAdapter(LocalDate.class,
                (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())
        ).create();
    }

    @Produces
    @Singleton
    public Retrofit retrofit(Gson gson, Configuracion config) {

        OkHttpClient clientOK = new OkHttpClient.Builder()
                .connectionPool(new okhttp3.ConnectionPool(1, 3, java.util.concurrent.TimeUnit.SECONDS))
                .build();


        return new Retrofit.Builder()
                .baseUrl(config.getUrlBase())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(clientOK)
                .build();
    }


    @Produces
    public NewspaperApi getNewspaperApi(Retrofit retrofit){
        return retrofit.create(NewspaperApi.class);
    }
}
