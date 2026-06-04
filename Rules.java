import java.util.ArrayList;
import java.util.List;
public class Rules {

    private static final TensorLeftRule tensorLeft = new TensorLeftRule();
    private static final PlusLeftRule plusLeft = new PlusLeftRule();
    private static final ParLeftRule parLeft = new ParLeftRule();
    private static final WithLeftRule withLeft = new WithLeftRule();
    private static final ImplicationLeftRule impLeft = new ImplicationLeftRule();
    public static List<Sequent> tensorLeft(Sequent s) {
        return tensorLeft.apply(s);
    }
    public static List<Sequent> plusLeft(Sequent s) {
        return plusLeft.apply(s);
    }
    public static List<Sequent> parLeft(Sequent s) {
        return parLeft.apply(s);
    }
    public static List<Sequent> withLeft(Sequent s) {
        return withLeft.apply(s);
    }
    public static List<Sequent> implicationLeft(Sequent s) {
        return impLeft.apply(s);
    }
    public static List<Sequent> parRight(Sequent s, Formula A, Formula B) { 
    return List.of(
            new Sequent(s.left(), List.of(A)),
            new Sequent(s.left(), List.of(B))
    );
    }
    public static Sequent withRight(Sequent left, Sequent right, Formula A, Formula B) {

        List<Formula> ctx = new ArrayList<>();
        ctx.addAll(left.left());
        ctx.addAll(right.left());

        return new Sequent(ctx, List.of(new With(A, B)));
    }
    public static Sequent plusRightLeft(Sequent s, Formula A, Formula B) {
        return new Sequent(s.left(), List.of(new Plus(A, B)));
    }
    public static Sequent plusRightRight(Sequent s, Formula A, Formula B) {
        return new Sequent(s.left(), List.of(new Plus(A, B)));
    }
    public static Sequent tensorRight(Sequent left, Sequent right, Formula A, Formula B) {

        // combine contexts
        List<Formula> ctx = new ArrayList<>();
        ctx.addAll(left.left());
        ctx.addAll(right.left());

        return new Sequent(ctx, List.of(new Tensor(A, B)));
    }
}

 
class ImplicationLeftRule implements Rule {

    @Override
    public String name() {
        return "⊸L";
    }

    @Override
    public List<Sequent> apply(Sequent s) {
        for (Formula f : s.left()) {

            if (f instanceof LinearImplication imp) {
                List<Formula> leftWithoutImp = new ArrayList<>(s.left());
                leftWithoutImp.removeIf(x -> x == imp);
                Sequent goal1 = new Sequent(
                        List.of(),                 
                        List.of(imp.premise())
                );
                List<Formula> left2 = new ArrayList<>(leftWithoutImp);
                left2.add(imp.conclusion());

                Sequent goal2 = new Sequent(
                        left2,
                        s.right()
                );

                return List.of(goal1, goal2);
            }
        }

        return List.of();
    }
}
class TensorLeftRule implements Rule {

    @Override
    public String name() {
        return "⊗L";
    }

    @Override
    public List<Sequent> apply(Sequent s) {

        for (Formula f : s.left()) {

            if (f instanceof Tensor t) {

                List<Formula> left = new ArrayList<>(s.left());
                left.removeIf(x -> x==t);
                left.add(t.left());
                left.add(t.right());

                return List.of(new Sequent(left, s.right()));
            }
        }

        return List.of();
    }
}
 class PlusLeftRule implements Rule {

    @Override
    public String name() {
        return "⊕L";
    }

    @Override
    public List<Sequent> apply(Sequent s) {

        for (Formula f : s.left()) {

            if (f instanceof Plus p) {

                List<Formula> left1 = new ArrayList<>(s.left());
                left1.removeIf(x -> x==p);
                left1.add(p.left());

                List<Formula> left2 = new ArrayList<>(s.left());
                left2.removeIf(x -> x==p);
                left2.add(p.right());

                return List.of(
                        new Sequent(left1, s.right()),
                        new Sequent(left2, s.right())
                );
            }
        }

        return List.of();
    }
}
 class ParLeftRule implements Rule {

    @Override
    public String name() {
        return "⅋L";
    }

    @Override
    public List<Sequent> apply(Sequent s) {

        for (Formula f : s.left()) {

            if (f instanceof Par p) {

                List<Formula> left1 = new ArrayList<>(s.left());
                left1.removeIf(x->x==p);
                left1.add(p.left());

                List<Formula> left2 = new ArrayList<>(s.left());
                left2.removeIf(x->x==p);
                left2.add(p.right());

                return List.of(
                        new Sequent(left1, s.right()),
                        new Sequent(left2, s.right())
                );
            }
        }

        return List.of();
    }
}
class WithLeftRule implements Rule {

    @Override
    public String name() {
        return "&L";
    }

    @Override
    public List<Sequent> apply(Sequent s) {

        for (Formula f : s.left()) {

            if (f instanceof With w) {

                List<Formula> left1 = new ArrayList<>(s.left());
                left1.removeIf(x -> x==w);
                left1.add(w.left());

                List<Formula> left2 = new ArrayList<>(s.left());
                left2.removeIf(x -> x==w);
                left2.add(w.right());

                return List.of(
                        new Sequent(left1, s.right()),
                        new Sequent(left2, s.right())
                );
            }
        }

        return List.of();
    }
}
class TensorRightRule implements Rule {

    @Override
    public String name() {
        return "⊗R";
    }

    @Override
    public List<Sequent> apply(Sequent s) {

        if (s.right().size() != 1) return List.of();

        Formula f = s.right().get(0);

        if (!(f instanceof Tensor t)) {
            return List.of();
        }
        Sequent leftGoal = new Sequent(
                new ArrayList<>(s.left()),
                List.of(t.left())
        );

        Sequent rightGoal = new Sequent(
                new ArrayList<>(s.left()),
                List.of(t.right())
        );

        return List.of(leftGoal, rightGoal);
    }
}
class PlusRightRule implements Rule {

    @Override
    public String name() {
        return "⊕R";
    }

    @Override
    public List<Sequent> apply(Sequent s) {

        if (s.right().size() != 1) return List.of();

        Formula f = s.right().get(0);

        if (!(f instanceof Plus p)) {
            return List.of();
        }


        Sequent leftChoice = new Sequent(
                new ArrayList<>(s.left()),
                List.of(p.left())
        );

        Sequent rightChoice = new Sequent(
                new ArrayList<>(s.left()),
                List.of(p.right())
        );

        return List.of(leftChoice, rightChoice);
    }
}
class WithRightRule implements Rule {

    @Override
    public String name() {
        return "&R";
    }

    @Override
    public List<Sequent> apply(Sequent s) {

        if (s.right().size() != 1) return List.of();

        Formula f = s.right().get(0);

        if (!(f instanceof With w)) {
            return List.of();
        }

        Sequent leftGoal = new Sequent(
                new ArrayList<>(s.left()),
                List.of(w.left())
        );

        Sequent rightGoal = new Sequent(
                new ArrayList<>(s.left()),
                List.of(w.right())
        );

        return List.of(leftGoal, rightGoal);
    }
}

class ParRightRule implements Rule {

    @Override
    public String name() {
        return "⅋R";
    }

    @Override
    public List<Sequent> apply(Sequent s) {

        if (s.right().size() != 1) return List.of();

        Formula f = s.right().get(0);

        if (!(f instanceof Par p)) {
            return List.of();
        }

        List<Formula> newRight = new ArrayList<>(s.right());
        newRight.remove(p);
        newRight.add(p.left());
        newRight.add(p.right());

        return List.of(new Sequent(
                new ArrayList<>(s.left()),
                newRight
        ));
    }
}