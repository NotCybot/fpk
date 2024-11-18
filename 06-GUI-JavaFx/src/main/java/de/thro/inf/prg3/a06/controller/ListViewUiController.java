package de.thro.inf.prg3.a06.controller;

import de.thro.inf.prg3.a06.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListViewUiController implements Initializable {

    /**
     * Following code is excpected from the UML Diagram
     */

/*

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private HBox HBox4Btns;

    @FXML
    private Label lblAddText;

    @FXML
    private ListView<String> listView;

    @FXML
    private Label titleLbl;

    @FXML
    private VBox vBoxMain;

    @FXML
    private TextField txtAddItem;

    private final ObservableList<Person> masterData = FXCollections.observableArrayList();

    */

    /**
     * Following code is really needed
     */

    @FXML
    private VBox VBoxMain;

    @FXML
    private ListView<Person> listViewBox;

    @FXML
    private TextField txtAddItem;

    @FXML
    private TextField searchField;

    private final ObservableList<Person> masterData = FXCollections.observableArrayList();



    @FXML
    private void addAction(ActionEvent action) {
        // We want to add an Element only if is not empty
        if (!txtAddItem.getText().equals("")) {
            masterData.add(new Person(txtAddItem.getText()));
            System.out.println("Add Button Pushed: " + masterData);
            txtAddItem.clear();
        }
    }


    @FXML
    private void deleteAction(ActionEvent action) {
        int selectedItem = listViewBox.getSelectionModel().getSelectedIndex();
        // if selectedItem is -1 that means no Element is selected, and we will get an Exception.
        if (selectedItem > -1) {
            masterData.remove(selectedItem);
            System.out.println("delete Button Pushed: " + selectedItem);
        }
    }


    @FXML
    private void updateAction (ActionEvent action) {
        // We want to update an Element only if is not empty
        int selectedItem = listViewBox.getSelectionModel().getSelectedIndex();
        if (selectedItem > -1) {
            Person persontoupdate = masterData.get(selectedItem);
            persontoupdate.setName(txtAddItem.getText());
            masterData.set(selectedItem, persontoupdate);
            System.out.println("Update Button Pushed: " + masterData);
        }
    }

    @FXML
    private void searchAction (ActionEvent action) {
        String searchText = searchField.getText();
        if (searchText.isEmpty()) {
            listViewBox.setItems(masterData);
        } else {
            listViewBox.setItems(masterData.filtered(person -> person.getName().startsWith(searchText)));
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewBox.setItems(masterData);

    }

}
