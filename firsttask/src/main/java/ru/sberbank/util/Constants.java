package ru.sberbank.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Constants {
    ONE_HUNDRED_FIFTY(150),
    ONE_HUNDRED(100),
    FIFTY(50),
    ZERO_POINT_ONE(0.1),
    ZERO_POINT_TWO(0.2);

    private final double num;
}
