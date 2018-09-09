package com.komputerkit.kasirlaundry.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.komputerkit.kasirlaundry.ActivityUtama;
import com.komputerkit.kasirlaundry.Adapter.AdapterPrintPreview;
import com.komputerkit.kasirlaundry.Adapter.AdapterProses;
import com.komputerkit.kasirlaundry.Model.QDetailOrder;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa-pc on 03/02/2018.
 */

@SuppressLint("ValidFragment")
public class DPrintPreView extends DialogFragment {

    View v ;
    Utilitas utilitas;
    Database db ;
    Fragment fragment ;
    Context context ;
    String faktur ;
    boolean stay ;
    ConstraintLayout wBack,btnCetak,btnKembali ;
    RecyclerView recyclerView ;

    @SuppressLint("ValidFragment")
    public DPrintPreView(Fragment fragment, Context context, String faktur, boolean stay) {
        this.fragment = fragment;
        this.context = context;
        this.faktur = faktur;
        this.stay = stay;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        utilitas = new Utilitas(context) ;
        db = new Database(context) ;

        setText();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_print_preview,container,false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view ;

        init() ;
        event();
    }

    public void init(){
        wBack = v.findViewById(R.id.wBack) ;
        btnCetak = v.findViewById(R.id.btnCetak) ;
        btnKembali = v.findViewById(R.id.btnKembali) ;
        recyclerView = v.findViewById(R.id.recProduk) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void event(){
        wBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluar();
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kembali() ;
            }
        });
        btnCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DPrinter dPrinter = new DPrinter(context,faktur) ;
                FragmentManager fm = getFragmentManager() ;
                dPrinter.show(fm,"") ;
            }
        });
    }

    public void keluar(){
        getActivity().getFragmentManager().beginTransaction().remove(DPrintPreView.this).commit() ;
    }

    public void kembali (){
        if (stay){
            keluar();
        } else {
            ((ActivityUtama)fragment.getActivity()).pindah(context.getString(R.string.mnBeranda));
            keluar();
        }
    }

    public void setText(){
        String identitas = "select * from tblidentitas" ;
        String q = "select * from qorder where faktur='"+faktur+"'" ;

        String toko = db.getValue(identitas,"nama") ;
        String alamat = db.getValue(identitas,"alamat") ;
        String telp = db.getValue(identitas,"telp") ;
        String capt1 = db.getValue(identitas,"caption1") ;
        String capt2 = db.getValue(identitas,"caption2") ;
        String capt3 = db.getValue(identitas,"caption3") ;

        String fakt = "Faktur : " + faktur ;
        String tgl = "Tanggal : " + db.getValue(q,"tglorder") ;
        String pelanggan = "Pelanggan : " + db.getValue(q,"pelanggan") ;
        String pegawai = "Pegawai : "+db.getValue(q,"pegawai") ;
        String total = "Total : " +utilitas.removeE(db.getValue(q,"totalorder"));
        String bayar = "Bayar : " +utilitas.removeE(db.getValue(q,"bayar"));
        String kembali = "Kembali : " +utilitas.removeE(db.getValue(q,"kembali"));
        String status = db.getValue(q,"flagtrans");

        if (status.equals("5")){
            status = "BELUM DIBAYAR" ;
            ConstraintLayout wstatus = v.findViewById(R.id.wStatus) ;
            wstatus.setVisibility(View.GONE);
        } else if (status.equals("3") || status.equals("4")){
            status = "TANDA TERIMA" ;
        } else {
            status = "STRUK PEMBAYARAN" ;
        }

        ArrayList<QDetailOrder> array = new ArrayList<>() ;
        AdapterPrintPreview adapter = new AdapterPrintPreview(array) ;
        recyclerView.setAdapter(adapter);

        q = "select * from qdetailorder where faktur='"+faktur+"'" ;
        Cursor c = db.select(q) ;

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
            }
            adapter.notifyDataSetChanged();
        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iNullData));
        }

        utilitas.setText(v,R.id.tvToko,toko) ;
        utilitas.setText(v,R.id.tvAlamat,alamat) ;
        utilitas.setText(v,R.id.tvTelp,telp) ;
        utilitas.setText(v,R.id.tvFaktur,fakt) ;
        utilitas.setText(v,R.id.tvTanggal,tgl) ;
        utilitas.setText(v,R.id.tvPelanggan,pelanggan) ;
        utilitas.setText(v,R.id.tvPegawai,pegawai) ;
        utilitas.setText(v,R.id.tvStatus,status) ;
        utilitas.setText(v,R.id.tvTotal,total) ;
        utilitas.setText(v,R.id.tvBayar,bayar) ;
        utilitas.setText(v,R.id.tvKembali,kembali) ;
        utilitas.setText(v,R.id.tvCapt,capt1 + "\n" + capt2 + "\n" + capt3) ;
    }

}
