package com.weslide.lovesmallscreen.map;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 *  Created by xu on 2016/4/27.
 * 罗盘使用帮助类型， 用于百度地图的指南针功能
 */
public class SensorHelper {
    private SensorManager sm=null;    
    private Sensor aSensor=null;    
    private Sensor mSensor=null;    
    private Context mContext;
    private int accuracy;
         
    float[] accelerometerValues=new float[3];    
    float[] magneticFieldValues=new float[3];    
    float[] values=new float[3];    
    float[] R=new float[9];    
    private static SensorHelper instance;
    
    public int getAccuracy(){
    	return accuracy;
    }
    
    private SensorHelper() {
	}
    
    public static SensorHelper getInstance(){
    	
    	if(instance == null){
    		instance = new SensorHelper();
    	}
    	
    	return instance;
    }
    
    public void start(Context context){
    	mContext = context;
    	sm=(SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE);    
        aSensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);    
        mSensor=sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);    
        sm.registerListener(myListener, aSensor, SensorManager.SENSOR_DELAY_GAME);    
        sm.registerListener(myListener, mSensor, SensorManager.SENSOR_DELAY_GAME);   
    }
    
    public void onPause() {    
        sm.unregisterListener(myListener);    
    }    
    final SensorEventListener myListener=new SensorEventListener(){    
    
        @Override    
        public void onAccuracyChanged(Sensor sensor, int accuracy) {    
        }    
    
        @Override    
        public void onSensorChanged(SensorEvent event) {    
            if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){    
                accelerometerValues=event.values;    
            }    
            if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){    
                magneticFieldValues=event.values;    
            }    
            SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);    
            SensorManager.getOrientation(R, values);    

            values[0]=(float)Math.toDegrees(values[0]);    
            
            accuracy = (int) values[0];
            
            if(accuracy < 0){
            	//绝对值
            	accuracy = Math.abs( accuracy );
            	accuracy = (180 + (180 - accuracy));
            }
            
        }};    
}
