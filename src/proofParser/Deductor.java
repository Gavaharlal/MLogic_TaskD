package proofParser;

import parser.Expression;
import parser.ExpressionType;
import parser.operations.unary.Negation;
import proofs.ProofPlacer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Deductor {

    private static final ProofParser proofParser = new ProofParser();


    private static List<String> deduce(List<Expression> restHypotheses, Expression from, Expression theorem, List<String> evidenceStrings) {


        String hypsAsString = restHypotheses.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        String sourceHeading = (hypsAsString.isEmpty() ? from.toString() : hypsAsString + ", " + from.toString()) + "|- " + theorem.toString();

        List<String> fullSource = new ArrayList<>();
        fullSource.add(sourceHeading);
        fullSource.addAll(evidenceStrings);

        proofParser.parse(fullSource);

        List<String> deducedEvidenceStrings = new ArrayList<>();

        proofParser.getEvidenceList().forEach(
                currentEvidenceLine -> {
                    if (currentEvidenceLine.getExpression().equals(from)) {
                        deducedEvidenceStrings.addAll(ProofPlacer.getLawOfIdentity(from));
                    } else {
                        switch (currentEvidenceLine.getStringType()) {
                            case AXIOM:
                            case HYPOTHESIS:
                                deducedEvidenceStrings.addAll(ProofPlacer.getDeducedAxiomOrHypothesis(from, currentEvidenceLine.getExpression()));
                                break;
                            case MODUS_PONENS:
                                deducedEvidenceStrings.addAll(ProofPlacer.getDeducedModusPonens(from, currentEvidenceLine.getFrom(), currentEvidenceLine.getExpression()));
                                break;
                        }
                    }
                }
        );

        return deducedEvidenceStrings;
    }

//    public List<String> deduce(InputStream inputStream) throws IOException {
//        proofParser.parse(inputStream);
//
//        EvidenceView evidenceView = proofParser.getEvidenceView();
//
//        List<Expression> fullHyps = evidenceView.getHypotheses();
//        List<Expression> restHyps = fullHyps.subList(0, fullHyps.size() - 1);
//        Expression from = fullHyps.get(fullHyps.size() - 1);
//        Expression theorem = evidenceView.getTheorem();
//        List<LineOfEvidence> evidenceArrayList = evidenceView.getEvidenceList();
//
//        return deduce(restHyps, from, theorem, evidenceArrayList);
//    }

    public static List<String> merge(List<Expression> restHypsForA, Expression a, List<String> evidence,
                                     List<Expression> restHypsForNotA, Expression notA, List<String> evidenceNotA, Expression theorem) {
        List<String> evidenceStrings = new ArrayList<>();
        List<String> deductionForA = deduce(restHypsForA, a, theorem, evidence);
        List<String> deductionForNotA = deduce(restHypsForNotA, notA, theorem, evidenceNotA);

        evidenceStrings.addAll(deductionForA);
        evidenceStrings.addAll(deductionForNotA);
        evidenceStrings.addAll(ProofPlacer.getMerge(a, theorem));

        return evidenceStrings;
    }

    public static List<String> merge(List<Expression> restHyps, Expression a, List<String> evidence, List<String> evidenceNotA, Expression theorem) {
        return merge(restHyps, a, evidence, restHyps, new Negation(a), evidenceNotA, theorem);
    }

    public static List<String> createEvidence(Expression formula, HashMap<String, Boolean> evals, List<String> curEvidenceList) {
        if (formula.getType() == ExpressionType.VARIABLE) {
            if (evals.get(formula.toString())) {
                curEvidenceList.add(formula.toString());
            }
        } else {
            Expression leftOp = formula.getLeftOperand();
            Expression rightOp = formula.getRightOperand();
            boolean a = leftOp.evaluate(evals);
            boolean b = rightOp.evaluate(evals);
            switch (formula.getType()) {
                case NEGATION:
                    createEvidence(leftOp, evals, curEvidenceList);
                    boolean val = formula.getLeftOperand().evaluate(evals);
                    if (val) {
                        curEvidenceList.addAll(ProofPlacer.getNegation1(leftOp));
                    } else {
                        curEvidenceList.addAll(ProofPlacer.getNegation0(leftOp));
                    }
                    break;
                case IMPLICATION:
                    createEvidence(leftOp, evals, curEvidenceList);
                    createEvidence(rightOp, evals, curEvidenceList);
                    if (a) {
                        if (b) {
                            curEvidenceList.addAll(ProofPlacer.getImplication11(leftOp, rightOp));
                        } else {
                            curEvidenceList.addAll(ProofPlacer.getImplication10(leftOp, rightOp));
                        }
                    } else {
                        if (b) {
                            curEvidenceList.addAll(ProofPlacer.getImplication01(leftOp, rightOp));
                        } else {
                            curEvidenceList.addAll(ProofPlacer.getImplication00(leftOp, rightOp));
                        }
                    }
                    break;
                case DISJUNCTION:
                    createEvidence(leftOp, evals, curEvidenceList);
                    createEvidence(rightOp, evals, curEvidenceList);
                    if (a) {
                        if (b) {
                            curEvidenceList.addAll(ProofPlacer.getDisjunction11(leftOp, rightOp));
                        } else {
                            curEvidenceList.addAll(ProofPlacer.getDisjunction10(leftOp, rightOp));
                        }
                    } else {
                        if (b) {
                            curEvidenceList.addAll(ProofPlacer.getDisjunction01(leftOp, rightOp));
                        } else {
                            curEvidenceList.addAll(ProofPlacer.getDisjunction00(leftOp, rightOp));
                        }
                    }
                    break;
                case CONJUNCTION:
                    createEvidence(leftOp, evals, curEvidenceList);
                    createEvidence(rightOp, evals, curEvidenceList);
                    if (a) {
                        if (b) {
                            curEvidenceList.addAll(ProofPlacer.getConjunction11(leftOp, rightOp));
                        } else {
                            curEvidenceList.addAll(ProofPlacer.getConjunction10(leftOp, rightOp));
                        }
                    } else {
                        if (b) {
                            curEvidenceList.addAll(ProofPlacer.getConjunction01(leftOp, rightOp));
                        } else {
                            curEvidenceList.addAll(ProofPlacer.getConjunction00(leftOp, rightOp));
                        }
                    }
                    break;
            }
        }
        return curEvidenceList;
    }
}
