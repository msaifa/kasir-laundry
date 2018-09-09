package com.komputerkit.kasirlaundry.Model;

/**
 * Created by msaifa on 20/01/2018.
 */

public class TblProduk {
    String idproduk,produk,kategori,harga,jumlah,keterangan,flagAktif ;

    public TblProduk(String idproduk, String produk, String kategori, String harga) {
        this.idproduk = idproduk;
        this.produk = produk;
        this.kategori = kategori;
        this.harga = harga;
    }

    public TblProduk(String idproduk, String produk, String kategori, String harga, String jumlah, String keterangan, String flagAktif) {
        this.idproduk = idproduk;
        this.produk = produk;
        this.kategori = kategori;
        this.harga = harga;
        this.jumlah = jumlah;
        this.keterangan = keterangan ;
        this.flagAktif = flagAktif ;

    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getIdproduk() {
        return idproduk;
    }

    public String getProduk() {
        return produk;
    }

    public String getKategori() {
        return kategori;
    }

    public String getHarga() {
        return harga;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getJumlah() {

        return jumlah;
    }

    public void setFlagAktif(String flagAktif) {
        this.flagAktif = flagAktif;
    }

    public String getFlagAktif() {
        return flagAktif;
    }
}
