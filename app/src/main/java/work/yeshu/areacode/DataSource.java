
package work.yeshu.areacode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataSource {

    public static List<AreaCode> getAreaCodeList(@NonNull Context context) {
        ArrayList<AreaCode> list = new ArrayList<>();
        String[] areaCodeList = context.getResources().getStringArray(R.array.area_code);

        for (int i = 0; i < areaCodeList.length; i++) {
            String[] info = areaCodeList[i].split(" ");
            if (0 == i) {
                list.add(new AreaCode(info[0], info[1]));
            } else {
                list.add(new AreaCode(info[0], info[1]));
            }
        }

        //按照字母排序，中文转换为拼音后按字母排序
        Collections.sort(list, new Comparator<AreaCode>() {
            @Override
            public int compare(AreaCode o1, AreaCode o2) {
                if (Pinyin.isChinese(o1.getArea().charAt(0))) {
                    return Pinyin.toPinyin(o1.getArea().charAt(0)).compareTo(Pinyin.toPinyin(o2.getArea().charAt(0)));
                } else {
                    return o1.getArea().compareTo(o2.getArea());
                }
            }
        });

        return list;
    }

    public static List<AreaCodeItem> getAreaCodeItemList(@NonNull List<AreaCode> list) {
        List<AreaCodeItem> data = new ArrayList<>(list.size());
        String preIndex = null;
        String index;
        boolean top;
        for (int j = 0; j < list.size(); j++) {
            AreaCode code = list.get(j);
            if (Pinyin.isChinese(code.getArea().charAt(0))) {
                index = String.valueOf(Pinyin.toPinyin(code.getArea().charAt(0)).charAt(0));
            } else {
                index = code.getArea().substring(0, 1);
            }

            top = !TextUtils.equals(index, preIndex);
            data.add(new AreaCodeItem(code, index, top));
            preIndex = index;
        }

        return data;
    }

    public static List<String> getIndexList(@NonNull List<AreaCode> list) {
        //填充groupName
        ArrayList<String> indexList = new ArrayList<>();
        String groupName;
        String oldName = null;
        for (int j = 0; j < list.size(); j++) {
            AreaCode code = list.get(j);
            if (Pinyin.isChinese(code.getArea().charAt(0))) {
                groupName = String.valueOf(Pinyin.toPinyin(code.getArea().charAt(0)).charAt(0));
            } else {
                groupName = code.getArea().substring(0, 1);
            }

            if (!TextUtils.equals(groupName, oldName)) {
                indexList.add(groupName);
            }

            oldName = groupName;
        }

        return indexList;
    }
}
