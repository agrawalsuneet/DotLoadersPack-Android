# DotsLoader
> Android dotsloader       [![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=5961db7fc335c70001c8d324&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/5961db7fc335c70001c8d324/build/latest?branch=master)


> A replacement of default android material progressbar with dots loader

### LinearDotsLoader
![linear](https://user-images.githubusercontent.com/12999622/29122717-e34ba8be-7d30-11e7-99b8-b440a37b7f6c.gif)
![LinearDotsLoader](https://user-images.githubusercontent.com/12999622/27900763-329c1dee-624d-11e7-877b-68ba0f4ea7d7.gif)

### Circular DotsLoader
![CircularDotsLoader](https://user-images.githubusercontent.com/12999622/27900765-32aef07c-624d-11e7-8679-f77a95030bd4.gif)
![circular](https://user-images.githubusercontent.com/12999622/29122715-e2f4ad3e-7d30-11e7-91ba-d049e21956bd.gif)



## How To use
include below dependency in build.gradle of application and compile it
```
compile 'com.agrawalsuneet.androidlibs:dotsloader:0.4'
```

### LinearDotsLoader
##### Through XML
```
<com.agrawalsuneet.dotsloader.ui.LinearDotsLoader
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
<com.agrawalsuneet.dotsloader.ui.CircularDotsLoader
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
        cirLoader.bigCircleRadius = 100
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

Feel free to drop a mail at agrawalsuneet@gmail.com if face any issue or require any additional functionality in it.

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
