/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.admin.web.message;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author yuanyi
 * @date 2018/1/22
 */
@Data
@Builder
public class PageMessage {

    private int total;
    private int currentPage;
    private int pageSize;

    private List data;

}
