package domain.servicios;

import dao.DaoNewspaper;
import modelo.utils.Newspaper;
import jakarta.inject.Inject;


import java.util.List;

public class ServicesNewspaper {

    private final DaoNewspaper daoNewspaper;

    @Inject
    public ServicesNewspaper(DaoNewspaper daoNewspaper) {
        this.daoNewspaper = daoNewspaper;
    }


    public List<Newspaper> getNewspaperList() {
        return daoNewspaper.getAll();
    }


    public Newspaper addNewspaper(Newspaper newspaper) {
        return daoNewspaper.save(newspaper);
    }

    public void deleteNewspaper(int id) {
        daoNewspaper.delete(id);
    }

    public Newspaper updateNewspaper(Newspaper newspaper) {
        return daoNewspaper.update(newspaper);
    }


}
