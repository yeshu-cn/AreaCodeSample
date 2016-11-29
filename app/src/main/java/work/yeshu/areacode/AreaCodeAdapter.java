package work.yeshu.areacode;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yeshu on 2016/11/25.
 * adapter of area code list
 */

public class AreaCodeAdapter extends RecyclerView.Adapter<AreaCodeAdapter.ViewHolder> {
    private List<AreaCode> mAreaCodeList;

    public AreaCodeAdapter(List<AreaCode> areaCodeList) {
        mAreaCodeList = areaCodeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area_code, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AreaCode areaCode = mAreaCodeList.get(position);
        holder.textArea.setText(areaCode.getArea());
        holder.textCode.setText(areaCode.getCode());
        holder.itemView.setTag(areaCode);
    }

    @Override
    public int getItemCount() {
        return mAreaCodeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textArea;
        TextView textCode;

        public ViewHolder(View itemView) {
            super(itemView);
            textArea = (TextView) itemView.findViewById(R.id.text_area);
            textCode = (TextView) itemView.findViewById(R.id.text_code);
        }
    }
}
