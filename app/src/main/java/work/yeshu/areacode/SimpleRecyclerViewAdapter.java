package work.yeshu.areacode;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个Adapter封装了下常用的模版代码，并且通过setTag保存了当前item的数据
 * @param <T> list列表的item类型
 * @param <K> ViewHolder
 */
public abstract class SimpleRecyclerViewAdapter<T, K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K> {
    private List<? extends T> mData = new ArrayList<>(0);
    private LayoutInflater mInflater;
    private OnItemClickListener<T> mItemClickListener;

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T item);
    }


    public void updateData(List<? extends T> data) {
        if (null == data) {
            return;
        }

        mData = data;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public K onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (null == mInflater) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        View view = mInflater.inflate(getViewHolderLayoutResId(), parent, false);
        return onCreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final K holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mItemClickListener) {
                    mItemClickListener.onItemClick(holder.getAdapterPosition(), mData.get(holder.getAdapterPosition()));
                }
            }
        });
        //setTag to save data for index decoration
        holder.itemView.setTag(mData.get(position));
        bindView(holder, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public abstract int getViewHolderLayoutResId();

    public abstract K onCreateViewHolder(View view);

    public abstract void bindView(K holder, T item);


}
