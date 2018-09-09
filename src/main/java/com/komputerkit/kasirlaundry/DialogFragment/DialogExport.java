package com.komputerkit.kasirlaundry.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.komputerkit.kasirlaundry.ActivityUtama;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Config;
import com.komputerkit.kasirlaundry.Utilitas.ExportExcel;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.io.IOException;

import jxl.write.WriteException;

/**
 * Created by SAIF on 03/01/2018.
 */

@SuppressLint("ValidFragment")
public class DialogExport extends DialogFragment {

    Context context ;
    ConstraintLayout wBack ;
    View v ;
    Utilitas utilitas ;
    Button btnIya,btnTidak ;
    EditText etPath ;

    String type,query ;

    @SuppressLint("ValidFragment")
    public DialogExport(String type) {
        this.type = type;
    }

    @SuppressLint("ValidFragment")
    public DialogExport(Context context, String type, String query) {
        this.context = context;
        this.type = type;
        this.query = query;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_export,container,false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view ;

        init() ;
        event();
    }

    public void init(){
        btnIya = v.findViewById(R.id.btnIya) ;
        btnTidak = v.findViewById(R.id.btnTidak) ;
        etPath = v.findViewById(R.id.etPath) ;

        etPath.setText("Internal Storage/Download/");
    }

    public void event(){
        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keluar();
            }
        });

        btnIya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((ActivityUtama)getActivity()).permissionStorage()){
                    if (type.equals("LTopup")){
                        lTopup(context.getString(R.string.mnLapTopup)); ;
                    } else if (type.equals("LPencucian")){
                        lPencucian(context.getString(R.string.mnPencucian));
                    } else if (type.equals("LSaldo")){
                        lSaldo(context.getString(R.string.mnLapSaldo));
                    } else if (type.equals("LProduk")){
                        lProduk(context.getString(R.string.mnLapCucian));
                    } else if (type.equals("LPegawai")){
                        lPegawai(context.getString(R.string.mnLapPegawai));
                    } else if (type.equals("LDetailPencucian")){
                        lDetailPencucian(context.getString(R.string.mnLapDetailCucian));
                    }
                    keluar();
                } else {
                    utilitas.toast(getString(R.string.iCekIzin));
                }
            }
        });
    }

    private void lDetailPencucian(String fileName) {
        String[] coba =  {"faktur","tglorder","pegawai","pelanggan","produk","jumlahorder","hargaorder","ketdetailorder"} ;
        String[] coba2 = {"Faktur","Tanggal Pencucian","Pegawai","Pelanggan","Jasa Cucian","Jumlah","Harga","Keterangan Detail Order"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }

    public void lProduk(String fileName){
        String[] coba =  {"produk","kategori","harga"} ;
        String[] coba2 = {"Nama Cucian","Kategori","Harga Cucian"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed  "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }

    public void lPegawai(String fileName){
        String[] coba =  {"pegawai","alamatpegawai","nohppegawai"} ;
        String[] coba2 = {"Nama Pegawai","Alamat Pegawai","No. Hp"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }

    public void lTopup(String fileName){
        String[] coba =  {"tgldeposit","deposit","ketdeposit","pelanggan","saldodeposit"} ;
        String[] coba2 = {"Tanggal Deposit","Jumlah Deposit","Keterangan Deposit","Pelanggan","Saldo Pelanggan"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }

    public void lSaldo(String fileName){
        String[] coba =  {"pelanggan","alamat","nohp","saldodeposit"} ;
        String[] coba2 = {"Nama Pelanggan","Alamat Pelanggan","No. Hp","Saldo Deposit"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }

    public void lPencucian(String fileName){
        String[] coba =  {"faktur","tglorder","pegawai","pelanggan","totalorder","bayar","kembali","ketorder"} ;
        String[] coba2 = {"Faktur","Tanggal Pencucian","Pegawai","Pelanggan","Jumlah Order","Bayar","Kembali","Keterangan Pencucian"} ;

        try {
            new ExportExcel(getActivity(),query, Config.getDefaultPath()+fileName,coba2,coba) ;
        } catch (WriteException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        } catch (IOException e) {
            e.printStackTrace();
            utilitas.toast("Failed "  + getString(R.string.iCekIzin));
        }
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(DialogExport.this).commit() ;
    }
}
