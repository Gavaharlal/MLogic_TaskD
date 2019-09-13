package proofParser;

import parser.Expression;

import java.util.*;

public class EvidenceView {
    private final List<Expression> hypotheses;
    private final Map<Expression, Set<Expression>> modusPonensResult;
    private final Map<Expression, LineOfEvidence> expressionToLine;
    private final Set<Expression> proofSet;
    private final List<LineOfEvidence> evidenceArrayList;
    private final Expression theorem;

    EvidenceView(List<Expression> hypotheses, Map<Expression, Set<Expression>> modusPonensResult, Map<Expression, LineOfEvidence> expressionToLine, Set<Expression> proofSet, List<LineOfEvidence> evidenceArrayList, Expression theorem) {
        this.hypotheses = Collections.unmodifiableList(hypotheses);
        this.modusPonensResult = Collections.unmodifiableMap(modusPonensResult);
        this.expressionToLine = Collections.unmodifiableMap(expressionToLine);
        this.proofSet = Collections.unmodifiableSet(proofSet);
        this.evidenceArrayList = Collections.unmodifiableList(evidenceArrayList);
        this.theorem = theorem;
    }

    public List<Expression> getHypotheses() {
        return hypotheses;
    }

    public Map<Expression, Set<Expression>> getModusPonensResult() {
        return modusPonensResult;
    }

    public Map<Expression, LineOfEvidence> getExpressionToLine() {
        return expressionToLine;
    }

    public Set<Expression> getProofSet() {
        return proofSet;
    }

    public List<LineOfEvidence> getEvidenceList() {
        return evidenceArrayList;
    }

    public Expression getTheorem() {
        return theorem;
    }
}
