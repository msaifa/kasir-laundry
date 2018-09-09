package com.komputerkit.kasirlaundry.Adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.komputerkit.kasirlaundry.Fragment.TPilihProduk;
import com.komputerkit.kasirlaundry.Model.TblProduk;
import com.komputerkit.kasirlaundry.R;
import com.komputerkit.kasirlaundry.Utilitas.Utilitas;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.text.DecimalFormat ;

/**
 * Created by msaifa on 23/01/2018.
 */

public class AdapterPilihProduk extends RecyclerView.Adapter<AdapterPilihProduk.ViewHolder>{

    ArrayList<TblProduk> data ;
    Utilitas utilitas ;
    TPilihProduk tPilihProduk ;
    boolean decimal  ;

    public AdapterPilihProduk(ArrayList<TblProduk> data, TPilihProduk tPilihProduk, boolean decimal) {
        this.data = data;
        this.tPilihProduk = tPilihProduk;
        this.decimal = decimal;

        utilitas = new Utilitas(tPilihProduk.getActivity()) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pilih_produk,parent,false) ;
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvProduk.setText(data.get(position).getProduk());
        holder.tvHarga.setText(Utilitas.removeE(data.get(position).getHarga()) + " /" + data.get(position).getKategori());
        holder.tvJumlah.setText(data.get(position).getJumlah());
        holder.etketerangan.setText(data.get(position).getKeterangan());

        double jum = utilitas.strToDouble(data.get(position).getJumlah()) ;
        if (jum > 0){
            holder.imgMin.setVisibility(View.VISIBLE);
        } else {
            holder.imgMin.setVisibility(View.GONE);
        }

        if (data.get(position).getFlagAktif().equals("0")){
            holder.wadah.setLayoutParams(new ViewGroup.LayoutParams(0,0));
        } else {
            holder.wadah.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        holder.imgMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat df = new DecimalFormat("#.####");
                df.setRoundingMode(RoundingMode.CEILING);

                double jum = utilitas.strToDouble(data.get(position).getJumlah()) ;
                double harga = utilitas.strToDouble(data.get(position).getHarga()) ;
                boolean kilo = false ;

                if (data.get(position).getKategori().equals("Kiloan") && decimal){
                    jum -= 0.1 ;
                    harga *= 0.1 ;
                    kilo = true ;
                } else {
                    jum -= 1 ;
                }

                data.get(position).setJumlah(df.format(jum).replace(",","."));
                holder.tvJumlah.setText(df.format(jum).replace(",","."));

                if (jum > 0){
                    holder.imgMin.setVisibility(View.VISIBLE);
                } else {
                    holder.imgMin.setVisibility(View.GONE);
                }

                tPilihProduk.min(data,harga,kilo);
            }
        });

        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat df = new DecimalFormat("#.####");
                df.setRoundingMode(RoundingMode.CEILING);

                double jum = utilitas.strToDouble(data.get(position).getJumlah()) ;
                double harga = utilitas.strToDouble(data.get(position).getHarga()) ;
                boolean kilo = false ;

                if (data.get(position).getKategori().equals("Kiloan") && decimal){
                    jum += 0.1 ;
                    harga *= 0.1 ;
                    kilo = true ;
                } else {
                    jum += 1 ;
                }

                data.get(position).setJumlah(df.format(jum).replace(",","."));
                holder.tvJumlah.setText(df.format(jum).replace(",","."));

                if (jum > 0){
                    holder.imgMin.setVisibility(View.VISIBLE);
                } else {
                    holder.imgMin.setVisibility(View.GONE);
                }

                tPilihProduk.plus(data,harga,kilo);
            }
        });

        holder.etketerangan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String note = s.toString() ;
                data.get(position).setKeterangan(note);

                tPilihProduk.note(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvProduk,tvHarga,tvJumlah;
        ImageView imgMin, imgPlus ;
        EditText etketerangan ;
        ConstraintLayout wadah ;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProduk = itemView.findViewById(R.id.tvProduk) ;
            tvHarga = itemView.findViewById(R.id.tvHarga) ;
            tvJumlah = itemView.findViewById(R.id.tvJumlah) ;
            imgMin = itemView.findViewById(R.id.imgMin) ;
            imgPlus = itemView.findViewById(R.id.imgPlus) ;
            etketerangan = itemView.findViewById(R.id.etKeterangan) ;
            wadah = itemView.findViewById(R.id.wadah) ;
        }
    }

    public void setArray(ArrayList<TblProduk> array){
        data = array ;
        notifyDataSetChanged() ;
    }
}