/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clue;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Robby
 */
public class WeaponsTable extends AbstractTableModel{
    ClueGame cGame;
    int playerIndex;
    private String[] columnNames =  {"", "", ""};
    Object[][] data;
    
    WeaponsTable(ClueGame cGame, int playerIndex){
        this.cGame = cGame;
        this.playerIndex = playerIndex;
        data = new Object[cGame.getWeapons().length][cGame.getWeapons().length];
        
        for(int i=0;i<cGame.getWeapons().length;i++){
            data[i][0] = cGame.getWeapons()[i];        
            if(cGame.getPlayers().get(playerIndex).getPlayerHand().contains(cGame.getWeapons()[i])){
                data[i][1] = true;
            }
            else{
                data[i][1] = false;
            }
            
            data[i][2] = "                         ";
        }
        
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
    @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
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
