package parser.operations.unary;

import parser.Expression;
import parser.ExpressionType;

import java.util.HashMap;

public class Negation extends UnaryOperation {

    public Negation(Expression operand) {
        super(operand);
    }

    @Override
    public String getToken() {
        return "!";
    }

    @Override
    public ExpressionType getType() {
        return ExpressionType.NEGATION;
    }

    @Override
    public boolean evaluate(HashMap<String, Boolean> evals) {
        return !operand.evaluate(evals);
    }
}
