package compp;

import java.util.Arrays;
import java.util.List;

public enum Type {
    STRING("String"), UNDEFINED("Undefined", -1), OPERATOR("Operator", 7, 8, 12, 11), SEPARATOR("Separator", 9, 10),
    REAL("Real number", 6) {
        @Override
        public boolean isValid(String token) {
            if (token != null && token.length() <= 9) {
                try {
                    double real = Double.parseDouble(token);
                    return real < 1000000000;
                } catch (Exception e) {
                    return false;
                }
            } else {
                return false;
            }
        }
    },
    INTEGER("Integer", 4) {
        @Override
        public boolean isValid(String token) {
            if (token != null && token.length() <= 7) {
                try {
                    int integer = Integer.parseInt(token);
                    return integer < 1657635;
                } catch (Exception e) {
                    return false;
                }
            } else
                return false;
        }
    },
    IDENTIFIER("Identifier", 1, 2) {
        @Override
        public boolean isValid(String token) {
            return token != null && token.length() <= 40 && token.matches("[A-Z]+");
        }

        @Override
        public boolean isKeyword(String token) {
            if (token != null) {
                switch (token) {
                    case "BEGIN":
                    case "END":
                    case "IF":
                    case "ELSE":
                    case "WHILE":
                    case "SWITCH":
                    case "BREAK":
                    case "PUBLIC":
                    case "case":
                    case "THIS":
                    case "FOR":
                    case "DO":
                        return true;
                    default:
                        return false;
                }
            } else {
                return false;
            }
        }

        @Override
        public boolean isType(String token) {
            if (token != null) {
                switch (token) {
                    case "INT":
                    case "FLOAT":
                    case "STRING":
                    case "CHAR":
                    case "DOUBLE":
                    case "BOOLEAN":
                        return true;
                    default:
                        return false;
                }
            } else {
                return false;
            }
        }
    };
    private List<Integer> stats;
    private String name;

    Type(String name, Integer... stat) {
        this.name = name;
        this.stats = Arrays.asList(stat);
    }

    public List<Integer> getStats() {
        return stats;
    }

    public boolean isValid(String token) {
        return true;
    }

    public boolean isKeyword(String token) {
        return false;
    }

    public boolean isType(String token) {
        return false;
    }

    public static Type getProperType(int stat) {
        for (Type type: values()) {
            if (type.getStats().contains(stat))
                return type;
        }
        return UNDEFINED;
    }

    @Override
    public String toString() {
        return name;
    }
}
