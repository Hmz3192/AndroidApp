package com.example.lenovo.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.lenovo.app.GoodsInfoActivity;
import com.example.lenovo.home.activity.GoodsListActivity;
import com.example.lenovo.home.activity.GoodsMoreActivity;
import com.example.lenovo.home.activity.WantListActivity;
import com.example.lenovo.home.bean.GoodsInfo;
import com.example.lenovo.home.bean.ResultBeanData;
import com.example.lenovo.home.bean.Test;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.utils.Constants;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;
import com.zhy.magicviewpager.transformer.RotateYTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Hmz on 2017/7/6.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter {
    /*广告条幅类型*/
    public static final int BANNER = 0;
    /*频道类型*/
    public static final int CHANNEL = 1;
    /*求购信息*/
    public static final int WANT = 2;
    /*活动类型*/
    public static final int ACT = 3;
    /*秒杀类型*/
    public static final int SECKILL = 4;
    /*推荐类型*/
    public static final int RECOMMEND = 5;
    /*热卖类型*/
    public static final int HOT = 6;
    private static final String GOODSBEAN = "goodsbean";
    /*初始化布局*/
    private final LayoutInflater mLayoutInflater;
    private final Context mcontext;
    /*数据载体*/
    private final ResultBeanData.ResultBean resultBean;
    /*当前默认类型*/
    private int currentType = BANNER;



    public HomeFragmentAdapter(Context mcontext, ResultBeanData.ResultBean resultBean) {
        this.mcontext = mcontext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mcontext);
    }


    /*相当于getview 创建viewholder部分*/
    /*创建viewholder
    * parent：父类
    * viewType：当前类型*/
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannnerViewHolder(mcontext, mLayoutInflater.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannerViewHolder(mcontext, mLayoutInflater.inflate(R.layout.channel_item, null));
        } else if (viewType == ACT) {
            return new ActViewHolder(mcontext, mLayoutInflater.inflate(R.layout.act_item, null));
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(mcontext, mLayoutInflater.inflate(R.layout.seckill_item, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mcontext, mLayoutInflater.inflate(R.layout.recommend_item, null));
        } else if (viewType == HOT) {
            return new HotViewHolder(mcontext, mLayoutInflater.inflate(R.layout.hot_item, null));
        } else if (viewType == WANT) {
            return new WantViewHolder(mcontext, mLayoutInflater.inflate(R.layout.want_item, null));

        }

        return null;
    }

    /*相当于getview方法中的绑定数据模块*/
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannnerViewHolder bannnerViewHolder = (BannnerViewHolder) holder;
            bannnerViewHolder.setData(resultBean.getBanner_info());

        } else if (getItemViewType(position) == CHANNEL) {
            ChannerViewHolder channerViewHolder = (ChannerViewHolder) holder;
            channerViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());

        }else if (getItemViewType(position) == WANT) {

            WantViewHolder wantViewHolder = (WantViewHolder) holder;
//            wantViewHolder.setData(testList);
        }


    }

    class WantViewHolder extends RecyclerView.ViewHolder {
        private final Context mcontext;
        private ViewFlipper vf ;
        public WantViewHolder(final Context mcontext,View itemView) {
            super(itemView);
            this.mcontext = mcontext;
            vf = itemView.findViewById(R.id.vf);
            vf.addView(itemView.inflate(mcontext, R.layout.item_want, null));
            vf.addView(itemView.inflate(mcontext, R.layout.item_want2, null));
            vf.addView(itemView.inflate(mcontext, R.layout.item_want3, null));
            vf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, WantListActivity.class);
                    mcontext.startActivity(intent);
                }
            });

        }

        public void setData(List<Test> testList) {
//            adapter = new WantRecyleViewAdapter(mcontext, testList);
//            rc_list.setAdapter(adapter);

        }
    }




    class HotViewHolder extends RecyclerView.ViewHolder {

        private HotGridViewAdapter adpter;
        private final Context mcontext;
        private TextView tv_more_hot;
        private GridView gv_hot;

        public HotViewHolder(final Context mcontext, View itemView) {
            super(itemView);
            this.mcontext = mcontext;
            tv_more_hot = itemView.findViewById(R.id.tv_more_hot);
            gv_hot = itemView.findViewById(R.id.gv_hot);

        }

        public void setData(final List<ResultBeanData.ResultBean.HotInfoBean> data) {
            /*有数据*/
            /*设置GridView适配器*/
            adpter = new HotGridViewAdapter(mcontext, data);
            gv_hot.setAdapter(adpter);

            /*设置点击事件*/
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Toast.makeText(mcontext, "position" + i, Toast.LENGTH_LONG).show();
                    ResultBeanData.ResultBean.HotInfoBean hotInfoBean = data.get(i);
                    /*热卖部分商品信息*/
                    GoodsInfo goodsInfo = new GoodsInfo();
                    goodsInfo.setCover_price(hotInfoBean.getCover_price());
                    goodsInfo.setFigure(hotInfoBean.getFigure());
                    goodsInfo.setName(hotInfoBean.getName());
                    goodsInfo.setProduct_id(hotInfoBean.getProduct_id());

                    startGoodsInfoActivity(goodsInfo);

                }
            });

            tv_more_hot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, GoodsMoreActivity.class);
                    mcontext.startActivity(intent);
                }
            });
        }
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private Context mcontext;
        private TextView tv_more_recommend;
        private GridView gv_recommend;
        private RecommendGridViewAdapter adapter;


        public RecommendViewHolder(final Context mcontext, View itemView) {
            super(itemView);
            this.mcontext = mcontext;
            tv_more_recommend = itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = itemView.findViewById(R.id.gv_recommend);
        }

        public void setData(final List<ResultBeanData.ResultBean.RecommendInfoBean> data) {
            /*得到了数据*/
            /*设置适配器*/
            adapter = new RecommendGridViewAdapter(mcontext, data);
            gv_recommend.setAdapter(adapter);
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = data.get(i);
                    /*热卖部分商品信息*/
                    GoodsInfo goodsInfo = new GoodsInfo();
                    goodsInfo.setCover_price(recommendInfoBean.getCover_price());
                    goodsInfo.setFigure(recommendInfoBean.getFigure());
                    goodsInfo.setName(recommendInfoBean.getName());
                    goodsInfo.setProduct_id(recommendInfoBean.getProduct_id());

                    startGoodsInfoActivity(goodsInfo);

                }
            });

            tv_more_recommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, GoodsMoreActivity.class);
                    mcontext.startActivity(intent);
                }
            });
        }
    }


    /*相差多少时间--毫秒*/
    private long dt = 0;


    class SeckillViewHolder extends RecyclerView.ViewHolder {
        private final Context mcontext;
        private TextView tv_time_seckill;
        private TextView tv_more_seckill;
        private RecyclerView rv_seckill;
        private SeckillRecyleViewAdapter adapter;

        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dt = dt - 1000;
                SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
                String time = format.format(new Date(dt));
                tv_time_seckill.setText(time);

                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 1000);
                if (dt <= 0) {
                    /*把消息移除*/
                    handler.removeCallbacksAndMessages(null);
                }

            }
        };

        public SeckillViewHolder(Context mcontext, View itemView) {
            super(itemView);
            tv_more_seckill = itemView.findViewById(R.id.tv_more_seckill);
            tv_time_seckill = itemView.findViewById(R.id.tv_time_seckill);
            rv_seckill = itemView.findViewById(R.id.rv_seckill);
            this.mcontext = mcontext;
        }

        public void setData(final ResultBeanData.ResultBean.SeckillInfoBean data) {
            /*得到数据*/
            /*设置数据：文本，recycleview*/
            adapter = new SeckillRecyleViewAdapter(mcontext, data.getList());
            rv_seckill.setAdapter(adapter);
            /*设置布局管理器*/
            rv_seckill.setLayoutManager(new LinearLayoutManager(mcontext, LinearLayoutManager.HORIZONTAL, false));

            /*设置item的点击事件*/
            adapter.setOnSeckillRecylerView(new SeckillRecyleViewAdapter.OnSeckillRecylerView() {
                @Override
                public void onItemClick(int postion) {
                    ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = data.getList().get(postion);
                    /*热卖部分商品信息*/
                    GoodsInfo goodsInfo = new GoodsInfo();
                    goodsInfo.setCover_price(listBean.getCover_price());
                    goodsInfo.setFigure(listBean.getFigure());
                    goodsInfo.setName(listBean.getName());
                    goodsInfo.setProduct_id(listBean.getProduct_id());

                    startGoodsInfoActivity(goodsInfo);
                }
            });
            /*秒杀倒计时*/
            dt = (Integer.valueOf(data.getEnd_time()) - Integer.valueOf(data.getStart_time()));
            handler.sendEmptyMessageDelayed(0, 1000);

            tv_more_seckill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, GoodsMoreActivity.class);
                    mcontext.startActivity(intent);
                }
            });
        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder {

        private Context mcontext;
        private ViewPager act_viewpager;
        private List<ResultBeanData.ResultBean.ActInfoBean> data;

        public ActViewHolder(Context mcontext, View itemView) {
            super(itemView);
            this.mcontext = mcontext;
            act_viewpager = itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
            act_viewpager.setPageMargin(20);
            act_viewpager.setOffscreenPageLimit(3);//>=3

            //setPageTransformer 决定动画效果
            act_viewpager.setPageTransformer(true, new RotateYTransformer());
//                    RotateDownPageTransformer(new AlphaPageTransformer(new ScaleInTransformer())));
            /*获得数据*/
            /*设置适配器,内部类生成了*/
            act_viewpager.setAdapter(new PagerAdapter() {
                /*返回总条数*/
                @Override
                public int getCount() {
                    return act_info.size();
                }

                /*view : 页面
                * object：instantiateitem方法返回的值*/

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;

                }


                /*container：viewpager
                * postion: 对应页面的位置*/
                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    ImageView imageView = new ImageView(mcontext);
                    /*拉伸*/
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    /*加载图片*/
                    Glide.with(mcontext).load(Constants.BASE_URL_IMAGE + act_info.get(position).getIcon_url()).into(imageView);
                    /*添加到容器*/
                    container.addView(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Toast.makeText(mcontext, "position" + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return imageView;
                }

                /*销毁*/
                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });

        }
    }

    class ChannerViewHolder extends RecyclerView.ViewHolder {
        private Context mcontext;
        private GridView gcChannel;
        private ChannelAdapter adapter;

        public ChannerViewHolder(final Context mcontext, View itemView) {
            super(itemView);
            this.mcontext = mcontext;
            gcChannel = itemView.findViewById(R.id.gv_channel);
            //设置item的点击事件
            gcChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
//                    Toast.makeText(mcontext, "position" + position, Toast.LENGTH_SHORT).show();
                    if (position <= 9) {
                        Intent intent = new Intent(mcontext, GoodsListActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("yes", "1");
                        mcontext.startActivity(intent);
                    } else {
//                        Toast.makeText(mcontext, "no choice", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> data) {
            /*得到数据了*/
            /*设置gridview适配器*/
            adapter = new ChannelAdapter(mcontext, data);
            gcChannel.setAdapter(adapter);


        }
    }

    class BannnerViewHolder extends RecyclerView.ViewHolder {
        private Context context;

        private Banner banner;

        public BannnerViewHolder(Context mcontext, View itemview) {
            super(itemview);
            this.context = mcontext;
            this.banner = (Banner) itemview.findViewById(R.id.banner);
        }

        public void setData(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
            /*设置banner的数据*/
            /*得到图片集合地址*/
            List<String> imagesUrl = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                String imageUrl = banner_info.get(i).getImage();
                imagesUrl.add(imageUrl);
            }
            /*设置循环指示点*/
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            /*设置动画效果*/
            banner.setBannerAnimation(Transformer.Accordion);
            /*加载回调图片*/
            banner.setImages(imagesUrl, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    /*联网请求图片--Glide*/
                    Glide.with(mcontext).load(Uri.parse(Constants.BASE_URL_IMAGE + url)).into(view);
                }
            });


            /*设置item的点击事件*/
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
//                    Toast.makeText(mcontext, "postion== " + (position), Toast.LENGTH_LONG).show();
//                    startGoodsInfoActivity(goodsInfo);
                }
            });
        }


    }

    /*启动商品详情页面*/
    private void startGoodsInfoActivity(GoodsInfo goodsInfo) {
        Intent intent = new Intent(mcontext, GoodsInfoActivity.class);
        intent.putExtra(GOODSBEAN, goodsInfo);
        mcontext.startActivity(intent);

    }



    /*得到不同类型*/
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case WANT:
                currentType = WANT;
                break;

            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    /*总共有多少个item*/
    @Override
    public int getItemCount() {
        /*开发过程中从1-->2*/
        return 7;
    }

}
