package com.weslide.lovesmallscreen.view_yy.viewinterface;


import com.weslide.lovesmallscreen.model_yy.javabean.TicketGoodsDtModel;

/**
 * Created by YY on 2017/6/1.
 */
public interface IShowTicketGoodsDtFg extends IShowBaseFg{
    //数据请求成功后的数据适配
    void showViewData(TicketGoodsDtModel ticketGoodsDtModel);
}
