package ui.pantallas.newspaper;

import lombok.Data;
import lombok.Getter;
import modelo.utils.Newspaper;

import java.util.List;
@Getter
public class NewspaperListState {
    private final List<Newspaper> newspapers;
    private final String error;


    public NewspaperListState(List<Newspaper> newspapers, String error) {
        this.newspapers = newspapers;
        this.error = error;
    }
}
