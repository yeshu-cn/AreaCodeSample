package work.yeshu.areacode;

/**
 * 将展示业务数据内容和UI实现分离出来，让这个控件更通用
 * @param <T> 业务数据
 */
public abstract class Item<T> {
    public T data; //业务数据

    public Item(T data) {
        this.data = data;
    }

    //ui相关的方法

    /**
     * 这个item是否是index组中的第一个
     * @return true or false
     */
    public abstract boolean isTop();

    /**
     * 获取index字符串
     * @return 返回字符串
     */
    public abstract String getIndex();
}
