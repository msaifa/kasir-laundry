package com.komputerkit.kasirlaundry.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.komputerkit.kasirlaundry.Model.QDetailOrder;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa-pc on 05/02/2018.
 */

public class AdapterDetailPencucian extends RecyclerView.Adapter<AdapterDetailPencucian.ViewHolder>{

    ArrayList<QDetailOrder> data ;

    public AdapterDetailPencucian(ArrayList<QDetailOrder> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_pencucian,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvProduk.setText(data.get(position).getProduk()) ;
        holder.tvJumlah.setText(data.get(position).getJumlahorder() + " x " + Utilitas.removeE(data.get(position).getHargaorder())) ;
        holder.tvPelanggan.setText(data.get(position).getPelanggan()) ;
        holder.tvFaktur.setText(data.get(position).getFaktur() + "\t\t" + data.get(position).getTglorder()) ;
        holder.etKeterangan.setText((data.get(position).getKetdetailorder().equals("")?"-":data.get(position).getKetdetailorder())) ;

        String harga = Utilitas.doubleToStr(Utilitas.strToDouble(data.get(position).getHargaorder()) * Utilitas.strToDouble(data.get(position).getJumlahorder())) ;
        holder.tvTotal.setText(Utilitas.removeE(harga));
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProduk, tvTotal,tvJumlah, tvFaktur,tvPelanggan;
        EditText etKeterangan ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProduk = itemView.findViewById(R.id.tvProduk) ;
            etKeterangan = itemView.findViewById(R.id.etKeterangan) ;
            tvTotal = itemView.findViewById(R.id.tvTotal) ;
            tvJumlah= itemView.findViewById(R.id.tvJumlah) ;
            tvFaktur= itemView.findViewById(R.id.tvFaktur) ;
            tvPelanggan= itemView.findViewById(R.id.tvPelanggan) ;
        }
    }
}