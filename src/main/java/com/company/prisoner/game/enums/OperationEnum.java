package com.company.prisoner.game.enums;

/**
 * @author user
 */

public enum OperationEnum {
    DELETE(1, "删除"),
    UPDATE(2, "更新")
    ;

    private int code;

    private String desc;

    OperationEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
