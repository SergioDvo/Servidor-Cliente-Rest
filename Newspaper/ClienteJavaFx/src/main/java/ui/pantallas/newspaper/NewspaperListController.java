package ui.pantallas.newspaper;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.utils.Newspaper;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;


import java.time.LocalDate;

import static java.lang.Integer.parseInt;

public class NewspaperListController extends BasePantallaController {

    private NewspaperViewModel newspaperViewModel;
    @FXML
    private TableView<Newspaper> tablaNewspaper;
    @FXML
    private TableColumn<Newspaper, String> id;
    @FXML
    private TableColumn<Newspaper, String> nameNewspaper;

    @FXML
    private TableColumn<Newspaper, LocalDate> dateNewspaper;
    @FXML
    private TextField nameTxt;
    @FXML
    private MFXDatePicker datePicker;


    @Inject
    public NewspaperListController(NewspaperViewModel newspaperViewModel) {
        this.newspaperViewModel = newspaperViewModel;
    }

    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.ID));
        nameNewspaper.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.NAME_NEWSPAPER));
        dateNewspaper.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.RELEASE_DATE));
        tablaNewspaper.getSelectionModel().selectedItemProperty().addListener((observableValue, oldNewspaper, newNewspaper) -> {
            if (newNewspaper != null) {
                nameTxt.setText(newNewspaper.getNameNewspaper());
                datePicker.setValue(newNewspaper.getReleaseDate());
            }
        });
        newspaperViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().sacarAlertError(newState.getError());
                newspaperViewModel.limpiarEstado();
            }
            if (newState.getNewspapers() != null) {
                tablaNewspaper.getItems().setAll(newState.getNewspapers());
            }
        });
        newspaperViewModel.getNewspaperList();
    }
    @FXML
    private void addNewspaper() {
        if (!nameTxt.getText().isEmpty() || datePicker.getValue() != null) {
            Newspaper newspaper = new Newspaper(nameTxt.getText(), datePicker.getValue());
            newspaperViewModel.addNewspaper(newspaper);
        }else {
            this.getPrincipalController().sacarAlertError(ConstantesPantallas.NO_SE_PUEDE_DEJAR_CAMPOS_VACIOS);
        }
    }
    @FXML
    private void updateNewspaper() {
        if (!nameTxt.getText().isEmpty() || datePicker.getValue() != null) {
                Newspaper newspaperSelected = tablaNewspaper.getSelectionModel().getSelectedItem();
                if (newspaperSelected != null) {
                    Newspaper newspaperUpdate = new Newspaper(newspaperSelected.getId(), nameTxt.getText(), datePicker.getValue());
                    newspaperViewModel.updateNewspaper(newspaperSelected, newspaperUpdate);
                } else {
                    this.getPrincipalController().sacarAlertError(ConstantesPantallas.NO_SE_HA_SELECCIONADO_NINGUN_NEWSPAPER);
                }
        }else {
            this.getPrincipalController().sacarAlertError(ConstantesPantallas.NO_SE_PUEDE_DEJAR_CAMPOS_VACIOS);
        }
    }
    @FXML
    private void deleteNewspaper() {
        Newspaper newspaperSelected = tablaNewspaper.getSelectionModel().getSelectedItem();
        if (newspaperSelected != null) {
            newspaperViewModel.deleteNewspaper(newspaperSelected);
        } else {
            this.getPrincipalController().sacarAlertError(ConstantesPantallas.NO_SE_HA_SELECCIONADO_NINGUN_NEWSPAPER);
        }
    }


}
