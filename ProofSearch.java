import java.util.*;

public class ProofSearch {

    private final List<Rule> rules;

    public ProofSearch(List<Rule> rules) {
        this.rules = rules;
    }

    public boolean prove(Sequent goal) {
        return dfs(goal, new HashSet<>());
    }
        private boolean dfs(Sequent current, Set<String> visited) {

    if (isAxiom(current)) {
        return true;
    }

    String key = current.toString();
    if (!visited.add(key)) {
        return false;
    }
    for (Rule r : rules) {

    List<Sequent> nexts = r.apply(current);

    if (nexts.isEmpty()) continue;
    if (r.name().equals("⊗R") || r.name().equals("&R")) {

        boolean allOk = true;

        for (Sequent n : nexts) {
            if (!dfs(copy(n), new HashSet<>(visited))) {
                allOk = false;
                break;
            }
        }

        if (allOk) return true;
    }
    else {

        for (Sequent n : nexts) {
            if (dfs(copy(n), new HashSet<>(visited))) {
                return true;
            }
        }
    }
}

    return false;
}
       private boolean isAxiom(Sequent s) {

    for (Formula f : s.left()) {
        for (Formula g : s.right()) {
            if (f.equals(g)) {
                return true;
            }
        }
    }

    return false;
}
        private Sequent copy(Sequent s) {
            return new Sequent(
                    new ArrayList<>(s.left()),
                    new ArrayList<>(s.right())
            );
    }
}


