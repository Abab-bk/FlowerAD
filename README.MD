# FlowerAD

Godot4对接口袋工厂SDK。

## 快速开始

### 编译插件

FlowerAD 以安卓插件的形式集成到Godot。

```
git clone https://github.com/Abab-bk/FlowerAD.githttps://github.com/Abab-bk/FlowerAD.git
```

使用Android Studio打开clone的项目。

Godot 中选择项目 -> 安装 Android 构建模板。此时你的项目文件夹下会生成 android 文件夹。

在 ``FlowerAD/libs`` 替换你的依赖：

- godot-lib_release.aar 替换为 ``` 项目根目录\android\build\libs\release\godot-lib.template_release.aar```

- 其余SDK换成口袋工厂下载的SDK了（仓库里的有可能不是新版），文件名也是你下载的最新版。

- 修改 FlowerAD/build.gradle 第35 - 40行，文件名改为你替换的文件。

打开 ``` FlowerAD/AppID.txt ``` 文件，让这个文件里只有你的AppID（口袋工厂创建应用后获取）。

打开 ``` FlowerADPath.txt ``` 文件，在这个文件里写入渠道，可以填taptap（随便填）。

编译：```.\gradlew build```

注意：目前只有激励视频，因为我只用到了激励视频，其他类型广告自己写，照着我的抄就行了，很简单，要看着口袋工厂提供的文档。

### 在 Godot 中激活。

找到编译好的 ```.aar``` 文件，复制到 ```res://android/plugins/``` 。并创建一个与你复制的 ```.aar``` 文件名相同的 ```.gdap``` 文件，写入以下内容：

```
[config]
name="RealPocket"
binary_type="local"
binary="FlowerAD-release.aar" #此项改为你build的.aar文件。
[dependencies]
custom_maven_repos=["https://jitpack.io"]
local=["csj_ad_sdk_230215.aar","gdt_ad_sdk_230210.aar","ks_ad_sdk_230116.aar","pocket_ad_sdk_3.2.8.aar"]
remote=["com.squareup.okhttp3:okhttp:4.9.2","com.google.code.gson:gson:2.8.8","androidx.legacy:legacy-support-core-ui:1.0.0","androidx.appcompat:appcompat:1.1.0","com.google.android.material:material:1.1.0","androidx.constraintlayout:constraintlayout:1.1.3"]
```

在Godot的导出中选择安卓，勾选安卓插件下的 ```Real Pocket``` 。

## 在Godot中使用：

```gdscript
extends Node

var ad

signal reward_get
signal reward_failed
signal reward_closed
signal reward_skip

func _ready() -> void:
    if ad != null:
        ad.adReady.connect(Callable(self, "adReady"))
        ad.RewardGet.connect(Callable(self, "RewardGet"))
        ad.RewardClosed.connect(Callable(self, "RewardClosed"))
        ad.RewardFailed.connect(Callable(self, "RewardFailed"))
        ad.RewardSkip.connect(Callable(self, "RewardSkip"))

func is_android() -> bool:
    if OS.get_name() == "Android" && Engine.has_singleton("RealPocket"):
        return true
    else:
        return false

func _init() -> void:
    if OS.get_name() == "Android" && Engine.has_singleton("RealPocket"):
        ad = Engine.get_singleton("RealPocket")
    else:
        print("无法初始化广告")

func adReady() -> void:
    if ad != null:
        Uhd.new_message_popup("广告", "准备完毕")

func ShowRewardAD(id:String = "57075") -> void: #这里传入的 id 是你的广告位ID。
    if ad != null:
        print("点击广告")
        ad.ShowRewardVideoAd(id)

func RewardSkip():
    if ad != null:
        reward_skip.emit()

func RewardClosed():
    if ad != null:
        reward_closed.emit()

func RewardGet():
    if ad != null:
        reward_get.emit()

func RewardFailed():
    if ad != null:
        reward_failed.emit()


```

## 感谢

小沙盒：[小沙盒工作室的个人空间-小沙盒工作室个人主页-哔哩哔哩视频 (bilibili.com)](https://space.bilibili.com/26680057)

码农不是我：[码农不是我的个人空间-码农不是我个人主页-哔哩哔哩视频 (bilibili.com)](https://space.bilibili.com/496294706)

## 捐赠

给我打钱。
<img src="https://img1.imgtp.com/2023/06/27/D1Xo8bN9.png" title="" alt="" style="zoom:25%;">
<img src="https://img1.imgtp.com/2023/06/27/UEtvieW8.jpg" title="" alt="" style="zoom:25%;">
