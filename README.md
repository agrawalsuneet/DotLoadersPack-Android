# DotsLoader
> Android dotsloader
> A replacement of default android material progressbar with dots loader

### DotsLoader
![loaders](https://cloud.githubusercontent.com/assets/12999622/22540869/ad0d5c24-e948-11e6-8d8a-873ff19e5985.gif)

### DotsLoader Dialog
![dialog](https://cloud.githubusercontent.com/assets/12999622/22540867/a7c15658-e948-11e6-81a4-533b48f7d0d3.gif)



## How To use
include below dependency in build.gradle of application and compile it
```
compile 'com.agrawalsuneet.androidlibs:dotsloader:0.1'
```

### DotsLoader
* Through XML
```
<com.agrawalsuneet.dotsloader.ui.ThreeDotsLoader
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:loader_circleRadius="6dp"
        app:loader_defaultColor="@color/blue_delfault"
        app:loader_dotsDist="2dp"
        app:loader_isSingleDir="true"
        app:loader_animDur="1000"
        app:loader_selectedColor="@color/blue_selected" />
```

*  Through Code
```
        LinearLayout containerLL = (LinearLayout) findViewById(R.id.container);

        ThreeDotsLoader loader = new ThreeDotsLoader(MainActivity.this);
        loader.setDefaultColor(R.color.loader_defalut);
        loader.setSelectedColor(R.color.loader_selected);
        loader.setIsSingleDir(true);
        loader.setRadius(30);
        loader.setDotsDist(20);
        loader.setAnimDur(500);
        containerLL.addView(loader);
```


### DotsLoader Dialog
```
DotsLoaderDialog dotsDialog = new DotsLoaderDialog.Builder(this)
                .setTextColor(R.color.white)
                .setBackground(R.color.blue_delfault)
                .setMessage("Loading...")
                .setTextSize(24)
                .setDotsDefaultColor(R.color.loader_defalut)
                .setDotsSelectedColor(R.color.loader_selected)
                .setAnimDuration(800)
                .setDotsDistance(28)
                .setDotsRadius(28)
                .setIsLoadingSingleDirection(true)
                .create();

        //dotsDialog.setCancelable(false);
        dotsDialog.show(getSupportFragmentManager(), "dotsDialog");
```

> Please note that the above dialog will handle all the saveInstanceState callbacks and will be visible again on Activity recreation or orientation change.

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
