package work.yeshu.areacode;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * 1. extends Item class
 * 2. extends SimpleRecyclerViewAdapter
 * 3. set decoration
 */
public class AreaCodeAdapter extends SimpleRecyclerViewAdapter<AreaCodeItem, AreaCodeAdapter.ViewHolder>{

    @Override
    public int getViewHolderLayoutResId() {
        return R.layout.item_area_code;
    }

    @Override
    public ViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(ViewHolder holder, AreaCodeItem item) {
        AreaCode areaCode = item.data;
        holder.textArea.setText(areaCode.getArea());
        holder.textCode.setText(areaCode.getCode());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textArea;
        TextView textCode;

        ViewHolder(View itemView) {
            super(itemView);
            textArea = itemView.findViewById(R.id.text_area);
            textCode = itemView.findViewById(R.id.text_code);
        }
    }
}
