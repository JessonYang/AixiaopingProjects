package com.weslide.lovesmallscreen.view_yy.viewinterface;


import com.weslide.lovesmallscreen.models.OrderMsgDtOb;

import java.util.List;

/**
 * Created by YY on 2017/5/5.
 */
public interface IShowSystemMsgFg extends IShowBaseFg{
    void showView(List<OrderMsgDtOb> list);
}
