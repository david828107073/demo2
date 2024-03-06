package com.david.common;

import lombok.Data;

/**
 * response data
 *
 * @param <T>
 */
@Data
public class BasicOut<T> {

    T body;
    String message;
    String retCode;
}
