package com.komputerkit.kasirlaundry.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komputerkit.kasirlaundry.ActivityUtama;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

/**
 * Created by msaifa on 17/01/2018.
 */

public class Beranda extends Fragment {

    Utilitas utilitas ;
    Database db ;
    View v ;
    TextView tvProduk, tvPelanggan, tvDeposit;
    ConstraintLayout btnSatu,btnDua,btnTiga, btnEmpat ;

    @Override
    public void onStart() {
        super.onStart();

        utilitas = new Utilitas(getActivity()) ;
        db = new Database(getActivity()) ;

        setText();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beranda,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;
        init() ;
        event();
    }

    public void init(){
        tvPelanggan = v.findViewById(R.id.tvPelanggan) ;
        tvProduk = v.findViewById(R.id.tvProduk) ;
        tvDeposit = v.findViewById(R.id.tvDeposit) ;
        btnSatu = v.findViewById(R.id.lPendapatan) ;
        btnDua = v.findViewById(R.id.lPengeluaran) ;
        btnTiga = v.findViewById(R.id.lPemindahanKas) ;
        btnEmpat = v.findViewById(R.id.lLaporan) ;
    }

    public void event(){
        btnSatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityUtama)getActivity()).pindah(getString(R.string.mnTopup));
            }
        });

        btnDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityUtama)getActivity()).pindah(getString(R.string.mnPencucian));
            }
        });

        btnTiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityUtama)getActivity()).pindah(getString(R.string.mnProsesCuci));
            }
        });
        btnEmpat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityUtama)getActivity()).pindah(getString(R.string.mnLapPencucian)) ;
            }
        });
    }

    public void setText(){
        String sql = "select * from tbltotal" ;
        String aDebet = db.getValue(sql,"deposit");
        String cucian = db.getValue(sql,"pencucian");

        sql = "select * from tblproduk" ;
        String produk = Utilitas.intToStr(db.getCount(sql)) ;

        sql = "select * from tblpelanggan" ;
        String pelanggan = Utilitas.intToStr(db.getCount(sql)) ;

        tvDeposit.setText (utilitas.removeE(aDebet));
        tvProduk.setText(utilitas.removeE(cucian) );
        tvPelanggan.setText (utilitas.removeE(pelanggan));
    }

}
