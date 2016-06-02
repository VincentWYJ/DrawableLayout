package com.example.drawlayouttest;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.bumptech.glide.Glide;

/**
 * Created by Jun on 2015/5/18.
 */
public abstract class SimpleBaseAdapter<T> extends BaseAdapter {

    public Context mContext;
    public LayoutInflater mInflater;
    public List<T> mData;
    public int layoutId;

    public SimpleBaseAdapter(Context context,int layoutId,List<T> data){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
        this.mData = data;
    }
    
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
                layoutId, position);

        getItemView(holder, getItem(position));

        return holder.getConvertView();
    }
    public abstract void getItemView(ViewHolder holder, T t);


    public void addAll(List<T> elem) {
        mData.addAll(elem);
        notifyDataSetChanged();
    }

    public void add(T elem) {
        mData.add(elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        mData.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mData.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        mData.clear();
        if (elem != null) {
            mData.addAll(elem);
        }
        notifyDataSetChanged();
    }


    /**
     * 通用ViewHolder�?
     */
    public static class ViewHolder{
        private  SparseArray<View> mViews;
        private int position;
        private  View mConvertView;
        private Context mContext;

        public ViewHolder(Context context,ViewGroup parent,int layoutId,int position){
            this.mContext = context;
            this.position = position;
            this.mViews = new SparseArray<View>();
            mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
            mConvertView.setTag(this);
        }

        public static ViewHolder get(Context context,View convertView,
                                     ViewGroup parent,int layoutId,int position){
            if(convertView == null){
                return  new ViewHolder(context,parent,layoutId,position);
            }else{
                ViewHolder holder = (ViewHolder) convertView.getTag();
                holder.position = position;
                return  holder;
            }
        }

        public View getConvertView(){
            return mConvertView;
        }

        /**
         * 通过viewId获取控件
         * @param viewId
         * @param <T>
         * @return
         */
        public  <T extends View> T getView(int viewId){
            View view = mViews.get(viewId);

            if(view == null){
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId,view);
            }
            return (T) view;
        }

        /**
         * 给TextVIew控件赋�??
         * @param viewId 控件Id
         * @param text �?
         * @return
         */
        public ViewHolder setText(int viewId,String text){
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }

        /**
         * 给ImageView控件赋�??
         * @param viewId 控件Id
         * @param resourceId 资源Id
         * @return
         */
        public ViewHolder setImageResource(int viewId,int resourceId){
            ImageView imageView = getView(viewId);
            imageView.setImageResource(resourceId);
            return  this;
        }

        /**
         * 给ImageView控件赋�??
         * @param viewId 控件Id
         * @param bitmap 图像位图
         * @return
         */
        public ViewHolder setImageBitmap(int viewId,Bitmap bitmap){
            ImageView imageView = getView(viewId);
            imageView.setImageBitmap(bitmap);
            return  this;
        }

        /**
         * 给ImageView控件赋�??
         * @param viewId 控件Id
         * @param url 图片链接地址
         * @return
         */
        public ViewHolder setImageURL(int viewId,String url){
            ImageView imageView = getView(viewId);
            Glide.with(mContext)
                    .load(url)
                    .centerCrop()
                    .into(imageView);

            return this;
        }

    }
}


