/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
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

package com.xuexiang.xupdateapi.custom;

import android.content.Context;

import androidx.annotation.NonNull;

import com.xuexiang.xupdate.aria.AriaDownloadServiceProxyImpl;
import com.xuexiang.xupdate.easy.config.IUpdateConfigProvider;
import com.xuexiang.xupdate.easy.config.UpdateConfig;
import com.xuexiang.xupdate.utils.UpdateUtils;

/**
 * 自定义版本更新配置【这里只是个例子】
 *
 * @author xuexiang
 * @since 2020/12/20 2:10 PM
 */
public class CustomUpdateConfigProvider implements IUpdateConfigProvider {

    @NonNull
    @Override
    public UpdateConfig getUpdateConfig(@NonNull Context context) {
        return UpdateConfig.create()
                .setIsDebug(true)
//                // 开启断点续传下载功能方法一
//                .setDownloadServiceProxy(new AriaDownloadServiceProxyImpl(context))
                .setParam("appKey", context.getPackageName())
                .setParam("versionCode", UpdateUtils.getVersionCode(context));
    }
}
