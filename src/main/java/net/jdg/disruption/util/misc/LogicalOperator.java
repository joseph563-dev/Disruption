package net.jdg.disruption.util.misc;

public enum LogicalOperator {
    OR,
    NOT,
    XOR,
    AND;

    public boolean check(boolean a, boolean b) {
        switch (this) {
            case OR -> {
                return a || b;
            }
            case AND -> {
                return a && b;
            }
            case XOR -> {
                return !(a == b);
            }
            case NOT -> {
                return a != b;
            }
            default -> {
                return true;
            }
        }
    }
}
