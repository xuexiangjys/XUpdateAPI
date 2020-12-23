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

import androidx.annotation.NonNull;

import com.xuexiang.xupdate.easy.service.IDownloadServiceProxy;
import com.xuexiang.xupdate.listener.OnInstallListener;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.proxy.IFileEncryptor;
import com.xuexiang.xupdate.proxy.IUpdateChecker;
import com.xuexiang.xupdate.proxy.IUpdateDownloader;
import com.xuexiang.xupdate.proxy.IUpdateHttpService;
import com.xuexiang.xupdate.proxy.IUpdateParser;
import com.xuexiang.xupdate.proxy.IUpdatePrompter;

import java.util.Map;
import java.util.TreeMap;

/**
 * 更新配置
 *
 * @author xuexiang
 * @since 2020/12/20 2:02 AM
 */
public class UpdateConfig {

    //========全局更新配置参数==========//
    /**
     * 是否是调试模式
     */
    private boolean mIsDebug;
    /**
     * 请求参数【比如apk-key或者versionCode等】
     */
    private Map<String, Object> mParams;
    /**
     * 是否使用的是Get请求
     */
    private boolean mIsGet;
    /**
     * Post是否使用Json格式
     */
    private boolean mIsPostJson;
    /**
     * 请求响应超时时间
     */
    private long mTimeout;
    /**
     * 是否只在wifi下进行版本更新检查
     */
    private boolean mIsWifiOnly;
    /**
     * 是否是自动版本更新模式【无人干预,有版本更新直接下载、安装】
     */
    private boolean mIsAutoMode;
    /**
     * 是否支持静默安装
     */
    private boolean mIsSupportSilentInstall;

    //========全局更新实现接口==========//
    /**
     * 版本更新网络请求服务API
     */
    private IUpdateHttpService mUpdateHttpService;
    /**
     * 版本更新检查器
     */
    private IUpdateChecker mUpdateChecker;
    /**
     * 版本更新解析器
     */
    private IUpdateParser mUpdateParser;
    /**
     * 版本更新提示器
     */
    private IUpdatePrompter mUpdatePrompter;
    /**
     * 版本更新下载器
     */
    private IUpdateDownloader mUpdateDownloader;
    /**
     * 文件加密器
     */
    private IFileEncryptor mFileEncryptor;
    /**
     * APK安装监听
     */
    private OnInstallListener mOnInstallListener;
    /**
     * 更新出错监听
     */
    private OnUpdateFailureListener mOnUpdateFailureListener;

    //========代理==========//

    /**
     * 下载服务代理，代理IUpdateHttpService中的下载功能
     */
    private IDownloadServiceProxy mDownloadServiceProxy;

    /**
     * 获取默认更新配置
     *
     * @return 默认更新配置
     */
    public static UpdateConfig create() {
        return new UpdateConfig();
    }

    private UpdateConfig() {
        mIsGet = true;
        mTimeout = 20000L;
    }

    //========全局更新配置参数==========//

    /**
     * 设置调试模式
     *
     * @param isDebug 是否是调试模式
     */
    public UpdateConfig setIsDebug(boolean isDebug) {
        mIsDebug = isDebug;
        return this;
    }

    /**
     * 设置公共请求参数
     *
     * @param params 参数
     */
    public UpdateConfig setParams(@NonNull Map<String, Object> params) {
        mParams = params;
        return this;
    }

    /**
     * 设置全局的apk更新请求参数
     *
     * @param key   键
     * @param value 值
     */
    public UpdateConfig setParam(@NonNull String key, @NonNull Object value) {
        if (mParams == null) {
            mParams = new TreeMap<>();
        }
        mParams.put(key, value);
        return this;
    }

    /**
     * 设置是否使用的是Get请求
     *
     * @param isGet 是否使用Get请求
     */
    public UpdateConfig setIsGet(boolean isGet) {
        mIsGet = isGet;
        return this;
    }

    /**
     * 设置Post是否使用Json格式
     *
     * @param isPostJson Post是否使用Json格式
     */
    public UpdateConfig setIsPostJson(boolean isPostJson) {
        mIsPostJson = isPostJson;
        return this;
    }

    /**
     * 设置请求响应超时时间
     *
     * @param timeout 请求响应超时时间
     */
    public UpdateConfig setTimeout(long timeout) {
        mTimeout = timeout;
        return this;
    }

    /**
     * 设置是否只在wifi下进行版本更新检查
     *
     * @param isWifiOnly 是否只在wifi下进行版本更新检查
     */
    public UpdateConfig setIsWifiOnly(boolean isWifiOnly) {
        mIsWifiOnly = isWifiOnly;
        return this;
    }

    /**
     * 设置是否是自动版本更新模式
     *
     * @param isAutoMode 是否是自动版本更新模式
     */
    public UpdateConfig setIsAutoMode(boolean isAutoMode) {
        mIsAutoMode = isAutoMode;
        return this;
    }

    /**
     * 设置是否支持静默安装
     *
     * @param isSupportSilentInstall 是否支持静默安装
     */
    public UpdateConfig setIsSupportSilentInstall(boolean isSupportSilentInstall) {
        mIsSupportSilentInstall = isSupportSilentInstall;
        return this;
    }

    public boolean isIsDebug() {
        return mIsDebug;
    }

    @NonNull
    public Map<String, Object> getParams() {
        if (mParams == null) {
            mParams = new TreeMap<>();
        }
        return mParams;
    }

    public boolean isGet() {
        return mIsGet;
    }

    public boolean isPostJson() {
        return mIsPostJson;
    }

    public long getTimeout() {
        return mTimeout;
    }

    public boolean isWifiOnly() {
        return mIsWifiOnly;
    }

    public boolean isAutoMode() {
        return mIsAutoMode;
    }

    public boolean isSupportSilentInstall() {
        return mIsSupportSilentInstall;
    }

    //========全局更新实现接口==========//

    /**
     * 设置版本更新网络请求服务API
     *
     * @param updateHttpService 版本更新网络请求服务API
     */
    public UpdateConfig setUpdateHttpService(IUpdateHttpService updateHttpService) {
        mUpdateHttpService = updateHttpService;
        return this;
    }

    /**
     * 设置版本更新检查器
     *
     * @param updateChecker 版本更新检查器
     */
    public UpdateConfig setUpdateChecker(IUpdateChecker updateChecker) {
        mUpdateChecker = updateChecker;
        return this;
    }

    /**
     * 设置版本更新解析器
     *
     * @param updateParser 版本更新解析器
     */
    public UpdateConfig setUpdateParser(IUpdateParser updateParser) {
        mUpdateParser = updateParser;
        return this;
    }

    /**
     * 设置版本更新提示器
     *
     * @param updatePrompter 版本更新提示器
     */
    public UpdateConfig setUpdatePrompter(IUpdatePrompter updatePrompter) {
        mUpdatePrompter = updatePrompter;
        return this;
    }

    /**
     * 设置版本更新下载器
     *
     * @param updateDownloader 版本更新下载器
     */
    public UpdateConfig setUpdateDownloader(IUpdateDownloader updateDownloader) {
        mUpdateDownloader = updateDownloader;
        return this;
    }

    /**
     * 设置文件加密器
     *
     * @param fileEncryptor 文件加密器
     */
    public UpdateConfig setFileEncryptor(IFileEncryptor fileEncryptor) {
        mFileEncryptor = fileEncryptor;
        return this;
    }

    /**
     * 设置APK安装监听
     *
     * @param onInstallListener APK安装监听
     */
    public UpdateConfig setOnInstallListener(OnInstallListener onInstallListener) {
        mOnInstallListener = onInstallListener;
        return this;
    }

    /**
     * 设置更新出错监听
     *
     * @param onUpdateFailureListener 更新出错监听
     */
    public UpdateConfig setOnUpdateFailureListener(OnUpdateFailureListener onUpdateFailureListener) {
        mOnUpdateFailureListener = onUpdateFailureListener;
        return this;
    }

    public IUpdateHttpService getUpdateHttpService() {
        return mUpdateHttpService;
    }

    public IUpdateChecker getUpdateChecker() {
        return mUpdateChecker;
    }

    public IUpdateParser getUpdateParser() {
        return mUpdateParser;
    }

    public IUpdatePrompter getUpdatePrompter() {
        return mUpdatePrompter;
    }

    public IUpdateDownloader getUpdateDownloader() {
        return mUpdateDownloader;
    }

    public IFileEncryptor getFileEncryptor() {
        return mFileEncryptor;
    }

    public OnInstallListener getOnInstallListener() {
        return mOnInstallListener;
    }

    public OnUpdateFailureListener getOnUpdateFailureListener() {
        return mOnUpdateFailureListener;
    }

    //========代理==========//

    /**
     * 设置下载服务代理
     *
     * @param downloadServiceProxy 下载服务代理
     */
    public UpdateConfig setDownloadServiceProxy(IDownloadServiceProxy downloadServiceProxy) {
        mDownloadServiceProxy = downloadServiceProxy;
        return this;
    }

    public IDownloadServiceProxy getDownloadServiceProxy() {
        return mDownloadServiceProxy;
    }

    @NonNull
    @Override
    public String toString() {
        return "UpdateConfig{" +
                "mIsDebug=" + mIsDebug +
                ", mParams=" + mParams +
                ", mIsGet=" + mIsGet +
                ", mIsPostJson=" + mIsPostJson +
                ", mTimeout=" + mTimeout +
                ", mIsWifiOnly=" + mIsWifiOnly +
                ", mIsAutoMode=" + mIsAutoMode +
                ", mIsSupportSilentInstall=" + mIsSupportSilentInstall +
                '}';
    }
}
