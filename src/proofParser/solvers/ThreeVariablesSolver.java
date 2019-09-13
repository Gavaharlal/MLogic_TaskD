package proofParser.solvers;

import parser.Expression;
import parser.ExpressionParser;
import parser.operations.unary.Negation;
import parser.operations.unary.Variable;
import proofParser.Deductor;
import proofParser.ProofParser;

import java.util.*;

@SuppressWarnings("Duplicates")
public class ThreeVariablesSolver {

    public List<String> solve(Expression formula, String varA, String varB, String varC) {
        HashMap<String, Boolean> map = new HashMap<>();
        map.put(varA, false);
        map.put(varB, false);
        map.put(varC, false);
        boolean phi000 = formula.evaluate(map);

        map.put(varA, false);
        map.put(varB, false);
        map.put(varC, true);
        boolean phi001 = formula.evaluate(map);

        map.put(varA, false);
        map.put(varB, true);
        map.put(varC, false);
        boolean phi010 = formula.evaluate(map);

        map.put(varA, false);
        map.put(varB, true);
        map.put(varC, true);
        boolean phi011 = formula.evaluate(map);

        map.put(varA, true);
        map.put(varB, false);
        map.put(varC, false);
        boolean phi100 = formula.evaluate(map);

        map.put(varA, true);
        map.put(varB, false);
        map.put(varC, true);
        boolean phi101 = formula.evaluate(map);

        map.put(varA, true);
        map.put(varB, true);
        map.put(varC, false);
        boolean phi110 = formula.evaluate(map);

        map.put(varA, true);
        map.put(varB, true);
        map.put(varC, true);
        boolean phi111 = formula.evaluate(map);

        if (phi111 && phi000 && phi001 && phi010 && phi011 && phi100 && phi101 && phi110) {
            return proofPhi(formula, varA, varB, varC);
        } else if (phi100 && phi101 && phi110 && phi111) {
            return proofPhiA(formula, varA, varB, varC);
        } else if (phi010 && phi011 && phi110 && phi111) {
            return proofPhiB(formula, varA, varB, varC);
        } else if (phi001 && phi011 && phi101 && phi111) {
            return proofPhiC(formula, varA, varB, varC);
        } else if (phi110 && phi111) {
            return proofPhiAB(formula, varA, varB, varC);
        } else if (phi101 && phi111) {
            return proofPhiAC(formula, varA, varB, varC);
        } else if (phi011 && phi111) {
            return proofPhiBC(formula, varA, varB, varC);
        } else if (phi111) {
            return proofPhiABC(formula, varA, varB, varC);
        } else if (!phi000 && !phi001 && !phi010 && !phi011 && !phi100 && !phi101 && !phi110) {
            return proofNotPhi(formula, varA, varB, varC);
        } else if (!phi000 && !phi001 && !phi010 && !phi011) {
            return proofNotPhiNotA(formula, varA, varB, varC);
        } else if (!phi000 && phi001 && !phi100 && phi101) {
            return proofNotPhiNotB(formula, varA, varB, varC);
        } else if (!phi000 && !phi010 && !phi100 && !phi110) {
            return proofNotPhiNotC(formula, varA, varB, varC);
        } else if (!phi000 && !phi001) {
            return proofNotPhiNotANotB(formula, varA, varB, varC);
        } else if (!phi000 && !phi010) {
            return proofNotPhiNotANotC(formula, varA, varB, varC);
        } else if (!phi000 && !phi100) {
            return proofNotPhiNotBNotC(formula, varA, varB, varC);
        } else if (!phi000) {
            return proofNotPhiNotANotBNotC(formula, varA, varB, varC);
        } else {
            return null;
        }

    }

    private List<String> proofNotPhiNotANotBNotC(Expression formula, String varA, String varB, String varC) {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, false);
        evals.put(varB, false);
        evals.put(varC, false);
        List<String> evidence = new ArrayList<>();
        evidence.add("!" + varA + ", " + "!" + varB + ", " + "!" + varC + " |- " + "!(" + formula + ")");
        Deductor.createEvidence(new Negation(formula), evals, evidence);
        return evidence;
    }

    private List<String> proofNotPhiNotBNotC(Expression formula, String varA, String varB, String varC) {
        formula = new Negation(formula);
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, true);
        evals.put(varB, false);
        evals.put(varC, false);
        List<String> evidence_A_NotB_NotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, false);
        evals.put(varB, false);
        evals.put(varC, false);
        List<String> evidence_NotA_NotB_NotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidence_NotB_NotC = Deductor.merge(Arrays.asList(new Negation(new Variable(varB)), new Negation(new Variable(varC))), new Variable(varA), evidence_A_NotB_NotC, evidence_NotA_NotB_NotC, formula);
        evidence_NotB_NotC.add(0, "!" + varB + ", !" + varC + " |- " + formula);
        return evidence_NotB_NotC;
    }

    private List<String> proofNotPhiNotANotC(Expression formula, String varA, String varB, String varC) {
        formula = new Negation(formula);
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, false);
        evals.put(varB, false);
        evals.put(varC, false);
        List<String> evidence_NotA_NotB_NotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, false);
        evals.put(varB, true);
        evals.put(varC, false);
        List<String> evidence_NotA_B_NotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidence_NotA_NotC = Deductor.merge(Arrays.asList(new Negation(new Variable(varA)), new Negation(new Variable(varC))), new Variable(varB), evidence_NotA_B_NotC, evidence_NotA_NotB_NotC, formula);
        evidence_NotA_NotC.add(0, "!" + varA + ", !" + varC + " |- " + formula);
        return evidence_NotA_NotC;
    }

    private List<String> proofNotPhiNotANotB(Expression formula, String varA, String varB, String varC) {
        formula = new Negation(formula);
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, false);
        evals.put(varB, false);
        evals.put(varC, false);
        List<String> evidence_NotA_NotB_NotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, false);
        evals.put(varB, false);
        evals.put(varC, true);
        List<String> evidence_NotA_NotB_C = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidence_NotA_NotB = Deductor.merge(Arrays.asList(new Negation(new Variable(varA)), new Negation(new Variable(varB))), new Variable(varC), evidence_NotA_NotB_NotC, evidence_NotA_NotB_C, formula);
        evidence_NotA_NotB.add(0, "!" + varA + ", !" + varB + " |- " + formula);
        return evidence_NotA_NotB;
    }

    private List<String> proofNotPhiNotC(Expression formula, String varA, String varB, String varC) {
        formula = new Negation(formula);
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, false);
        evals.put(varB, false);
        evals.put(varC, false);
        List<String> evidence_NotA_NotB_NotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, false);
        evals.put(varB, true);
        evals.put(varC, false);
        List<String> evidence_NotA_B_NotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidence_NotA_NotC = Deductor.merge(Arrays.asList(new Negation(new Variable(varA)), new Negation(new Variable(varC))), new Variable(varB), evidence_NotA_B_NotC, evidence_NotA_NotB_NotC, formula);

        evals.put(varA, true);
        evals.put(varB, true);
        evals.put(varC, false);
        List<String> evidence_A_B_NotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, true);
        evals.put(varB, false);
        evals.put(varC, false);
        List<String> evidence_A_NotB_NotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidence_A_NotC = Deductor.merge(Arrays.asList(new Variable(varA), new Negation(new Variable(varC))), new Variable(varB), evidence_A_B_NotC, evidence_A_NotB_NotC, formula);

        List<String> evidence_NotC = Deductor.merge(Collections.singletonList(new Negation(new Variable(varC))), new Variable(varA), evidence_A_NotC, evidence_NotA_NotC, formula);

        evidence_NotC.add(0, "!" + varC + " |- " + formula);
        return evidence_NotC;
    }

    private List<String> proofNotPhiNotB(Expression formula, String varA, String varB, String varC) {
        formula = new Negation(formula);
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, false);
        evals.put(varB, false);
        evals.put(varC, false);
        List<String> evidence_NotA_NotB_NotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, false);
        evals.put(varB, false);
        evals.put(varC, true);
        List<String> evidence_NotA_NotB_C = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidence_NotA_NotB = Deductor.merge(Arrays.asList(new Negation(new Variable(varA)), new Negation(new Variable(varB))), new Variable(varC), evidence_NotA_NotB_C, evidence_NotA_NotB_NotC, formula);

        evals.put(varA, true);
        evals.put(varB, false);
        evals.put(varC, true);
        List<String> evidence_A_NotB_C = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, true);
        evals.put(varB, false);
        evals.put(varC, false);
        List<String> evidence_A_NotB_NotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidence_A_NotB = Deductor.merge(Arrays.asList(new Variable(varA), new Negation(new Variable(varB))), new Variable(varC), evidence_A_NotB_C, evidence_A_NotB_NotC, formula);

        List<String> evidence_NotB = Deductor.merge(Collections.singletonList(new Negation(new Variable(varB))), new Variable(varA), evidence_A_NotB, evidence_NotA_NotB, formula);
        evidence_NotB.add(0, "!" + varB + " |- " + formula);
        return evidence_NotB;
    }

    private List<String> proofNotPhiNotA(Expression formula, String varA, String varB, String varC) {
        formula = new Negation(formula);
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, false);
        evals.put(varB, true);
        evals.put(varC, true);
        List<String> evidenceNotABC = Deductor.createEvidence(formula, evals, new ArrayList<>());
        evals.put(varA, false);
        evals.put(varB, false);
        evals.put(varC, true);
        List<String> evidenceNotANotBC = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceNotANotBC);
        List<String> evidenceNotAC = Deductor.merge(Arrays.asList(new Negation(new Variable(varA)), new Variable(varC)), new Variable(varB), evidenceNotABC, evidenceNotANotBC, formula);

        evals.put(varA, false);
        evals.put(varB, false);
        evals.put(varC, false);
        List<String> evidence_NotA_NotB_NotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, false);
        evals.put(varB, true);
        evals.put(varC, false);
        List<String> evidence_NotA_B_NotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidence_NotA_NotC = Deductor.merge(Arrays.asList(new Negation(new Variable(varA)), new Negation(new Variable(varC))), new Variable(varB), evidence_NotA_B_NotC, evidence_NotA_NotB_NotC, formula);

        List<String> evidence_NotA = Deductor.merge(Collections.singletonList(new Negation(new Variable(varA))), new Variable(varC), evidenceNotAC, evidence_NotA_NotC, formula);
        evidence_NotA.add(0, "!" + varA + " |- " + formula);
        return evidence_NotA;
    }

    private List<String> proofNotPhi(Expression formula, String varA, String varB, String varC) {
        return proofPhi(new Negation(formula), varA, varB, varC);
    }

    private List<String> proofPhiABC(Expression formula, String varA, String varB, String varC) {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, true);
        evals.put(varB, true);
        evals.put(varC, true);
        List<String> evidence = new ArrayList<>();
        evidence.add(varA + ", " + varB + ", " + varC + " |- " + formula);
        Deductor.createEvidence(formula, evals, evidence);
        return evidence;
    }

    private List<String> proofPhiBC(Expression formula, String varA, String varB, String varC) {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, true);
        evals.put(varB, true);
        evals.put(varC, true);
        List<String> evidenceABC = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceABC);
        evals.put(varA, false);
        evals.put(varB, true);
        evals.put(varC, true);
        List<String> evidenceNotABC = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceNotABC);
        List<String> merged = Deductor.merge(Arrays.asList(new Variable(varB), new Variable(varC)), new Variable(varA), evidenceABC, evidenceNotABC, formula);
        merged.add(0, varB + ", " + varC + " |- " + formula);
        return merged;
    }

    private List<String> proofPhiAC(Expression formula, String varA, String varB, String varC) {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, true);
        evals.put(varB, true);
        evals.put(varC, true);
        List<String> evidenceABC = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceABC);
        evals.put(varA, true);
        evals.put(varB, false);
        evals.put(varC, true);
        List<String> evidenceANotBC = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceANotBC);
        List<String> merged = Deductor.merge(Arrays.asList(new Variable(varA), new Variable(varC)), new Variable(varB), evidenceABC, evidenceANotBC, formula);
        merged.add(0, varA + ", " + varC + " |- " + formula);
        return merged;
    }

    private List<String> proofPhiAB(Expression formula, String varA, String varB, String varC) {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, true);
        evals.put(varB, true);
        evals.put(varC, true);
        List<String> evidenceABC = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceABC);
        evals.put(varA, true);
        evals.put(varB, true);
        evals.put(varC, false);
        List<String> evidenceABNotC = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceABNotC);
        List<String> merged = Deductor.merge(Arrays.asList(new Variable(varA), new Variable(varB)), new Variable(varC), evidenceABC, evidenceABNotC, formula);
        merged.add(0, varA + ", " + varB + " |- " + formula);
        return merged;
    }

    private List<String> proofPhiC(Expression formula, String varA, String varB, String varC) {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, true);
        evals.put(varB, true);
        evals.put(varC, true);
        List<String> evidenceABC = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceABC);
        evals.put(varA, true);
        evals.put(varB, false);
        evals.put(varC, true);
        List<String> evidenceANotBC = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceANotBC);
        List<String> evidenceAC = Deductor.merge(Arrays.asList(new Variable(varA), new Variable(varC)), new Variable(varB), evidenceABC, evidenceANotBC, formula);

        evals.put(varA, false);
        evals.put(varB, true);
        evals.put(varC, true);
        List<String> evidenceNotABC = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceNotABC);
        evals.put(varA, false);
        evals.put(varB, false);
        evals.put(varC, true);
        List<String> evidenceNotANotBC = new ArrayList<>();
        Deductor.createEvidence(formula, evals, evidenceNotANotBC);
        List<String> evidenceNotAC = Deductor.merge(Arrays.asList(new Negation(new Variable(varA)), new Variable(varC)), new Variable(varB), evidenceNotABC, evidenceNotANotBC, formula);

        List<String> merged = Deductor.merge(Collections.singletonList(new Variable(varC)), new Variable(varA), evidenceAC, evidenceNotAC, formula);
        merged.add(0, varC + " |- " + formula);
        return merged;
    }

    private List<String> proofPhiB(Expression formula, String varA, String varB, String varC) {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, true);
        evals.put(varB, true);
        evals.put(varC, true);
        List<String> evidenceABC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, true);
        evals.put(varB, true);
        evals.put(varC, false);
        List<String> evidenceABNotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidenceAB = Deductor.merge(Arrays.asList(new Variable(varA), new Variable(varB)), new Variable(varC), evidenceABC, evidenceABNotC, formula);

        evals.put(varA, false);
        evals.put(varB, true);
        evals.put(varC, true);
        List<String> evidenceNotABC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, false);
        evals.put(varB, true);
        evals.put(varC, false);
        List<String> evidenceNotABNotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidenceNotAB = Deductor.merge(Arrays.asList(new Negation(new Variable(varA)), new Variable(varB)), new Variable(varC), evidenceNotABC, evidenceNotABNotC, formula);

        List<String> merged = Deductor.merge(Collections.singletonList(new Variable(varB)), new Variable(varA), evidenceAB, evidenceNotAB, formula);
        merged.add(0, varB + " |- " + formula);
        return merged;
    }

    private List<String> proofPhiA(Expression formula, String varA, String varB, String varC) {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, true);
        evals.put(varB, true);
        evals.put(varC, true);
        List<String> evidenceABC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, true);
        evals.put(varB, true);
        evals.put(varC, false);
        List<String> evidenceABNotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidenceAB = Deductor.merge(Arrays.asList(new Variable(varA), new Variable(varB)), new Variable(varC), evidenceABC, evidenceABNotC, formula);

        evals.put(varA, true);
        evals.put(varB, false);
        evals.put(varC, true);
        List<String> evidenceANotBC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, true);
        evals.put(varB, false);
        evals.put(varC, false);
        List<String> evidenceANotBNotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidenceANotB = Deductor.merge(Arrays.asList(new Variable(varA), new Negation(new Variable(varB))), new Variable(varC), evidenceANotBC, evidenceANotBNotC, formula);

        List<String> evidenceA = Deductor.merge(Collections.singletonList(new Variable(varA)), new Variable(varB), evidenceAB, evidenceANotB, formula);
        evidenceA.add(0, varA + " |- " + formula);
        return evidenceA;
    }

    private List<String> proofPhi(Expression formula, String varA, String varB, String varC) {
        HashMap<String, Boolean> evals = new HashMap<>();
        evals.put(varA, false);
        evals.put(varB, false);
        evals.put(varC, true);
        List<String> evidenceNotANotBC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, false);
        evals.put(varB, false);
        evals.put(varC, false);
        List<String> evidenceNotANotBNotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidenceNotANotB = Deductor.merge(Arrays.asList(new Negation(new Variable(varA)), new Negation(new Variable(varB))), new Variable(varC), evidenceNotANotBC, evidenceNotANotBNotC, formula);

        evals.put(varA, false);
        evals.put(varB, true);
        evals.put(varC, true);
        List<String> evidenceNotABC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        evals.put(varA, false);
        evals.put(varB, true);
        evals.put(varC, false);
        List<String> evidenceNotABNotC = Deductor.createEvidence(formula, evals, new ArrayList<>());

        List<String> evidenceNotAB = Deductor.merge(Arrays.asList(new Negation(new Variable(varA)), new Variable(varB)), new Variable(varC), evidenceNotABC, evidenceNotABNotC, formula);

        List<String> evidenceNotA = Deductor.merge(Collections.singletonList(new Negation(new Variable(varA))), new Variable(varB), evidenceNotAB, evidenceNotANotB, formula);

        List<String> evidenceA = proofPhiA(formula, varA, varB, varC);
        evidenceA.remove(0);

        List<String> merged = Deductor.merge(new ArrayList<>(), new Variable(varA), evidenceA, evidenceNotA, formula);

        merged.add(0, "|- " + formula);
        return merged;
    }

    private void check(List<String> ev) {
        ProofParser proofParser = new ProofParser();
        proofParser.parse(ev);
        if (!proofParser.isEvidenceCorrect()) {
            throw new RuntimeException();
        }
    }
}
