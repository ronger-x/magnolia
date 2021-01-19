package com.rymcu.magnolia.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ronger
 */
@Data
public class PatInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String patientCode;

    private String patientName;

    private List<PatFeeItem> patFeeItems;

}
