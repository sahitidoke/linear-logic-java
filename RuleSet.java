import java.util.List;

public final class RuleSet {

    private RuleSet() {}

    public static List<Rule> defaultRules() {
        return List.of(
        new TensorRightRule(),
        new WithRightRule(),
        new PlusRightRule(),
        new ParRightRule(),

        new TensorLeftRule(),
        new PlusLeftRule(),
        new ParLeftRule(),
        new WithLeftRule(),
        new ImplicationLeftRule()
);
    }
}