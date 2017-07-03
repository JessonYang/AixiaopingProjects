package com.weslide.lovesmallscreen.managers;
import com.weslide.lovesmallscreen.models.CityModel;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * Created by Dong on 2016/6/14.
 */

public class XmlParserHandler extends DefaultHandler {

    /**
     * 存储所有的解析对象
     */
    private List<CityModel> provinceList = new ArrayList<CityModel>();

    public XmlParserHandler() {

    }

    public List<CityModel> getDataList() {
        return provinceList;
    }

    @Override
    public void startDocument() throws SAXException {
        // 当读到第一个开始标签的时候，会触发这个方法
    }

    CityModel provinceModel = new CityModel();
    CityModel cityModel = new CityModel();
    CityModel districtModel = new CityModel();


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        // 当遇到开始标记的时候，调用这个方法
        if (qName.equals("province")) {
            provinceModel = new CityModel();
            provinceModel.setCurrentName(attributes.getValue(0));
            provinceModel.setCityList(new ArrayList<CityModel>());
        } else if (qName.equals("city")) {
            cityModel = new CityModel();
            cityModel.setCurrentName(attributes.getValue(0));
            cityModel.setCityList(new ArrayList<CityModel>());
        } else if (qName.equals("district")) {
            districtModel = new CityModel();
            districtModel.setCurrentName(attributes.getValue(0));
            districtModel.setZipcode(attributes.getValue(1));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        // 遇到结束标记的时候，会调用这个方法
        if (qName.equals("district")) {
            cityModel.getCityList().add(districtModel);
        } else if (qName.equals("city")) {
            provinceModel.getCityList().add(cityModel);
        } else if (qName.equals("province")) {
            provinceList.add(provinceModel);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    }
}
