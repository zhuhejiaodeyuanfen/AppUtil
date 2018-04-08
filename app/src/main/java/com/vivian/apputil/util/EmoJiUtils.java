package com.vivian.apputil.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.util.ArrayMap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.vivian.apputil.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lq on 16/10/11.
 */

public class EmoJiUtils {

    public static List<String> smileList;
    public static List<String> allRes;
    private static final Map<Pattern, Integer> emoticons = new HashMap<Pattern, Integer>();
    public static ArrayMap<String, Integer> emoJiMap = null;
    public static ArrayMap<String, Integer> allMap = null;
    private static final Spannable.Factory factory = Spannable.Factory.getInstance();

    static {
        smileList = new ArrayList<>();
        allRes = new ArrayList<>();
        emoJiMap = new ArrayMap<>();
        allMap = new ArrayMap<>();


/**************************************************微信表情包**************************************************/
        emoJiMap.put("[呵呵]", R.drawable.d_hehe);
        emoJiMap.put("[可爱]", R.drawable.d_keai);
        emoJiMap.put("[太开心]", R.drawable.d_taikaixin);
        emoJiMap.put("[鼓掌]", R.drawable.d_guzhang);
        emoJiMap.put("[嘻嘻]", R.drawable.d_xixi);
        emoJiMap.put("[哈哈]", R.drawable.d_haha);
        emoJiMap.put("[笑哭]", R.drawable.d_xiaoku);
        emoJiMap.put("[急眼]", R.drawable.d_jiyan);
        emoJiMap.put("[大馋嘴]", R.drawable.d_chanzui);
        emoJiMap.put("[懒得理你]", R.drawable.d_landelini);
        emoJiMap.put("[黑线]", R.drawable.d_heixian);
        emoJiMap.put("[挖鼻屎]", R.drawable.d_wabishi);
        emoJiMap.put("[哼]", R.drawable.d_heng);
        emoJiMap.put("[怒]", R.drawable.d_nu);
        emoJiMap.put("[抓狂]", R.drawable.d_zhuakuang);
        emoJiMap.put("[委屈]", R.drawable.d_weiqu);
        emoJiMap.put("[可怜]", R.drawable.d_kelian);
        emoJiMap.put("[失望]", R.drawable.d_shiwang);
        emoJiMap.put("[悲伤]", R.drawable.d_beishang);
        emoJiMap.put("[累]", R.drawable.d_lei);
        emoJiMap.put("[害羞]", R.drawable.d_haixiu);
        emoJiMap.put("[捂]", R.drawable.d_wu);
        emoJiMap.put("[爱你]", R.drawable.d_aini);
        emoJiMap.put("[亲亲]", R.drawable.d_qinqin);
        emoJiMap.put("[花心]", R.drawable.d_huaxin);
        emoJiMap.put("[舔]", R.drawable.d_tian);
        emoJiMap.put("[钱]", R.drawable.d_qian);
        emoJiMap.put("[神烦狗]", R.drawable.d_doge);
        emoJiMap.put("[喵]", R.drawable.d_miao);
        emoJiMap.put("[二哈]", R.drawable.d_erha);
        emoJiMap.put("[哭]", R.drawable.d_ku);
        emoJiMap.put("[坏笑]", R.drawable.d_huaixiao);
        emoJiMap.put("[阴险]", R.drawable.d_yinxian);
        emoJiMap.put("[偷笑]", R.drawable.d_touxiao);
        emoJiMap.put("[思考]", R.drawable.d_sikao);
        emoJiMap.put("[疑问]", R.drawable.d_yiwen);
        emoJiMap.put("[晕]", R.drawable.d_yun);
        emoJiMap.put("[傻眼]", R.drawable.d_shayan);
        emoJiMap.put("[衰]", R.drawable.d_shuai);
        emoJiMap.put("[骷髅]", R.drawable.d_kulou);
        emoJiMap.put("[嘘]", R.drawable.d_xu);
        emoJiMap.put("[闭嘴]", R.drawable.d_bizui);
        emoJiMap.put("[汗]", R.drawable.d_han);
        emoJiMap.put("[吃惊]", R.drawable.d_chijing);
        emoJiMap.put("[感冒]", R.drawable.d_ganmao);
        emoJiMap.put("[生病]", R.drawable.d_shengbing);
        emoJiMap.put("[吐]", R.drawable.d_tu);
        emoJiMap.put("[拜拜]", R.drawable.d_baibai);
        emoJiMap.put("[鄙视]", R.drawable.d_bishi);
        emoJiMap.put("[左哼哼]", R.drawable.d_zuohengheng);
        emoJiMap.put("[右哼哼]", R.drawable.d_youhengheng);
        emoJiMap.put("[怒骂]", R.drawable.d_numa);
        emoJiMap.put("[打脸]", R.drawable.d_dalian);
        emoJiMap.put("[敲头]", R.drawable.d_ding);
        emoJiMap.put("[打哈气]", R.drawable.d_dahaqi);
        emoJiMap.put("[困]", R.drawable.d_kun);
        emoJiMap.put("[互粉]", R.drawable.f_hufen);
        emoJiMap.put("[抱抱]", R.drawable.d_baobao);
        emoJiMap.put("[摊手]", R.drawable.d_tanshou);
        emoJiMap.put("[心]", R.drawable.l_xin);
        emoJiMap.put("[伤心]", R.drawable.l_shangxin);
        emoJiMap.put("[鲜花]", R.drawable.w_xianhua);
        emoJiMap.put("[男孩儿]", R.drawable.d_nanhaier);
        emoJiMap.put("[女孩儿]", R.drawable.d_nvhaier);
        emoJiMap.put("[握手]", R.drawable.h_woshou);
        emoJiMap.put("[作揖]", R.drawable.h_zuoyi);
        emoJiMap.put("[赞]", R.drawable.h_zan);
        emoJiMap.put("[耶]", R.drawable.h_ye);
        emoJiMap.put("[好]", R.drawable.h_good);
        emoJiMap.put("[弱]", R.drawable.h_ruo);
        emoJiMap.put("[不要]", R.drawable.h_buyao);
        emoJiMap.put("[好的]", R.drawable.h_ok);
        emoJiMap.put("[哈哈]", R.drawable.h_haha);
        emoJiMap.put("[来]", R.drawable.h_lai);//
        emoJiMap.put("[熊猫]", R.drawable.d_xiongmao);
        emoJiMap.put("[兔子]", R.drawable.d_tuzi);
        emoJiMap.put("[猪头]", R.drawable.d_zhutou);
        emoJiMap.put("[神兽]", R.drawable.d_shenshou);
        emoJiMap.put("[奥特曼]", R.drawable.d_aoteman);
        emoJiMap.put("[太阳]", R.drawable.w_taiyang);
        emoJiMap.put("[月亮]", R.drawable.w_yueliang);
        emoJiMap.put("[浮云]", R.drawable.w_fuyun);
        emoJiMap.put("[下雨]", R.drawable.w_xiayu);
        emoJiMap.put("[沙尘暴]", R.drawable.w_shachenbao);
        emoJiMap.put("[微风]", R.drawable.w_weifeng);
        emoJiMap.put("[飞机]", R.drawable.o_feiji);
        emoJiMap.put("[照相机]", R.drawable.o_zhaoxiangji);
        emoJiMap.put("[话筒]", R.drawable.o_huatong);
        emoJiMap.put("[音乐]", R.drawable.o_yinyue);
        emoJiMap.put("[给力]", R.drawable.f_geili);
        emoJiMap.put("[囧]", R.drawable.f_jiong);
        emoJiMap.put("[萌]", R.drawable.f_meng);
        emoJiMap.put("[神马]", R.drawable.f_shenma);
        emoJiMap.put("[织]", R.drawable.f_zhi);
        emoJiMap.put("[最右]", R.drawable.d_zuiyou);
        emoJiMap.put("[蜡烛]", R.drawable.o_lazhu);
        emoJiMap.put("[围观]", R.drawable.o_weiguan);
        emoJiMap.put("[干杯]", R.drawable.o_ganbei);
        emoJiMap.put("[蛋糕]", R.drawable.o_dangao);
        emoJiMap.put("[礼物]", R.drawable.o_liwu);
        emoJiMap.put("[囍]", R.drawable.f_xi);
        emoJiMap.put("[钟]", R.drawable.o_zhong);
        emoJiMap.put("[肥皂]", R.drawable.d_feizao);
        emoJiMap.put("[绿丝带]", R.drawable.o_lvsidai);
        emoJiMap.put("[围脖]", R.drawable.o_weibo);
        emoJiMap.put("[删除]", R.drawable.compose_emotion_delete_highlighted);


        smileList.add("[呵呵]");
        smileList.add("[可爱]");
        smileList.add("[太开心]");
        smileList.add("[鼓掌]");
        smileList.add("[嘻嘻]");
        smileList.add("[哈哈]");
        smileList.add("[笑哭]");
        smileList.add("[急眼]");
        smileList.add("[大馋嘴]");
        smileList.add("[懒得理你]");
        smileList.add("[黑线]");
        smileList.add("[挖鼻屎]");
        smileList.add("[哼]");
        smileList.add("[怒]");
        smileList.add("[抓狂]");
        smileList.add("[委屈]");
        smileList.add("[可怜]");
        smileList.add("[失望]");
        smileList.add("[悲伤]");
        smileList.add("[累]");
        smileList.add("[害羞]");
        smileList.add("[捂]");
        smileList.add("[爱你]");
        smileList.add("[亲亲]");
        smileList.add("[花心]");
        smileList.add("[舔]");
        smileList.add("[钱]");
        smileList.add("[神烦狗]");
        smileList.add("[喵]");
        smileList.add("[二哈]");
        smileList.add("[哭]");
        smileList.add("[坏笑]");
        smileList.add("[阴险]");
        smileList.add("[偷笑]");
        smileList.add("[思考]");
        smileList.add("[疑问]");
        smileList.add("[晕]");
        smileList.add("[傻眼]");
        smileList.add("[衰]");
        smileList.add("[骷髅]");
        smileList.add("[嘘]");
        smileList.add("[闭嘴]");
        smileList.add("[汗]");
        smileList.add("[吃惊]");
        smileList.add("[感冒]");
        smileList.add("[生病]");
        smileList.add("[吐]");
        smileList.add("[拜拜]");
        smileList.add("[鄙视]");
        smileList.add("[左哼哼]");
        smileList.add("[右哼哼]");
        smileList.add("[怒骂]");
        smileList.add("[打脸]");
        smileList.add("[敲头]");
        smileList.add("[打哈气]");
        smileList.add("[困]");
        smileList.add("[互粉]");
        smileList.add("[抱抱]");
        smileList.add("[摊手]");
        smileList.add("[心]");
        smileList.add("[伤心]");
        smileList.add("[鲜花]");
        smileList.add("[男孩儿]");
        smileList.add("[女孩儿]");
        smileList.add("[握手]");
        smileList.add("[作揖]");
        smileList.add("[赞]");
        smileList.add("[耶]");
        smileList.add("[好]");
        smileList.add("[弱]");
        smileList.add("[不要]");
        smileList.add("[好的]");
        smileList.add("[哈哈]");
        smileList.add("[来]");//
        smileList.add("[熊猫]");
        smileList.add("[兔子]");
        smileList.add("[猪头]");
        smileList.add("[神兽]");
        smileList.add("[奥特曼]");
        smileList.add("[太阳]");
        smileList.add("[月亮]");
        smileList.add("[浮云]");
        smileList.add("[下雨]");
        smileList.add("[沙尘暴]");
        smileList.add("[微风]");
        smileList.add("[飞机]");
        smileList.add("[照相机]");
        smileList.add("[话筒]");
        smileList.add("[音乐]");
        smileList.add("[给力]");
        smileList.add("[囧]");
        smileList.add("[萌]");
        smileList.add("[神马]");
        smileList.add("[织]");
        smileList.add("[最右]");
        smileList.add("[蜡烛]");
        smileList.add("[围观]");
        smileList.add("[干杯]");
        smileList.add("[蛋糕]");
        smileList.add("[礼物]");
        smileList.add("[囍]");
        smileList.add("[钟]");
        smileList.add("[肥皂]");
        smileList.add("[绿丝带]");
        smileList.add("[围脖]");


        allRes.addAll(smileList);
        allMap.putAll((Map<? extends String, ? extends Integer>) emoJiMap);

    }

    public static List<String> getResList(int type) {

        return smileList;
    }

    public static List<String> getAllRes() {
        return allRes;
    }


    public static ArrayMap<String, Integer> getEmoJiMap(int type) {

        return emoJiMap;
    }

    /**
     * 解析EmoJi表情
     *
     * @param type
     * @param context
     * @param content
     * @return
     */
    public static SpannableString parseEmoJi(int type, Context context, String content) {

        SpannableString spannable = new SpannableString(content);
        String reg = "\\[[a-zA-Z0-9\\u4e00-\\u9fa5]+\\]";//校验表情正则
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {

            String regEmoJi = matcher.group();//获取匹配到的emoji字符串
            int start = matcher.start();//匹配到字符串的开始位置
            int end = matcher.end();//匹配到字符串的结束位置
            Integer resId = allMap.get(regEmoJi);//通过emoji名获取对应的表情id

            if (resId != null) {

                Drawable drawable = context.getResources().getDrawable(resId);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                ImageSpan imageSpan = new ImageSpan(drawable, content);
                spannable.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }
        return spannable;
    }


}
