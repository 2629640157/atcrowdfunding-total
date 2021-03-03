package com.you.mybatisPlusSampleGenerator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author youbin
 * @since 2020-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Clbum implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "clbum_id", type = IdType.AUTO)
    private Integer clbumId;

    private String clbumName;

    private Integer gradeId;

    private Integer studentNum;


}
