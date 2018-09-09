package com.komputerkit.kasirlaundry.Fragment.Master;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.komputerkit.kasirlaundry.ActivityUtama;
import com.komputerkit.kasirlaundry.Adapter.AdapterProduk;
import com.komputerkit.kasirlaundry.DialogFragment.DProduk;
import com.komputerkit.kasirlaundry.Model.TblProduk;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Config;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.InApp;
import com.komputerkit.kasirlaundry.Utilitas.MyApp;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 17/01/2018.
 */

public class MProduk extends Fragment {

    View v ;
    FloatingActionButton FABTambah ;
    RecyclerView recyclerView ;
    EditText etCari ;
    Utilitas utilitas ;
    Database db ;

    @Override
    public void onStart() {
        super.onStart();

        utilitas = new Utilitas(getActivity()) ;
        db = new Database(getActivity()) ;

        loadData("");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_master,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;

        init() ;
        event() ;
    }

    public void init(){
        FABTambah = v.findViewById(R.id.FABTambah) ;
        recyclerView = v.findViewById(R.id.recMaster) ;
        etCari = v.findViewById(R.id.etCari) ;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void event(){
        FABTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp myApp = new MyApp(utilitas,db) ;

                if (myApp.cekMaster("tblproduk")){
                    ((ActivityUtama)getActivity()).bayar();
                } else {
                    FragmentManager fm = getFragmentManager() ;
                    DProduk dProduk = new DProduk(MProduk.this) ;
                    dProduk.show(fm,"TambahData");
                }
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
                loadData(s.toString());
            }
        });
    }

    public void loadData(String cari){
        ArrayList<TblProduk> array = new ArrayList() ;
        AdapterProduk adapter = new AdapterProduk(this,array) ;
        recyclerView.setAdapter(adapter);

        String q = "select * from tblproduk where produk like '%"+cari+"%' order by produk asc" ;
        Cursor c = db.select(q) ;

        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                array.add(new TblProduk(
                        db.getString(c,"idproduk"),
                        db.getString(c,"produk"),
                        db.getString(c,"kategori"),
                        db.getString(c,"harga")
                )) ;
            }
            adapter.notifyDataSetChanged();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }

    public void ubah(String id){
        FragmentManager fm = getFragmentManager() ;
        DProduk dProduk = new DProduk(this,id) ;
        dProduk.show(fm,"TambahData");
    }

    public void hapus(String id){
        final String q = "delete from tblproduk where idproduk='"+id+"'" ;

        utilitas.showDialog(getString(R.string.iConfirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (db.execution(q)){
                    loadData("");
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iHapusSuccess));
                } else {
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iHapusGagal));
                }
            }
        });
    }
}
