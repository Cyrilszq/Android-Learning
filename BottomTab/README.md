# BottomBar(一个底部导航菜单)
第一次封装一个控件，写着玩的
## 使用方法
- 布局文件
```
<com.example.cyril.bottomtab.BottomTabLayout
       android:layout_width="match_parent"
       android:layout_height="56dp"
       android:id="@+id/bottom_tab">
   </com.example.cyril.bottomtab.BottomTabLayout>
```
- acitivity
```
mBottomTabLayout = (BottomTabLayout) findViewById(R.id.bottom_tab);
        //不设置选中或未选中颜色的话默认使用colorPrimary和colorPrimaryDark
        //颜色需在colors文件中设置
        mBottomTabLayout.setUnSelectColor(R.color.tabUnselect);
        mBottomTabLayout.setSelectColor(R.color.tabSelect);
        //不要设置文字只需要图标时可将第三个参数设置为null
        mBottomTabLayout.addItem(this, R.drawable.home, "主页")
                .addItem(this,R.drawable.classification,"分类")
                .addItem(this,R.drawable.shopping_cart,"购物车")
                .addItem(this,R.drawable.me,"我的");
        mBottomTabLayout.setOnTabSelectedListener(new BottomTabLayout.OnTabSelectedListener() {
            //选中回调
            @Override
            public void onTabSelected(int position, int prePosition) {

            }
            //重复选中回调
            @Override
            public void onTabReselected(int position) {

            }
        });
```
