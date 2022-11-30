package ui.pantallas.reader;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.utils.Reader;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.time.LocalDate;

public class ReaderListController extends BasePantallaController {

    private ReaderViewModel readerViewModel;
    @FXML
    private TableView<Reader> tablaReaders;
    @FXML
    private TableColumn<Reader, String> id;
    @FXML
    private TableColumn<Reader, String> nameReader;
    @FXML
    private TableColumn<Reader, LocalDate> birthDate;
    @FXML
    private TextField nameTxt;
    @FXML
    private MFXDatePicker datePicker;


    @Inject
    public ReaderListController(ReaderViewModel readerViewModel) {
        this.readerViewModel = readerViewModel;
    }

    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.ID));
        nameReader.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.NAME));
        birthDate.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.DATE_OF_BIRTH));
        readerViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().sacarAlertError(newState.getError());
                readerViewModel.limpiarEstado();
            }
            if (newState.getReaders() != null) {
                tablaReaders.getItems().setAll(newState.getReaders());
            }
        });
        tablaReaders.getSelectionModel().selectedItemProperty().addListener((observableValue, oldNewspaper, newNewspaper) -> {
            if (newNewspaper != null) {
                nameTxt.setText(newNewspaper.getName());
                datePicker.setValue(newNewspaper.getDateOfBirth());
            }
        });
        readerViewModel.getNewspaperList();
    }

    @FXML
    private void addReader() {
        try {
            if (!nameTxt.getText().isEmpty() || !datePicker.getText().isEmpty()) {
                Reader reader = new Reader(nameTxt.getText(), datePicker.getValue());
                readerViewModel.addReader(reader);
            } else {
                this.getPrincipalController().sacarAlertError(ConstantesPantallas.NO_SE_PUEDE_DEJAR_CAMPOS_VACIOS);
            }
        } catch (Exception e) {
            this.getPrincipalController().sacarAlertError(e.getMessage());
        }
    }

    @FXML
    private void updateReader() {
        if (!nameTxt.getText().isEmpty() || datePicker.getValue() != null) {
            Reader readerSelected = tablaReaders.getSelectionModel().getSelectedItem();
            if (readerSelected != null) {
                Reader newspaperUpdate = new Reader(readerSelected.getId(), nameTxt.getText(), datePicker.getValue());
                readerViewModel.updateReader(readerSelected, newspaperUpdate);
            } else {
                this.getPrincipalController().sacarAlertError(ConstantesPantallas.NO_SE_HA_SELECCIONADO_NINGUN_READER);
            }
        } else {
            this.getPrincipalController().sacarAlertError(ConstantesPantallas.NO_SE_PUEDE_DEJAR_CAMPOS_VACIOS);
        }
    }
    @FXML
    private void deleteReader() {
        Reader reader = tablaReaders.getSelectionModel().getSelectedItem();
        if (reader != null) {
            readerViewModel.deleteReader(reader);
        } else {
            this.getPrincipalController().sacarAlertError(ConstantesPantallas.NO_SE_HA_SELECCIONADO_NINGUN_READER);
        }
    }

}
