package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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

public class UpdelViewController implements Initializable {

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private final ObservableList<Barang> listBarang = FXCollections.observableArrayList();
    private final List<Barang> brg = new ArrayList<Barang>();
    private String[] jns = {"Matic", "Sport", "Bebek"};
    private Barang barang = new Barang();
    private String selDrop, key;
    private int idB, unitB;

    Connection conn;
    PreparedStatement stat;
    ResultSet res;
    String sql;

    @FXML
    private Button btn_Clear;

    @FXML
    private Button btn_Search;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_update;

    @FXML
    private TableColumn<Barang, Integer> id;

    @FXML
    private TableColumn<Barang, String> jenis;

    @FXML
    private TableColumn<Barang, String> nama;

    @FXML
    private TableView<Barang> tbl_show;

    @FXML
    private ComboBox<String> txt_jenis;

    @FXML
    private TextField txt_nama;

    @FXML
    private TextField txt_namaID;

    @FXML
    private TextField txt_unit;

    @FXML
    private TextField txtf_Search;

    @FXML
    private TableColumn<Barang, Integer> unit;

    @FXML
    void btnClearOnAction(ActionEvent event) throws SQLException {
        tbl_show.getItems().clear();
        setTable("Default");
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        key = txtf_Search.getText();
        setTable("Nama");
        Barang bg = searchA(key, listBarang, 0, listBarang.size() - 1);
        tbl_show.getItems().clear();
        if (bg != null) {
            tbl_show.getItems().add(0, bg);
            txt_nama.setText(bg.getNama());
            txt_unit.setText(String.valueOf(bg.getUnit()));
            txt_jenis.setPromptText(bg.getJenis());
            txt_jenis.setValue(bg.getJenis());
        } else {
            tbl_show.setPlaceholder(new Label("Item Not Found!!"));
        }
    }

    public Barang searchA(String key, List<Barang> data, int low, int high) {
        int mid = (low + high) / 2;
        while (low <= high) {

            if (data.get(mid).getNama().compareToIgnoreCase(key) == 0) {
                return data.get(mid);
            }
            if (data.get(mid).getNama().compareToIgnoreCase(key) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
            mid = (low + high) / 2;
        }
        return null;
    }

    @FXML
    void btn_DeleteData(ActionEvent event) throws SQLException {
        deleteMember();
        clearInput();
        tbl_show.getSelectionModel().clearSelection();
    }

    public void deleteMember() throws SQLException {
        String sql = "DELETE FROM barang WHERE nama_barang = '" + txt_nama.getText() + "'";
        stat = conn.prepareStatement(sql);
        stat.execute();
        setTable("Default");
    }

    @FXML
    void tableOnMouseClicked(MouseEvent event) {
        Barang brgg = (Barang) tbl_show.getSelectionModel().getSelectedItem();
        try {
            if (brgg == null) {
                System.out.println("data empty");
            } else {

                txt_nama.setText(brgg.getNama());
                txt_namaID.setText(brgg.getNama());
                txt_unit.setText(String.valueOf(brgg.getUnit()));
                txt_jenis.setPromptText(brgg.getJenis());
                txt_jenis.setValue(brgg.getJenis());
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @FXML
    void btn_UpdateData(ActionEvent event) throws SQLException {
        boolean cek = dataCorrectness(event);

        if (cek == true) {
            clearInput();
        } else {
            int unt = Integer.parseInt(txt_unit.getText());
            barang.setNama(txt_namaID.getText());
            barang.setUnit(unt);
            barang.setJenis(txt_jenis.getSelectionModel().getSelectedItem());

            dataUpdate();
            tbl_show.getItems().clear();
            setTable("Default");
        }

    }

    private void dataUpdate() throws SQLException {
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
        if (cekD == false) {
            successInput(2);
        } else {

            unitB += barang.getUnit();
            String sqlU = "UPDATE barang SET nama_barang = '" + txt_nama.getText()
                    + "', unit_barang = '" + txt_unit.getText()
                    + "', jenis_barang = '" + txt_jenis.getSelectionModel().getSelectedItem()
                    + "' WHERE id = '" + idB + "'";
            stat = conn.prepareStatement(sqlU);
            stat.execute();
            System.out.println("Data Berhasil disimpan");
            successInput(1);
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

    private void successInput(int id) {
        try {
            if (id == 1) {
                alert.setTitle("Update Success");
                alert.setHeaderText(null);
                alert.setContentText("Data Updated");
                alert.showAndWait();
                clearInput();
            } else if(id == 2){
                alert.setTitle("Update Failed");
                alert.setHeaderText(null);
                alert.setContentText("Data Not Found");
                alert.showAndWait();
                clearInput();
            } else if(id == 3) {
                alert.setTitle("Delete Success");
                alert.setHeaderText(null);
                alert.setContentText("Data Deleted");
                alert.showAndWait();
                clearInput();
            } else if(id == 4) {
                alert.setTitle("Delete Failed");
                alert.setHeaderText(null);
                alert.setContentText("Data Not Found");
                alert.showAndWait();
                clearInput();
            }

        } catch (Exception e) {
            System.out.println(e);
            clearInput();
        }
    }

    private void clearInput() {
        txt_nama.setText("");
        txt_namaID.setText("");
        txt_unit.setText("");
        txt_jenis.setPromptText("Jenis");
        txt_jenis.setValue("");
    }

    static void quickSortName(List<Barang> a, int lo, int hi) {
        int high = lo, low = hi;
        String pivot = a.get(lo).getNama();
        Barang temp;

        do {
            while (a.get(high).getNama().compareTo(pivot) < 0) {
                high++;
            }
            while (a.get(low).getNama().compareTo(pivot) > 0) {
                low--;
            }
            if (high <= low) {
                temp = a.get(high);
                a.set(high, a.get(low));
                a.set(low, temp);
                high++;
                low--;
            }
        } while (high <= low);

        if (lo < low) {
            quickSortName(a, lo, low);
        }
        if (high < hi) {
            quickSortName(a, high, hi);
        }
    }

    private void setTable(String type) throws SQLException {
        barang.setNama("");
        barang.setUnit(0);
        barang.setJenis("");
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

        brg.clear();
        listBarang.clear();
        brg.addAll(list);

        if (type.equalsIgnoreCase("Nama")) {
            quickSortName(brg, 0, list.size() - 1);
            listBarang.clear();
            listBarang.addAll(brg);
            brg.clear();
            tbl_show.setItems(listBarang);

        } else if (type.equalsIgnoreCase("Default")) {
            listBarang.addAll(list);
            tbl_show.setItems(listBarang);

        }
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
            setTable("Default");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
