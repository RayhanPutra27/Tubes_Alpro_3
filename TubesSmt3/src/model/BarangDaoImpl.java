package model;

import java.util.ArrayList;
import java.util.List;

public class BarangDaoImpl implements BarangDao {
    List<Barang> brg = new ArrayList<>();;

    public BarangDaoImpl() {
        Barang brg1 = new Barang(1, "asd", 12, "qwe");
        Barang brg2 = new Barang(2, "asdf", 123, "qwer");
        brg.add(brg1);
        brg.add(brg2);
    }

    @Override
    public List<Barang> getAllBarang() {
        return brg;
    }

    @Override
    public Barang getBarang(String nama) {
        return null;
    }

    @Override
    public void updateBarang(Barang barang) {

    }

    @Override
    public void deleteBarang(Barang barang) {
        brg.remove(barang.getNama());
    }
}
