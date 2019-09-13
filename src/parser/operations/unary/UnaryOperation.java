package parser.operations.unary;

import parser.Expression;
import parser.operations.Operation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class UnaryOperation implements Expression, Operation {

    Expression operand;

    UnaryOperation(Expression val) {
        this.operand = val;
    }

    @Override
    public String getTree() {
        return "(" + getToken() + operand.getTree() + ")";
    }

    @Override
    public String toString() {
        return getToken() + "(" + operand.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnaryOperation that = (UnaryOperation) o;
        return Objects.equals(operand, that.operand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operand);
    }

    @Override
    public Expression getLeftOperand() {
        return operand;
    }

    @Override
    public Expression getRightOperand() {
        return operand;
    }

    @Override
    public Set<String> getVariablesSet() {
        return operand.getVariablesSet();
    }

}