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

/**
 * User--Hu mingzhi on 2017/9/21.
 * Created by ThinKPad
 */

public class MyAdapterViewAdapter4 extends RecyclerView.Adapter<MyAdapterViewAdapter4.MyViewHolder> {


    private final Context mContext;
    private onRecyclerItemClick mOnRecyclerItemClick;
    private final List<EssayBean.ResultBean.DaiGouBean> tests;


    public MyAdapterViewAdapter4(Context context, List<EssayBean.ResultBean.DaiGouBean> tests) {
        mContext = context;
        this.tests = tests;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.want_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

//        MyViewHolder viewHolder = (MyViewHolder) holder;
        EssayBean.ResultBean.DaiGouBean daiGouBean = tests.get(position);

        File file = new File(daiGouBean.getFigure());
//        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + daiGouBean.getFigure()).into(holder.imageView3);
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(daiGouBean.getFigure());
            Bitmap zoom = BitmapUtils.zoom(bm, 130, 130);
            bm.recycle();
            holder.imageView3.setImageBitmap(zoom);
        }
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE + daiGouBean.getFigure()).into(holder.imageView3);
        holder.tv_inside.setText(daiGouBean.getEssay().substring(0,5)+"......");
        holder.tv_title.setText(daiGouBean.getTitle().substring(0,4)+"...");
        holder.tv_date.setText(daiGouBean.getTime());
        holder.tv_name.setText("来自"+daiGouBean.getUser());

//        viewHolder.setData(tests.get(position),mContext);
        holder.ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "postion4"+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, WantDetailActivity.class);
                intent.putExtra("postion", String.valueOf(position));
                intent.putExtra("kind", "3");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

   class MyViewHolder extends RecyclerView.ViewHolder{

        private EssayBean.ResultBean.DaiGouBean datas;
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
