package com.company.prisoner.game.enums;

/**
 * @author user
 */
public enum ResultEnum {

    /**
     * 成功标识
     */
    SUCCESSFUL("successful"),

    /**
     * 失败标识
     */
    FAILED("failed")
    ;

    private String code;

    ResultEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
