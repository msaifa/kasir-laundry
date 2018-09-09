package com.komputerkit.kasirlaundry.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.komputerkit.kasirlaundry.ActivityUtama;
import com.komputerkit.kasirlaundry.DialogFragment.DPilihPelanggan;
import com.komputerkit.kasirlaundry.Model.TblPelanggan;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Config;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.InApp;
import com.komputerkit.kasirlaundry.Utilitas.MyApp;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

/**
 * Created by msaifa on 17/01/2018.
 */

public class TTopup extends Fragment {

    View v ;
    ConstraintLayout wPelanggan,wTanggal,wSimpan ;
    Utilitas utilitas ;
    Database db ;
    TblPelanggan tblPelanggan ;
    EditText etJumlah ;

    @Override
    public void onStart() {
        super.onStart();

        utilitas = new Utilitas(getActivity()) ;
        db = new Database(getActivity()) ;
        pilihPelanggan();
        utilitas.setText(v,R.id.etTanggal,utilitas.getDate(getString(R.string.typeDate))) ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topup,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;
        init() ;
        event() ;
    }

    public void init(){
        wPelanggan = v.findViewById(R.id.wPelanggan) ;
        wTanggal = v.findViewById(R.id.btnTanggal) ;
        wSimpan = v.findViewById(R.id.wSimpan) ;
        etJumlah = v.findViewById(R.id.etJumlah) ;
    }

    public void event(){
        wPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihPelanggan();
            }
        });

        wTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilitas.showDateDialog(v,R.id.etTanggal);
            }
        });

        etJumlah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etJumlah.removeTextChangedListener(this);
                try{
                    String temp = s.toString().replace(".","") ;
                    String nf = utilitas.numberFormat(temp) ;
                    etJumlah.setText(nf) ;
                    etJumlah.setSelection(nf.length());
                }catch (Exception e){

                }
                etJumlah.addTextChangedListener(this);
            }
        });

        wSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApp myApp = new MyApp(utilitas,db) ;

                String jum = utilitas.getText(v,R.id.etJumlah).replace(".","") ;
                if (utilitas.isEmpty(v,R.id.etJumlah)){
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iFillForm));
                } else if (utilitas.strToDouble(jum) < 1){
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iLebihDariNol));
                } else if (myApp.cekTrans("tbldeposit")){
                    ((ActivityUtama)getActivity()).bayar();
                }else {
                    simpan() ;
                }
            }
        });
    }

    public void pilihPelanggan(){
        DPilihPelanggan dPilihPelanggan = new DPilihPelanggan(this) ;
        FragmentManager fm = getFragmentManager() ;
        dPilihPelanggan.show(fm,"Pilih Pelanggan");
    }

    public void setPelangagn(TblPelanggan tblPelanggan){
        this.tblPelanggan = tblPelanggan ;
        utilitas.setText(v,R.id.tvPelanggan,tblPelanggan.getPelanggan()) ;
        utilitas.setText(v,R.id.tvKet,utilitas.removeE(tblPelanggan.getSaldodeposit())) ;
    }

    public void simpan(){
        String jumlah = utilitas.getText(v,R.id.etJumlah).replace(".","");
        String date = utilitas.getText(v,R.id.etTanggal) ;
        String tanggal = utilitas.getYear(date) + utilitas.getMonth(date) + utilitas.getDay(date) ;
        String ket = utilitas.getText(v,R.id.etKeterangan) ;

        String q = "insert into tbldeposit (idpelanggan,deposit,tgldeposit,ketdeposit,tglmix) values (" +
                "'"+tblPelanggan.getIdpelanggan()+"'," +
                "'"+jumlah+"'," +
                "'"+date+"'," +
                "'"+ket+"'," +
                "'"+tanggal+"'" +
                ")" ;

        if (db.execution(q)){
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iSuccessTrans));

            String jum = utilitas.doubleToStr(utilitas.strToDouble(tblPelanggan.getSaldodeposit()) + utilitas.strToDouble(jumlah));
            tblPelanggan.setSaldodeposit(jum);
            utilitas.setText(v,R.id.tvKet,utilitas.removeE(jum)) ;
            utilitas.setText(v,R.id.etJumlah,"") ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iFailTrans));
        }
    }

}
