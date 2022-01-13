package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Barang;
import model.Config;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InsertViewController implements Initializable {

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private ObservableList<Barang> listBarang = FXCollections.observableArrayList();
    private List<Barang> brg = new ArrayList<Barang>();
    private String[] jns = {"Matic", "Sport", "Bebek"};
    private Barang barang = new Barang();
    private int idB, unitB;

    Connection conn;
    PreparedStatement stat;
    ResultSet res;
    String sql;


    @FXML
    private TableColumn<Barang, Integer> id;

    @FXML
    private TableColumn<Barang, String> jenis;

    @FXML
    private TableColumn<Barang, String> nama;

    @FXML
    private TableColumn<Barang, Integer> unit;

    @FXML
    private Button btn_insert;

    @FXML
    private TableView<Barang> tbl_show;

    @FXML
    private TextField txt_unit;

    @FXML
    private ComboBox<String> txt_jenis;

    @FXML
    private TextField txt_nama;

    @FXML
    public void btn_InsertData(ActionEvent event) throws SQLException {
//        writeData();
        boolean cek = dataCorrectness(event);

        if (cek == true) {
            clearInput();
        } else {
            int unt = Integer.parseInt(txt_unit.getText());
            barang.setNama(txt_nama.getText());
            barang.setUnit(unt);
            barang.setJenis(txt_jenis.getSelectionModel().getSelectedItem());

            writeData();
            tbl_show.getItems().clear();
            setTable();
//            System.out.println(barang.getNama());
//            System.out.println(barang.getUnit());
//            System.out.println(barang.getJenis());
        }


    }

    public void writeData() throws SQLException {
        conn = Config.getConnection();
        boolean cekD = false;
        for (int i = 0; i < listBarang.size(); i++) {
            if (barang.getNama().equalsIgnoreCase(listBarang.get(i).getNama())) {
                idB = listBarang.get(i).getId();
                unitB = listBarang.get(i).getUnit();
                cekD = true;
                break;
            }
        }
        unitB += barang.getUnit();

        if (cekD == true) {
            String sqlU = "UPDATE barang SET unit_barang = '" + unitB
                    + "' WHERE id = '" + idB + "'";
            stat = conn.prepareStatement(sqlU);
            stat.execute();
            successInput();

        } else {
            String sqlI = "INSERT INTO barang(nama_barang, unit_barang, jenis_barang) VALUES ('"
                    + barang.getNama() + "','"
                    + barang.getUnit() + "','"
                    + barang.getJenis() + "')";
            stat = conn.prepareStatement(sqlI);
            stat.execute();
            successInput();
//            System.out.println("Data Berhasil disimpan");
        }
    }

    private boolean dataCorrectness(ActionEvent event) {
        try {
            if (txt_unit.getText().matches("[0-9]*")) {
                if (txt_unit.getText().length() > 3) {
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Panjang data tidak boleh lebih dari 3");
                    alert.showAndWait();
                    clearInput();
                    return true;
                }

            } else {
                alert.setTitle("Input Error");
                alert.setContentText("Input harus berupa angka!!");
                alert.setHeaderText(null);
                alert.showAndWait();
                clearInput();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            clearInput();
        }
        return false;
    }

    private void successInput() {
        try {
            alert.setTitle("Input Success");
            alert.setHeaderText(null);
            alert.setContentText("Data Berhasil di Inputkan");
            alert.showAndWait();
            clearInput();

        } catch (Exception e) {
            System.out.println(e);
            clearInput();
        }
    }

    private void clearInput() {
        txt_nama.setText("");
        txt_unit.setText("");
        txt_jenis.setPromptText("Jenis");
        txt_jenis.setValue("");
    }

    private void setTable() throws SQLException {
        conn = Config.getConnection();
        ObservableList<Barang> list = readData(conn);

        id.setCellValueFactory(new PropertyValueFactory<Barang, Integer>("id"));
        id.prefWidthProperty().bind(tbl_show.widthProperty().multiply(0.25));

        nama.setCellValueFactory(new PropertyValueFactory<Barang, String>("nama"));
        nama.prefWidthProperty().bind(tbl_show.widthProperty().multiply(0.25));

        unit.setCellValueFactory(new PropertyValueFactory<Barang, Integer>("unit"));
        unit.prefWidthProperty().bind(tbl_show.widthProperty().multiply(0.25));

        jenis.setCellValueFactory(new PropertyValueFactory<Barang, String>("jenis"));
        jenis.prefWidthProperty().bind(tbl_show.widthProperty().multiply(0.25));

        listBarang.addAll(list);
        tbl_show.setItems(listBarang);
    }

    public ObservableList<Barang> readData(Connection connection) throws SQLException {
        String sql = "SELECT * FROM barang";

        stat = connection.prepareStatement(sql);
        res = stat.executeQuery();
        ObservableList<Barang> allData = FXCollections.observableArrayList();

        while (res.next()) {
            Barang member = new Barang(
                    res.getInt("id"),
                    res.getString("nama_barang"),
                    res.getInt("unit_barang"),
                    res.getString("jenis_barang"));
            allData.add(member);
        }
        return allData;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        conn = Config.getConnection();
        for (String type : jns) {
            txt_jenis.getItems().add(type);
        }
        try {
            setTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
