package me.zhengjie.modules.system.cons;

/**
 * @author 黄星星
 * @date 2020-02-27
 */
public enum  MessageReadStatus {
    NO_READ("未读", 0),
    READEDD("已读", 1),
    ;

    private String name;

    private Integer status;

    MessageReadStatus(String name, Integer status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
