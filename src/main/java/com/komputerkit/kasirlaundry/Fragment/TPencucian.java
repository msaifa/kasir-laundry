package com.komputerkit.kasirlaundry.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.komputerkit.kasirlaundry.ActivityUtama;
import com.komputerkit.kasirlaundry.Adapter.AdapterCart;
import com.komputerkit.kasirlaundry.DialogFragment.DCartPilih;
import com.komputerkit.kasirlaundry.DialogFragment.DPrintPreView;
import com.komputerkit.kasirlaundry.DialogFragment.DPrinter;
import com.komputerkit.kasirlaundry.Model.TblPegawai;
import com.komputerkit.kasirlaundry.Model.TblPelanggan;
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

@SuppressLint("ValidFragment")
public class TPencucian extends Fragment {

    View v ;
    Utilitas utilitas ;
    Database db ;
    ArrayList<TblProduk> data ;
    ArrayList<TblProduk> baru = new ArrayList<>() ;
    RecyclerView recyclerView ;
    TblPelanggan tblPelanggan ;
    TblPegawai tblPegawai ;
    Switch swMetode,swBayar ;
    EditText etBayar ;
    TextView tKet,tvKet ;
    ConstraintLayout wButton,wPelanggan,wPegawai,wBayar ;
    boolean tunai,sekarang ;
    String totalBayar = "0" ;
    String faktur ;
    double sisa = 0;

    @SuppressLint("ValidFragment")
    public TPencucian(ArrayList<TblProduk> data) {
        this.data = data;
    }

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
        return inflater.inflate(R.layout.fragment_pencucian,container, false) ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view ;

        init() ;
        event() ;
    }

    public void setText(){
        MyApp myApp = new MyApp(utilitas,db) ;
        faktur = myApp.getFaktur() ;
        utilitas.setText(v,R.id.tvFaktur,faktur) ;
        utilitas.setText(v,R.id.tvTanggal,utilitas.getDate(getString(R.string.typeDate))) ;

        AdapterCart adapter = new AdapterCart(baru) ;
        recyclerView.setAdapter(adapter) ;

        double total = 0 ;
        for (int i = 0 ; i < data.size() ; i++){
            total += utilitas.strToDouble(data.get(i).getHarga()) *utilitas.strToDouble(data.get(i).getJumlah()) ;

            if(!data.get(i).getJumlah().equals("0")){
                baru.add(data.get(i)) ;
            }
        }

        adapter.notifyDataSetChanged() ;
        totalBayar = utilitas.removeE(total) ;
        utilitas.setText(v,R.id.tvTotalBayar,totalBayar) ;
    }

    public void init(){
        recyclerView = v.findViewById(R.id.recCart) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        etBayar = v.findViewById(R.id.etBayar) ;
        tKet = v.findViewById(R.id.tKet) ;
        tvKet = v.findViewById(R.id.tKet) ;
        swMetode = v.findViewById(R.id.swMetode) ;
        swBayar = v.findViewById(R.id.swBayar) ;
        wButton = v.findViewById(R.id.wButton) ;
        wPegawai = v.findViewById(R.id.wPegawai) ;
        wPelanggan = v.findViewById(R.id.wPelanggan) ;
        wBayar = v.findViewById(R.id.wBayar) ;
    }

    public void event(){
        swMetode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tunai = isChecked ;

                ubahMetode() ;
            }
        });

        etBayar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tunai){
                    etBayar.removeTextChangedListener(this);
                    try{
                        String temp = s.toString().replace(".","") ;
                        String nf = utilitas.numberFormat(temp) ;
                        etBayar.setText(nf) ;
                        etBayar.setSelection(nf.length());
                    }catch (Exception e){

                    }
                    etBayar.addTextChangedListener(this);

                    tKet.setText(getString(R.string.iKembali));
                    sisa = utilitas.strToDouble(s.toString().replace(".","")) - utilitas.strToDouble(totalBayar.replace(".","")) ;
                    utilitas.setText(v,R.id.tvKet,utilitas.removeE(sisa)) ;
                }  else {
                    tKet.setText(getString(R.string.iSisaSaldo));
                    if (tblPelanggan != null){
                        sisa = utilitas.strToDouble(tblPelanggan.getSaldodeposit()) - utilitas.strToDouble(totalBayar.replace(".","")) ;
                        utilitas.setText(v,R.id.tvKet,utilitas.removeE(sisa)) ;
                    } else {
                        utilitas.setText(v,R.id.tvKet,"0") ;
                    }
                }
            }
        });

        wPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DCartPilih dCartPilih = new DCartPilih(TPencucian.this,"pegawai") ;
                FragmentManager fm = getFragmentManager() ;
                dCartPilih.show(fm,"coba");
            }
        });

        wPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DCartPilih dCartPilih = new DCartPilih(TPencucian.this,"pelanggan") ;
                FragmentManager fm = getFragmentManager() ;
                dCartPilih.show(fm,"coba");
            }
        });

        wButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viwq) {
                MyApp myApp = new MyApp(utilitas,db) ;

                if (tblPegawai == null){
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iPilihPegawai));
                } else if (tblPelanggan == null){
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iPilihPelanggan));
                } else if (myApp.cekTrans("tblorder")){
                    ((ActivityUtama)getActivity()).bayar();
                }else {
                    simpan();
                }
            }
        });

        swBayar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    wBayar.setVisibility(View.VISIBLE);
                    sekarang = true ;
                } else {
                    wBayar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void ubahMetode() {
        if (tunai){
            etBayar.setText("0");
        } else {
            etBayar.setText(totalBayar);
        }
        etBayar.setEnabled(tunai);
    }

    public void setPelangagn(TblPelanggan pelangagn) {
        this.tblPelanggan = pelangagn;
        utilitas.setText(v,R.id.tvPelanggan,tblPelanggan.getPelanggan()) ;
        ubahMetode() ;
    }

    public void setPelangagn(TblPegawai tblPegawai) {
        this.tblPegawai = tblPegawai ;
        utilitas.setText(v,R.id.tvPegawai,tblPegawai.getPegawai()) ;
    }

    public void simpan(){
        String q = "" ;
        String flagTrans = "" ;
        String idpelanggan = tblPelanggan.getIdpelanggan() ;
        String idpegawai = tblPegawai.getIdpegawai() ;
        String tglorder = utilitas.getDate(getString(R.string.typeDate)) ;
        String totalorder = totalBayar.replace(".","") ;
        String bayar = etBayar.getText().toString().replace(".","") ;
        String kembali = utilitas.doubleToStr(sisa) ;
        String ketrder = utilitas.getText(v,R.id.etKeterangan) ;
        String bulan   = (utilitas.getMonth(tglorder)) ;
        String tahun   = (utilitas.getYear(tglorder)) ;
        String tanggal = tahun + bulan + (utilitas.getDay(tglorder)) ;

        if (sekarang){
            if (tunai){
                if (sisa < 0){
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iBayarKurang));
                } else{
                    flagTrans = "4" ;
                }
            } else{
                if (sisa < 0){
                    utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iSaldoKurang));
                } else {
                    kembali = "0" ;
                    flagTrans = "3" ;
                }
            }
        } else {
            bayar = "0" ;
            kembali = "0" ;
            flagTrans = "5" ;
        }

        q = "INSERT INTO tblorder (idpelanggan,idpegawai,faktur,tglorder,totalorder,bayar,kembali,ketorder,tglmix,flagtrans) " +
                "values (" +
                "'"+idpelanggan+"'," +
                "'"+idpegawai+"'," +
                "'"+faktur+"'," +
                "'"+tglorder+"'," +
                "'"+totalorder+"'," +
                "'"+bayar+"'," +
                "'"+kembali+"'," +
                "'"+ketrder+"'," +
                "'"+tanggal+"'," +
                "'"+flagTrans+"'" +
                ")" ;

        if (flagTrans.equals("")){}else if (db.execution(q)){

            q = "select * from tblorder order by idorder desc" ;
            String idorder = db.getValue(q,"idorder") ;
            int jum = 0 ;
            for (int i = 0 ; i < baru.size() ; i++){
                String sql = "insert into tbldetailorder (idorder,idproduk,hargaorder,jumlahorder,ketdetailorder) " +
                        "values (" +
                        "'"+idorder+"'," +
                        "'"+baru.get(i).getIdproduk()+"'," +
                        "'"+baru.get(i).getHarga()+"'," +
                        "'"+baru.get(i).getJumlah()+"'," +
                        "'"+baru.get(i).getKeterangan()+"'" +
                        ")" ;

                if (db.execution(sql)){
                    jum++ ;
                }
            }

            if (jum == baru.size()){
                utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iSuccessTrans));
                showPrint(faktur);
            } else {
                utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iFailTrans));
            }

        } else {
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iFailTrans));
        }
    }

    public void showPrint(String faktur){
        DPrintPreView dPrinter = new DPrintPreView(this,getActivity(),faktur,false) ;
        FragmentManager fm = getFragmentManager() ;
        dPrinter.show(fm,"") ;
    }
}
