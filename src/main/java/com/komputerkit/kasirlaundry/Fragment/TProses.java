package com.komputerkit.kasirlaundry.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import android.widget.TextView;

import com.komputerkit.kasirlaundry.ActivityUtama;
import com.komputerkit.kasirlaundry.Adapter.AdapterProses;
import com.komputerkit.kasirlaundry.DialogFragment.DPrintPreView;
import com.komputerkit.kasirlaundry.DialogFragment.DPrinter;
import com.komputerkit.kasirlaundry.Model.QDetailOrder;
import com.komputerkit.kasirlaundry.Model.QOrder;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Database;
import com.komputerkit.kasirlaundry.Utilitas.MyApp;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 27/01/2018.
 */

@SuppressLint("ValidFragment")
public class TProses extends Fragment {

    View v ;
    QOrder qOrder;
    Utilitas utilitas ;
    Database db ;
    RecyclerView recyclerView ;
    CardView cButon ;
    Switch swMetode ;
    boolean tunai ;
    TextView tSudahBayar ;
    ConstraintLayout wBayar ;
    int flag ;
    EditText etBayar ;

    @SuppressLint("ValidFragment")
    public TProses(QOrder faktur) {
        this.qOrder = faktur ;
    }

    @Override
    public void onStart() {
        super.onStart();

        utilitas = new Utilitas(getActivity()) ;
        db = new Database(getActivity()) ;

        settext() ;
        loadData();
        cekPembayaran();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_proses,container, false) ;
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
        tSudahBayar = v.findViewById(R.id.tSudahBayar) ;
        wBayar = v.findViewById(R.id.wBayar) ;
        swMetode = v.findViewById(R.id.swMetode) ;
        etBayar = v.findViewById(R.id.etBayar) ;

        cButon = v.findViewById(R.id.cButton) ;
    }

    public void event(){
        cButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double sisa = utilitas.strToDouble(utilitas.getText(v,R.id.tvKet).replace(".","")) ;

                if (flag == 5){
                    if (sisa < 0){
                        if (tunai){
                            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iBayarKurang));
                        } else {
                            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iSaldoKurang));
                        }
                    } else {
                        selesai();
                    }
                } else {
                    selesai();
                }
            }
        });

        swMetode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tunai = isChecked ;
                cekPembayaran() ;
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
                double sisa = 0;
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

                    utilitas.setText(v,R.id.tKet,getString(R.string.iKembali)) ;
                    sisa = utilitas.strToDouble(s.toString().replace(".","")) - utilitas.strToDouble(qOrder.getTotalorder()) ;
                    utilitas.setText(v,R.id.tvKet,utilitas.removeE(sisa)) ;
                }  else {
                    utilitas.setText(v,R.id.tKet,getString(R.string.iSisaSaldo)) ;
                    sisa = utilitas.strToDouble(qOrder.getSaldodeposit()) - utilitas.strToDouble(qOrder.getTotalorder()) ;
                    utilitas.setText(v,R.id.tvKet,utilitas.removeE(sisa)) ;
                }
            }
        });
    }

    private void cekPembayaran() {
        if (tunai){
            utilitas.setText(v,R.id.etBayar,"0") ;
        } else {
            utilitas.setText(v,R.id.etBayar,utilitas.removeE(qOrder.getTotalorder())) ;
        }
        etBayar.setEnabled(tunai);
    }

    public void settext(){
        String q = "select * from qorder where faktur='"+qOrder.getFaktur()+"'" ;

        utilitas.setText(v,R.id.tvFaktur,qOrder.getFaktur()) ;
        utilitas.setText(v,R.id.tvTanggal,db.getValue(q,"tglorder")) ;
        utilitas.setText(v,R.id.tvHarga,utilitas.removeE(db.getValue(q,"totalorder"))) ;
        utilitas.setText(v,R.id.tvKeterangan,(db.getValue(q,"ketorder").equals(""))?"-":db.getValue(q,"ketorder")) ;

        flag = utilitas.strToInt(qOrder.getFlagtrans()) ;
        if (flag == 3 || flag == 4){
            tSudahBayar.setVisibility(View.VISIBLE);
            wBayar.setVisibility(View.GONE) ;
        }
    }

    public void loadData(){
        ArrayList<QDetailOrder> array = new ArrayList<>() ;
        AdapterProses adapter = new AdapterProses(array) ;
        recyclerView.setAdapter(adapter);

        String q = "select * from qdetailorder where faktur='"+qOrder.getFaktur()+"'" ;
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
    }

    public void selesai(){
        String q = "" ;
        String bayar = utilitas.getText(v,R.id.etBayar).replace(".","") ;
        String kembali = utilitas.intToStr(utilitas.strToInt(bayar)-utilitas.strToInt(qOrder.getTotalorder())) ;
        if (flag == 3){
            q = "update tblorder set flagtrans=1 where faktur='"+qOrder.getFaktur()+"'" ;
        } else if (flag == 4){
            q = "update tblorder set flagtrans=2 where faktur='"+qOrder.getFaktur()+"'" ;
        } else {
            if (tunai){
                q = "update tblorder set flagtrans=2,bayar='"+bayar+"',kembali='"+kembali+"' where faktur='"+qOrder.getFaktur()+"'" ;
            } else {
                q = "update tblorder set flagtrans=1,bayar='"+bayar+"',kembali='0' where faktur='"+qOrder.getFaktur()+"'" ;
            }
        }

        if (db.execution(q)){
            if(flag == 5 && !tunai){
                MyApp myApp = new MyApp(utilitas,db) ;
                myApp.updateSaldo(qOrder.getIdpelanggan(),qOrder.getTotalorder());
            }
            utilitas.getSnackBar(v,R.id.wadah,getString(R.string.iSuccessTrans));
            showPrint(qOrder.getFaktur());
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
