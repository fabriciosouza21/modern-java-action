package org.example.dsl.discount;

import java.util.function.DoubleUnaryOperator;

public class DiscountCalculator {

    private DoubleUnaryOperator discount = price -> price;

    public DiscountCalculator with(DoubleUnaryOperator discount){
        this.discount = this.discount.andThen(discount);
        return this;
    }

    public double calculate(double price) {
        return discount.applyAsDouble(price);
    }

}
