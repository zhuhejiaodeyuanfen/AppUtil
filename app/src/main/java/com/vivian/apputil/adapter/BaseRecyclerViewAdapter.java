package com.vivian.apputil.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vivianWQ on 2017/5/18
 * Mail: wangqi_vivian@sina.com
 * <p>
 * 建议使用recyclerView  封装出了数据处理类
 * desc: RecyclerView的baseAdapter
 * Version: 1.0
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {


    /**
     * adapter itemClick点击事件回调
     */
    public interface IOnItemClick {
        void onItemClick(View view, int position);
    }

    protected List<T> listAllData = new ArrayList<>();

    private IOnItemClick iOnItemClick;

    public BaseRecyclerViewAdapter(Context context) {
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return new BaseViewHolder(view, iOnItemClick);
    }

    public List<T> getAllData() {
        return listAllData;
    }

    /**
     * 设置Item点击监听
     *
     * @param iOnItemClick
     */
    public void setOnItemClickListener(IOnItemClick iOnItemClick) {
        this.iOnItemClick = iOnItemClick;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, final int position) {

        bindData(holder, position);

    }

    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            bindData(holder, position);

        }
    }

    public void set(int position, T data) {
        if (listAllData.size() > 0 && listAllData.size() > position) {
            listAllData.set(position, data);
            notifyDataSetChanged();
        }
    }

    /**
     * 刷新数据
     *
     * @param newData
     */
    public void refresh(List<T> newData) {
        this.listAllData.clear();
        this.listAllData.addAll(newData);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (listAllData != null) {
            return listAllData.get(position);
        } else
            return null;
    }

    /**
     * 删除某一位置的元素
     *
     * @param position
     */
    public void removeItem(int position) {
        if (listAllData != null) {
            listAllData.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listAllData.size());
            notifyDataSetChanged();
        }
    }


    /**
     * 添加数据
     *
     * @param newData
     */
    public void addData(List<T> newData) {
        this.listAllData.addAll(newData);
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clear() {
        this.listAllData.clear();
        notifyDataSetChanged();
    }

    /**
     * 添加单一数据
     *
     * @param data
     */
    public void addData(T data) {
        this.listAllData.add(data);
        notifyDataSetChanged();
    }

    /**
     * 添加单一数据
     *
     * @param data
     */
    public void addData(T data, boolean isRefresh) {
        this.listAllData.add(data);
        if (isRefresh)
            notifyDataSetChanged();
    }

    public void refreshData() {
        notifyDataSetChanged();
    }


    public void setDatas(List<T> listData) {
        if (this.listAllData != null)
            this.listAllData.clear();
        this.listAllData.addAll(listData);
        notifyDataSetChanged();
    }

    /**
     * 绑定数据
     *
     * @param holder   具体的viewHolder
     * @param position 对应的索引
     */
    protected abstract void bindData(BaseViewHolder holder, int position);


    @Override
    public int getItemCount() {

        return listAllData == null ? 0 : listAllData.size();
    }


    /**
     * 封装ViewHolder ,子类可以直接使用
     */
    public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private IOnItemClick iOnItemClick;
        private Map<Integer, View> mViewMap;

        public BaseViewHolder(View itemView, IOnItemClick iOnItemClick) {
            super(itemView);
            mViewMap = new HashMap<>();
            this.iOnItemClick = iOnItemClick;
        }

        /**
         * 获取设置的view
         *
         * @param id
         * @return
         */
        public View getView(int id, boolean isClick) {
            View view = mViewMap.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                if (isClick)
                    view.setOnClickListener(this);
                mViewMap.put(id, view);
            }
            return view;
        }

        @Override
        public void onClick(View v) {
            if (iOnItemClick != null) {
                iOnItemClick.onItemClick(v, getPosition());
            }

        }
    }

    /**
     * 获取子item
     *
     * @return
     */
    public abstract int getLayoutId();


    /**
     * 设置文本属性
     *
     * @param view
     * @param text
     */
    public void setItemText(View view, String text) {
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }
}
