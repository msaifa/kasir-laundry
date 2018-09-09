package com.komputerkit.kasirlaundry.Model;

/**
 * Created by msaifa on 24/01/2018.
 */

public class QDeposit {
    String iddeposit,idpelanggan,deposit,tgldeposit,ketdeposit,tglmix,pelanggan,alamat,nohp,saldodeposit ;

    public QDeposit(String iddeposit, String idpelanggan, String deposit, String tgldeposit, String ketdeposit, String tglmix, String pelanggan, String alamat, String nohp, String saldodeposit) {
        this.iddeposit = iddeposit;
        this.idpelanggan = idpelanggan;
        this.deposit = deposit;
        this.tgldeposit = tgldeposit;
        this.ketdeposit = ketdeposit;
        this.tglmix = tglmix;
        this.pelanggan = pelanggan;
        this.alamat = alamat;
        this.nohp = nohp;
        this.saldodeposit = saldodeposit;
    }

    public String getIddeposit() {
        return iddeposit;
    }

    public String getIdpelanggan() {
        return idpelanggan;
    }

    public String getDeposit() {
        return deposit;
    }

    public String getTgldeposit() {
        return tgldeposit;
    }

    public String getKetdeposit() {
        return ketdeposit;
    }

    public String gettglmix() {
        return tglmix;
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
}
