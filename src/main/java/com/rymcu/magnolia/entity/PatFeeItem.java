package com.rymcu.magnolia.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ronger
 */
@Data
public class PatFeeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idPatFeeItem;

    private String feeItemName;

}
