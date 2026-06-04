sealed interface Formula permits Atom,
        Tensor, Par,
        With, Plus,
        LinearImplication {

}

record Atom(String name) implements Formula {

    @Override
    public String toString() {
        return name;
    }
    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

record Tensor(Formula left,
              Formula right)
        implements Formula {

    @Override
    public String toString() {
        return "(" + left + " ⊗ " + right + ")";
    }
    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
} 

record Par(Formula left, Formula right) implements Formula {
    @Override
    public String toString() {
        return "(" + left + " ⅋ " + right + ")";
    }
    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

record With(Formula left, Formula right) implements Formula {
    @Override
    public String toString() {
        return "(" + left + " & " + right + ")";
    }
    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
}

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

record Plus(Formula left, Formula right) implements Formula {
    @Override
    public String toString() {
        return "(" + left + " ⊕ " + right + ")";
    }
    @Override
public boolean equals(Object o) {
    return this.toString().equals(o.toString());
}

@Override
public int hashCode() {
    return toString().hashCode();
}
} 

record LinearImplication(Formula premise, Formula conclusion)implements Formula {

    @Override
        public String toString() {
         return "(" + premise + " ⊸ " + conclusion + ")";
    }
    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }
     @Override
    public int hashCode() {
        return toString().hashCode();
    }
} 