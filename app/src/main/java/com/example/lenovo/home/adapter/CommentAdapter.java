package com.example.lenovo.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lenovo.home.bean.Comment;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.BitmapUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * User--Hu mingzhi on 2017/9/23.
 * Created by ThinKPad
 */

public class CommentAdapter extends RecyclerView.Adapter {


    private static Context mcontext;
    private final List<Comment> datas;

    public CommentAdapter(Context application, List<Comment> commentList) {
        this.mcontext = application;
        this.datas = commentList;

    }



    /**
     * 添加一条评论,刷新列表
     * @param comment
     */
    public void addComment(Comment comment) {
        datas.add(comment);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentAdapter.ViewHolder(View.inflate(mcontext, R.layout.item_comment, null));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentAdapter.ViewHolder viewHolder = (CommentAdapter.ViewHolder) holder;
        viewHolder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    /**
     * 静态类，便于GC回收
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_date,tv_comm;
        ImageButton iv_photo;

        public ViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_comm = (TextView) itemView.findViewById(R.id.tv_comm);
            iv_photo = itemView.findViewById(R.id.iv_photo);
        }

        public void setData(Comment comment) {
            // 适配数据
        tv_name.setText(comment.getUserName());
        tv_date.setText(comment.getTime());
        tv_comm.setText(comment.getComm());
//        Glide.with(mcontext).load(Constants.BASE_URL_IMAGE + datas.get(i).getUrl()).into(holder.iv_photo);
        try {
//                int avatarResId = Integer.parseInt(user.getAvatar());
            Picasso.with(mcontext)
                    .load(comment.getUrl())
//                    .networkPolicy(NetworkPolicy.NO_CACHE)
//                    .memoryPolicy(MemoryPolicy.NO_CACHE)//不加载缓存
                    .transform(new Transformation() {
                        @Override
                        public Bitmap transform(Bitmap bitmap) {
                            //先对图片进行压缩
//                Bitmap zoom = BitmapUtils.zoom(bitmap, DensityUtil.dip2px(mContext, 62), DensityUtil.dip2px(mContext, 62));
                            Bitmap zoom = BitmapUtils.zoom(bitmap, 90, 90);
                            //对请求回来的Bitmap进行圆形处理
                            Bitmap ciceBitMap = BitmapUtils.circleBitmap(zoom);
                            bitmap.recycle();//必须队更改之前的进行回收
                            return ciceBitMap;
                        }

                        @Override
                        public String key() {
                            return "";
                        }
                    }).into(iv_photo);
        }catch (Exception e) {
            //use default avatar
//                Glide.with(context).load(username).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_appitem_del_btn_normal).into(imageView);
            Picasso.with(mcontext).load(R.drawable.human).transform(new Transformation() {
                @Override
                public Bitmap transform(Bitmap bitmap) {
                    //先对图片进行压缩
//                Bitmap zoom = BitmapUtils.zoom(bitmap, DensityUtil.dip2px(mContext, 62), DensityUtil.dip2px(mContext, 62));
                    Bitmap zoom = BitmapUtils.zoom(bitmap, 90, 90);
                    //对请求回来的Bitmap进行圆形处理
                    Bitmap ciceBitMap = BitmapUtils.circleBitmap(zoom);
                    bitmap.recycle();//必须队更改之前的进行回收
                    return ciceBitMap;
                }

                @Override
                public String key() {
                    return "";
                }
            }).into(iv_photo);
        }
        }
    }
}
