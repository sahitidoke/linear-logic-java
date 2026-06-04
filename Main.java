import java.util.*;

public class Main {

    public static void main(String[] args) {

        Formula A = new Atom("A");
        Formula B = new Atom("B");
        Formula C = new Atom("C");

        testTensorLeft(A, B, C);
        testParLeft(A, B, C);
        testPlusLeft(A, B, C);
        testWithLeft(A, B, C);
        testImplicationLeft(A, B, C);
        testTensorRight(A, B);
        testWithRight(A, B);
        testPlusRight(A, B);
        testParRight(A, B);
    } 
    private static void testTensorLeft(Formula A, Formula B, Formula C) {

        System.out.println("\n=== ⊗L Tensor Left ===");
        Tensor t = new Tensor(A, B);

        Sequent s = new Sequent(
                List.of(t),
                List.of(C)
        );
        System.out.println("Input: " + s);
        List<Sequent> result = Rules.tensorLeft(s);
        result.forEach(r -> System.out.println("Output: " + r));
    }

    private static void testParLeft(Formula A, Formula B, Formula C) {
        System.out.println("\n=== ⅋L Par Left ===");
        Par p = new Par(A, B);
        Sequent s = new Sequent(
                List.of(p),
                List.of(C)
        );
        System.out.println("Input: " + s);
        Rules.parLeft(s)
                .forEach(r -> System.out.println("Output: " + r));
    }
    private static void testPlusLeft(Formula A, Formula B, Formula C) {
        System.out.println("\n=== ⊕L Plus Left ===");
        Plus p = new Plus(A, B);

        Sequent s = new Sequent(
                List.of(p),
                List.of(C)
        );
        System.out.println("Input: " + s);
        Rules.plusLeft(s)
                .forEach(r -> System.out.println("Output: " + r));
    }
    private static void testWithLeft(Formula A, Formula B, Formula C) {

        System.out.println("\n=== &L With Left ===");

        With w = new With(A, B);

        Sequent s = new Sequent(
                List.of(w),
                List.of(C)
        );

        System.out.println("Input: " + s);

        Rules.withLeft(s)
             .forEach(r -> System.out.println("Output: " + r));
    }
    private static void testImplicationLeft(Formula A, Formula B, Formula C) {

        System.out.println("\n=== ⊸L Implication Left ===");

        LinearImplication imp = new LinearImplication(A, B);

        Sequent s = new Sequent(
                List.of(imp),
                List.of(C)
        );

        System.out.println("Input: " + s);

        Rules.implicationLeft(s)
                .forEach(r -> System.out.println("Output: " + r));
    }
    private static void testTensorRight(Formula A, Formula B) {

        System.out.println("\n=== ⊗R Tensor Right ===");

        Sequent left = new Sequent(List.of(), List.of(A));
        Sequent right = new Sequent(List.of(), List.of(B));

        System.out.println("Input left: " + left);
        System.out.println("Input right: " + right);

        Sequent result = Rules.tensorRight(left, right, A, B);

        System.out.println("Output: " + result);
    }
    private static void testWithRight(Formula A, Formula B) {

        System.out.println("\n=== &R With Right ===");

        Sequent left = new Sequent(List.of(), List.of(A));
        Sequent right = new Sequent(List.of(), List.of(B));

        System.out.println("Input left: " + left);
        System.out.println("Input right: " + right);

        Sequent result = Rules.withRight(left, right, A, B);

        System.out.println("Output: " + result);
    }
    private static void testPlusRight(Formula A, Formula B) {

        System.out.println("\n=== ⊕R Plus Right ===");

        Sequent s = new Sequent(List.of(), List.of(new Plus(A, B)));

        System.out.println("Input: " + s);

        System.out.println("Left injection:");
        Sequent leftChoice = new Sequent(s.left(), List.of(A));
        System.out.println(leftChoice);

        System.out.println("Right injection:");
        Sequent rightChoice = new Sequent(s.left(), List.of(B));
        System.out.println(rightChoice);
    }
    private static void testParRight(Formula A, Formula B) {

        System.out.println("\n=== ⅋R Par Right ===");

        Sequent s = new Sequent(List.of(), List.of(new Par(A, B)));

        System.out.println("Input: " + s);

        Rules.parRight(s, A, B)
                .forEach(r -> System.out.println("Output: " + r));
    }
}