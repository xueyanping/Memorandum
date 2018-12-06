package com.xue.yado.memorandum.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xue.yado.memorandum.Constant;
import com.xue.yado.memorandum.R;
import com.xue.yado.memorandum.entity.Memoire;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/3.
 */

public class TypeListAdapter extends BaseAdapter{

    private List<String> data = new ArrayList<>();
    private Context context;
    private Memoire memoire;
    public TypeListAdapter(Context context,Memoire memoire) {
        this.context = context;
        this.memoire = memoire;
    }

    public void setData(List<String> data){
        this.data.clear();
        this.data.addAll(data);

    }

    @Override
    public int getCount() {
       // Log.i( "getCount: ","size=="+data.size());
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            view = LayoutInflater.from(context).inflate(R.layout.type_list_adapter_layout, viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.type_list_adapter_image = view.findViewById(R.id.type_list_adapter_image);
            viewHolder.type_list_adapter_check = view.findViewById(R.id.type_list_adapter_check);
            viewHolder.type_list_adapter_text = view.findViewById(R.id.type_list_adapter_text);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        if(memoire!=null){
            Log.i("TypeListAdapter", "getView: "+memoire.getType());
            for( int a = 0;a < data.size();a++){
                if(data.get(i).toString().equals(memoire.getType())){
                    viewHolder.type_list_adapter_check.setImageResource(R.mipmap.single_check_ed);
                }else{
                   viewHolder.type_list_adapter_check.setImageResource(R.mipmap.single_check);
                }
            }

        }
            viewHolder.type_list_adapter_image.setImageResource(R.mipmap.biaoqian1);
            viewHolder.type_list_adapter_text.setText(data.get(i));
        return view;
    }

    class ViewHolder{
        ImageView type_list_adapter_image,type_list_adapter_check;
        TextView type_list_adapter_text;
    }
}
