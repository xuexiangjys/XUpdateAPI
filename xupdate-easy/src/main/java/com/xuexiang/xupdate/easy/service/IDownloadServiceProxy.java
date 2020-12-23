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

package com.xuexiang.xupdate.easy.service;

import androidx.annotation.NonNull;

import com.xuexiang.xupdate.proxy.IUpdateHttpService;

/**
 * 下载服务代理
 *
 * @author xuexiang
 * @since 2020/12/23 1:01 AM
 */
public interface IDownloadServiceProxy {

    /**
     * 文件下载
     *
     * @param url      下载地址
     * @param path     文件保存路径
     * @param fileName 文件名称
     * @param callback 文件下载回调
     */
    void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull IUpdateHttpService.DownloadCallback callback);

    /**
     * 取消文件下载
     *
     * @param url      下载地址
     */
    void cancelDownload(@NonNull String url);

}
