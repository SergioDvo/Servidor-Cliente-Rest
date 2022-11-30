package servicios;


import dao.DaoNewspaper;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.utils.Newspaper;


import java.util.List;


public class ServicesNewspaper {

    private final DaoNewspaper daoNewspaper;

    @Inject
    public ServicesNewspaper(DaoNewspaper daoNewspaper) {
        this.daoNewspaper = daoNewspaper;
    }


    public Single<Either<String,List<Newspaper>>> getNewspaperList(){
        return daoNewspaper.getAllNewspapers();
    }
    public Single<Either<String,Newspaper>> addNewspaper(Newspaper newspaper){
        return daoNewspaper.addNewspaper(newspaper);
    }
    public Single<Either<String,Newspaper>> updateNewspaper(Newspaper newspaper){
        return daoNewspaper.updateNewspaper(newspaper);
    }
    public Single<String> deleteNewspaper(Newspaper newspaper){
        return daoNewspaper.deleteNewspaper(newspaper);
    }




}
