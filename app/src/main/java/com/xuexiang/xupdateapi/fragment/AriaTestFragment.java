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

package com.xuexiang.xupdateapi.fragment;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadTaskListener;
import com.arialyy.aria.core.scheduler.TaskSchedulers;
import com.arialyy.aria.core.task.DownloadTask;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xupdate.utils.UpdateUtils;
import com.xuexiang.xupdate.widget.NumberProgressBar;
import com.xuexiang.xupdateapi.R;
import com.xuexiang.xupdateapi.core.BaseFragment;
import com.xuexiang.xutil.file.FileUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xuexiang.xaop.consts.PermissionConsts.STORAGE;

/**
 * @author xuexiang
 * @since 2020/12/22 12:49 AM
 */
@Page(name = "Aria 下载测试")
public class AriaTestFragment extends BaseFragment implements DownloadTaskListener {

    private static final String TAG = "Aria";

    private static final String TEST_URL = "https://down.qq.com/qqweb/QQ_1/android_apk/Android_8.5.0.5025_537066738.apk";

    @BindView(R.id.npb_progress)
    NumberProgressBar npbProgress;

    private long mTaskId = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_aria_test;
    }

    @Override
    protected void initArgs() {
        Aria.download(this).register();
    }

    @Override
    protected void initViews() {
        initTask();
    }


    private String getCacheApkFilePath(String apkUrl) {
        String dirPath = FileUtils.getDiskCacheDir("apk");
        if (!FileUtils.createOrExistsDir(dirPath)) {
            dirPath = FileUtils.getDiskCacheDir();
        }
        return dirPath + File.separator + UpdateUtils.getApkNameByDownloadUrl(apkUrl);
    }


    private void initTask() {
        String path = getCacheApkFilePath(TEST_URL);
        DownloadEntity entity = getTaskByUrl(TEST_URL, path);
        if (entity != null) {
            mTaskId = entity.getId();
            npbProgress.setProgress(entity.getPercent());
        }

    }

    /**
     * 获取下载任务
     *
     * @param url 下载地址
     * @return 获取下载任务
     */
    private DownloadEntity getTaskByUrl(@NonNull String url, @NonNull String filePath) {
        List<DownloadEntity> entityList = Aria.download(this).getDownloadEntity(url);
        if (entityList != null && entityList.size() > 0) {
            for (DownloadEntity entity : entityList) {
                if (filePath.equals(entity.getFilePath())) {
                    return entity;
                }
            }
        }
        return null;
    }

    @Permission(STORAGE)
    @SingleClick
    @OnClick({R.id.btn_clean, R.id.btn_start, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_clean:
                clean();
                break;
            case R.id.btn_start:
                start();
                break;
            case R.id.btn_stop:
                stop();
                break;
            default:
                break;
        }
    }

    private void clean() {
        if (mTaskId != -1) {
            Aria.download(this)
                    .load(mTaskId)
                    .cancel(true);
            mTaskId = -1;
        }
    }

    private void start() {
        if (mTaskId == -1) {
            String path = getCacheApkFilePath(TEST_URL);
            mTaskId = Aria.download(this)
                    .load(TEST_URL)
                    .setFilePath(path)
                    .create();
        } else {
            Aria.download(this).load(mTaskId).resume();
        }
    }

    private void stop() {
        if (mTaskId != -1) {
            Aria.download(this)
                    .load(mTaskId)
                    .stop();
        }
    }


    @Override
    public void onWait(DownloadTask task) {
        if (task.getKey().equals(TEST_URL)) {
            Log.d(TAG, "onWait ==> " + task.getDownloadEntity().getFileName());
        }
    }

    @Override
    public void onPre(DownloadTask task) {
        if (task.getKey().equals(TEST_URL)) {
            Log.d(TAG, "onPre ==> " + task.getDownloadEntity().getFileName());
        }
    }

    @Override
    public void onTaskPre(DownloadTask task) {

    }


    @Override
    public void onTaskStart(DownloadTask task) {
        if (task.getKey().equals(TEST_URL)) {
            ALog.d(TAG, "onTaskStart ==> isComplete = " + task.isComplete() + ", state = " + task.getState());
        }
    }

    @Override
    public void onTaskRunning(DownloadTask task) {
        if (task.getKey().equals(TEST_URL)) {
            if (npbProgress != null) {
                npbProgress.setProgress(task.getPercent());
            }
        }
    }

    @Override
    public void onNoSupportBreakPoint(DownloadTask task) {

    }


    @Override
    public void onTaskResume(DownloadTask task) {
        if (task.getKey().equals(TEST_URL)) {
            Log.d(TAG, "onTaskResume ==> " + task.getDownloadEntity().getFileName());
        }
    }

    @Override
    public void onTaskStop(DownloadTask task) {
        if (task.getKey().equals(TEST_URL)) {
            Log.d(TAG, "onTaskStop ==> " + task.getDownloadEntity().getFileName());
        }
    }

    @Override
    public void onTaskCancel(DownloadTask task) {
        if (task.getKey().equals(TEST_URL)) {
            mTaskId = -1;
            Log.d(TAG, "onTaskCancel ==> " + task.getDownloadEntity().getFileName());
            if (npbProgress != null) {
                npbProgress.setProgress(0);
            }
        }
    }

    @Override
    public void onTaskFail(DownloadTask task, Exception e) {
        Log.d(TAG, "onTaskFail ==> ");
    }

    @Override
    public void onTaskComplete(DownloadTask task) {
        if (task.getKey().equals(TEST_URL)) {
            Log.d(TAG, "onTaskComplete ==> " + task.getDownloadEntity().getFileName());
            ALog.d(TAG, "下载完成的文件md5: " + CommonUtil.getFileMD5(new File(task.getFilePath())));
            if (npbProgress != null) {
                npbProgress.setProgress(100);
            }
        }
    }

    @Override
    public void onDestroyView() {
        Aria.download(this).unRegister();
        TaskSchedulers.getInstance().unRegister(this);
        super.onDestroyView();
    }
}
