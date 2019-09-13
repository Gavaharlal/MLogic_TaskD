package proofs;

import parser.Expression;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProofPlacer {

    private ProofPlacer() {
    }

    //deduce_axiom_or_hypothesis

    public static List<String> getConjunction00(Expression a, Expression b) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("B", b.toString());
        return makeProof("conj00", replacement);
    }

    public static List<String> getConjunction01(Expression a, Expression b) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("B", b.toString());
        return makeProof("conj01", replacement);
    }

    public static List<String> getConjunction10(Expression a, Expression b) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("B", b.toString());
        return makeProof("conj10", replacement);
    }

    public static List<String> getConjunction11(Expression a, Expression b) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("B", b.toString());
        return makeProof("conj11", replacement);
    }


    public static List<String> getDisjunction00(Expression a, Expression b) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("B", b.toString());
        return makeProof("disj00", replacement);
    }

    public static List<String> getDisjunction01(Expression a, Expression b) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("B", b.toString());
        return makeProof("disj01", replacement);
    }

    public static List<String> getDisjunction10(Expression a, Expression b) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("B", b.toString());
        return makeProof("disj10", replacement);
    }

    public static List<String> getDisjunction11(Expression a, Expression b) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("B", b.toString());
        return makeProof("disj11", replacement);
    }


    public static List<String> getImplication00(Expression a, Expression b) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("B", b.toString());
        return makeProof("impl00", replacement);
    }

    public static List<String> getImplication01(Expression a, Expression b) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("B", b.toString());
        return makeProof("impl01", replacement);
    }

    public static List<String> getImplication10(Expression a, Expression b) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("B", b.toString());
        return makeProof("impl10", replacement);
    }

    public static List<String> getImplication11(Expression a, Expression b) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("B", b.toString());
        return makeProof("impl11", replacement);
    }

    public static List<String> getNegation0(Expression a) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        return makeProof("neg0", replacement);
    }

    public static List<String> getNegation1(Expression a) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        return makeProof("neg1", replacement);
    }


    public static List<String> getLawOfIdentity(Expression expression) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", expression.toString());
        return makeProof("law_of_identity", replacement);
    }

    public static List<String> getDeducedAxiomOrHypothesis(Expression a, Expression c) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("C", c.toString());
        return makeProof("deduce_axiom_or_hypothesis", replacement);
    }

    //a -> d
    //a -> (d -> c)
    //...
    //a -> c
    public static List<String> getDeducedModusPonens(Expression a, Expression d, Expression c) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("D", d.toString());
        replacement.put("C", c.toString());
        return makeProof("deduce_mp", replacement);
    }

    public static List<String> getMerge(Expression a, Expression c) {
        HashMap<String, String> replacement = new HashMap<>();
        replacement.put("A", a.toString());
        replacement.put("C", c.toString());
        return makeProof("merge", replacement);
    }

    private static List<String> makeProof(String proofFile, HashMap<String, String> replacement) {
        Path proofPath = Paths.get("src", "proofs", proofFile);
        List<String> lines;
        try {
            lines = Files.readAllLines(proofPath);
        } catch (IOException e) {
            throw new RuntimeException();
        }

        List<String> result = new ArrayList<>();
        lines.forEach(line -> result.add(replace(line, replacement)));
        return result;
    }

    private static String replace(String line, HashMap<String, String> replacementRule) {
        String patternString = "(" + String.join("|", replacementRule.keySet()) + ")";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(line);

        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, replacementRule.get(matcher.group(1)));
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }
}
