package compp;

import java.util.LinkedList;

/**
 * @author 9albeldine
 */
public class TextTool {
    private String text = "";
    private LinkedList<String> savedStrings = new LinkedList<>();

    public void reset(String text) {
        if (text == null)
            text = "";

        this.text = text;
        savedStrings.clear();
    }

    public void saveLiteralStrings() {
        savedStrings.clear();
        boolean insideString = false;
        StringBuilder buffer = new StringBuilder();
        StringBuilder literalString = new StringBuilder("\"");
        for (char c : text.toCharArray()) {
            if (c == '\"') {
                if (insideString) {
                    savedStrings.addLast(literalString.append("\"").toString());
                    literalString = new StringBuilder();
                } else
                    buffer.append("\"");
                insideString = !insideString;
            } else {
                if (!insideString)
                    buffer.append(c);
                else {
                    literalString.append(c);
                }
            }
        }

        if (insideString)
            savedStrings.addLast(literalString.append("\"").toString());

        text = buffer.toString();
    }

    public LinkedList<String> getSavedStrings() {
        return savedStrings;
    }

    public String getText() {
        if (text == null || text.isEmpty())
            return "";

        String result = text.replace("\"", "$");

        int index = 0;
        while (index < savedStrings.size()) {
            result = result.replaceFirst("\\$", savedStrings.get(index));
            index++;
        }

        return result;
    }

    public String getRawText() {
        if (text == null)
            return "";
        else
            return text;
    }

    private static int TestType(char A) {

        if (((Character.isAlphabetic(A)) && (Character.isUpperCase(A))) || A == '_') return 1;
        if ((Character.isDigit(A))) return 2;
        if (A == ';' || A == '(' || A == ')' || A == '<' || A == '>' || A == ':' || A == '=') return 3;
        if (A == '/' || A == '*') return 5;
        if (A == '-' || A == '+') return 4;
        if (A == '.') return 6;
        return 0;
    }

    private static boolean allow(char a, char b) {
        if (TestType(a) == TestType(b)) return true;
        if ((TestType(a) == 1 && TestType(b) == 2) || (TestType(a) == 2 && TestType(b) == 1)) return true;
        if ((TestType(a) == 2 && TestType(b) == 6) || (TestType(a) == 6 && TestType(b) == 2)) return true;
        return (TestType(a) == 4 && TestType(b) == 2);
    }

    public void makeDyZe() {
        if (text == null || text.isEmpty()) {
            text = "";
            return;
        }

        StringBuilder builder = new StringBuilder();
        int i = 0;
        do {
            do {
                if (text.charAt(i) == '#') {
                    i++;
                    break;
                }
                builder.append(text.charAt(i));
                i++;
            } while ((i < text.length()) && allow(text.charAt(i - 1), text.charAt(i)));

            if (i < text.length() && text.charAt(i) != '#') builder.append('#');
        } while (i < text.length());

        text = removeExtraHashtag(builder.append('#').toString());
    }

    private static String removeExtraHashtag(String s) {
        s = s.replaceAll("#+", "#");

        if (s.equals("END#.#") || s.endsWith("#END#.#"))
            s = s.substring(0, s.length() - 3) + ".#";

        return s;
    }

    public void removeComments() {
        text = text.replaceAll("\n", " ");
        char c = '$';
        String s1, s2;

        while (true) {
            int L = text.length();
            int X1 = text.indexOf(c);
            if (X1 == (-1)) {
                return;
            } else if (X1 == (L - 1)) {
                text = text.substring(0, L - 1);
                break;
            } else {
                int X2 = text.indexOf('$', X1 + 1);
                if ((X2 == (-1)) || (X2 == (L - 1))) {
                    if (X1 == 0) {
                        text = " ";
                        break;
                    } else if (X1 == 1) {
                        text = text.charAt(0) + " ";
                        break;
                    } else {
                        text = text.substring(0, X1);
                        break;
                    }
                } else {
                    s2 = text.substring((X2 + 1), L);
                    s1 = text.substring(0, X1);
                    text = s1 + " " + s2;
                }
            }

        }
        while (text.contains("  ")) {
            text = text.replace(" ", " ");
        }
    }


    public void removeSpaces() {
        text = text.trim();
        if (text.equals("END") || (text.length() > 3 && !Character.isUpperCase(text.charAt(text.length() - 4))))
            text += ".";
        text = text.replaceAll("\\s+", "#");
    }

}