package clue;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class SuspectsTable extends AbstractTableModel {

    ClueGame cGame;
    int playerIndex;
    private String[] columnNames = {"", "", ""};
    Object[][] data;

    SuspectsTable(ClueGame cGame, int playerIndex) {
        this.cGame = cGame;
        this.playerIndex = playerIndex;
        data = new Object[6][6];
        int h = 0;

        
      
        
        for (int i = 0; i < cGame.getSuspects().length; i++) {

            data[h][0] = cGame.getSuspects()[i];
            if (cGame.getPlayers().get(playerIndex).getPlayerHand().contains(cGame.getSuspects()[i])) {
                data[h][1] = true;
            } else {
                data[h][1] = false;
            }

            data[h][2] = "                         ";
            h++;
        }
      
        
        
        
        
        
        //data[h][0] = "<<<WEAPONS>>>";
        //h++;
        /*
        for (int j = 0; j < cGame.getWeapons().length; j++) {
            data[h][0] = cGame.getWeapons()[j];
            if (cGame.getPlayers().get(playerIndex).getPlayerHand().contains(cGame.getWeapons()[j])) {
                data[h][1] = true;
            } else {
                data[h][1] = false;
            }

            data[h][2] = "                                                ";
            h++;
        }

        //data[h][0] = "<<<ROOMS>>>";
        //h++;

        for (int k = 0; k < cGame.getRooms().length; k++) {
            data[h][0] = cGame.getRooms()[k];
            if (cGame.getPlayers().get(playerIndex).getPlayerHand().contains(cGame.getRooms()[k])) {
                data[h][1] = true;
            } else {
                data[h][1] = false;
            }

            data[h][2] = "                                                ";
            h++;
        }
        */
    }

    //new String[0];
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    //public Class getColumnClass(int column) {
    //    Object value = this.getValueAt(0, column);
    //    return (value == null ? Object.class : value.getClass());
   // }
     @Override
     public Class getColumnClass(int c) {
         return getValueAt(1, c).getClass();
     }

    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col > 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {

        data[row][col] = value;
        fireTableCellUpdated(row, col);


    }
}
