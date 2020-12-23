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

package com.xuexiang.xupdate.aria;

import android.text.TextUtils;

import com.xuexiang.xupdate.utils.FileUtils;

import java.io.File;

/**
 * 工具类
 *
 * @author xuexiang
 * @since 2020/12/23 1:21 AM
 */
final class Utils {

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取文件的路径
     *
     * @param dirPath  目录
     * @param fileName 文件名
     * @return 拼接的文件的路径
     */
    public static String getFilePath(String dirPath, String fileName) {
        return getDirPath(dirPath) + fileName;
    }

    /**
     * 获取文件目录的路径，自动补齐"/"
     *
     * @param dirPath 目录路径
     * @return 自动补齐"/"的目录路径
     */
    public static String getDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        if (!dirPath.trim().endsWith(File.separator)) {
            dirPath = dirPath.trim() + File.separator;
        }
        return dirPath;
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 目录路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(FileUtils.getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(final File file) {
        // 如果存在，是目录则返回 true，是文件则返回 false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }
}
