package ui.pantallas.newspaper;

import config.ConstantesConfig;
import io.reactivex.rxjava3.core.Scheduler;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.utils.Newspaper;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import servicios.ServicesNewspaper;
import ui.pantallas.common.ConstantesPantallas;

import java.util.List;


public class NewspaperViewModel {

    private final ServicesNewspaper servicesNewspaper;
    private final ObjectProperty<NewspaperListState> state;

    @Inject
    public NewspaperViewModel(ServicesNewspaper servicesNewspaper) {
        this.servicesNewspaper = servicesNewspaper;
        state = new SimpleObjectProperty<>(new NewspaperListState(null, null));
    }
    public ReadOnlyObjectProperty<NewspaperListState> getState() {
        return state;
    }
    public void getNewspaperList(){
        servicesNewspaper.getNewspaperList()
                .observeOn(JavaFxScheduler.platform())
                .subscribe(either -> {
                    NewspaperListState ls = null;
                    if (either.isLeft())
                        ls = new NewspaperListState(null, either.getLeft());
                    else
                        ls = new NewspaperListState(either.get(), null);
                    state.setValue(ls);
                });
    }
    public void addNewspaper(Newspaper newspaper){
        servicesNewspaper.addNewspaper(newspaper)
                .observeOn(JavaFxScheduler.platform())
                .subscribe(either -> {
                    NewspaperListState ls = null;
                    if (either.isLeft())
                        ls = new NewspaperListState(null, either.getLeft());
                    else {
                        List<Newspaper> newspapers = state.get().getNewspapers();
                        newspapers.add(either.get());
                        ls = new NewspaperListState(newspapers, null);
                    }
                    state.setValue(ls);
                });
    }
    public void updateNewspaper(Newspaper newspaperSelected, Newspaper newspaperUpdated){
        servicesNewspaper.updateNewspaper(newspaperUpdated)
                .observeOn(JavaFxScheduler.platform())
                .subscribe(either -> {
                    NewspaperListState ls = null;
                    if (either.isLeft())
                        ls = new NewspaperListState(null, either.getLeft());
                    else {
                        List<Newspaper> newspapers = state.get().getNewspapers();
                        newspapers.remove(newspaperSelected);
                        newspapers.add(newspaperUpdated);
                        ls = new NewspaperListState(newspapers, null);
                    }
                    state.setValue(ls);
                });
    }
    public void deleteNewspaper(Newspaper newspaper){
        servicesNewspaper.deleteNewspaper(newspaper)
                .observeOn(JavaFxScheduler.platform())
                .subscribe(string -> {
                    if (string.equals(ConstantesPantallas.NO_CONTENT)) {
                        List<Newspaper> newspapers = state.get().getNewspapers();
                        newspapers.remove(newspaper);
                        state.setValue(new NewspaperListState(newspapers, null));
                    } else {
                        state.setValue(new NewspaperListState(null, string));
                    }
                });
    }
    public void limpiarEstado() {
        state.set(new NewspaperListState(null, null));
    }

}
