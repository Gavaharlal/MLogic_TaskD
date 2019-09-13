package proofParser.solvers;

import parser.Expression;
import parser.ExpressionParser;
import parser.operations.unary.Negation;
import parser.operations.unary.Variable;
import proofParser.Deductor;
import proofParser.ProofParser;

import java.util.*;

@SuppressWarnings("Duplicates")
public class TwoVariablesSolver {


    private static final ProofParser proofParser = new ProofParser();
    private static final ExpressionParser expressionParser = new ExpressionParser();

    public List<String> solve(Expression formula, String varA, String varB) {
        HashMap<String, Boolean> map = new HashMap<>();
        map.put(varA, false);
        map.put(varB, false);
        boolean phi00 = formula.evaluate(map);

        map.put(varA, false);
        map.put(varB, true);
        boolean phi01 = formula.evaluate(map);

        map.put(varA, true);
        map.put(varB, false);
        boolean phi10 = formula.evaluate(map);

        map.put(varA, true);
        map.put(varB, true);
        boolean phi11 = formula.evaluate(map);

        if (phi11 && phi10 && phi01 && phi00) {
            return proofPhi(formula, varA, varB);
        } else if (phi11 && phi10) {
            return proofPhiA(formula, varA, varB);
        } else if (phi11 && phi01) {
            return proofPhiB(formula, varA, varB);
        } else if (phi11) {
            return proofPhiAB(formula, varA, varB);
        } else if (!phi10 && !phi01 && !phi00) {
            return proofNotPhi(formula, varA, varB);
        } else if (!phi00 && !phi01) {
            return proofNotPhiNotA(formula, varA, varB);
        } else if (!phi00 && !phi10) {
            return proofNotPhiNotB(formula, varA, varB);
        } else if (!phi00) {
            return proofNotPhiNotANotB(formula, varA, varB);
        } else {
            return null;
        }
    }

    private List<String> proofNotPhiNotANotB(Expression formula, String varA, String varB) {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, false);
        evals.put(varB, false);
        List<String> evidence = new ArrayList<>();
        evidence.add("!" + varA + ", " + "!" + varB + " |- " + "!(" + formula + ")");
        Deductor.createEvidence(new Negation(formula), evals, evidence);
        return evidence;
    }

    private List<String> proofNotPhiNotB(Expression formula, String varA, String varB) {
        formula = new Negation(formula);
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, true);
        evals.put(varB, false);
        List<String> evidenceANotB = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceANotB);

        evals.put(varA, false);
        evals.put(varB, false);
        List<String> evidenceNotANotB = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceNotANotB);

        List<String> merged = Deductor.merge(Collections.singletonList(new Negation(new Variable(varB))), new Variable(varA), evidenceANotB,
                Collections.singletonList(new Negation(new Variable(varB))), expressionParser.parse("!(" + varA + ")"), evidenceNotANotB, formula);

        merged.add(0, "!" + varB + " |- " + formula);
        return merged;
    }

    private List<String> proofNotPhiNotA(Expression formula, String varA, String varB) {
        formula = new Negation(formula);
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, false);
        evals.put(varB, true);
        List<String> evidenceNotAB = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceNotAB);

        evals.put(varA, false);
        evals.put(varB, false);
        List<String> evidenceNotANotB = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceNotANotB);

        List<String> merged = Deductor.merge(Collections.singletonList(new Negation(new Variable(varA))), new Variable(varB), evidenceNotAB,
                Collections.singletonList(new Negation(new Variable(varA))), expressionParser.parse("!(" + varB + ")"), evidenceNotANotB, formula);

        merged.add(0, "!" + varA + " |- " + formula);
        return merged;
    }

    private List<String> proofNotPhi(Expression formula, String varA, String varB) {
        return proofPhi(new Negation(formula), varA, varB);
    }

    private List<String> proofPhiAB(Expression formula, String varA, String varB) {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, true);
        evals.put(varB, true);
        List<String> evidence = new ArrayList<>();
        evidence.add(varA + ", " + varB + " |- " + formula);
        Deductor.createEvidence(formula, evals, evidence);
        return evidence;
    }

    private List<String> proofPhiB(Expression formula, String varA, String varB) {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, true);
        evals.put(varB, true);
        List<String> evidenceAB = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceAB);

        evals.put(varA, false);
        evals.put(varB, true);
        List<String> evidenceNotAB = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceNotAB);

        List<String> merged = Deductor.merge(Collections.singletonList(new Variable(varB)), new Variable(varA), evidenceAB,
                Collections.singletonList(new Variable(varB)), expressionParser.parse("!(" + varA + ")"), evidenceNotAB, formula);

        merged.add(0, varB + " |- " + formula);
        return merged;
    }

    private List<String> proofPhiA(Expression formula, String varA, String varB) {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, true);
        evals.put(varB, true);
        List<String> evidenceAB = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceAB);

        evals.put(varA, true);
        evals.put(varB, false);
        List<String> evidenceANotB = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceANotB);

        List<String> merged = Deductor.merge(Collections.singletonList(new Variable(varA)), new Variable(varB), evidenceAB,
                Collections.singletonList(new Variable(varA)), expressionParser.parse("!(" + varB + ")"), evidenceANotB, formula);

        merged.add(0, varA + " |- " + formula);
        return merged;
    }

    private List<String> proofPhi(Expression formula, String varA, String varB) {

        List<String> evidenceA = proofPhiA(formula, varA, varB);
        evidenceA.remove(0);

        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, false);
        evals.put(varB, true);
        List<String> evidenceNotAB = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceNotAB);

        evals.put(varA, false);
        evals.put(varB, false);
        List<String> evidenceNotANotB = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceNotANotB);

        List<String> evidenceNotA = Deductor.merge(Collections.singletonList(new Negation(new Variable(varA))), new Variable(varB), evidenceNotAB,
                Collections.singletonList(new Negation(new Variable(varA))), new Negation(new Variable(varB)), evidenceNotANotB, formula);

        List<String> merged = Deductor.merge(new ArrayList<>(), new Variable(varA), evidenceA,
                new ArrayList<>(), new Negation(new Variable(varA)), evidenceNotA, formula);

        merged.add(0, "|- " + formula);
        return merged;
    }

}
