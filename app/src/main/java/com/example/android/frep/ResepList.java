package com.example.android.frep;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Go on 24 Oct 2017.
 */

public class ResepList extends ArrayAdapter<resepNusantara> {

    private Activity context;
    List<resepNusantara> listResepNusantara;

    public ResepList(Activity context, List<resepNusantara> listResepNusantara) {
        super(context,R.layout.layout_resep,listResepNusantara);
        this.context = context;
        this.listResepNusantara = listResepNusantara;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_resep, null, true);

        TextView judulResep = (TextView) listViewItem.findViewById(R.id.judulResep);
        TextView ketResep = (TextView) listViewItem.findViewById(R.id.ketResep);

        resepNusantara resepNusantara = listResepNusantara.get(position);
        judulResep.setText(resepNusantara.getNama());
        ketResep.setText(resepNusantara.getKeteranganResep());

        return listViewItem;
    }
}
