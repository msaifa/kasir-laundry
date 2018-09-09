package com.komputerkit.kasirlaundry.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.komputerkit.kasirlaundry.Model.TblProduk;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 23/01/2018.
 */

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder>{

    ArrayList<TblProduk> data ;

    public AdapterCart(ArrayList<TblProduk> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvProduk.setText(data.get(position).getProduk()) ;
        holder.tvJumlah.setText(data.get(position).getJumlah()) ;

        String harga = Utilitas.doubleToStr(Utilitas.strToDouble(data.get(position).getHarga()) * Utilitas.strToDouble(data.get(position).getJumlah())) ;

        holder.tvHarga.setText(Utilitas.removeE(harga));
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProduk,tvHarga,tvJumlah ;
        ConstraintLayout wadah ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProduk = itemView.findViewById(R.id.tvProduk) ;
            tvHarga = itemView.findViewById(R.id.tvTotalBayar) ;
            tvJumlah= itemView.findViewById(R.id.tvHarga) ;
            wadah = itemView.findViewById(R.id.wadah) ;
        }
    }
}