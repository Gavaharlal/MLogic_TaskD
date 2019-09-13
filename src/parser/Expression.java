package parser;

import java.util.HashMap;
import java.util.Set;

public interface Expression {
    String getTree();

    ExpressionType getType();

    Expression getLeftOperand();

    Expression getRightOperand();

    Set<String> getVariablesSet();

    boolean evaluate(HashMap<String, Boolean> evals);

}