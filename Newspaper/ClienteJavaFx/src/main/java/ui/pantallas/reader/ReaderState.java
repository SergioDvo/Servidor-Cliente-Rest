package ui.pantallas.reader;


import lombok.Data;
import lombok.Getter;
import modelo.utils.Reader;

import java.util.List;
@Getter
public class ReaderState {
    private final List<Reader> readers;
    private final String error;

    public ReaderState(List<Reader> readers, String error) {
        this.readers = readers;
        this.error = error;

    }
}
