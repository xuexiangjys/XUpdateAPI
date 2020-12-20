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

package com.xuexiang.xupdate.easy.config;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 默认版本更新配置提供者
 *
 * @author xuexiang
 * @since 2020/12/20 1:42 PM
 */
public class DefaultUpdateConfigProvider implements IUpdateConfigProvider {
    @NonNull
    @Override
    public UpdateConfig getUpdateConfig(@NonNull Context context) {
        return UpdateConfig.create();
    }
}
