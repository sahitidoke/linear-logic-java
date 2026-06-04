import java.util.ArrayList;
import java.util.List;

public record Sequent(
        List<Formula> left,
        List<Formula> right
) {
    public Sequent {
        left = new ArrayList<>(left);
        right = new ArrayList<>(right);
    }
    @Override
public String toString() {
    return left + " ⊢ " + right;
}

}