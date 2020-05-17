package me.zhengjie.utils;

/**
 * @author 黄星星
 * @date 2020-05-05
 */
public enum  ProcStatusEnum {
    WAIT_SEND_GOOD("等待发货", "WAIT_SEND_GOOD"),
    SENDING_GOOD("发货中", "SENDING_GOOD"),
    COMPLETED("已完结", "COMPLETED"),

    OUT_SOURCE_ING("委外中", "OUT_SOURCE_ING"),
    OUT_SOURCE_INSPECTION_ING("委外验收中", "OUT_SOURCE_INSPECTION_ING"),
    OUT_SOURCE_INSPECTIONED("委外验收结束", "OUT_SOURCE_INSPECTIONED")
    ;

    private String name;

    private String code;

    ProcStatusEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ProcStatusEnum getProcStatusEnum(String code) {
        ProcStatusEnum[] funcEnums = values();
        for (ProcStatusEnum funcEnum : funcEnums) {
            if (funcEnum.getCode().equals(code)) {
                return funcEnum;
            }
        }
        return null;
    }
}
