package com.komputerkit.kasirlaundry.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.komputerkit.kasirlaundry.Fragment.Master.MPegawai;
import com.komputerkit.kasirlaundry.DialogFragment.DCartPilih;
import com.komputerkit.kasirlaundry.Model.TblPegawai;
import com.komputerkit.kasirlaundry.Model.TblPelanggan;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 23/01/2018.
 */

public class AdapterCardPilih extends RecyclerView.Adapter<AdapterCardPilih.ViewHolder>{

    ArrayList<TblPegawai> data ;
    ArrayList<TblPelanggan> data2 ;
    Utilitas utilitas ;
    DCartPilih DCartPilih;
    boolean pegawai ;

    public AdapterCardPilih(DCartPilih DCartPilih, ArrayList<TblPegawai> data) {
        this.data = data;
        this.DCartPilih= DCartPilih;

        utilitas = new Utilitas(DCartPilih.getActivity()) ;
        pegawai = true ;
    }

    public AdapterCardPilih(ArrayList<TblPelanggan> data2, DCartPilih DCartPilih) {
        this.data2 = data2;
        this.DCartPilih = DCartPilih;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelanggan,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (pegawai){
            holder.tvPelanggan.setText(data.get(position).getPegawai());
            holder.tvAlamat.setText(data.get(position).getAlamatpegawai());
            holder.tvTelp.setText(data.get(position).getNohppegawai());
        } else {
            holder.tvPelanggan.setText(data2.get(position).getPelanggan());
            holder.tvAlamat.setText(data2.get(position).getAlamat());
            holder.tvTelp.setText(data2.get(position).getNohp());
        }
        
        holder.wOpsi.setVisibility(View.GONE);
        
        holder.wadah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pegawai){
                    DCartPilih.pilih(data.get(position));
                } else {
                    DCartPilih.pilih(data2.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (pegawai){
            return data.size() ;
        } else {
            return data2.size() ;
        }
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
