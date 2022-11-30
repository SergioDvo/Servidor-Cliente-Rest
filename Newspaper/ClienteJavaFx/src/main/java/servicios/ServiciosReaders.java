package servicios;

import dao.DaoReaders;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.utils.Reader;

import java.util.List;

public class ServiciosReaders {
    private final DaoReaders daoReaders;

    @Inject
    public ServiciosReaders(DaoReaders daoReaders) {
        this.daoReaders = daoReaders;
    }


    public Single<Either<String, List<Reader>>> getReaderList(){
        return daoReaders.getAllReaders();
    }
    public Single<Either<String,Reader>> addReader(Reader reader){
        return daoReaders.addReader(reader);
    }

    public Single<Either<String,Reader>> updateReader(Reader reader){
        return daoReaders.updateReader(reader);
    }
    public Single<String> deleteReader(Reader reader){
        return daoReaders.deleteReader(reader);
    }
}
