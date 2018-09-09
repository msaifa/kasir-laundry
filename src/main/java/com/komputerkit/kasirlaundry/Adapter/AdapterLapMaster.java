package com.komputerkit.kasirlaundry.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komputerkit.kasirlaundry.Model.TblPegawai;
import com.komputerkit.kasirlaundry.Model.TblPelanggan;
import com.komputerkit.kasirlaundry.Model.TblProduk;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 24/01/2018.
 */

public class AdapterLapMaster extends RecyclerView.Adapter<AdapterLapMaster.ViewHolder>{

    ArrayList<TblPelanggan> data1 ;
    ArrayList<TblPegawai> data2 ;
    ArrayList<TblProduk> data3 ;
    int type =  0 ;

    public AdapterLapMaster(ArrayList<TblPelanggan> data1) {
        this.data1 = data1;
        type = 1 ;
    }

    public AdapterLapMaster(ArrayList<TblPegawai> data2,String type) {
        this.data2 = data2;
        this.type = 2 ;
    }

    public AdapterLapMaster(ArrayList<TblProduk> data3, int type) {
        this.data3 = data3;
        this.type = 3 ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelanggan,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (type  == 1){
            holder.tvPelanggan.setText(data1.get(position).getPelanggan());
            holder.tvAlamat.setText(data1.get(position).getAlamat());
            holder.tvTelp.setText(data1.get(position).getNohp());
            holder.tvSaldo.setText(Utilitas.removeE(data1.get(position).getSaldodeposit()));
            holder.wOpsi.setVisibility(View.GONE);
        } else if (type == 2){
            holder.tvPelanggan.setText(data2.get(position).getPegawai());
            holder.tvAlamat.setText(data2.get(position).getAlamatpegawai());
            holder.tvTelp.setText(data2.get(position).getNohppegawai());
            holder.wOpsi.setVisibility(View.GONE);
        } else if (type == 3){
            holder.tvPelanggan.setText(data3.get(position).getProduk());
            holder.tvAlamat.setText(Utilitas.removeE(data3.get(position).getHarga()) + "/ " + data3.get(position).getKategori());
            holder.tvTelp.setVisibility(View.GONE);
            holder.wOpsi.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (type == 1){
            return data1.size() ;
        } else if (type == 2){
            return data2.size() ;
        } else {
            return data3.size() ;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvPelanggan,tvAlamat,tvTelp,tvSaldo ;
        ConstraintLayout wOpsi ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvPelanggan = itemView.findViewById(R.id.tvPelanggan) ;
            tvTelp   = itemView.findViewById(R.id.tvTelp) ;
            tvAlamat = itemView.findViewById(R.id.tvAlamat) ;
            tvSaldo  = itemView.findViewById(R.id.tvSaldo) ;
            wOpsi    = itemView.findViewById(R.id.wOpsi) ;
        }
    }
}