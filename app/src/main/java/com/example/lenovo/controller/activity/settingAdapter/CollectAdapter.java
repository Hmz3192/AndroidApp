package com.example.lenovo.controller.activity.settingAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.controller.activity.SettingDB.CollectDao;
import com.example.lenovo.controller.activity.settingActivity.CollectActivity;
import com.example.lenovo.home.bean.GoodsInfo;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.Constants;

import java.util.List;

/**
 * User--Hu mingzhi on 2017/8/6.
 * Created by ThinKPad
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<GoodsInfo> datas;
    private final CollectActivity mcontext;
    private CollectDao collectDao;

    public CollectAdapter(CollectActivity collectActivity, List<GoodsInfo> dataFromDB) {
        this.mcontext = collectActivity;
        this.datas = dataFromDB;
        collectDao = new CollectDao(mcontext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mcontext, R.layout.collect_good, null));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView collect_pic;
        private TextView collect_name;
        private TextView collec_ori;
        private TextView col_now_price;
        private GoodsInfo data;
        private Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            collect_pic = (ImageView) itemView.findViewById(R.id.collect_pic);
            collect_name = (TextView) itemView.findViewById(R.id.collect_name);
            collec_ori = (TextView) itemView.findViewById(R.id.collec_ori);
            col_now_price = itemView.findViewById(R.id.col_now_price);

            delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(this);
        }

        public void setData(GoodsInfo data) {
            this.data = data;
            Glide.with(mcontext).load(Constants.BASE_URL_IMAGE +data.getFigure()).into(collect_pic);
            collect_name.setText(data.getName());
            col_now_price.setText("$" + data.getCover_price());
            collec_ori.setText("$" + data.getCover_price());


        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.delete:
                    int pos = getAdapterPosition();
                    collectDao.updateById(data.getProduct_id(), "1");
                    datas.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(0,datas.size());

                    break;

            }
        }
    }
}
