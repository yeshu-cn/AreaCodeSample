package work.yeshu.areacode;

/**
 * Created by yeshu on 2016/11/25.
 * area code item
 */

public class AreaCode {
    private String mArea;
    private String mCode;

    AreaCode(String area, String code) {
        mArea = area;
        mCode = code;
    }

    public String getArea() {
        return mArea;
    }

    public String getCode() {
        return mCode;
    }
}
