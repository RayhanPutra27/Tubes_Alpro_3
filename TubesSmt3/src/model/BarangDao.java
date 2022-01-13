package model;

import java.util.List;

public interface BarangDao {
    public List<Barang> getAllBarang();
    public Barang getBarang(String nama);
    public void updateBarang(Barang barang);
    public void deleteBarang(Barang barang);

}
