# CircleChangeView
一个可以圆形交替显示的View

要应用该View很简单，就像使用Button之类的控件一样，你可以使用它来进行页面加载时的动画或者一个圆形按钮，
   布局文件中：
    
    <com.example.asus.custom_view_demo1.CircleChangeView
        android:id="@+id/demoView"
        android:layout_width="120dp"
        android:layout_height="120dp"
        demoView:timeDelta="80"
        />
        
  当然，你应该引入这一句来找到自定义的属性： xmlns:demoView="http://schemas.android.com/apk/res-auto" <br>
  
  
  其中 timeDelta是用来控制圆形变化的时间间隔（即速率，当然：速率也和圆的大小有关） <br>
  
  
  然后呢？你可以在你的Activity中通过下面的方式来找到它 <br>
  
  
  
  CircleChangeView circleChangeView = (CircleChangeView) findViewById(R.id.demoView);
  
  
  
  你以为这样就完了？呵呵 <br>
  
  
  你可以控制颜色变化哦，通过传入一个颜色数组，就像下面这样：
  
  
   private int[] tempColors = new int[]{R.color.color1
            , R.color.color2, R.color.color3, R.color.color4, R.color.color5, R.color.color6}; <br>
            
            
   注意：目前只能使用颜色的资源id号来指定颜色，不支持类似 "#ff00ff" 这种的方式。


   你还可以通过调用对应方法来控制该View的开始和停止：
   
        circleChangeView.start();    //开始View变换
        
        
        circleChangeView.stop();     //停止View变换
        
        
        
   
   当View在变换过程中，如果你设置了停止，那么此时该View并不会立即停止下来，而是要去完成它接下来未完成的动画，然后停止下来（避免两个圆重叠的现象）比如View停在下面这种情况时就尴尬了：
    
    

 ![](https://github.com/youngkaaa/CircleChangeView/raw/master/app/src/screens/Screenshot_2016-05-22-19-22-43_com.example.asus.custom_view_demo1.png)  
 
    
   
所以我选择的是将View动画执行完毕再停止。
   
   然后呢？你还可以给该View设置监听器：
   
   circleChangeView.setOnCircleChangeListener(new CircleChangeView.OnCircleChangeListener() {
   
            @Override
            
            public void onStart() {
            
                Toast.makeText(MainActivity.this, "onStart", Toast.LENGTH_SHORT).show();
                
            }
            
            @Override
            
            public void onStop() {
            
                Toast.makeText(MainActivity.this, "onStop", Toast.LENGTH_SHORT).show();
                
            }
            
    });  
   
然后，就没了。新手初学自定义View所写，写的不到位之处请各位勿喷

当然，无.gif无真相。运行图放在下面（gif卡是由于我的电脑太卡，应用跑在模拟器上就卡，望见谅）
  
        
    
  ![](https://github.com/youngkaaa/CircleChangeView/raw/master/app/src/screens/demo1.gif)  
  
  
  
  
