package ui.pantallas.reader;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.utils.Newspaper;
import modelo.utils.Reader;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import servicios.ServiciosReaders;
import ui.pantallas.common.ConstantesPantallas;
import ui.pantallas.newspaper.NewspaperListState;

import java.util.List;

public class ReaderViewModel {
    private final ServiciosReaders serviciosReaders;
    private final ObjectProperty<ReaderState> state;

    @Inject
    public ReaderViewModel(ServiciosReaders serviciosReaders) {
        this.serviciosReaders = serviciosReaders;
        state = new SimpleObjectProperty<>(new ReaderState(null, null));
    }
    public ReadOnlyObjectProperty<ReaderState> getState() {
        return state;
    }
    public void getNewspaperList(){
        serviciosReaders.getReaderList()
                .observeOn(JavaFxScheduler.platform())
                .subscribe(either -> {
                    ReaderState ls = null;
                    if (either.isLeft())
                        ls = new ReaderState(null, either.getLeft());
                    else
                        ls = new ReaderState(either.get(), null);
                    state.setValue(ls);
                });
    }
    public void addReader(Reader reader){
        serviciosReaders.addReader(reader)
                .observeOn(JavaFxScheduler.platform())
                .subscribe(either -> {
                    ReaderState ls = null;
                    if (either.isLeft())
                        ls = new ReaderState(null, either.getLeft());

                    else {
                        List<Reader> readers = state.get().getReaders();
                        readers.add(either.get());
                        ls = new ReaderState(readers, null);
                    }
                    state.setValue(ls);
                });
    }
    public void updateReader(Reader readerSelected, Reader readerUpdated){
        serviciosReaders.updateReader(readerUpdated)
                .observeOn(JavaFxScheduler.platform())
                .subscribe(either -> {
                    ReaderState ls = null;
                    if (either.isLeft())
                        ls = new ReaderState(null, either.getLeft());
                    else {
                        List<Reader> newspapers = state.get().getReaders();
                        newspapers.remove(readerSelected);
                        newspapers.add(readerUpdated);
                        ls = new ReaderState(newspapers, null);
                    }
                    state.setValue(ls);
                });
    }
    public void deleteReader(Reader reader){
        serviciosReaders.deleteReader(reader)
                .observeOn(JavaFxScheduler.platform())
                .subscribe(string -> {
                    if (string.equals(ConstantesPantallas.NO_CONTENT)) {
                        List<Reader> readers = state.get().getReaders();
                        readers.remove(reader);
                        state.setValue(new ReaderState(readers, null));
                    } else {
                        state.setValue(new ReaderState(null, string));
                    }
                });
    }
    public void limpiarEstado() {
        state.set(new ReaderState(null, null));
    }
}
