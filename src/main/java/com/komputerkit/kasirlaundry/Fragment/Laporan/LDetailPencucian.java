package com.komputerkit.kasirlaundry.Fragment.Laporan;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.komputerkit.kasirlaundry.Adapter.AdapterDetailPencucian;
import com.komputerkit.kasirlaundry.Adapter.AdapterLapPencucian;
import com.komputerkit.kasirlaundry.DialogFragment.DPrintPreView;
import com.komputerkit.kasirlaundry.DialogFragment.DialogExport;
import com.komputerkit.kasirlaundry.Model.QDetailOrder;
import com.komputerkit.kasirlaundry.Model.QOrder;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.MyApp;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa-pc on 05/02/2018.
 */

public class LDetailPencucian extends Fragment {

    View v ;
    RecyclerView recyclerView ;
    Utilitas utilitas ;
    Database db ;
    EditText etCari,etDari,etSampai ;
    ConstraintLayout lDari,lSampai ;
    boolean isLoad = false ;
    ConstraintLayout wExport ;
    String q ;

    @Override
    public void onStart() {
        super.onStart();
        utilitas = new Utilitas(getActivity()) ;
        db = new Database(getActivity()) ;

        etDari.setText(utilitas.getDate(getString(R.string.typeDate)));
        etSampai.setText(utilitas.getDate(getString(R.string.typeDate)));
        loadData("");
        isLoad = true ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_laporan,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;
        init() ;
        event();
    }

    public void init(){
        recyclerView = v.findViewById(R.id.recLaporan) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        etCari = v.findViewById(R.id.etCari) ;
        lDari = v.findViewById(R.id.lDari) ;
        lSampai = v.findViewById(R.id.lSampai) ;
        etDari = v.findViewById(R.id.etDari) ;
        etSampai = v.findViewById(R.id.etSampai) ;
        wExport = v.findViewById(R.id.wExport) ;
    }

    public void event(){
        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isLoad){
                    loadData(s.toString());
                }
            }
        });

        lDari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilitas.showDateDialog(v,R.id.etDari);
            }
        });

        lSampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilitas.showDateDialog(v,R.id.etSampai);
            }
        });

        etDari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isLoad){
                    loadData(etCari.getText().toString());
                }
            }
        });

        etSampai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isLoad){
                    loadData(etCari.getText().toString());
                }
            }
        });

        wExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogExport dialogExport = new DialogExport(getActivity(),"LDetailPencucian",q) ;

                FragmentManager fm = getFragmentManager() ;
                dialogExport.show(fm,"UbahAkun");
            }
        });
    }

    public void loadData(String cari){
        MyApp myApp = new MyApp(utilitas,db) ;
        ArrayList<QDetailOrder> array = new ArrayList() ;
        AdapterDetailPencucian adapter = new AdapterDetailPencucian(array) ;
        recyclerView.setAdapter(adapter);

        q = "select * from qdetailorder where "+myApp.sqlBetweenDate(etDari.getText().toString(),etSampai.getText().toString())+" " +
                "and (pelanggan like '%"+cari+"%' or produk like '%"+cari+"%') order by faktur desc" ;
        Cursor c = db.select(q) ;

        double total = 0;
        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                array.add(new QDetailOrder(
                        db.getString(c,"idpegawai"),
                        db.getString(c,"pegawai"),
                        db.getString(c,"alamatpegawai"),
                        db.getString(c,"nohppegawai"),
                        db.getString(c,"idpelanggan"),
                        db.getString(c,"pelanggan"),
                        db.getString(c,"alamat"),
                        db.getString(c,"nohp"),
                        db.getString(c,"saldodeposit"),
                        db.getString(c,"idorder"),
                        db.getString(c,"faktur"),
                        db.getString(c,"tglorder"),
                        db.getString(c,"totalorder"),
                        db.getString(c,"bayar"),
                        db.getString(c,"kembali"),
                        db.getString(c,"ketorder"),
                        db.getString(c,"tglmix"),
                        db.getString(c,"flagtrans"),
                        db.getString(c,"iddetailorder"),
                        db.getString(c,"idproduk"),
                        db.getString(c,"hargaorder"),
                        db.getString(c,"jumlahorder"),
                        db.getString(c,"ketdetailorder"),
                        db.getString(c,"kategori"),
                        db.getString(c,"produk"),
                        db.getString(c,"harga")
                )) ;
                total += utilitas.strToDouble(db.getString(c,"totalorder")) ;
            }
            utilitas.setText(v,R.id.tvSaldo,utilitas.removeE(total)) ;
            adapter.notifyDataSetChanged();
        } else {
            utilitas.setText(v,R.id.tvSaldo,"0") ;
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }

}
