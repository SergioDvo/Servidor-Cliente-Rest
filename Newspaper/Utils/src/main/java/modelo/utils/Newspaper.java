package modelo.utils;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class Newspaper {

    private int id;
    private String nameNewspaper;
    private LocalDate releaseDate;

    public Newspaper() {
    }

    public Newspaper(String nameNewspaper, LocalDate releaseDate) {
        this.id = 0;
        this.nameNewspaper = nameNewspaper;
        this.releaseDate = releaseDate;
    }

    public Newspaper(int id, String nameNewspaper, LocalDate releaseDate) {
        this.id = id;
        this.nameNewspaper = nameNewspaper;
        this.releaseDate = releaseDate;
    }
}
