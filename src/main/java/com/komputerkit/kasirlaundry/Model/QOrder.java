package com.komputerkit.kasirlaundry.Model;

/**
 * Created by msaifa on 24/01/2018.
 */

public class QOrder {
    String idpegawai,pegawai,alamatpegawai,nohppegawai,idpelanggan,pelanggan,alamat,nohp,saldodeposit,idorder,faktur,tglorder,totalorder,bayar,
            kembali,ketorder,tglmix,flagtrans;

    public QOrder(String idpegawai, String pegawai, String alamatpegawai, String nohppegawai, String idpelanggan, String pelanggan, String alamat, String nohp, String saldodeposit, String idorder, String faktur, String tglorder, String totalorder, String bayar,
                  String kembali, String ketorder, String tglmix, String flagtrans) {
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
}
