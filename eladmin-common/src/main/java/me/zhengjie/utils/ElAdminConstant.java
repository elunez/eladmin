package me.zhengjie.utils;

/**
 * 常用静态常量
 *
 * @author jie
 * @date 2018-12-26
 */
public class ElAdminConstant {

    public static final String RESET_PASS = "重置密码";

    public static final String RESET_MAIL = "重置邮箱";

    public static final String REG_MAIL = "邮箱注册";

    /**
     * 常用接口
     */
    public static class Url {
        public static final String SM_MS_URL = "https://sm.ms/api/upload";
    }


    public enum CodeType {
        email("重置邮箱", "email"),
        email_reg("邮箱注册", "reg_email");

        public static String getName(String index) {
            for (CodeType c : CodeType.values()) {
                if (c.getIndex().equals(index)) {
                    return c.name;
                }
            }
            return null;
        }

        private CodeType(String name, String index) {
            this.name = name;
            this.index = index;
        }

        private String name;

        private String index;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }
    }

}
