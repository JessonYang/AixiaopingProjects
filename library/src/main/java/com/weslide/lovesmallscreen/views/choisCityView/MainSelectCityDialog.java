package com.weslide.lovesmallscreen.views.choisCityView;

        import android.content.Context;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.view.View.OnClickListener;
        import android.widget.TextView;

        import net.aixiaoping.library.R;

/**
 * Created by Dong on 2016/6/14.
 */

public class MainSelectCityDialog extends BaseSelectCityDialog implements OnClickListener, WheelView.OnWheelChangedListener {

    private SelectedCity selected;

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private TextView mBtnConfirm;
    private TextView mBtnCancel;

    public MainSelectCityDialog(Context context,int theme,SelectedCity selected,String [] address) {
        super(context,theme);

        Window window = this.getWindow();
        LayoutInflater getInflate = LayoutInflater.from(context);
        final View addView = getInflate.inflate(R.layout.activity_address_select, null);
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setContentView(addView);
        window.setWindowAnimations(R.style.dialogWindowAnim);  //添加动画

        setUpViews();
        setUpListener();
        setUpData();

        this.selected = selected;
        initCurrentAddress(address);
    }


    private void initCurrentAddress(String [] address){
        if(address!=null&&address.length>1&&!address[0].equals("")){
            mCurrentProviceName = address[0];
            mCurrentCityName = address[1];
            if(address.length>2){
                mCurrentDistrictName = address[2];
            }
            int province = getIndex(mProvinceDatas,mCurrentProviceName);
            if(province!=-1){
                mViewProvince.setCurrentItem(province);
            }
            int city = getIndex(mCitisDatasMap.get(mCurrentProviceName),mCurrentCityName);
            if(city!=-1){
                mViewCity.setCurrentItem(city);
            }
        }
    }

    private int getIndex(String [] strs,String contains){
        int index = -1;
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            if(str.contains(contains)){
                index = i;
                break;
            }
        }
        return index;
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        mBtnConfirm = (TextView) findViewById(R.id.btn_confirm);
        mBtnCancel = (TextView) findViewById(R.id.btn_cancel);

        mViewProvince.setCyclic(true);
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
        mBtnConfirm.setOnClickListener(this);

        mBtnCancel.setOnClickListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new WheelTextAdapter(getContext(), mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        mViewProvince.setCurrentItem(0);
        if(mProvinceDatas.length>7){
            mViewProvince.setCurrentItem(3);
        }else{
            mViewProvince.setCurrentItem(0);
        }
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
        if (areas == null) {
            areas = new String[] { "" };
        }
        mViewDistrict.setViewAdapter(new WheelTextAdapter(getContext(), areas));
        if(areas.length>7) {
            mViewDistrict.setCurrentItem(3);
        }else{
            mViewDistrict.setCurrentItem(0);
        }

        updateDistrict();
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent;
        pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        mViewCity.setViewAdapter(new WheelTextAdapter(getContext(), cities));
        if(cities.length>7) {
            mViewCity.setCurrentItem(3);
        }else{
            mViewCity.setCurrentItem(0);
        }
        updateAreas();
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateDistrict(){
        String [] districts =  mDistrictDatasMap.get(mCurrentCityName);
        if(districts.length>7) {
            mViewDistrict.setCurrentItem(3);
            mCurrentDistrictName = districts[3];
        }else{
            mViewDistrict.setCurrentItem(0);
            mCurrentDistrictName = districts[0];
        }
        mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.btn_confirm:
                showSelectedResult();
                break;
            case R.id.btn_cancel_window:
                this.cancel();
                break;
            default:
                break;
        }*/
        if(v.getId()==R.id.btn_confirm){
            showSelectedResult();
        }
        if(v.getId()==R.id.btn_cancel){
            this.cancel();
        }
    }

    private void showSelectedResult() {
        this.cancel();
        if (mCurrentCityName.equals(mCurrentProviceName)) {
            mCurrentCityName = "";
        }
        if (this.selected!=null) {
            this.selected.selectedCity(mCurrentProviceName,mCurrentCityName,mCurrentDistrictName,mCurrentZipCode);
        }
    }

    public void locateArea(String province,String city,String area){
        if (mProvinceDatas != null){
            for (int i = 0; i < mProvinceDatas.length; i++) {
                if (province.equals(mProvinceDatas[i])) {
                    mViewProvince.setCurrentItem(i);
                    String[] cities = mCitisDatasMap.get(mProvinceDatas[i]);
                    for (int j = 0; j < cities.length; j++) {
                        if (city.equals(cities[j])) {
                            mViewCity.setCurrentItem(j);
                            String[] areas = mDistrictDatasMap.get(cities[j]);
                            for (int m = 0; m < areas.length; m++) {
                                if (area.equals(areas[m])) {
                                    mViewDistrict.setCurrentItem(m);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


