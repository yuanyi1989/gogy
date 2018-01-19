package com.github.gogy.admin.web.message;

import lombok.Builder;
import lombok.Getter;

/**
 * @author yuanyi
 * @date 2018/1/13
 */
@Getter
@Builder
public class DefaultResponse {

    private int status;
    private String message;
    private Object body;

}
