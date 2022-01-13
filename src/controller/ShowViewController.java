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

public class ShowViewController implements Initializable {

    private final ObservableList<Barang> listBarang = FXCollections.observableArrayList();
    private final List<Barang> brg = new ArrayList<Barang>();
    private final String[] dropItem = {"Default", "Nama", "Unit", "Jenis"},
            dropFilter = {"Search", "Filter"},
            dataNama = new String[listBarang.size()];
    private String selDrop, key;

    Connection conn;
    PreparedStatement stat;
    ResultSet res;
    String sql;

    @FXML
    private Button btn_Clear;

    @FXML
    private Button btn_Filter;

    @FXML
    private Button btn_Search;

    @FXML
    private Button btn_Show;

    @FXML
    private ComboBox<String> drb_Filter;

    @FXML
    private TableColumn<Barang, Integer> id;

    @FXML
    private TableColumn<Barang, String> jenis;

    @FXML
    private TableColumn<Barang, String> nama;

    @FXML
    private TextField txtf_Search;

    @FXML
    private TableView<Barang> tbl_show;

    @FXML
    private TableColumn<Barang, Integer> unit;

    @FXML
    private ComboBox<String> drb_FilterType;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        tbl_show.getItems().clear();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        key = txtf_Search.getText();
        setTable("Nama");
        Barang bg = searchA(key, listBarang, 0, listBarang.size() - 1);
        tbl_show.getItems().clear();
        if(bg != null) {
            tbl_show.getItems().add(0, bg);
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
    void filterSelected(ActionEvent event) {
        selDrop = drb_Filter.getSelectionModel().getSelectedItem();
        System.out.println(selDrop);
        if (selDrop.equalsIgnoreCase("Search")) {
            txtf_Search.setVisible(true);
            btn_Search.setVisible(true);
            drb_FilterType.setVisible(false);
        } else if (selDrop.equalsIgnoreCase("Filter")) {
            txtf_Search.setVisible(false);
            btn_Search.setVisible(false);
            drb_FilterType.setVisible(true);
        }
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
//        btn_Clear.setStyle("-fx-background-color: ");
    }

    @FXML
    void typeSelected(ActionEvent event) throws SQLException {
        selDrop = drb_FilterType.getSelectionModel().getSelectedItem();
        System.out.println(selDrop);
        setTable(selDrop);
    }

    @FXML
    void btnShowOnAction(ActionEvent event) throws SQLException {
        tbl_show.getItems().clear();
        setTable("Default");
    }

    static void quickSortJenis(List<Barang> a, int lo, int hi) {
        int high = lo, low = hi;
        String pivot = a.get(lo).getJenis();
        Barang temp;

        do {
            while (a.get(high).getJenis().compareTo(pivot) < 0) {
                high++;
            }
            while (a.get(low).getJenis().compareTo(pivot) > 0) {
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
            quickSortJenis(a, lo, low);
        }
        if (high < hi) {
            quickSortJenis(a, high, hi);
        }
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

    static void quickSortUnit(List<Barang> a, int lo, int hi) {
        int high = lo, low = hi;
        int pivot = a.get(lo).getUnit();
        Barang temp;

        do {
            while (a.get(high).getUnit() < pivot) {
                high++;
            }
            while (a.get(low).getUnit() > pivot) {
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
            quickSortUnit(a, lo, low);
        }
        if (high < hi) {
            quickSortUnit(a, high, hi);
        }
    }

    private void setTable(String type) throws SQLException {
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
        } else if (type.equalsIgnoreCase("Jenis")) {
            quickSortJenis(brg, 0, list.size() - 1);
            listBarang.clear();
            listBarang.addAll(brg);
            brg.clear();
            tbl_show.setItems(listBarang);

        } else if (type.equalsIgnoreCase("Unit")) {
            quickSortUnit(brg, 0, list.size() - 1);
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
        try {
            setTable("Default");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for (String items : dropItem) {
            drb_FilterType.getItems().add(items);
        }
        for (String items : dropFilter) {
            drb_Filter.getItems().add(items);
        }
    }
}
