package com.komputerkit.kasirlaundry.Fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.komputerkit.kasirlaundry.ActivityUtama;
import com.komputerkit.kasirlaundry.Adapter.AdapterPilihProduk;
import com.komputerkit.kasirlaundry.Model.TblProduk;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 23/01/2018.
 */

public class TPilihProduk extends Fragment {

    View v ;
    RecyclerView recyclerView ;
    Switch swDecimal ;
    ArrayList<TblProduk> arrayList = new ArrayList<>() ;
    Database db ;
    Utilitas utilitas ;
    double totalBarang = 0 ;
    double totalBayar = 0 ;
    CardView btnLanjut ;
    boolean decimal = false ;
    AdapterPilihProduk adapter ;
    EditText etCari ;

    @Override
    public void onStart() {
        super.onStart();
        db = new Database(getActivity()) ;
        utilitas = new Utilitas(getActivity()) ;

        loadData();
        setButton();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pilih_produk,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;
        init() ;
        event() ;
    }

    public void init(){
        recyclerView = v.findViewById(R.id.recProduk) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swDecimal = v.findViewById(R.id.swDecimal);
        btnLanjut = v.findViewById(R.id.btnLanjut) ;
        etCari = v.findViewById(R.id.etCari) ;
    }

    public void event(){
        swDecimal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                decimal = isChecked ;
                adapter = new AdapterPilihProduk(arrayList,TPilihProduk.this,decimal) ;
                recyclerView.setAdapter(adapter);
            }
        });

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityUtama)getActivity()).pencucian(arrayList);
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
                search(s.toString());
            }
        });
    }

    public void loadData(){
        totalBarang = 0 ;
        totalBayar = 0 ;
        arrayList.clear();
        adapter = new AdapterPilihProduk(arrayList,this,decimal) ;
        recyclerView.setAdapter(adapter);

        String q = "select * from tblproduk order by produk asc" ;
        Cursor c = db.select(q) ;

        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                arrayList.add(new TblProduk(
                        db.getString(c,"idproduk"),
                        db.getString(c,"produk"),
                        db.getString(c,"kategori"),
                        db.getString(c,"harga"),
                        "0",
                        "",
                        "1"
                )) ;
            }

            adapter.notifyDataSetChanged();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }

        cekButton();
    }

    public void search(String cari){
        for (int i = 0 ; i < arrayList.size() ; i++){
            if (arrayList.get(i).getProduk().toLowerCase().contains(cari.toLowerCase())){
                arrayList.get(i).setFlagAktif("1");
            } else {
                arrayList.get(i).setFlagAktif("0");
            }
        }

        adapter.setArray(arrayList) ;
    }

    public void min(ArrayList<TblProduk> data, double harga, boolean kilo){
        arrayList = data ;

        totalBayar -= harga ;
        totalBarang -= (kilo)?0.1:1 ;

        setButton();
    }

    public void plus(ArrayList<TblProduk> data, double harga, boolean kilo){
        arrayList = data ;

        totalBayar += harga ;
        totalBarang += (kilo)?0.1:1 ;

        setButton();
    }

    public void note(ArrayList<TblProduk> data){
        arrayList = data ;
    }

    public void cekButton(){
        if (totalBarang > 0){
            btnLanjut.setVisibility(View.VISIBLE);
        } else {
            btnLanjut.setVisibility(View.GONE);
        }
    }

    public void setButton(){
        utilitas.setText(v,R.id.tvTotalBayar,utilitas.removeE(totalBayar)) ;
        utilitas.setText(v,R.id.tvTotalBarang,utilitas.removeE(totalBarang)) ;

        cekButton();
    }

}
