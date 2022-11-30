package dao;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.utils.Reader;
import retrofit.llamadas.NewspaperApi;

import java.util.List;

public class DaoReaders extends DaoGenerics {
    private final NewspaperApi newspaperApi;

    @Inject
    public DaoReaders(NewspaperApi newspaperApi, Gson gson) {
        super(gson);
        this.newspaperApi = newspaperApi;
    }
    public Single<Either<String, List<Reader>>> getAllReaders(){
        return safeSingleApiCall(newspaperApi.getAllReaders());
    }
    public Single<Either<String, Reader>> addReader(Reader reader){
        return safeSingleApiCall(newspaperApi.addReader(reader));
    }
    public Single<Either<String, Reader>> updateReader(Reader reader){
        return safeSingleApiCall(newspaperApi.updateReader(reader));
    }
    public Single<String> deleteReader(Reader reader){
        return safeSingleAPICallToDelete(newspaperApi.deleteReader(reader.getId()));
    }
}
