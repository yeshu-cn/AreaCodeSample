package work.yeshu.areacode;

/**
 *  封装下数据，添加index字符串和是否是index的第一行元素的两个方法
 */
public class AreaCodeItem extends Item<AreaCode>{
    private boolean top;
    private String index;

    AreaCodeItem(AreaCode data, String index, boolean top) {
        super(data);
        this.index = index;
        this.top = top;
    }

    @Override
    public boolean isHead() {
        return top;
    }

    @Override
    public String getGroupName() {
        return index;
    }
}
