package com.cadenza.bottomnavigation.model;

import java.io.Serializable;
public class PemesananModel {
    private String id;
    private String idPaket;
    private String namaPaket;
    private String keberangkatan;
    private String pilihanPaket;
    private String harga;
    private String lamaWaktu;

    public PemesananModel(String id, String idPaket, String namaPaket, String keberangkatan, String pilihanPaket, String harga, String lamaWaktu) {
        this.id = id;
        this.idPaket = idPaket;
        this.namaPaket = namaPaket;
        this.keberangkatan = keberangkatan;
        this.pilihanPaket = pilihanPaket;
        this.harga = harga;
        this.lamaWaktu = lamaWaktu;
    }

    // Tambahkan getter sesuai kebutuhan



    public String getId() {
        return id;
    }

    public String getIdPaket() {
        return idPaket;
    }


    public String getNamaPaket() {
        return namaPaket;
    }

    public String getKeberangkatan() {
        return keberangkatan;
    }

    public String getPilihanPaket() {
        return pilihanPaket;
    }

    public String getHarga() {
        return harga;
    }

    public String getLamaWaktu() {
        return lamaWaktu;
    }
}
