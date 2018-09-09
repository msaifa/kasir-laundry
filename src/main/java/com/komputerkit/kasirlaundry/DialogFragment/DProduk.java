package com.komputerkit.kasirlaundry.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.komputerkit.kasirlaundry.Fragment.Master.MProduk;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.Arrays;

/**
 * Created by msaifa on 19/01/2018.
 */

@SuppressLint("ValidFragment")
public class DProduk extends DialogFragment {

    View v ;
    String id ;
    MProduk mProduk ;
    Utilitas utilitas ;
    Database db ;
    boolean tambah ;
    ConstraintLayout wSimpan, wBack ;
    EditText etharga, etProduk ;
    Spinner spKategori ;
    TextView tvTitle ;

    @SuppressLint("ValidFragment")
    public DProduk(MProduk mProduk,String id ) {
        this.id = id;
        this.mProduk = mProduk;

        utilitas = new Utilitas(mProduk.getActivity()) ;
        db = new Database(mProduk.getActivity()) ;
        tambah = false ;
    }

    @SuppressLint("ValidFragment")
    public DProduk(MProduk mProduk) {
        this.mProduk = mProduk;

        utilitas = new Utilitas(mProduk.getActivity()) ;
        db = new Database(mProduk.getActivity()) ;
        tambah = true ;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if(!tambah){
            setText();
            tvTitle.setText(getString(R.string.cUbahPelanggan));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_produk,container,false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view ;
        init() ;
        event() ;
    }

    public void init(){
        wSimpan = v.findViewById(R.id.wSimpan) ;
        wBack = v.findViewById(R.id.wBack) ;
        etharga= v.findViewById(R.id.etharga) ;
        etProduk = v.findViewById(R.id.etProduk) ;
        spKategori = v.findViewById(R.id.spKategori) ;
        tvTitle = v.findViewById(R.id.tvTitle) ;
    }

    public void event(){
        wSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double harga = utilitas.strToDouble(etharga.getText().toString().replace(".","")) ;
                if (etProduk.getText().equals("") || etharga.getText().equals("") || harga <= 0){
                    utilitas.getSnackBar(v,R.id.wadah,"Harap isi dengan benar.");
                } else {
                    if (tambah){
                        tambah() ;
                    } else {
                        ubah() ;
                    }
                }
            }
        });

        etharga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etharga.removeTextChangedListener(this);
                try{
                    String temp = s.toString().replace(".","") ;
                    String nf = utilitas.numberFormat(temp) ;
                    etharga.setText(nf) ;
                    etharga.setSelection(nf.length());
                }catch (Exception e){

                }
                etharga.addTextChangedListener(this);
            }
        });

        wBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluar();
            }
        });

        etProduk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                utilitas.setText(v,R.id.tvProduk,utilitas.intToStr(s.toString().length()) + "/30") ;
            }
        });
    }
    public void setText(){
        String sql = "select * from tblproduk where idproduk='"+id+"'" ;
        String[] baru = mProduk.getActivity().getResources().getStringArray(R.array.aKategori) ;

        int p = Arrays.asList(baru).indexOf(db.getValue(sql,"kategori")) ;
        spKategori.setSelection(p);

        etProduk.setText(db.getValue(sql,"produk"));
        etharga.setText(utilitas.removeE(db.getValue(sql,"harga")));
    }

    public void tambah(){
        String produk = etProduk.getText().toString() ;
        String kategori = spKategori.getSelectedItem().toString() ;
        String harga = etharga.getText().toString().replace(".","") ;

        String sql = "insert into tblproduk (produk,kategori,harga) values " +
                "('"+produk+"','"+kategori+"','"+harga+"')" ;

        if (db.execution(sql)){
            mProduk.loadData("");
            keluar();
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iAddSuccess)) ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iAddFail));
        }
    }

    public void ubah(){
        String produk = etProduk.getText().toString() ;
        String kategori = spKategori.getSelectedItem().toString() ;
        String harga = etharga.getText().toString().replace(".","") ;

        String sql = "update tblproduk set produk='"+produk+"', kategori='"+kategori+"',harga='"+harga+"'" +
                " where idproduk='"+id+"'" ;

        if (db.execution(sql)){
            mProduk.loadData("");
            keluar();
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iUbahSuccess)) ;
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iUbahFail));
        }
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(DProduk.this).commit() ;
    }
}
