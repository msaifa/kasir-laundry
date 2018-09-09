package com.komputerkit.kasirlaundry.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.komputerkit.kasirlaundry.Fragment.Laporan.LPencucian;
import com.komputerkit.kasirlaundry.Model.QOrder;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.util.ArrayList;

/**
 * Created by msaifa on 25/01/2018.
 */

public class AdapterLapPencucian extends RecyclerView.Adapter<AdapterLapPencucian.ViewHolder>{

    ArrayList<QOrder> data ;
    LPencucian lPencucian ;

    public AdapterLapPencucian(LPencucian lPencucian,ArrayList<QOrder> data) {
        this.lPencucian = lPencucian ;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lap_transaksi ,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvPelanggan.setText(data.get(position).getPelanggan());
        holder.tvPegawai.setText(data.get(position).getPegawai());
        holder.tvTanggal.setText(data.get(position).getFaktur() + "\t" + data.get(position).getTglorder());
        holder.tvJumlah.setText(Utilitas.removeE(data.get(position).getTotalorder())) ;

        holder.wOpsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilitas utilitas = new Utilitas(lPencucian.getActivity()) ;
                utilitas.showPopUp(v,R.menu.menu_hapus).setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.iHapus){
                            lPencucian.hapus(data.get(position).getFaktur(),data.get(position).getIdorder());
                        } else if (item.getItemId() == R.id.iPrint){
                            lPencucian.showPrint(data.get(position).getFaktur());
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

        TextView tvPelanggan,tvPegawai,tvTanggal,tvJumlah ;
        ConstraintLayout wOpsi ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvPelanggan = itemView.findViewById(R.id.tvPelanggan) ;
            tvPegawai = itemView.findViewById(R.id.tvPegawai) ;
            tvTanggal = itemView.findViewById(R.id.tvTanggal) ;
            tvJumlah = itemView.findViewById(R.id.tvHarga) ;
            wOpsi    = itemView.findViewById(R.id.wOpsi) ;
        }
    }
}