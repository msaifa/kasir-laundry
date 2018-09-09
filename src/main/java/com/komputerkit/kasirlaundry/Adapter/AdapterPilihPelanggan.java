package com.komputerkit.kasirlaundry.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.komputerkit.kasirlaundry.DialogFragment.DPilihPelanggan;
import com.komputerkit.kasirlaundry.Model.TblPelanggan;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 20/01/2018.
 */


public class AdapterPilihPelanggan extends RecyclerView.Adapter<AdapterPilihPelanggan.ViewHolder>{

    ArrayList<TblPelanggan> data ;
    Utilitas utilitas ;
    DPilihPelanggan DPilihPelanggan;

    public AdapterPilihPelanggan(DPilihPelanggan DPilihPelanggan, ArrayList<TblPelanggan> data) {
        this.data = data;
        this.DPilihPelanggan = DPilihPelanggan ;

        utilitas = new Utilitas(DPilihPelanggan.getActivity()) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelanggan,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvPelanggan.setText(data.get(position).getPelanggan());
        holder.tvAlamat.setText(data.get(position).getAlamat());
        holder.tvTelp.setText(data.get(position).getNohp());
        holder.wOpsi.setVisibility(View.GONE);

        holder.wadah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DPilihPelanggan.pilih(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvPelanggan,tvAlamat,tvTelp ;
        ConstraintLayout wOpsi,wadah ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvPelanggan = itemView.findViewById(R.id.tvPelanggan) ;
            tvTelp = itemView.findViewById(R.id.tvTelp) ;
            tvAlamat= itemView.findViewById(R.id.tvAlamat) ;
            wOpsi = itemView.findViewById(R.id.wOpsi) ;
            wadah = itemView.findViewById(R.id.wadah) ;
        }
    }
}