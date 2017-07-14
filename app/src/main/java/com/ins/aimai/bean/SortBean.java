package com.ins.aimai.bean;

import android.text.Html;
import android.text.TextUtils;

/**
 * by liaoinstan
 * 排序列表实体基类，提供基础字段，这些字段必须存在，其余需求增加字段可以基础该类进行拓展
 */

public class SortBean {

    //根据name解析出的首字母，例如（'廖'->'l'）
    private String tag;
    //名称，也是选择实体的唯一选择依据
    private String name;
    //存储名称的有色html字符，（用户查找匹配的时候匹配部分文字要变为高亮，未匹配部分原色，为实现这种复杂颜色字符串，这里采用html文本添加color标签处理，也可以使用SpanableString）
    private String nameHtml;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameHtml() {
        return nameHtml;
    }

    public void setNameHtml(String nameHtml) {
        this.nameHtml = nameHtml;
    }

    public CharSequence getNameSmart() {
        if (!TextUtils.isEmpty(nameHtml)){
            return Html.fromHtml(nameHtml);
        }else {
            return name;
        }
    }
}
