import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class WorkshopTableModel extends AbstractTableModel {
    static final int WORKSHOP_NAME_COL = 0;
    static final int WORKSHOPID_COL = 1;
    static final int DAYID_COL = 2;
    static final int NUMBER_OF_SEATS_COL = 3;

    String[] columnNames = {"WorkshopName", "WorkshopID", "DayID", "NumberOfSeats"};

    List<Workshop> workshopList = new ArrayList<>();

    public WorkshopTableModel(List<Workshop> workshopList){
        this.workshopList = workshopList;
    }

    @Override
    public int getColumnCount(){
        return this.columnNames.length;
    }

    @Override
    public int getRowCount(){
        return this.workshopList.size();
    }

    @Override
    public String getColumnName(int col){
        return this.columnNames[col];
    }

    @Override
    public String getValueAt(int row, int col){
        Workshop workshopTmp = this.workshopList.get(row);

        switch(col){
            case WORKSHOPID_COL:
                return workshopTmp.WorkshopID;
            case DAYID_COL:
                return workshopTmp.DayID;
            case NUMBER_OF_SEATS_COL:
                return workshopTmp.NumberOfSeats;
            case WORKSHOP_NAME_COL:
                return workshopTmp.WorkshopName;
            default:
                return workshopTmp.WorkshopName;
        }
    }

    @Override
    public Class getColumnClass(int col){
        return getValueAt(0, col).getClass();
    }

}
