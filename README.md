
# AreaCodeSample
类似微信通讯录界面，选择国家电话区号

![](https://github.com/yeshu-cn/AreaCodeSample/blob/master/demo.gif)

1. 自定义侧边栏字母索引表`IndexView`
2. 使用RecyclerView.ItemDecoration实现微信通讯录列表中字母显示
3. 使用RecyclerView.ItemDecoration实现列表的分割线显示
4. RecyclerView滑动到指定position,并置顶显示。可设置滑动速度
5. 使用[TinyPinyin](https://github.com/promeG/TinyPinyin)实现中文转拼音，中文按拼音首字母排序

## 列表的实现

1。 继承Item,实现抽象方法
2。 Adapter继承SimpleRecyclerViewAdapter,其中的List数据传入第一步中实现的类
3。 AreaCodeIndexDecoration中实现画list index view