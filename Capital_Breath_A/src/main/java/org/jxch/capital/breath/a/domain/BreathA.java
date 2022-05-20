package org.jxch.capital.breath.a.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * breath_a
 * @author 
 */
@Data
public class BreathA extends BreathAKey implements Serializable {
    private Integer score;

    private static final long serialVersionUID = 1L;
}