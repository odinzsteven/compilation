package compp;

import java.util.List;
import java.util.Objects;

import javax.swing.table.AbstractTableModel;

public class LexicalAnalyseTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -791473873033356628L;

    private List<AnalyzedToken> data;
    private int entriesCount;

    LexicalAnalyseTableModel(List<AnalyzedToken> data) {
        Objects.requireNonNull(data);
        this.data = data;
        entriesCount = data.size();
    }

    @Override
    public int getColumnCount() {
        return 3; // Token, Is-valid and Type columns
    }

    @Override
    public int getRowCount() {
        return entriesCount;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Token";
            case 1:
                return "Is valid";
            case 2:
                return "Type";
            default:
                return "-";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AnalyzedToken result = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return result.getToken();
            case 1:
                return result.isValid();
            case 2:
                return result.getType();
            default:
                return "-";
        }
    }

}
