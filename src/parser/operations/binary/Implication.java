package parser.operations.binary;

import parser.Expression;
import parser.ExpressionType;

import java.util.HashMap;

public class Implication extends BinaryOperation {


    public Implication(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public String getToken() {
        return "->";
    }

    @Override
    public ExpressionType getType() {
        return ExpressionType.IMPLICATION;
    }

    @Override
    public boolean evaluate(HashMap<String, Boolean> evals) {
        return !leftOperand.evaluate(evals) || rightOperand.evaluate(evals);
    }
}
