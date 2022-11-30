package ui.pantallas.common;

public enum Pantallas {


    PANTALLAINICIO("/fxml/inicio.fxml"),
    PANTALLANEWS("/fxml/newspapers.fxml"),
    PANTALLAREADERS("/fxml/readers.fxml");


    private String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }


}
