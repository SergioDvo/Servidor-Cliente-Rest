module RetrofitPSP {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires com.google.gson;
    requires org.apache.logging.log4j;
    requires jakarta.cdi;
    requires jakarta.inject;
    requires lombok;
    requires retrofit2;
    requires okhttp3;
    requires retrofit2.converter.gson;
    requires io.vavr;
    requires io.reactivex.rxjava3;
    requires org.pdfsam.rxjavafx;
    requires retrofit2.adapter.rxjava3;
    requires Utils;

    exports servicios;
    exports dao;
    exports ui.main to javafx.graphics;
    exports ui.pantallas.principal;
    exports ui.pantallas.common;
    exports config;
    exports ui.pantallas.inicio;
    exports retrofit.di;
    exports retrofit.llamadas;
    exports ui.pantallas.newspaper;
    exports ui.pantallas.reader;


    opens ui.pantallas.principal;
    opens ui.main;
    opens config;
    opens ui.pantallas.inicio to javafx.fxml;
    opens ui.pantallas.newspaper;
    opens ui.pantallas.reader;

}
