package com.cadenza.bottomnavigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView txtNik, txtNama, txtLogout;

    private ImageView btnLogout;
    private SharedPreferences sharedPreferences;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // inisialisasi berdasarkan ID
        TextView btnEdit = rootView.findViewById(R.id.btnEdit);
        txtNama = rootView.findViewById(R.id.txtNama);
        txtNik = rootView.findViewById(R.id.txtNik);
        btnLogout = rootView.findViewById(R.id.btnLogout);
        txtLogout = rootView.findViewById(R.id.txtLogout);
        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE); // Inisialisasi sharedPreferences
//        Toast.makeText(requireActivity(), sharedPreferences.getString("NIK", ""), Toast.LENGTH_SHORT).show();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Menghapus data yang ada di SharedPreferences
                editor.apply();
                startActivity(new Intent(requireActivity(), LogJamaahActivity.class));
                requireActivity().finish();
            }
        });
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Menghapus data yang ada di SharedPreferences
                editor.apply();
                startActivity(new Intent(requireActivity(), LogJamaahActivity.class));
                requireActivity().finish();
            }
        });

        // Ambil data dari SharedPreferences
        String nama_lengkap = sharedPreferences.getString("nama_lengkap", ""); // Ganti "nama" sesuai dengan nama yang digunakan saat menyimpan data
        String NIK = sharedPreferences.getString("NIK", ""); // Ganti "no_telp" sesuai dengan nama yang digunakan saat menyimpan data


        // Set teks pada TextView dengan data dari SharedPreferences
        txtNama.setText(nama_lengkap);
        txtNik.setText(NIK);
        return rootView;
    }


}
