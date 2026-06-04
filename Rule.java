import java.util.List;

public interface Rule {

    String name();
    List<Sequent> apply(Sequent sequent);
}