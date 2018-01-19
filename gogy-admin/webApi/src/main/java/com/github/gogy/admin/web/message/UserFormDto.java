/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.admin.web.message;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yuanyi
 * @date 2018/1/16
 */
@Getter
@Setter
public class UserFormDto {

    private String userName;
    private String password;
    private String ticket;
    private String roleCode;

}
