package com.komputerkit.kasirlaundry.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.komputerkit.kasirlaundry.Adapter.AdapterCardPilih;
import com.komputerkit.kasirlaundry.Adapter.AdapterCart;
import com.komputerkit.kasirlaundry.Adapter.AdapterPilihPelanggan;
import com.komputerkit.kasirlaundry.Fragment.Master.MPegawai;
import com.komputerkit.kasirlaundry.Fragment.TPencucian;
import com.komputerkit.kasirlaundry.Fragment.TTopup;
import com.komputerkit.kasirlaundry.Model.TblPegawai;
import com.komputerkit.kasirlaundry.Model.TblPelanggan;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 20/01/2018.
 */

@SuppressLint("ValidFragment")
public class DCartPilih extends DialogFragment {

    View v ;
    Utilitas utilitas ;
    Database db ;
    public TPencucian tPencucian;
    ConstraintLayout wBack ;
    RecyclerView recyclerView ;
    EditText etCari ;
    String type ;
    FloatingActionButton fabTambah ;

    @SuppressLint("ValidFragment")
    public DCartPilih(TPencucian tPencucian,String type) {
        this.tPencucian = tPencucian ;
        this.type = type ;

        utilitas = new Utilitas(tPencucian.getActivity()) ;
        db = new Database(tPencucian.getActivity()) ;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if (type.equals("pelanggan")){
            loadData("");
            utilitas.setText(v,R.id.tvTitle,"Pilih Pelanggan") ;
        } else {
            utilitas.setText(v,R.id.tvTitle,"Pilih Pegawai") ;
            loadData2("");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_pilih_pelanggan,container,false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view ;
        init() ;
        event() ;
    }

    public void init(){
        etCari = v.findViewById(R.id.etCari) ;
        fabTambah = v.findViewById(R.id.FABTambah) ;
        fabTambah.setVisibility(View.VISIBLE);
        wBack = v.findViewById(R.id.wBack) ;
        recyclerView = v.findViewById(R.id.recMaster) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void event() {
        wBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluar();
            }
        });

        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (type.equals("pelanggan")){
                    loadData(s.toString());
                } else {
                    loadData2(s.toString());
                }
            }
        });

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("pelanggan")){
                    FragmentManager fm = getFragmentManager() ;
                    DPelanggan dProduk = new DPelanggan(DCartPilih.this) ;
                    dProduk.show(fm,"TambahData");
                } else {
                    FragmentManager fm = getFragmentManager() ;
                    DPegawai dProduk = new DPegawai(DCartPilih.this,"pegawai") ;
                    dProduk.show(fm,"TambahData");
                }
            }
        });
    }

    public void loadData(String cari){
        ArrayList<TblPelanggan> array = new ArrayList() ;
        AdapterCardPilih adapter = new AdapterCardPilih(array,this) ;
        recyclerView.setAdapter(adapter);

        String q = "select * from tblpelanggan where pelanggan like '%"+cari+"%' order by pelanggan asc" ;
        Cursor c = db.select(q) ;

        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                array.add(new TblPelanggan(
                        db.getString(c,"idpelanggan"),
                        db.getString(c,"pelanggan"),
                        db.getString(c,"alamat"),
                        db.getString(c,"nohp"),
                        db.getString(c,"saldodeposit")
                )) ;
            }
            adapter.notifyDataSetChanged();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }

    public void loadData2(String cari){
        ArrayList<TblPegawai> array = new ArrayList() ;
        AdapterCardPilih adapter = new AdapterCardPilih(this,array) ;
        recyclerView.setAdapter(adapter);

        String q = "select * from tblpegawai where pegawai like '%"+cari+"%' order by pegawai asc" ;
        Cursor c = db.select(q) ;

        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                array.add(new TblPegawai(
                        db.getString(c,"idpegawai"),
                        db.getString(c,"pegawai"),
                        db.getString(c,"alamatpegawai"),
                        db.getString(c,"nohppegawai")
                )) ;
            }
            adapter.notifyDataSetChanged();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }

    public void pilih(TblPelanggan tblPelanggan){
        tPencucian.setPelangagn(tblPelanggan);
        keluar();
    }

    public void pilih(TblPegawai tblPegawai){
        tPencucian.setPelangagn(tblPegawai);
        keluar();
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(DCartPilih.this).commit() ;
    }

}
