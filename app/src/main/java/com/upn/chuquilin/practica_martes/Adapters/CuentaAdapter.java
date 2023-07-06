package com.upn.chuquilin.practica_martes.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upn.chuquilin.practica_martes.CuentaDetallesActivity;
import com.upn.chuquilin.practica_martes.R;
import com.upn.chuquilin.practica_martes.entities.Cuenta;

import java.util.List;

public class CuentaAdapter extends RecyclerView.Adapter {

    private List<Cuenta> items;

    public CuentaAdapter(List<Cuenta> items){
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_cuenta,parent,false);
        NameViewHolder viewHolder = new NameViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Cuenta item = items.get(position);
        View view = holder.itemView;

        TextView tvNroIDCListar = view.findViewById(R.id.tvnNroIDCListar);
        TextView tvCuentaListar = view.findViewById(R.id.tvCuentaListar);
        TextView tvCuentaSincro = view.findViewById(R.id.tvCuentaSincro);

        tvNroIDCListar.setText(String.valueOf(item.id));
        tvCuentaListar.setText(item.nameCuenta);
        tvCuentaSincro.setText(String.valueOf(item.sincronizadoCuenta));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CuentaDetallesActivity.class);
                intent.putExtra("id",item.id);
                v.getContext().startActivity(intent);
                Log.d("APP_MAIN: IDCuenta", String.valueOf(item.id));
            }
        });



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
