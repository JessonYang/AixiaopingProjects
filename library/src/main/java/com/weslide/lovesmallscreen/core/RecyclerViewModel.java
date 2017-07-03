package com.weslide.lovesmallscreen.core;

/**
 * Created by xu on 2016/6/3.
 * RecyclerView 使用的数据实体
 */
public class RecyclerViewModel extends BaseModel {
    private int itemType;
    private Object data;
    private boolean first = true;

    /** 用于RecyclerView 布局分类 */
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) { this.itemType = itemType; }


    /** 主数据 */
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    /** 是否是首次加载 */
    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }
}
