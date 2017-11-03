package net.aixiaoping.unlock.views;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by YY on 2017/10/17.
 */
public class Rotate3dAnimation extends Animation{
    public static final Byte ROTATE_X_AXIS = 0x00;
    public static final Byte ROTATE_Y_AXIS = 0x01;
    public static final Byte ROTATE_Z_AXIS = 0x02;
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera mCamera;
    private Byte mRotateAxis;  // 0：X轴  1：Y轴  2：Z轴

    /**创建3D旋转动画
     * @param fromDegrees 动画开始角度
     * @param toDegrees 动画结束角度
     * @param centerX view旋转中心的x坐标
     * @param centerY view旋转中心的y坐标
     * @param depthZ view旋转中心的z坐标
     * @param rotateAxis view旋转轴
     * @param reverse 是否反转
     */
    public Rotate3dAnimation(float fromDegrees, float toDegrees, float centerX, float centerY, float depthZ, Byte rotateAxis, boolean reverse) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mRotateAxis = rotateAxis;
        mReverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
//        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);
        float degrees = 360 * interpolatedTime;

        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();
        // 将当前的摄像头位置保存下来，以便变换进行完成后恢复成原位
        camera.save();
        if (mReverse) {
            // z的偏移会越来越大。这就会形成这样一个效果，view从近到远
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        } else {
            // z的偏移会越来越小。这就会形成这样一个效果，我们的View从一个很远的地方向我们移过来，越来越近，最终移到了我们的窗口上面
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }
        // 是给我们的View加上旋转效果，在移动的过程中，视图还会以XYZ轴为中心进行旋转。
        if (ROTATE_X_AXIS.equals(mRotateAxis)) {
            camera.rotateX(degrees);
        } else if (ROTATE_Y_AXIS.equals(mRotateAxis)) {
            camera.rotateY(degrees);
        } else {
            camera.rotateZ(degrees);
        }

        // 这个是将我们刚才定义的一系列变换应用到变换矩阵上面，调用完这句之后，我们就可以将camera的位置恢复了，以便下一次再使用。
        camera.getMatrix(matrix);
        // camera位置恢复
        camera.restore();

        // 下面两句是为了动画是以View中心为旋转点
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
