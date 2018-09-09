package com.komputerkit.kasirlaundry.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komputerkit.kasirlaundry.Model.QDetailOrder;
import com.komputerkit.kasirlaundry.Model.TblProduk;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 27/01/2018.
 */

public class AdapterProses extends RecyclerView.Adapter<AdapterProses.ViewHolder>{

    ArrayList<QDetailOrder> data ;

    public AdapterProses(ArrayList<QDetailOrder> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proses,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvProduk.setText(data.get(position).getProduk()) ;
        holder.tvJumlah.setText(data.get(position).getJumlahorder()) ;
        holder.tvKeterangan.setText((data.get(position).getKetdetailorder().equals("")?"-":data.get(position).getKetdetailorder())) ;

        String harga = Utilitas.doubleToStr(Utilitas.strToDouble(data.get(position).getHargaorder()) * Utilitas.strToDouble(data.get(position).getJumlahorder())) ;
        holder.tvHarga.setText(Utilitas.removeE(harga));
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProduk,tvHarga,tvJumlah,tvKeterangan ;
        ConstraintLayout wadah ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProduk = itemView.findViewById(R.id.tvProduk) ;
            tvKeterangan = itemView.findViewById(R.id.tvKeterangan) ;
            tvHarga = itemView.findViewById(R.id.tvHarga) ;
            tvJumlah= itemView.findViewById(R.id.tvJumlah) ;
            wadah = itemView.findViewById(R.id.wadah) ;
        }
    }
}