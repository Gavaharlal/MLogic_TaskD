package parser.operations.binary;

import parser.Expression;
import parser.operations.Operation;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class BinaryOperation implements Expression, Operation {
    final Expression leftOperand;
    final Expression rightOperand;

    BinaryOperation(Expression left, Expression right) {
        this.leftOperand = left;
        this.rightOperand = right;
    }

    @Override
    public String getTree() {
        return "(" + getToken() + "," + leftOperand.getTree() + "," + rightOperand.getTree() + ")";
    }

    @Override
    public Set<String> getVariablesSet() {
        HashSet<String> resultSet = new HashSet<>();
        resultSet.addAll(leftOperand.getVariablesSet());
        resultSet.addAll(rightOperand.getVariablesSet());
        return resultSet;
    }

    @Override
    public String toString() {
        return "(" + leftOperand.toString() + " " + getToken() + " " + rightOperand.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryOperation that = (BinaryOperation) o;
        return Objects.equals(leftOperand, that.leftOperand) &&
                Objects.equals(rightOperand, that.rightOperand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftOperand, rightOperand);
    }

    @Override
    public Expression getLeftOperand() {
        return leftOperand;
    }

    @Override
    public Expression getRightOperand() {
        return rightOperand;
    }
}