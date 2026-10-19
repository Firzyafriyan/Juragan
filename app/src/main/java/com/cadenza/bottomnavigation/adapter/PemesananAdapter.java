package com.cadenza.bottomnavigation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cadenza.bottomnavigation.DetailShopActivity;
import com.cadenza.bottomnavigation.R;
import com.cadenza.bottomnavigation.model.PemesananModel;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PemesananAdapter extends RecyclerView.Adapter<PemesananAdapter.ViewHolder> {
    private List<PemesananModel> dataList;
    private Context context;

    public PemesananAdapter(Context context, List<PemesananModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PemesananModel model = dataList.get(position);

        holder.namaPaketTextView.setText(model.getNamaPaket());
        holder.keberangkatanTextView.setText(" " + formatTanggal(model.getKeberangkatan()));
        holder.hargaTextView.setText("Harga \n" + formatRupiah(model.getHarga()));
        holder.lamaWaktuTextView.setText(" "+model.getLamaWaktu() + " hari");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailShopActivity.class);

                // Perbaiki pemanggilan metode pada objek model, bukan dataList
                intent.putExtra("id", model.getId());
                intent.putExtra("id_paket", model.getIdPaket());
                intent.putExtra("nama_paket", model.getNamaPaket());
                intent.putExtra("keberangkatan", model.getKeberangkatan());
                intent.putExtra("pilihan_paket", model.getPilihanPaket());
                intent.putExtra("harga", model.getHarga());
                intent.putExtra("lama_waktu", model.getLamaWaktu());

                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaPaketTextView, keberangkatanTextView, hargaTextView, lamaWaktuTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPaketTextView = itemView.findViewById(R.id.txt_nama_paket);
            hargaTextView = itemView.findViewById(R.id.txt_harga_paket);
            keberangkatanTextView = itemView.findViewById(R.id.txt_waktu_berangkat);
            lamaWaktuTextView = itemView.findViewById(R.id.txt_lama_waktu);
        }
    }

    private String formatTanggal(String tanggal) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(tanggal);
            SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
            return output.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return tanggal;
        }
    }
    private String formatRupiah(String harga) {
        try {
            // Mengubah string harga ke dalam bentuk double
            double hargaDouble = Double.parseDouble(harga);

            // Menggunakan NumberFormat untuk memformat mata uang Rupiah
            NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            return rupiahFormat.format(hargaDouble);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return harga;
        }
    }
}
