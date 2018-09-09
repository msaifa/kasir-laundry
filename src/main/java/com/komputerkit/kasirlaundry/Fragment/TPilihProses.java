package com.komputerkit.kasirlaundry.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.komputerkit.kasirlaundry.ActivityUtama;
import com.komputerkit.kasirlaundry.Adapter.AdapterPilihProduk;
import com.komputerkit.kasirlaundry.Adapter.AdapterPilihProses;
import com.komputerkit.kasirlaundry.DialogFragment.DPrintPreView;
import com.komputerkit.kasirlaundry.DialogFragment.DPrinter;
import com.komputerkit.kasirlaundry.Model.QOrder;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 27/01/2018.
 */

public class TPilihProses extends Fragment {

    View v ;
    RecyclerView recyclerView ;
    EditText etCari ;
    Database db ;
    Utilitas utilitas ;

    @Override
    public void onStart() {
        super.onStart();

        db = new Database(getActivity()) ;
        utilitas = new Utilitas(getActivity()) ;

        loadData("");
    }

    public void showPrint(String faktur){
        DPrintPreView dPrinter = new DPrintPreView(this,getActivity(),faktur,true) ;
        FragmentManager fm = getFragmentManager() ;
        dPrinter.show(fm,"") ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pilih_proses,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;

        init() ;
        event() ;
    }

    public void init(){
        recyclerView = v.findViewById(R.id.recProses) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        etCari = v.findViewById(R.id.etCari) ;
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
    }

    public void loadData(String cari){
        ArrayList<QOrder> array = new ArrayList<>() ;
        AdapterPilihProses adapter = new AdapterPilihProses(array,this) ;
        recyclerView.setAdapter(adapter);

        String q = "select * from qorder where pelanggan like '%"+cari+"%' and flagtrans>2 order by faktur asc" ;
        Cursor c = db.select(q) ;

        if (db.getCount(q) > 0){
            while(c.moveToNext()){
                array.add(new QOrder(
                        db.getString(c,"idpegawai") ,
                        db.getString(c,"pegawai") ,
                        db.getString(c,"alamatpegawai") ,
                        db.getString(c,"nohppegawai") ,
                        db.getString(c,"idpelanggan") ,
                        db.getString(c,"pelanggan") ,
                        db.getString(c,"alamat") ,
                        db.getString(c,"nohp") ,
                        db.getString(c,"saldodeposit") ,
                        db.getString(c,"idorder") ,
                        db.getString(c,"faktur") ,
                        db.getString(c,"tglorder") ,
                        db.getString(c,"totalorder") ,
                        db.getString(c,"bayar") ,
                        db.getString(c,"kembali") ,
                        db.getString(c,"ketorder") ,
                        db.getString(c,"tglmix"),
                        db.getString(c,"flagtrans")
                )) ;
            }
            adapter.notifyDataSetChanged();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }
    }

    public void pindah(QOrder faktur){
        ((ActivityUtama)getActivity()).proses(faktur);
    }

    public void hapus(String faktur, final String idOrder){

        String q = "delete from tblorder where faktur='" + faktur + "'" ;

        final String finalQ = q;
        utilitas.showDialog(getString(R.string.iHapusItem), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sql = "select * from tbldetailorder where idorder=" + idOrder ;
                Cursor c = db.select(sql) ;
                int no = 0 ;
                int jum = db.getCount(sql) ;
                while(c.moveToNext()){
                    String q = "delete from tbldetailorder where iddetailorder=" + db.getString(c,"iddetailorder") ;
                    if (db.execution(q)){
                        no++ ;
                    }
                }

                if (no == jum && db.execution(finalQ)){
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iHapusSuccess));
                    loadData("");
                } else {
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iHapusGagal));
                }
            }
        });
    }

}
