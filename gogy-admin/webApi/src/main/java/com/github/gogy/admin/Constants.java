/******************************************************************************* 
 * Copyright (C) Microfountain Technology, Inc. All Rights Reserved. 
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.   
 * Proprietary and confidential
 ******************************************************************************/
package com.github.gogy.admin;

/**
 * @author yuanyi
 * @date 2018/1/17
 */
public class Constants {

    public enum Status {
        NOT_LOGIN(0),
        FORBIDDEN(403),
        NOT_FOUND(404),
        SUCCESS(1),
        FAIL(2),
        SERVER_ERROR(500);

        private int code;

        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

}
