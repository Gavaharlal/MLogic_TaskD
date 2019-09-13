package parser.operations.binary;

import parser.Expression;
import parser.ExpressionType;

import java.util.HashMap;

public class Conjunction extends BinaryOperation {

    public Conjunction(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public String getToken() {
        return "&";
    }


    @Override
    public ExpressionType getType() {
        return ExpressionType.CONJUNCTION;
    }

    @Override
    public boolean evaluate(HashMap<String, Boolean> evals) {
        return leftOperand.evaluate(evals) && rightOperand.evaluate(evals);
    }
}
