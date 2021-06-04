package compp;

import java.io.Serializable;
import java.util.Objects;

/**
 * An instance of this class represent the result of analyzing a token
 */
public class AnalyzedToken implements Serializable {
    private static final long serialVersionUID = -4822777314533930435L;

    private String token;
    private Type type;

    AnalyzedToken(String token, Type type) {
        this.token = Objects.requireNonNull(token);
        this.type = Objects.requireNonNull(type);
    }

    public String getToken() {
        return token;
    }

    public boolean isValid() {
        return type.isValid(token);
    }

    public String getType() {
        if (type != Type.IDENTIFIER) {
            return type.toString();
        } else {
            if (Type.IDENTIFIER.isKeyword(token))
                return "Keyword";
            else if (Type.IDENTIFIER.isType(token))
                return "Type";
            else
                return type.toString();
        }
    }

    @Override
    public String toString() {
        return "result [token: " + token + ", type: " + type + "]";
    }
}
