# 超实用工具类！
## 这是我的[个人博客 点击查看](https://blog.csdn.net/fengyeNom1?spm=1011.2124.3001.5343)
*首次提交 里面包含AES加密解密、Activity管理栈、文件工具类以及各种获取intent意图*
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
	        implementation 'com.github.Mazhanzhu:MzzUtils:Tag'//最新 1.0.0
	}
  ```
  
### 1.0.0 增加：
圆形倒计时 使用如下：【也可以在代码中进行设置，比如倒计时时间、开始、停止以及监听等】
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
