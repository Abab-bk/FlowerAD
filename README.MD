# FlowerAD

Godot4对接口袋工厂SDK。

## 快速开始

```
git clone https://github.com/Abab-bk/FlowerAD.githttps://github.com/Abab-bk/FlowerAD.git
```

使用Android Studio打开clone的项目。

Godot 中选择项目 -> 安装 Android 构建模板。此时你的项目文件夹下会生成 android 文件夹。

在 ``` FlowerAD/libs `` 替换你的依赖：

- godot-lib_release.aar 替换为 ``` 项目跟目录\android\build\libs\release\godot-lib.template_release.aar```

- 其余SDK换成口袋工厂下载的SDK了（仓库里的有可能不是新版），文件名也是你下载的最新版。

- 修改 FlowerAD/build.gradle 第35 - 40行，文件名改为你替换的文件。

打开 ``` FlowerAD/src/main/java/com/acidwall/flowerad/FlowerAD.java``` 转到第64行。

修改 ``` PocketSdk.initSDK(activity, "taptap", "12519"); ``` ”taptap“可以不改，12519改为你的口袋工厂APP ID。

编译：``` gradle build ```

注意：目前只有激励视频，因为我只用到了激励视频，其他类型广告自己写，照着我的抄就行了，很简单，要看着口袋工厂提供的文档。

## 捐赠

给我打钱。

<img title="" src="https://img1.imgtp.com/2023/06/27/D1Xo8bN9.png" alt="" style="zoom:33%;">

<img src="https://img1.imgtp.com/2023/06/27/UEtvieW8.jpg" title="" alt="" style="zoom:33%;">
