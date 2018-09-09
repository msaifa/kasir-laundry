package com.komputerkit.kasirlaundry.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.komputerkit.kasirlaundry.Fragment.TPilihProses;
import com.komputerkit.kasirlaundry.Model.QOrder;
import com.komputerkit.kasirlaundry.Model.TblProduk;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by msaifa on 27/01/2018.
 */

public class AdapterPilihProses extends RecyclerView.Adapter<AdapterPilihProses.ViewHolder>{

    ArrayList<QOrder> data ;
    Utilitas utilitas ;
    TPilihProses tPilihProses ;

    public AdapterPilihProses(ArrayList<QOrder> data, TPilihProses tPilihProses) {
        this.data = data;
        this.tPilihProses = tPilihProses;

        utilitas = new Utilitas(tPilihProses.getActivity()) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pilih_proses,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvHarga.setText(utilitas.removeE(data.get(position).getTotalorder()));
        holder.tvFaktur.setText(data.get(position).getFaktur());
        holder.tvPelanggan.setText(data.get(position).getPelanggan());
        holder.tvTanggal.setText(data.get(position).getTglorder());

        holder.wadah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tPilihProses.pindah(data.get(position)); ;
            }
        });

        holder.wOpsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilitas.showPopUp(v,R.menu.menu_hapus).setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.iHapus){
                            tPilihProses.hapus(data.get(position).getFaktur(),data.get(position).getIdorder());
                        } else if (item.getItemId() == R.id.iPrint){
                            tPilihProses.showPrint(data.get(position).getFaktur());
                        }
                        return false;
                    }
                }); ;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvPelanggan,tvHarga,tvFaktur,tvTanggal;
        ConstraintLayout wadah,wOpsi  ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvHarga = itemView.findViewById(R.id.tvHarga) ;
            tvFaktur = itemView.findViewById(R.id.tvFaktur) ;
            tvPelanggan = itemView.findViewById(R.id.tvPelanggan) ;
            tvTanggal = itemView.findViewById(R.id.tvTanggal) ;
            wadah = itemView.findViewById(R.id.wadah) ;
            wOpsi = itemView.findViewById(R.id.wOpsi) ;
        }
    }
}
