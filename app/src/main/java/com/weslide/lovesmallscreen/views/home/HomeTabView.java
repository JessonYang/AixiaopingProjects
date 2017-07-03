package com.weslide.lovesmallscreen.views.home;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.weslide.lovesmallscreen.models.ImageAndText;
import com.weslide.lovesmallscreen.models.ImageText;

import net.aixiaoping.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu on 2016/6/3.
 * 首页选项卡
 */
public class HomeTabView extends FrameLayout {

    CommonTabLayout mTabLayout;

    List<CustomTabEntity> mTabEntities = new ArrayList<CustomTabEntity>();
//    int[] mIconUnselectIds = {
//            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
//            R.mipmap.tab_more_unselect, R.mipmap.tab_contact_unselect};
//    int[] mIconSelectIds = {
//            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
//            R.mipmap.tab_more_select, R.mipmap.tab_contact_select};

    int[] mIconUnselectIds = {
            R.drawable.icon_shouye, R.drawable.icon_shop,
            R.drawable.icon_dingdan, R.drawable.icon_my};
    int[] mIconSelectIds = {
            R.drawable.icon_shouye_click, R.drawable.icon_shop_click,
            R.drawable.icon_dingdan_click, R.drawable.icon_my_click};

    List<Integer> mIconUnselectId;
    List<Integer> mIconSelectId;
    List<String> title;

    //选项卡切换的区域
    int mContainerViewId;
    //选项卡切换的Fragment
    ArrayList<Fragment> mFragments;


    public HomeTabView(Context context) {
        super(context);
       // initView(context);
    }

    public HomeTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //initView(context);
    }

    public HomeTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //initView(context);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.home_view_tab, this, true);
        mTabLayout = (CommonTabLayout) findViewById(R.id.tl_tab);

       /* String[] titles = getResources().getStringArray(R.array.array_home_tab);
        for (int i = 0; i < titles.length; i++) {
            mTabEntities.add(new TabEntity(titles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }*/
        for (int i = 0; i < title.size(); i++) {
            mTabEntities.add(new TabEntity(title.get(i), mIconSelectId.get(i), mIconUnselectId.get(i)));
        }
    }

    public void setData( Context context,List<Integer> mIconUnselectId,List<Integer> mIconSelectId,List<String> title) {
        this.mIconUnselectId = mIconUnselectId;
        this.mIconSelectId = mIconSelectId;
        this.title = title;
        initView(context);
    }

    public void bindPageChange(int containerViewId, ArrayList<Fragment> fragments) {
        mContainerViewId = containerViewId;
        mFragments = fragments;

        if (mContainerViewId == 0 || mFragments == null) {
            mTabLayout.setTabData((ArrayList<CustomTabEntity>) mTabEntities);
        } else {
            mTabLayout.setTabData((ArrayList<CustomTabEntity>) mTabEntities, (FragmentActivity) getContext(), mContainerViewId, mFragments);
        }


    }

    public CommonTabLayout getTabLayout() {
        return mTabLayout;
    }


    class TabEntity implements CustomTabEntity {
        public String title;
        public int selectedIcon;
        public int unSelectedIcon;

        public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return selectedIcon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelectedIcon;
        }
    }

}