package com.upn.chuquilin.practica_martes.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.upn.chuquilin.practica_martes.CuentaDetallesActivity;
import com.upn.chuquilin.practica_martes.R;
import com.upn.chuquilin.practica_martes.entities.Cuenta;
import com.upn.chuquilin.practica_martes.entities.Movimientos;

import java.util.List;

public class MovimientoAdapter extends RecyclerView.Adapter {
    private List<Movimientos> items;

    public MovimientoAdapter(List<Movimientos> items){
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_movimiento,parent,false);
        MovimientoAdapter.NameViewHolder viewHolder = new MovimientoAdapter.NameViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movimientos item = items.get(position);
        View view = holder.itemView;

        TextView tvTipoDetallesMov = view.findViewById(R.id.tvTipoDetallesMov);
        TextView tvMontoDetallesMov = view.findViewById(R.id.tvMontoDetallesMov);
        TextView tvMotivoDetallesMov = view.findViewById(R.id.tvMotivoDetallesMov);
        TextView tvLatitudDetMov = view.findViewById(R.id.tvLatitudDetMov);
        TextView tvLongitudDetMov = view.findViewById(R.id.tvLongitudDetMov);
        TextView tvUrlDetMov = view.findViewById(R.id.tvUrlDetMov);
        ImageView ivImagenMovimiento = view.findViewById(R.id.ivImagenItemMov);

        tvTipoDetallesMov.setText(item.tipoMovimiento);
        tvMontoDetallesMov.setText(String.valueOf(item.monto));
        tvMotivoDetallesMov.setText(item.motivo);
        tvLatitudDetMov.setText(item.latitud);
        tvLongitudDetMov.setText(item.longitud);
        tvUrlDetMov.setText(item.urlimagen);
        Picasso.get().load(item.urlimagen).into(ivImagenMovimiento);



//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), CuentaDetallesActivity.class);
//                intent.putExtra("idM",item.id);
//                v.getContext().startActivity(intent);
//                Log.d("APP_MAIN: IDMovi", String.valueOf(item.id));
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        Paisajes item = items.get(position);
//        return item == null ? 0 : 1;
//    }

    public class NameViewHolder extends RecyclerView.ViewHolder {
        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
