# DotsLoader
> Android dotsloader       [![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=5961db7fc335c70001c8d324&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/5961db7fc335c70001c8d324/build/latest?branch=master)


> A replacement of default android material progressbar with dots loader

### LinearDotsLoader
![lineardotsloader](https://user-images.githubusercontent.com/12999622/35482391-54665328-042c-11e8-954b-93a92ebe2b0c.gif)


### CircularDotsLoader
![circulardotsloader](https://user-images.githubusercontent.com/12999622/36224573-f274b956-11bf-11e8-8f97-e4c031959465.gif)


### LazyLoader
![lazyloader](https://user-images.githubusercontent.com/12999622/36225792-b7044432-11c3-11e8-8e22-5bbdcafa2312.gif)


### TashieLoader
![tashieloader](https://user-images.githubusercontent.com/12999622/36225793-b71f694c-11c3-11e8-9a81-8414bafb26c5.gif)


### SlidingLoader
![slidingloader](https://user-images.githubusercontent.com/12999622/34130222-f58ba220-e43e-11e7-8f60-4971918fecde.gif)


### RotatingCircularDotsLoader
![RotatingCircularDotsLoader](https://user-images.githubusercontent.com/12999622/34453427-d9aa8294-ed4c-11e7-8b1d-fe98d0c2c3dc.gif)


Other loaders: [SVGLoader](https://github.com/agrawalsuneet/SVGLoadersPack-Android), [ClockLoader](https://github.com/agrawalsuneet/LoadersPack), [RippleLoader](https://github.com/agrawalsuneet/LoadersPack), [RotatingCircularSticksLoader](https://github.com/agrawalsuneet/LoadersPack), [CircularSticksLoader](https://github.com/agrawalsuneet/LoadersPack), [ZipZapLoader](https://github.com/agrawalsuneet/SquareLoadersPack-Android), [WaveLoader](https://github.com/agrawalsuneet/SquareLoadersPack-Android), [MusicPlayerLoader](https://github.com/agrawalsuneet/SquareLoadersPack-Android), [RotatingSquareLoader](https://github.com/agrawalsuneet/SquareLoadersPack-Android), [FourFoldLoader](https://github.com/agrawalsuneet/FourFoldLoader)

## How To use
include below dependency in build.gradle of application and compile it
```
compile 'com.agrawalsuneet.androidlibs:dotsloader:0.8'
```

### LinearDotsLoader
##### Through XML
```
<com.agrawalsuneet.dotsloader.loaders.LinearDotsLoader
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:loader_animDur="1000"
        app:loader_noOfDots="8"
        app:loader_circleRadius="6dp"
        app:loader_defaultColor="@color/pink_default"
        app:loader_selectedColor="@color/pink_selected"
        app:loader_dotsDist="3dp"
        app:loader_expandOnSelect="false"
        app:loader_isSingleDir="false"
        app:loader_selectedRadius="10dp"
        app:loader_showRunningShadow="true"
        app:loader_firstShadowColor="@color/blue_selected"
        app:loader_secondShadowColor="@color/blue_delfault"/>
```

##### Through Code

* Kotlin
```
        var loader = LinearDotsLoader(this)
        loader.defaultColor = ContextCompat.getColor(this, R.color.loader_defalut)
        loader.selectedColor = ContextCompat.getColor(this, R.color.loader_selected)
        loader.isSingleDir = false
        loader.noOfDots = 5
        loader.selRadius = 60
        loader.expandOnSelect = false
        loader.radius = 40
        loader.dotsDistance = 20
        loader.animDur = 1000
        loader.firstShadowColor = ContextCompat.getColor(this, R.color.pink_selected)
        loader.secondShadowColor = ContextCompat.getColor(this, R.color.purple_selected)
        loader.showRunningShadow = true
        containerLL.addView(loader)
```


* Java
```
        LinearLayout containerLL = (LinearLayout) findViewById(R.id.container);

        LinearDotsLoader loader = new LinearDotsLoader(this);
        loader.setDefaultColor(ContextCompat.getColor(this, R.color.loader_defalut));
        loader.setSelectedColor(ContextCompat.getColor(this, R.color.loader_selected));
        loader.setSingleDir(true);
        loader.setNoOfDots(5);
        loader.setSelRadius(40);
        loader.setExpandOnSelect(true);
        loader.setRadius(30);
        loader.setDotsDistance(20);
        loader.setAnimDur(500);
        loader.setShowRunningShadow(true);
        loader.setFirstShadowColor(ContextCompat.getColor(this, R.color.blue_selected));
        loader.setSecondShadowColor(ContextCompat.getColor(this, R.color.blue_delfault));
        
        containerLL.addView(loader);
```


### CircularDotsLoader
##### Through XML
```
<com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:loader_animDur="100"
        app:loader_bigCircleRadius="42dp"
        app:loader_circleRadius="14dp"
        app:loader_defaultColor="@color/purple_default"
        app:loader_firstShadowColor="@color/pink_selected"
        app:loader_secondShadowColor="@color/pink_default"
        app:loader_selectedColor="@color/purple_selected"
        app:loader_showRunningShadow="true" />
```

#####  Through Code

* Kotlin
```
var cirLoader = CircularDotsLoader(this)
        cirLoader.defaultColor = ContextCompat.getColor(this, R.color.blue_delfault)
        cirLoader.selectedColor = ContextCompat.getColor(this, R.color.blue_selected)
        cirLoader.bigCircleRadius = 120
        cirLoader.radius = 40
        cirLoader.animDur = 100
        cirLoader.firstShadowColor = ContextCompat.getColor(this, R.color.pink_selected)
        cirLoader.secondShadowColor = ContextCompat.getColor(this, R.color.purple_selected)
        cirLoader.showRunningShadow = true

        containerLL.addView(cirLoader)
        
```

* Java
```
CircularDotsLoader loader = new CircularDotsLoader(this);
        loader.setDefaultColor(ContextCompat.getColor(this,R.color.blue_delfault));
        loader.setSelectedColor(ContextCompat.getColor(this,R.color.blue_selected));
        loader.setBigCircleRadius(80);
        loader.setRadius(24);
        loader.setAnimDur(300);
        loader.setShowRunningShadow(true);
        loader.setFirstShadowColor(ContextCompat.getColor(this, R.color.blue_selected));
        loader.setSecondShadowColor(ContextCompat.getColor(this, R.color.blue_delfault));
```

> For avoiding overlapping in CircularDotsLoader, set BigCircleLoader nearly four times of dotsRadius.

> If the showRunningShadow is true and no firstShadowColor and secondShadowColor provided, it'll take 0.7f and 0.5f alpha of selectedColor

### LazyLoader
##### Through XML
```
<com.agrawalsuneet.dotsloader.loaders.LazyLoader
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:layout_marginTop="30dp"
        app:lazyloader_animDur="800"
        app:lazyloader_dotsDist="10dp"
        app:lazyloader_dotsRadius="16dp"
        app:lazyloader_firstDelayDur="150"
        app:lazyloader_firstDotColor="@color/red"
        app:lazyloader_interpolator="@android:anim/decelerate_interpolator"
        app:lazyloader_secondDelayDur="300"
        app:lazyloader_secondDotColor="@color/green"
        app:lazyloader_thirdDotColor="@color/yellow" />
```

##### Through Code

* Kotlin
```
        var lazyLoader = LazyLoader(this, 15, 5,
                        ContextCompat.getColor(this, R.color.loader_selected),
                        ContextCompat.getColor(this, R.color.loader_selected),
                        ContextCompat.getColor(this, R.color.loader_selected))
                        .apply {
                            animDuration = 500
                            firstDelayDuration = 100
                            secondDelayDuration = 200
                            interpolator = DecelerateInterpolator()
                        }
        containerLL.addView(lazyLoader)
```


* Java
```
        LazyLoader loader = new LazyLoader(this, 30, 20, ContextCompat.getColor(this, R.color.loader_selected),
                        ContextCompat.getColor(this, R.color.loader_selected),
                        ContextCompat.getColor(this, R.color.loader_selected));
                loader.setAnimDuration(500);
                loader.setFirstDelayDuration(100);
                loader.setSecondDelayDuration(200);
                loader.setInterpolator(new LinearInterpolator());
        
        containerLL.addView(loader);
```

### TashieLoader
##### Through XML
```
<com.agrawalsuneet.dotsloader.loaders.TashieLoader
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:tashieloader_animDelay="200"
        app:tashieloader_animDur="1000"
        app:tashieloader_dotsColor="@color/purple_selected"
        app:tashieloader_dotsDist="5dp"
        app:tashieloader_dotsRadius="20dp"
        app:tashieloader_interpolator="@android:anim/accelerate_interpolator"
        app:tashieloader_noOfDots="6" />
```

#####  Through Code

* Kotlin
```
var tashie = TashieLoader(this)
                .apply {
                    noOfDots = 5
                    dotsDist = 10
                    dotsRadius = 30
                    animDuration = 500
                    animDelay = 100
                    dotsColor = resources.getColor(R.color.green)
                    interpolator = LinearInterpolator()
                }
        containerLL.addView(tashie)
        
```

* Java
```
TashieLoader tashie = new TashieLoader(this);
        tashie.setNoOfDots(8);
        tashie.setDotsRadius(20);
        tashie.setDotsDist(10);
        tashie.setDotsColor(ContextCompat.getColor(this, R.color.blue_selected));
        tashie.setAnimDuration(500);
        tashie.setAnimDelay(100);
        tashie.setInterpolator(new LinearInterpolator());
        
        containerLL.addView(tashie);
```

### SlidingLoader
##### Through XML
```
<com.agrawalsuneet.dotsloader.loaders.SlidingLoader
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:slidingloader_animDur="2000"
        app:slidingloader_distanceToMove="12"
        app:slidingloader_dotsDist="6dp"
        app:slidingloader_dotsRadius="10dp"
        app:slidingloader_firstDotColor="@color/colorPrimary"
        app:slidingloader_secondDotColor="@color/colorAccent"
        app:slidingloader_thirdDotColor="@color/colorPrimaryDark" />
```

#####  Through Code

* Kotlin
```
var sliding = SlidingLoader(this, 40, 10,
                ContextCompat.getColor(this, R.color.red),
                ContextCompat.getColor(this, R.color.yellow),
                ContextCompat.getColor(this, R.color.green)).apply {
            animDuration = 1000
            distanceToMove = 12
        }

        containerLL.addView(sliding)
        
```

* Java
```
SlidingLoader sliding = new SlidingLoader(this, 40, 10,
                ContextCompat.getColor(this, R.color.red),
                ContextCompat.getColor(this, R.color.yellow),
                ContextCompat.getColor(this, R.color.green));
        sliding.setAnimDuration(1000);
        sliding.setDistanceToMove(12);

        containerLL.addView(sliding);
```

### RotatingCircularDotsLoader
##### Through XML
```
<com.agrawalsuneet.dotsloader.loaders.RotatingCircularDotsLoader
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:rotatingcircular_animDur="3000"
        app:rotatingcircular_bigCircleRadius="42dp"
        app:rotatingcircular_dotsColor="@color/blue_selected"
        app:rotatingcircular_dotsRadius="14dp" />
```

#####  Through Code

* Kotlin
```
val loader = RotatingCircularDotsLoader(this,
                20, 60, ContextCompat.getColor(this, R.color.red))
                .apply {
                    animDuration = 3000
                }

        containerLL.addView(loader)
        
```

* Java
```
RotatingCircularDotsLoader loader = new RotatingCircularDotsLoader(this,
                20, 60, ContextCompat.getColor(this, R.color.red));
        loader.setAnimDuration(3000);

        containerLL.addView(loader);
```

Please take a 2 mins survey to make this library better [here](https://goo.gl/forms/81Cf63sL2X1WhXHl2).
It won't take more than 2 mins I promise :) or feel free to drop an email at agrawalsuneet@gmail.com if face any issue or require any additional functionality in it.
```
Copyright 2017 Suneet Agrawal

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
