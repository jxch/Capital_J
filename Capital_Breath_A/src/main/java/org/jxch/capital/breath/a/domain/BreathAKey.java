package org.jxch.capital.breath.a.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * breath_a
 * @author 
 */
@Data
public class BreathAKey implements Serializable {
    private Date date;

    private String sector;

    private static final long serialVersionUID = 1L;
}