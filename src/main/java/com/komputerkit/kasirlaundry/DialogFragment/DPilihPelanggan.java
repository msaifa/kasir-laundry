package com.komputerkit.kasirlaundry.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;

import com.komputerkit.kasirlaundry.Adapter.AdapterPelanggan;
import com.komputerkit.kasirlaundry.Adapter.AdapterPilihPelanggan;
import com.komputerkit.kasirlaundry.Fragment.Master.MPelanggan;
import com.komputerkit.kasirlaundry.Fragment.TTopup;
import com.komputerkit.kasirlaundry.Model.TblPelanggan;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 20/01/2018.
 */

@SuppressLint("ValidFragment")
public class DPilihPelanggan extends DialogFragment {

    View v ;
    Utilitas utilitas ;
    Database db ;
    TTopup tTopup ;
    ConstraintLayout wBack ;
    RecyclerView recyclerView ;
    EditText etCari ;

    @SuppressLint("ValidFragment")
    public DPilihPelanggan(TTopup tTopup) {
        this.tTopup = tTopup;

        utilitas = new Utilitas(tTopup.getActivity()) ;
        db = new Database(tTopup.getActivity()) ;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        loadData("");
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
                loadData(s.toString());
            }
        });
    }

    public void loadData(String cari){
        ArrayList<TblPelanggan> array = new ArrayList() ;
        AdapterPilihPelanggan adapter = new AdapterPilihPelanggan(this,array) ;
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

    public void pilih(TblPelanggan tblPelanggan){
        tTopup.setPelangagn(tblPelanggan);
        keluar();
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(DPilihPelanggan.this).commit() ;
    }

}
