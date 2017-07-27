package com.ins.aimai.bean.eyekey;

import java.io.Serializable;

/**
 * 人脸信息
 *
 * @author wangzhi
 */
public class Face implements Serializable{

    private String face_id;

    private String tag;

    public String getFace_id() {
        return this.face_id;
    }

    public void setFace_id(String face_id) {
        this.face_id = face_id;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
