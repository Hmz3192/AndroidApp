package com.example.lenovo.home.adapter.TabAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.home.activity.WantDetailActivity;
import com.example.lenovo.home.bean.EssayBean;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.BitmapUtils;
import com.example.lenovo.utils.Constants;

import java.io.File;
import java.util.List;

import static android.R.attr.data;

/**
 * User--Hu mingzhi on 2017/9/21.
 * Created by ThinKPad
 */

public class MyAdapterViewAdapter3 extends RecyclerView.Adapter<MyAdapterViewAdapter3.MyViewHolder> {

    private final Context mContext;
    private final List<EssayBean.ResultBean.NewSellBean> newSellBeanList;
    private onRecyclerItemClick mOnRecyclerItemClick;

    public MyAdapterViewAdapter3(Context context, List<EssayBean.ResultBean.NewSellBean> newSellBeanList) {
        mContext = context;
        this.newSellBeanList = newSellBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.want_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.setData(newSellBeanList.get(position),mContext);
        viewHolder.ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "postion3"+position, Toast.LENGTH_SHORT).show();
//                int pos = holder.getPosition();
                Intent intent = new Intent(mContext, WantDetailActivity.class);
                intent.putExtra("postion", String.valueOf(position));
                intent.putExtra("kind", "2");
                mContext.startActivity(intent);
//                mOnRecyclerItemClick.onItemClick(view,pos);
            }
        });

        /*if (mOnRecyclerItemClick != null){
            if(!holder.itemView.hasOnClickListeners()){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getPosition();
                        mOnRecyclerItemClick.onItemClick(v,pos);
                    }
                });
            }
        }*/
    }

    @Override
    public int getItemCount() {
        return newSellBeanList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private EssayBean.ResultBean.NewSellBean datas;
        private TextView tv_title;
        private TextView tv_inside;
        private ImageView imageView3;
        private LinearLayout ll_all;
        private TextView tv_date,tv_name;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv_inside = itemView.findViewById(R.id.tv_inside);
            tv_title = itemView.findViewById(R.id.tv_title);
            imageView3 = itemView.findViewById(R.id.imageView3);
            ll_all = itemView.findViewById(R.id.ll_all);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_name = itemView.findViewById(R.id.tv_name);

        }

        public void setData(EssayBean.ResultBean.NewSellBean newSellBean, Context mContext) {
            this.datas = newSellBean;
            File file = new File(datas.getFigure());
//        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + daiGouBean.getFigure()).into(holder.imageView3);
            if (file.exists()) {
                Bitmap bm = BitmapFactory.decodeFile(datas.getFigure());
                Bitmap zoom = BitmapUtils.zoom(bm, 130, 130);
                bm.recycle();
                imageView3.setImageBitmap(zoom);
            }
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE + datas.getFigure()).into(imageView3);
            tv_inside.setText(datas.getEssay().substring(0,5)+"......");
            tv_title.setText(datas.getTitle().substring(0,4)+"...");
            tv_date.setText(datas.getTime());
            tv_name.setText("来自"+datas.getUser());
        }
    }
    public interface onRecyclerItemClick {
        void onItemClick(View view, int position);
    }
    public void setOnRecyclerItemClick(onRecyclerItemClick mOnRecyclerItemClick) {
        this.mOnRecyclerItemClick = mOnRecyclerItemClick;
    }
   /* private List<Test> datas;
    private Context mcontext;
    private OnItemClickListener onItemClickListener;


    public MyAdapterViewAdapter(Context mcontext, List<Test> datas) {
        this.mcontext = mcontext;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mcontext).inflate(R.layout.want_list, parent, false);
        return  new ViewHolder(mView);
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



    class ViewHolder extends RecyclerView.ViewHolder {


        private Test datas;
        private TextView tv_title;
        private TextView tv_inside;
        private ImageView imageView3;


        public ViewHolder(View itemView) {
            super(itemView);
            initView(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.setOnItemClickListener(getLayoutPosition());
                    }
                }
            });
        }

        private void initView(View itemView) {
            tv_inside = itemView.findViewById(R.id.tv_inside);
            tv_title = itemView.findViewById(R.id.tv_title);
            imageView3 = itemView.findViewById(R.id.imageView3);


        }

        public void setData(Test data) {
            this.datas = data;
//            Glide.with(mcontext).load(Constants.BASE_URL_IMAGE + datas.getUrl()).into(order_pic);
            tv_inside.setText(datas.getMes());
            tv_title.setText(datas.getTitle());


        }

    }



    public interface OnItemClickListener {
        void setOnItemClickListener(int postion);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }*/
}
