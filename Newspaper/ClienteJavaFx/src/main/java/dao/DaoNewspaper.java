package dao;


import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.utils.Newspaper;
import retrofit.llamadas.NewspaperApi;

import java.util.List;

public class DaoNewspaper extends DaoGenerics {

    private final NewspaperApi newspaperApi;

    @Inject
    public DaoNewspaper(NewspaperApi newspaperApi, Gson gson) {
        super(gson);
        this.newspaperApi = newspaperApi;
    }
    public Single<Either<String, List<Newspaper>>> getAllNewspapers(){
        return safeSingleApiCall(newspaperApi.getAllNewspapers());
    }
    public Single<Either<String, Newspaper>> addNewspaper(Newspaper newspaper){
        return safeSingleApiCall(newspaperApi.addNewspaper(newspaper));
    }
    public Single<Either<String, Newspaper>> updateNewspaper(Newspaper newspaper){
        return safeSingleApiCall(newspaperApi.updateNewspaper(newspaper));
    }
    public Single<String> deleteNewspaper(Newspaper newspaper){
        return safeSingleAPICallToDelete(newspaperApi.deleteNewspaper(newspaper.getId()));
    }
}
