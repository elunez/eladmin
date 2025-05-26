package me.zhengjie.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * @author Zheng Jie
 * @date 2019-10-24 20:48:53
 */
@Getter
public class BaseDTO implements Serializable {

    @ApiModelProperty(value = "Creator")
    private String createBy;

    @ApiModelProperty(value = "Updater")
    private String updateBy;

    @ApiModelProperty(value = "Creation Time: yyyy-MM-dd HH:mm:ss", hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @ApiModelProperty(value = "Update Time: yyyy-MM-dd HH:mm:ss", hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;

    /**
     * Method chaining setter for createBy
     * @param createBy creator
     * @return this instance
     */
    public BaseDTO setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    /**
     * Method chaining setter for updateBy
     * @param updateBy updater
     * @return this instance
     */
    public BaseDTO setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    /**
     * Method chaining setter for createTime
     * @param createTime creation time
     * @return this instance
     */
    public BaseDTO setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * Method chaining setter for updateTime
     * @param updateTime update time
     * @return this instance
     */
    public BaseDTO setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        Field[] fields = this.getClass().getDeclaredFields();
        try {
            for (Field f : fields) {
                f.setAccessible(true);
                builder.append(f.getName(), f.get(this)).append("\n");
            }
        } catch (Exception e) {
            builder.append("toString builder encounter an error");
        }
        return builder.toString();
    }
}
