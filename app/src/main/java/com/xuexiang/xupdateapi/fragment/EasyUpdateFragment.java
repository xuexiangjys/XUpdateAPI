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

package com.xuexiang.xupdateapi.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.xuexiang.xaop.annotation.MemoryCache;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xupdate.easy.EasyUpdate;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.proxy.impl.DefaultUpdateChecker;
import com.xuexiang.xupdate.proxy.impl.DefaultUpdateParser;
import com.xuexiang.xupdateapi.Constants;
import com.xuexiang.xupdateapi.R;
import com.xuexiang.xupdateapi.core.BaseFragment;
import com.xuexiang.xupdateapi.utils.XToastUtils;
import com.xuexiang.xutil.resource.ResUtils;
import com.xuexiang.xutil.resource.ResourceUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * EasyUpdate，非要简易的使用方式
 *
 * @author xuexiang
 * @since 2019-07-08 00:52
 */
@Page(name = "EasyUpdate 使用案例")
public class EasyUpdateFragment extends BaseFragment {

    @BindView(R.id.tv_message)
    TextView tvMessage;

    /**
     * 布局的资源id
     */
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_easy;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        tvMessage.setText(String.format("更新配置参数:\n%s", EasyUpdate.getUpdateConfig(getContext())));
    }

    @SingleClick
    @OnClick({R.id.btn_default, R.id.btn_auto, R.id.btn_force, R.id.btn_background_update, R.id.btn_update_entity, R.id.btn_custom_style, R.id.btn_custom_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_default:
                EasyUpdate.checkUpdate(getContext(), Constants.UPDATE_DEFAULT_URL);
                break;
            case R.id.btn_auto:
                EasyUpdate.create(getContext(), Constants.UPDATE_DEFAULT_URL)
                        .isAutoMode(true)
                        .update();
                break;
            case R.id.btn_force:
                EasyUpdate.checkUpdate(getContext(), Constants.UPDATE_FORCE_URL);
                break;
            case R.id.btn_background_update:
                EasyUpdate.create(getContext(), Constants.UPDATE_DEFAULT_URL)
                        .supportBackgroundUpdate(true)
                        .update();
                break;
            case R.id.btn_update_entity:
                EasyUpdate.checkUpdate(getContext(), getUpdateEntityFromAssets());
                break;
            case R.id.btn_custom_style:
                EasyUpdate.create(getContext(), Constants.UPDATE_DEFAULT_URL)
                        .promptThemeColor(ResUtils.getColor(R.color.update_theme_color))
                        .promptButtonTextColor(Color.WHITE)
                        .promptTopResId(R.mipmap.bg_update_top)
                        .promptWidthRatio(0.7F)
                        .update();
                break;
            case R.id.btn_custom_check:
                EasyUpdate.create(getContext(), Constants.UPDATE_DEFAULT_URL)
                        .updateChecker(new DefaultUpdateChecker() {
                            @Override
                            public void onBeforeCheck() {
                                super.onBeforeCheck();
                                getMessageLoader("查询中...").show();
                            }

                            @Override
                            public void onAfterCheck() {
                                super.onAfterCheck();
                                getMessageLoader().dismiss();
                            }

                            @Override
                            public void noNewVersion(Throwable throwable) {
                                super.noNewVersion(throwable);
                                // 没有最新版本的处理
                                XToastUtils.toast("已是最新版本！");
                            }
                        })
                        .update();
                break;
            default:
                break;
        }
    }


    @MemoryCache
    private UpdateEntity getUpdateEntityFromAssets() {
        return new DefaultUpdateParser().parseJson(ResourceUtils.readStringFromAssert("update_test.json"));
    }
}
