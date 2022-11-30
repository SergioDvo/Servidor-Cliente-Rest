package retrofit.llamadas;

import io.reactivex.rxjava3.core.Single;
import modelo.utils.Newspaper;
import modelo.utils.Reader;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface NewspaperApi {

    @GET(ConstantesLlamadasRetrofit.NEWSPAPERS)
    Single<List<Newspaper>> getAllNewspapers();
    @POST(ConstantesLlamadasRetrofit.NEWSPAPERS)
    Single<Newspaper> addNewspaper(@Body Newspaper newspaper);
    @PUT(ConstantesLlamadasRetrofit.NEWSPAPERS)
    Single<Newspaper> updateNewspaper(@Body Newspaper newspaper);

    @DELETE(ConstantesLlamadasRetrofit.NEWSPAPERS_ID)
    Single<Response<Object>> deleteNewspaper(@Path(ConstantesLlamadasRetrofit.ID) int id);
    @GET(ConstantesLlamadasRetrofit.READERS)
    Single<List<Reader>> getAllReaders();
    @POST(ConstantesLlamadasRetrofit.READERS)
    Single<Reader> addReader(@Body Reader reader);
    @PUT(ConstantesLlamadasRetrofit.READERS)
    Single<Reader> updateReader(@Body Reader reader);
    @DELETE(ConstantesLlamadasRetrofit.READERS_ID)
    Single<Response<Object>> deleteReader(@Path(ConstantesLlamadasRetrofit.ID) int id);

}
