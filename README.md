# XUpdateAPI

[![](https://jitpack.io/v/xuexiangjys/XUpdateAPI.svg)](https://jitpack.io/#xuexiangjys/XUpdateAPI)
[![api](https://img.shields.io/badge/API-14+-brightgreen.svg)](https://android-arsenal.com/api?level=14)
[![I](https://img.shields.io/github/issues/xuexiangjys/XUpdateAPI.svg)](https://github.com/xuexiangjys/XUpdateAPI/issues)
[![Star](https://img.shields.io/github/stars/xuexiangjys/XUpdateAPI.svg)](https://github.com/xuexiangjys/XUpdateAPI)

简化 [XUpdate](https://github.com/xuexiangjys/XUpdate) 的使用, 一键实现版本更新功能!

## 关于我

[![github](https://img.shields.io/badge/GitHub-xuexiangjys-blue.svg)](https://github.com/xuexiangjys)   [![csdn](https://img.shields.io/badge/CSDN-xuexiangjys-green.svg)](http://blog.csdn.net/xuexiangjys)   [![简书](https://img.shields.io/badge/简书-xuexiangjys-red.svg)](https://www.jianshu.com/u/6bf605575337)   [![掘金](https://img.shields.io/badge/掘金-xuexiangjys-brightgreen.svg)](https://juejin.im/user/598feef55188257d592e56ed)   [![知乎](https://img.shields.io/badge/知乎-xuexiangjys-violet.svg)](https://www.zhihu.com/people/xuexiangjys) 

## 项目介绍

为了方便大家更快地使用XUpdate，降低集成的难度，我编写了这个配套的拓展库。本库目前包含如下两部分内容：

* EasyUpdate: 提供快速接入XUpdate的功能，无需初始化便可直接使用。

* AriaDownloader: 提供断点续传下载的功能。

## 集成指南

### 添加Gradle依赖

1.先在项目根目录的 build.gradle 的 repositories 添加:

```
allprojects {
     repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

2.然后在dependencies添加:

```
   implementation 'com.github.xuexiangjys:XUpdate:2.0.9'
   implementation 'com.github.xuexiangjys.XUpdateAPI:xupdate-easy:1.0.1'
   // 如果需要使用断点续传下载功能的话添加该依赖（可选）
   implementation 'com.github.xuexiangjys.XUpdateAPI:xupdate-downloader-aria:1.0.1'
```

3.自定义初始化配置（可选）

因为本库采取了自动初始化的功能，因此你无需进行初始化，但是如果你需要自定义初始化配置的话，你可以实现`IUpdateConfigProvider`，并在`Application`的`attachBaseContext`前调用`EasyUpdate.setUpdateConfigProvider`方法设置自定义配置。

```
    @Override
    protected void attachBaseContext(Context base) {
        // 实现自定义配置
        EasyUpdate.setUpdateConfigProvider(new CustomUpdateConfigProvider());
        super.attachBaseContext(base);
    }

```

4.开启断点续传下载功能（可选）

* 方法一: 在自定义配置中设置下载代理

```
UpdateConfig.create()
        // 开启断点续传下载功能
        .setDownloadServiceProxy(new AriaDownloadServiceProxyImpl(context))
```

* 方法二: 使用`AriaDownloader.enable`开启

```
AriaDownloader.enable(this);
```

* 方法三: 使用`AriaDownloader.getUpdateHttpService`获取下载服务

```
EasyUpdate.create(getContext(), Constants.UPDATE_DEFAULT_URL)
        .updateHttpService(AriaDownloader.getUpdateHttpService(getContext()))
```

***【注意】：在使用断点续传下载功能的时候，请务必设置md5值，否则将会导致升级异常。***

## 注意事项

需要注意的是，在使用`EasyUpdate`的时候，务必保证服务器返回的json格式应包括如下内容：

```
{
  "Code": 0, //0代表请求成功，非0代表失败
  "Msg": "", //请求出错的信息
  "UpdateStatus": 1, //0代表不更新，1代表有版本更新，不需要强制升级，2代表有版本更新，需要强制升级
  "VersionCode": 3,
  "VersionName": "1.0.2",
  "ModifyContent": "1、优化api接口。\r\n2、添加使用demo演示。\r\n3、新增自定义更新服务API接口。\r\n4、优化更新提示界面。",
  "DownloadUrl": "https://raw.githubusercontent.com/xuexiangjys/XUpdate/master/apk/xupdate_demo_1.0.2.apk",
  "ApkSize": 2048
  "ApkMd5": "..."  //md5值没有的话，就无法保证apk是否完整，每次都会重新下载。
}
```

如果你不想使用默认的json格式的话，可参考[XUpdate中如何自定义版本更新解析器](https://github.com/xuexiangjys/XUpdate/wiki/%E9%AB%98%E9%98%B6%E4%BD%BF%E7%94%A8#%E8%87%AA%E5%AE%9A%E4%B9%89%E7%89%88%E6%9C%AC%E6%9B%B4%E6%96%B0%E8%A7%A3%E6%9E%90%E5%99%A8)

## 使用方法

EasyUpdate主要提供了如下两个方法:

* EasyUpdate.create: 构建版本更新检查管理者

* EasyUpdate.checkUpdate: 直接版本更新

具体使用参见 [EasyUpdateFragment](https://github.com/xuexiangjys/XUpdateAPI/blob/master/app/src/main/java/com/xuexiang/xupdateapi/fragment/EasyUpdateFragment.java)。

当然XUpdate的所有方法也都是支持的，具体可以参考[XUpdate的使用说明](https://github.com/xuexiangjys/XUpdate/wiki/%E5%9F%BA%E7%A1%80%E4%BD%BF%E7%94%A8).

## 混淆配置

* XUpdate

```
-keep class com.xuexiang.xupdate.entity.** { *; }

//注意，如果你使用的是自定义Api解析器解析，还需要给你自定义Api实体配上混淆，如下是本demo中配置的自定义Api实体混淆规则：
-keep class com.xuexiang.xupdatedemo.entity.** { *; }
```

* AriaDownloader

```
-dontwarn com.arialyy.aria.**
-keep class com.arialyy.aria.**{*;}
-keep class **$$DownloadListenerProxy{ *; }
-keep class **$$UploadListenerProxy{ *; }
-keep class **$$DownloadGroupListenerProxy{ *; }
-keep class **$$DGSubListenerProxy{ *; }
-keepclasseswithmembernames class * {
    @Download.* <methods>;
    @Upload.* <methods>;
    @DownloadGroup.* <methods>;
}
```

## 配套设置

* [XUpdate核心库](https://github.com/xuexiangjys/XUpdate)
* [后台版本更新管理服务](https://github.com/xuexiangjys/XUpdateService)
* [后台版本更新管理系统](https://github.com/xuexiangjys/xupdate-management)
* [Flutter插件](https://github.com/xuexiangjys/flutter_xupdate)
* [React-Native插件](https://github.com/xuexiangjys/react-native-xupdate)

## 如果觉得项目还不错，可以考虑打赏一波

> 你的打赏是我维护的动力，我将会列出所有打赏人员的清单在下方作为凭证，打赏前请留下打赏项目的备注！

![pay.png](https://ss.im5i.com/2021/06/14/6twG6.png)

## 联系方式

> 更多资讯内容，欢迎扫描关注我的个人微信公众号:【我的Android开源之旅】

![gzh_weixin.jpg](https://ss.im5i.com/2021/06/14/65yoL.jpg)
