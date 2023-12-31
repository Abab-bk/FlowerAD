package com.acidwall.flowerad;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.zh.pocket.PocketSdk;
import com.zh.pocket.ads.reward_video.RewardVideoAD;
import com.zh.pocket.ads.reward_video.RewardVideoADListener;
import com.zh.pocket.error.ADError;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import org.godotengine.godot.plugin.UsedByGodot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FlowerAD extends GodotPlugin {
    //定义信号名字 信号链接是在godot链接的
    public SignalInfo adReady = new SignalInfo("adReady");
    //激励广告信号
    public SignalInfo RewardGet    = new SignalInfo("RewardGet");
    public SignalInfo RewardSkip = new SignalInfo("RewardSkip");
    public SignalInfo RewardClosed = new SignalInfo("RewardClosed");
    public SignalInfo RewardFailed = new SignalInfo("RewardFailed");

    public SignalInfo InitAD = new SignalInfo("InitAD");

    public FrameLayout layout;
    public Activity activity;
    public String Tag;

    public FlowerAD(Godot godot) {
        super(godot);
        activity = getActivity();
        Tag = FlowerAD.class.toString();
    }

    @Override
    public String getPluginName() {
        return "RealPocket";
    }

    //向引擎注册信号
    @Override
    public Set<SignalInfo> getPluginSignals() {
        HashSet<SignalInfo> signals = new HashSet<SignalInfo>();
        signals.add(adReady);
        //激励
        signals.add(RewardSkip);
        signals.add(RewardGet);
        signals.add(RewardClosed);
        signals.add(RewardFailed);
        signals.add(InitAD);
        return signals;
    }

    @Override
    public View onMainCreate(Activity activity) {
        this.layout = new FrameLayout(activity);

        String path = "taptap";
        String AppID = "12519";

        try {
            path = read_file("FlowerAD/Path.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            AppID = read_file("FlowerAD/AppID.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        PocketSdk.initSDK(activity, path, AppID);
        return this.layout;
    }

    public static String read_file(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = reader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }
        reader.close();
        return content.toString();
    }

    private RewardVideoAD VideoAd;

    @UsedByGodot
    public void DisRewardVideoAD() {
        if (VideoAd != null) {
            VideoAd.destroy();
        }
    }

    // 展示激励视频
    @UsedByGodot
    public void ShowRewardVideoAd(String id) {
        emitSignal(InitAD.getName());
        Log.e("传入ID: ", id);
        VideoAd = new RewardVideoAD(getActivity(), id);
        VideoAd.setRewardVideoADListener(new RewardVideoADListener() {
            @Override
            public void onFailed(ADError adError) {
                Log.e("RewardVideo","激励视频加载失败");
                Log.e("RewardVideo",adError.toString());
                emitSignal(RewardFailed.getName());
            }

            @Override
            public void onSuccess() {
                Log.e("RewardVideo","激励视频加载成功");
            }

            @Override
            public void onVideoCached() {
                Log.e("RewardVideo","奖励视频被缓存");

            }

            @Override
            public void onADShow() {
                Log.e("RewardVideo","奖励视频被展示");
            }

            @Override
            public void onADExposure() {
                Log.e("RewardVideo","奖励视频被曝光");
            }

            @Override
            public void onReward() {
                emitSignal(RewardGet.getName());
            }

            @Override
            public void onADClicked() {
                Log.e("RewardVideo","广告被点击");
            }

            @Override
            public void onADClosed() {
                Log.e("RewardVideo","奖励视频被关闭");
                emitSignal(RewardClosed.getName());
            }

            @Override
            public void onVideoComplete() {
                Log.e("RewardVideo","奖励视频播放完毕");
            }

            @Override
            public void onSkippedVideo() {
                Log.e("RewardVideo","广告被跳过");
                emitSignal(RewardSkip.getName());
            }

            @Override
            public void onADLoaded() {
                Log.e("RewardVideo","激励视频加载了");
                VideoAd.showAD();
            }
        });
        VideoAd.loadAD();
    }
}