package proofParser.solvers;

import parser.Expression;
import parser.ExpressionParser;
import parser.operations.unary.Variable;
import proofParser.Deductor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OneVariableSolver {

    private String var;
    private Expression formula;

    public List<String> solve(Expression formula, String var) {

        this.var = var;
        this.formula = formula;

        HashMap<String, Boolean> map = new HashMap<>();
        map.put(var, false);
        boolean phi0 = formula.evaluate(map);
        map.put(var, true);
        boolean phi1 = formula.evaluate(map);

        if (phi1) {
            if (phi0) {
                return proofPhiWithOutA();
            } else {
                return proofPhiWithA();
            }
        } else {
            if (phi0) {
                return null;
            } else {
                return proofNotPhiWithOutA();
            }
        }
    }

    private List<String> proofPhiWithA() {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(var, true);
        List<String> evidence = new ArrayList<>();
        evidence.add(var + " |- " + formula);
        Deductor.createEvidence(formula, evals, evidence);
        return evidence;
    }


    private List<String> proofPhiWithOutA() {
        HashMap<String, Boolean> evals = new HashMap<>();

        evals.put(var, true);
        List<String> evidenceA = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceA);

        evals.put(var, false);
        List<String> evidenceNotA = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceNotA);

        List<String> merged = Deductor.merge(new ArrayList<>(), new Variable(var), evidenceA,
                new ArrayList<>(), new ExpressionParser().parse("!(" + var + ")"), evidenceNotA, formula);
        merged.add(0, "|- " + formula);
        return merged;
    }


    private List<String> proofNotPhiWithOutA() {
        HashMap<String, Boolean> evals = new HashMap<>();
        formula = new ExpressionParser().parse("!(" + formula + ")");
        return proofPhiWithOutA();
    }
}
