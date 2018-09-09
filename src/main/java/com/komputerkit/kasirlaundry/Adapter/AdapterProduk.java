package com.komputerkit.kasirlaundry.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.komputerkit.kasirlaundry.Fragment.Master.MProduk;
import com.komputerkit.kasirlaundry.Model.TblProduk;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 20/01/2018.
 */

public class AdapterProduk extends RecyclerView.Adapter<AdapterProduk.ViewHolder>{

    ArrayList<TblProduk> data ;
    int pilih = -1 ;
    Utilitas utilitas ;
    MProduk mProduk ;

    public AdapterProduk(MProduk mProduk, ArrayList<TblProduk> data) {
        this.data = data;
        this.mProduk = mProduk ;

        utilitas = new Utilitas(mProduk.getActivity()) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvProduk.setText(data.get(position).getProduk());
        holder.tvHarga.setText(utilitas.removeE(data.get(position).getHarga()));

        holder.wOpsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilitas.showPopUp(v,R.menu.menu_opsi).setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId() ;
                        if (id == R.id.iUbah){
                            mProduk.ubah(data.get(position).getIdproduk());
                        } else if (id == R.id.iHapus){
                            mProduk.hapus(data.get(position).getIdproduk());
                        }
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProduk,tvHarga;
        ConstraintLayout wOpsi ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProduk = itemView.findViewById(R.id.tvProduk) ;
            tvHarga = itemView.findViewById(R.id.tvHarga) ;
            wOpsi = itemView.findViewById(R.id.wOpsi) ;
        }
    }
}
