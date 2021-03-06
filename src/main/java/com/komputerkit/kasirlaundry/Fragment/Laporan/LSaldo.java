package com.komputerkit.kasirlaundry.Fragment.Laporan;

import android.app.Fragment;
import android.app.FragmentManager;
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

import com.komputerkit.kasirlaundry.Adapter.AdapterLapMaster;
import com.komputerkit.kasirlaundry.DialogFragment.DialogExport;
import com.komputerkit.kasirlaundry.Model.TblPelanggan;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 17/01/2018.
 */

public class LSaldo extends Fragment {

    View v ;
    RecyclerView recyclerView ;
    Utilitas utilitas ;
    Database db ;
    EditText etCari ;
    double total ;
    ConstraintLayout wExport ;
    String q ;

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
        wExport = v.findViewById(R.id.wExport) ;
        ConstraintLayout wTanggal = v.findViewById(R.id.wTanggal) ;
        wTanggal.setVisibility(View.GONE);
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
                loadData(s.toString());
            }
        });

        wExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogExport dialogExport = new DialogExport(getActivity(),"LSaldo",q) ;

                FragmentManager fm = getFragmentManager() ;
                dialogExport.show(fm,"UbahAkun");
            }
        });
    }

    public void loadData(String cari){
        total = 0 ;
        ArrayList<TblPelanggan> array = new ArrayList() ;
        AdapterLapMaster adapter = new AdapterLapMaster(array) ;
        recyclerView.setAdapter(adapter);

        q = "select * from tblpelanggan where pelanggan like '%"+cari+"%' order by pelanggan asc" ;
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
                total += utilitas.strToDouble(db.getString(c,"saldodeposit")) ;
            }
            utilitas.setText(v,R.id.tvSaldo,utilitas.removeE(total)) ;
            adapter.notifyDataSetChanged();
        } else {
            utilitas.setText(v,R.id.tvSaldo,"0") ;
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }

}
