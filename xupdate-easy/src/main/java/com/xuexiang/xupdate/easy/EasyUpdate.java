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

package com.xuexiang.xupdate.easy;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.xuexiang.xupdate.UpdateManager;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.easy.config.DefaultUpdateConfigProvider;
import com.xuexiang.xupdate.easy.config.IUpdateConfigProvider;
import com.xuexiang.xupdate.easy.config.UpdateConfig;
import com.xuexiang.xupdate.easy.service.IDownloadServiceProxy;
import com.xuexiang.xupdate.easy.service.OkHttpUpdateHttpServiceImpl;
import com.xuexiang.xupdate.entity.UpdateEntity;

/**
 * 简单更新API
 *
 * @author xuexiang
 * @since 2020/12/20 1:42 AM
 */
public final class EasyUpdate {

    private static IUpdateConfigProvider sUpdateConfigProvider;

    private EasyUpdate() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 设置更新配置提供者
     *
     * @param sUpdateConfigProvider 更新配置提供者
     */
    public static void setUpdateConfigProvider(IUpdateConfigProvider sUpdateConfigProvider) {
        EasyUpdate.sUpdateConfigProvider = sUpdateConfigProvider;
    }

    /**
     * 获取更新配置
     *
     * @param context 上下文
     * @return 更新配置
     */
    public static UpdateConfig getUpdateConfig(Context context) {
        if (EasyUpdate.sUpdateConfigProvider == null) {
            EasyUpdate.sUpdateConfigProvider = new DefaultUpdateConfigProvider();
        }
        return EasyUpdate.sUpdateConfigProvider.getUpdateConfig(context);
    }

    //=============初始化==================//

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public static void init(@NonNull Application context) {
        init(context, getUpdateConfig(context));
    }

    /**
     * 初始化
     *
     * @param context      上下文
     * @param updateConfig 更新配置
     */
    public static void init(@NonNull Application context, @NonNull UpdateConfig updateConfig) {
        XUpdate.get()
                .debug(updateConfig.isIsDebug())
                .isWifiOnly(updateConfig.isWifiOnly())
                .isGet(updateConfig.isGet())
                .params(updateConfig.getParams())
                .setIUpdateHttpService(new OkHttpUpdateHttpServiceImpl(updateConfig.getTimeout(), updateConfig.isPostJson(), updateConfig.getDownloadServiceProxy()))
                .isAutoMode(updateConfig.isAutoMode())
                .supportSilentInstall(updateConfig.isSupportSilentInstall())
                .init(context);

        if (updateConfig.getUpdateHttpService() != null) {
            XUpdate.get().setIUpdateHttpService(updateConfig.getUpdateHttpService());
        }
        if (updateConfig.getUpdateChecker() != null) {
            XUpdate.get().setIUpdateChecker(updateConfig.getUpdateChecker());
        }
        if (updateConfig.getUpdateParser() != null) {
            XUpdate.get().setIUpdateParser(updateConfig.getUpdateParser());
        }
        if (updateConfig.getUpdatePrompter() != null) {
            XUpdate.get().setIUpdatePrompter(updateConfig.getUpdatePrompter());
        }
        if (updateConfig.getUpdateDownloader() != null) {
            XUpdate.get().setIUpdateDownLoader(updateConfig.getUpdateDownloader());
        }
        if (updateConfig.getFileEncryptor() != null) {
            XUpdate.get().setIFileEncryptor(updateConfig.getFileEncryptor());
        }
        if (updateConfig.getOnInstallListener() != null) {
            XUpdate.get().setOnInstallListener(updateConfig.getOnInstallListener());
        }
        if (updateConfig.getOnUpdateFailureListener() != null) {
            XUpdate.get().setOnUpdateFailureListener(updateConfig.getOnUpdateFailureListener());
        }
    }

    /**
     * 开启下载代理功能【可实现断点续传下载】
     *
     * @param context 上下文
     * @param proxy   下载服务代理, 实现自定义下载功能
     */
    public static void enableDownloadProxy(Context context, IDownloadServiceProxy proxy) {
        UpdateConfig config = EasyUpdate.getUpdateConfig(context);
        XUpdate.get().setIUpdateHttpService(new OkHttpUpdateHttpServiceImpl(config.getTimeout(), config.isPostJson(), proxy));
    }

    /**
     * 禁止下载代理功能
     *
     * @param context 上下文
     */
    public static void disableDownloadProxy(Context context) {
        UpdateConfig config = EasyUpdate.getUpdateConfig(context);
        XUpdate.get().setIUpdateHttpService(new OkHttpUpdateHttpServiceImpl(config.getTimeout(), config.isPostJson(), null));
    }


    //=============更新API==================//

    /**
     * 创建版本更新管理构建者
     *
     * @param context 上下文
     * @param url     版本更新检查的url
     * @return 版本更新管理构建者
     */
    public static UpdateManager.Builder create(@NonNull Context context, @NonNull String url) {
        return XUpdate.newBuild(context)
                .updateUrl(url);
    }

    /**
     * 默认版本更新
     *
     * @param context 上下文
     * @param url     版本更新检查的url
     */
    public static void checkUpdate(@NonNull Context context, @NonNull String url) {
        XUpdate.newBuild(context)
                .updateUrl(url)
                .update();
    }

    /**
     * 直接构建更新信息实体进行更新
     *
     * @param context      上下文
     * @param updateEntity 版本更新信息实体
     */
    public static void checkUpdate(@NonNull Context context, @NonNull UpdateEntity updateEntity) {
        XUpdate.newBuild(context)
                .build()
                .update(updateEntity);
    }


}
