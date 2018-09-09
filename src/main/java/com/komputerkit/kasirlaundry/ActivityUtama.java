package com.komputerkit.kasirlaundry;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.komputerkit.kasirlaundry.DialogFragment.DCariPrinter;
import com.komputerkit.kasirlaundry.DialogFragment.DPrinter;
import com.komputerkit.kasirlaundry.Fragment.About;
import com.komputerkit.kasirlaundry.Fragment.Beranda;
import com.komputerkit.kasirlaundry.Fragment.Laporan.LDetailPencucian;
import com.komputerkit.kasirlaundry.Fragment.Laporan.LPegawai;
import com.komputerkit.kasirlaundry.Fragment.Laporan.LPencucian;
import com.komputerkit.kasirlaundry.Fragment.Laporan.LProduk;
import com.komputerkit.kasirlaundry.Fragment.Laporan.LSaldo;
import com.komputerkit.kasirlaundry.Fragment.Laporan.LTopup;
import com.komputerkit.kasirlaundry.Fragment.Master.MPegawai;
import com.komputerkit.kasirlaundry.Fragment.Master.MPelanggan;
import com.komputerkit.kasirlaundry.Fragment.Master.MProduk;
import com.komputerkit.kasirlaundry.Fragment.TPencucian;
import com.komputerkit.kasirlaundry.Fragment.TPilihProduk;
import com.komputerkit.kasirlaundry.Fragment.TPilihProses;
import com.komputerkit.kasirlaundry.Fragment.TProses;
import com.komputerkit.kasirlaundry.Fragment.TTopup;
import com.komputerkit.kasirlaundry.Fragment.Utilitas.UBackupDB;
import com.komputerkit.kasirlaundry.Fragment.Utilitas.UBeliFitur;
import com.komputerkit.kasirlaundry.Fragment.Utilitas.UIdentitas;
import com.komputerkit.kasirlaundry.Fragment.Utilitas.UResetData;
import com.komputerkit.kasirlaundry.Fragment.Utilitas.URestoreDB;
import com.komputerkit.kasirlaundry.Model.QOrder;
import com.komputerkit.kasirlaundry.Model.TblProduk;
import com.komputerkit.kasirlaundry.Other.DrawerActivityUtama;
import com.komputerkit.kasirlaundry.Utilitas.Config;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.MyApp;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

public class ActivityUtama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BillingProcessor.IBillingHandler{

    NavigationView navigationView ;
    DrawerActivityUtama menu ;
    boolean posisi,isPay ;
    FragmentTransaction fragmentTransaction ;
    String type = "" ;
    BillingProcessor bp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);

        init() ;
        pindah(getString(R.string.mnBeranda));
        permissionStorage();
    }

    @Override
    public void onBackPressed() {

        if (posisi){
            if (type.equals("")){
                pindah(getString(R.string.mnBeranda));
                posisi = false ;
            } else {
                pindah(type) ;
            }
        } else {
            Utilitas utilitas = new Utilitas(this) ;
            utilitas.showDialog(getString(R.string.iKeluar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity();
                    } else {
                        finish();
                    }
                }
            });
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void init(){
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menu = new DrawerActivityUtama(this,navigationView) ;

        menu.getExpListView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (menu.getExpListView().isGroupExpanded(groupPosition)) {
                    menu.getExpListView().collapseGroupWithAnimation(groupPosition);
                } else {
                    menu.getExpListView().expandGroupWithAnimation(groupPosition);
                }
                TextView item = v.findViewById(R.id.textTitle) ;
                pindah(item.getText().toString()) ;
                return true;
            }

        });

        menu.getExpListView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                TextView item = view.findViewById(R.id.textTitle) ;
                pindah(item.getText().toString()) ;
                return true;
            }
        });

        bp = new BillingProcessor(this, Config.getBASE64ENCODE(),this) ;

        notifPay();
    }

    public void notifPay(){
        Utilitas utilitas = new Utilitas(this) ;
        Database db = new Database(this) ;

        if (db.getCount("select * from tblproduk") == 0){
            db.execution("insert into tblproduk (produk,harga,kategori) values ('Cuci Kering','3000','Kiloan')") ;
        }

        if (db.getCount("select * from tblpegawai") == 0){
            db.execution("insert into tblpegawai (pegawai,alamatpegawai,nohppegawai) values ('Pegawai','Indonesia','62')") ;
        }

        if (db.getCount("select * from tblpelanggan") == 0){
            db.execution("insert into tblpelanggan (pelanggan,alamat,nohp,saldodeposit) values ('Komputerkit','Sidoarjo','083832032077','0')") ;
        }

        if (!utilitas.getSession(Config.getIDFullVersion(),false)){
            utilitas.showAlert(getString(R.string.iNotifPay), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
    }

    public void setJudul(String judul){
        Utilitas.setText(this.findViewById(android.R.id.content),R.id.tvTitle,judul) ;
    }

    public void pencucian(ArrayList<TblProduk> data){
        fragmentTransaction = getFragmentManager().beginTransaction() ;
        TPencucian mProduk = new TPencucian(data) ;
        fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
        setJudul(getString(R.string.mnPencucian));
    }

    public void proses(QOrder faktur){
        type = getString(R.string.mnProsesCuci) ;
        fragmentTransaction = getFragmentManager().beginTransaction() ;
        TProses mProduk = new TProses(faktur) ;
        fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
        setJudul(getString(R.string.mnProsesCuci));
    }

    public void pindah(String tujuan) {
        boolean select = false ;
        posisi = true ;
        type = "" ;
        fragmentTransaction = getFragmentManager().beginTransaction() ;

        if (tujuan.equals(getString(R.string.mnProduk))){

            MProduk mProduk = new MProduk() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnPelanggan))){

            MPelanggan mProduk = new MPelanggan() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnPegawai))){

            MPegawai mProduk = new MPegawai() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnTopup))){

            TTopup mProduk = new TTopup() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan);
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnPencucian))){

            TPilihProduk mProduk = new TPilihProduk() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(getString(R.string.cPilihProduk));
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapSaldo))){

            LSaldo mProduk = new LSaldo() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapPencucian))){

            LPencucian mProduk = new LPencucian() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapTopup))){

            LTopup mProduk = new LTopup() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnBackup))){

            UBackupDB mProduk = new UBackupDB() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnRestore))){

            URestoreDB mProduk = new URestoreDB() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnReset))){

            UResetData mProduk = new UResetData() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnIdentitas))){

            UIdentitas mProduk = new UIdentitas() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnBeliFitur))){

            UBeliFitur mProduk = new UBeliFitur() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnBeranda))){

            Beranda mProduk = new Beranda() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;
            posisi = false ;

        } else if (tujuan.equals(getString(R.string.mnProsesCuci))){

            TPilihProses mProduk = new TPilihProses() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapPegawai))){

            LPegawai mProduk = new LPegawai() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapCucian))){

            LProduk mProduk = new LProduk() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnAbout))){

            About mProduk = new About() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnLapDetailCucian))){

            LDetailPencucian mProduk = new LDetailPencucian() ;
            fragmentTransaction.replace(R.id.wFragment,mProduk).commit() ;
            setJudul(tujuan) ;
            select = true ;

        } else if (tujuan.equals(getString(R.string.mnGuide))){

            Utilitas utilitas = new Utilitas(this) ;
            utilitas.showAlert("Silahkan tekan OK untuk mengunjunngi cara penggunaan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("https://goo.gl/3WsnXs")) ;
                    startActivity(i);
                }
            });

        }

        if(select){
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void openDrawer(View v){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }

    public void bayar(){
        bp.purchase(this,Config.getIDFullVersion()) ;
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Utilitas utilitas = new Utilitas(this) ;
        utilitas.setSession(Config.getIDFullVersion(),true) ;
        utilitas.toast(getString(R.string.iPaySuccess));
    }

    @Override
    public void onPurchaseHistoryRestored() {
        Utilitas utilitas = new Utilitas(this) ;
        utilitas.setSession(Config.getIDFullVersion(),false) ;
    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Utilitas utilitas = new Utilitas(this) ;
        utilitas.setSession(Config.getIDFullVersion(),false) ;
    }

    @Override
    public void onBillingInitialized() {

    }

    public boolean permissionStorage(){
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE ;
        int requestCode = 0x3 ;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,permission)) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }
            return false ;
        } else {
            return true ;
        }
    }
}
