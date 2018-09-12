package work.yeshu.areacode;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<AreaCodeItem> mCodeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        IndexView indexView = findViewById(R.id.index_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        AreaCodeIndexDecoration groupDecoration = new AreaCodeIndexDecoration(100, 0xffF1F4F6, 32, 0xffFF5252);
        //实现index view显示
        mRecyclerView.addItemDecoration(groupDecoration);
        //实现分割线显示
        mRecyclerView.addItemDecoration(new AreaCodeDividerDecoration(new ColorDrawable(ContextCompat.getColor(this, R.color.colorAccent)), 24, 24));

        List<AreaCode> codeList = DataSource.getAreaCodeList(this);
        indexView.setIndexList(DataSource.getIndexList(codeList));

        AreaCodeAdapter areaCodeAdapter = new AreaCodeAdapter();
        mCodeList = DataSource.getAreaCodeItemList(codeList);
        areaCodeAdapter.updateData(mCodeList);
        mRecyclerView.setAdapter(areaCodeAdapter);

        indexView.setOnSelectedListener(new IndexView.OnSelectedListener() {
            @Override
            public void onSelected(String index) {
                selectedIndex(index);
            }
        });
    }

    private void selectedIndex(String index) {
        int position = -1;
        for (int i = 0; i < mCodeList.size(); i++) {
            if (mCodeList.get(i).getIndex().equals(index)) {
                position = i;
                break;
            }
        }

        if (-1 == position) {
            return;
        }

        mRecyclerView.smoothScrollToPosition(position);
    }
}
