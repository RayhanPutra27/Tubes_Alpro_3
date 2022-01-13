package model;

public class Barang {
    private String nama, jenis;
    private int unit, id;

    public Barang() {
        this.id = 0;
        this.nama = "";
        this.unit = 0;
        this.jenis = "";
    }
    public Barang(int i, String nm, int un, String jn) {
        id = i;
        nama = nm;
        unit = un;
        jenis = jn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
