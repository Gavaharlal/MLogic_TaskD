import parser.Expression;
import parser.ExpressionParser;
import proofParser.ProofParser;
import proofParser.solvers.OneVariableSolver;
import proofParser.solvers.ThreeVariablesSolver;
import proofParser.solvers.TwoVariablesSolver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {

        ProofParser proofParser = new ProofParser();
        ExpressionParser expressionParser = new ExpressionParser();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("in.txt")));

        Expression formula = expressionParser.parse(in.readLine());

        Set<String> varSet = formula.getVariablesSet();

        List<String> evidence;
        Iterator<String> iterator;
        switch (varSet.size()) {
            case 1:
                OneVariableSolver oneVariableSolver = new OneVariableSolver();
                evidence = oneVariableSolver.solve(formula, varSet.iterator().next());
                if (evidence == null) {
                    System.out.println(":(");
                } else {
                    System.out.println(evidence.get(0));
                    proofParser.parse(evidence);
                    proofParser.getEvidenceList().forEach(System.out::println);
                }
                break;
            case 2:
                TwoVariablesSolver twoVariablesSolver = new TwoVariablesSolver();
                iterator = varSet.iterator();
                evidence = twoVariablesSolver.solve(formula, iterator.next(), iterator.next());
                if (evidence == null) {
                    System.out.println(":(");
                } else {
                    System.out.println(evidence.get(0));
                    proofParser.parse(evidence);
                    proofParser.getEvidenceList().forEach(System.out::println);
                }
                break;
            case 3:
                ThreeVariablesSolver threeVariablesSolver = new ThreeVariablesSolver();
                iterator = varSet.iterator();
                evidence = threeVariablesSolver.solve(formula, iterator.next(), iterator.next(), iterator.next());
                if (evidence == null) {
                    System.out.println(":(");
                } else {
                    System.out.println(evidence.get(0));
                    proofParser.parse(evidence);
                    proofParser.getEvidenceList().forEach(System.out::println);
                }
                break;
        }
    }
}
