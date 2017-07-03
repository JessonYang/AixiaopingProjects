package com.weslide.lovesmallscreen.views.choisCityView;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import com.weslide.lovesmallscreen.managers.XmlParserHandler;
import com.weslide.lovesmallscreen.models.CityModel;

/**
 * Created by Dong on 2016/6/14.
 */
public class BaseSelectCityDialog extends Dialog {

    public BaseSelectCityDialog(Context context,int theme) {
        super(context,theme);
    }

    public BaseSelectCityDialog(Context context) {
        super(context);
    }

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName = "";
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName = "";
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName ="";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode ="";

    XmlParserHandler handler = new XmlParserHandler();

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas()
    {
        List<CityModel> provinceList = null;
        AssetManager asset = getContext().getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList!= null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getCurrentName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getCurrentName();
                    List<CityModel> districtList = cityList.get(0).getCityList();
                    mCurrentDistrictName = districtList.get(0).getCurrentName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i=0; i< provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getCurrentName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j=0; j< cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getCurrentName();
                    List<CityModel> districtList = cityList.get(j).getCityList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    CityModel[] distrinctArray = new CityModel[districtList.size()];
                    for (int k=0; k<districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        CityModel districtModel = new CityModel(districtList.get(k).getZipcode(),districtList.get(k).getCurrentName());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getCurrentName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getCurrentName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getCurrentName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }
}

