package com.komputerkit.kasirlaundry.Model;

/**
 * Created by msaifa on 24/01/2018.
 */

public class QDetailOrder {
    String idpegawai,pegawai,alamatpegawai,nohppegawai,idpelanggan,pelanggan,alamat,nohp,saldodeposit,idorder,faktur,tglorder,totalorder
            ,bayar,kembali,ketorder,tglmix, flagtrans,iddetailorder,idproduk,hargaorder,jumlahorder,ketdetailorder,kategori
            ,produk,harga ;

    public QDetailOrder(String idpegawai, String pegawai, String alamatpegawai, String nohppegawai, String idpelanggan, String pelanggan, String alamat, String nohp, String saldodeposit, String idorder, String faktur, String tglorder, String totalorder, String bayar, String kembali, String ketorder, String tglmix, String flagtrans, String iddetailorder, String idproduk, String hargaorder, String jumlahorder, String ketdetailorder, String kategori, String produk, String harga) {
        this.idpegawai = idpegawai;
        this.pegawai = pegawai;
        this.alamatpegawai = alamatpegawai;
        this.nohppegawai = nohppegawai;
        this.idpelanggan = idpelanggan;
        this.pelanggan = pelanggan;
        this.alamat = alamat;
        this.nohp = nohp;
        this.saldodeposit = saldodeposit;
        this.idorder = idorder;
        this.faktur = faktur;
        this.tglorder = tglorder;
        this.totalorder = totalorder;
        this.bayar = bayar;
        this.kembali = kembali;
        this.ketorder = ketorder;
        this.tglmix = tglmix;
        this.flagtrans = flagtrans;
        this.iddetailorder = iddetailorder;
        this.idproduk = idproduk;
        this.hargaorder = hargaorder;
        this.jumlahorder = jumlahorder;
        this.ketdetailorder = ketdetailorder;
        this.kategori = kategori;
        this.produk = produk;
        this.harga = harga;
    }

    public String getIdpegawai() {
        return idpegawai;
    }

    public String getPegawai() {
        return pegawai;
    }

    public String getAlamatpegawai() {
        return alamatpegawai;
    }

    public String getNohppegawai() {
        return nohppegawai;
    }

    public String getIdpelanggan() {
        return idpelanggan;
    }

    public String getPelanggan() {
        return pelanggan;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNohp() {
        return nohp;
    }

    public String getSaldodeposit() {
        return saldodeposit;
    }

    public String getIdorder() {
        return idorder;
    }

    public String getFaktur() {
        return faktur;
    }

    public String getTglorder() {
        return tglorder;
    }

    public String getTotalorder() {
        return totalorder;
    }

    public String getBayar() {
        return bayar;
    }

    public String getKembali() {
        return kembali;
    }

    public String getKetorder() {
        return ketorder;
    }

    public String gettglmix() {
        return tglmix;
    }

    public String getFlagtrans() {
        return flagtrans;
    }

    public String getIddetailorder() {
        return iddetailorder;
    }

    public String getIdproduk() {
        return idproduk;
    }

    public String getHargaorder() {
        return hargaorder;
    }

    public String getJumlahorder() {
        return jumlahorder;
    }

    public String getKetdetailorder() {
        return ketdetailorder;
    }

    public String getKategori() {
        return kategori;
    }

    public String getProduk() {
        return produk;
    }

    public String getHarga() {
        return harga;
    }
}
