/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.xupdateapi;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.xuexiang.xupdate.aria.AriaDownloader;
import com.xuexiang.xupdate.easy.EasyUpdate;
import com.xuexiang.xupdateapi.custom.CustomUpdateConfigProvider;
import com.xuexiang.xupdateapi.utils.sdkinit.UMengInit;
import com.xuexiang.xupdateapi.utils.sdkinit.XBasicLibInit;

/**
 * @author xuexiang
 * @since 2018/11/7 下午1:12
 */
public class MyApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        initEasyUpdate();
        super.attachBaseContext(base);
        //解决4.x运行崩溃的问题
        MultiDex.install(this);
    }

    /**
     * EasyUpdate会在attachBaseContext之后自动进行初始化，如果需要自定义配置，请务必在attachBaseContext之前设置UpdateConfigProvider
     */
    private void initEasyUpdate() {
        // 自定义更新配置的位置。可以不设置，使用默认配置
        EasyUpdate.setUpdateConfigProvider(new CustomUpdateConfigProvider());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLibs();
    }

    /**
     * 初始化基础库
     */
    private void initLibs() {
        XBasicLibInit.init(this);
        UMengInit.init(this);

//        // 开启断点续传下载功能方法二
//        AriaDownloader.enable(this);
    }


    /**
     * @return 当前app是否是调试开发模式
     */
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }


}
