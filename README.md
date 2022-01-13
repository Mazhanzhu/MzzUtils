# 超实用工具类！
## 这是我的[个人博客 点击查看](https://blog.csdn.net/fengyeNom1?spm=1011.2124.3001.5343)
### Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```
### Add the dependency
```
dependencies {
	        implementation 'com.github.Mazhanzhu:MzzUtils:Tag'//最新 1.0.1
	}
  ```
  
### 1.0.0 增加：
*AES加密解密、Activity管理栈、文件工具类以及各种获取intent意图  
圆形倒计时 使用如下：【也可以在代码中进行设置，比如倒计时时间、开始、停止以及监听等】*
```
<com.mazhanzhu.utils.view.CountDownProgressView
        android:id="@+id/sp_countview"
        android:layout_width="@dimen/mzzdp_40"
        android:layout_height="@dimen/mzzdp_40"
        android:layout_alignParentRight="true"
        android:layout_alignParentBottom="true"
        android:layout_marginRight="@dimen/mzzdp_10"
        android:layout_marginBottom="@dimen/mzzdp_20"
        app:cd_circFrameColor="@color/green_light"
        app:cd_circSolidColor="@color/cardview_shadow_start_color"
        app:cd_progressColor="@color/red"
        app:cd_textColor="@color/white"
        app:cd_textstring="跳过" />
	
属性介绍
        <!-- 圆实心的颜色 -->
        <attr name="cd_circSolidColor" format="color"></attr>
        <!-- 圆边框的颜色 -->
        <attr name="cd_circFrameColor" format="color"></attr>
        <!-- 进度条的颜色 -->
        <attr name="cd_progressColor" format="color"></attr>
        <!-- 文字的颜色 -->
        <attr name="cd_textColor" format="color"></attr>
        <!--文字设置-->
        <attr name="cd_textstring" format="string"></attr>
```
效果大概这样
![image](https://img-blog.csdnimg.cn/bf340fec70964596b047004f37e5e62c.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5Yav5Lq65ZSQ,size_20,color_FFFFFF,t_70,g_se,x_16)
### 1.0.1 新增
*自定义形状图片，增加两点间距离公式、增加glide依赖：*
```
double distance = MzzDistanceUtils.getInstance().getLongDistance(115.405912, 39.533194, 115.881071, 39.502472);//第一个点的经纬度、第二个点的经纬度，返回距离【单位 米】

MzzGlide.toView(imagview, object);//glide使用

//自定义形状使用
<com.mazhanzhu.utils.view.ShapeImageView
                android:id="@+id/user_img"
                android:layout_width="70dp"
                android:layout_height="70dp"
                android:layout_centerVertical="true"
                android:layout_marginLeft="20dp"
                app:siv_border_color="@color/white"
                app:siv_border_size="2dp"
                app:siv_round_radius="33dp"
                app:siv_shape="circle" />

        <!--形状选择-->
        <attr name="siv_shape" format="enum">
            <!--矩形-->
            <enum name="rect" value="1" />
            <!--圆-->
            <enum name="circle" value="2" />
            <!--椭圆-->
            <enum name="oval" value="3" />
        </attr>
        <!--圆角大小-->
        <attr name="siv_round_radius" format="dimension" />
        <!--单独设置圆角——左上-->
        <attr name="siv_round_radius_leftTop" format="dimension" />
        <!--单独设置圆角——左下-->
        <attr name="siv_round_radius_leftBottom" format="dimension" />
        <!--单独设置圆角——右上-->
        <attr name="siv_round_radius_rightTop" format="dimension" />
        <!--单独设置圆角——右下-->
        <attr name="siv_round_radius_rightBottom" format="dimension" />
        <attr name="siv_border_size" format="dimension" />
        <!--颜色-->
        <attr name="siv_border_color" format="color" />
```
