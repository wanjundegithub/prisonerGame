package com.company.prisoner.game.enums;

/**
 * @author user
 */

public enum OptionEnum {

    UN_CHOOSE("未选择", 0),

    COOPERATION("合作", 1),

    BETRAY("背叛", 2);

    private String desc;

    private int code;

    OptionEnum(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static int getCodeByDesc(String desc){
        int result = -1;
        switch (desc){
            case "未选择":
                result = UN_CHOOSE.getCode();
                break;
            case "合作":
                result = COOPERATION.getCode();
                break;
            case "背叛":
                result = BETRAY.getCode();
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }

    public static String getDescByCode(int code){
        String result ;
        switch (code){
            case 0:
                result = UN_CHOOSE.getDesc();
                break;
            case 1:
                result = COOPERATION.getDesc();
                break;
            case 2:
                result = BETRAY.getDesc();
                break;
            default:
                throw new RuntimeException("非法选择");
        }
        return result;
    }
}
