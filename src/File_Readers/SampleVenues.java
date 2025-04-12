package File_Readers;

import java.util.Arrays;

public class SampleVenues {

    private String venueType;
    private String[][] venueLayout;
    private int VIPRowNum;
    private int SEATRowNum;
    private int leftCol;
    private int middleCol;

    public SampleVenues(String venueType, int[]dimension){
        this.venueType = venueType;
        this.VIPRowNum = dimension[0];
        this.SEATRowNum = dimension[1];
        this.leftCol = dimension[3];
        this.middleCol = dimension[4];
        this.venueLayout = setVenueLayout(dimension);
    }

    public int getLeftCol() {
        return leftCol;
    }

    public int getMiddleCol() {
        return middleCol;
    }

    public String getVenueType(){
        return venueType;
    }

    public String[][] copyVenueLayout() {
        if (venueLayout == null) {
            return null; // Return null if the original layout is null
        }

        String[][] copy = new String[venueLayout.length][];
        for (int i = 0; i < venueLayout.length; i++) {
            copy[i] = Arrays.copyOf(venueLayout[i], venueLayout[i].length);
        }
        return copy;
    }

    public int getVIPRowNum(){
        return VIPRowNum;
    }

    public int getSEATRowNum(){
        return SEATRowNum;
    }

    public String[][] setVenueLayout(int[] dimension){
        int STANDRowNum = dimension[2];
        int rightCol = dimension[5];
        int totalCol = leftCol + middleCol + rightCol + 6;
        int totalRow = VIPRowNum + SEATRowNum + STANDRowNum;
        String[][] layoutDiagram = new String[totalRow][totalCol];
        String prefix = "";
        int currentRowNum = 1;
        for(int r = 0; r<totalRow; r++){
            if(r < VIPRowNum){
                prefix = String.format("V%d", currentRowNum++);
            }
            else if(r < VIPRowNum + SEATRowNum){
                if(r==VIPRowNum){
                    currentRowNum = 1;
                }
                prefix = String.format("S%d", currentRowNum++);
            }
            else{
                if(r==VIPRowNum + SEATRowNum){
                    currentRowNum = 1;
                }
                prefix = String.format("T%d", currentRowNum++);
            }
            layoutDiagram[r] = generateRow(prefix, totalCol, leftCol, middleCol, rightCol);
        }

        return layoutDiagram;
    }

    private static String[] generateRow(String prefix, int totalCol, int leftCol, int middleCol, int rightCol) {
        String[] row = new String[totalCol];
        row[0] = prefix; row[totalCol-1] = prefix;
        int l=0; int m=0; int r=0; int seatNumber = 1;
        for(int i = 1; i<totalCol-1;i++){
            if(i==1 || i== totalCol - 2){
                row[i] = " ";
            }
            else if(i<=leftCol+1){
                row[i] = String.format("[%d]",seatNumber++);
            }
            else if(i==+leftCol+2){
                row[i] = " ";
            }
            else if(i<=leftCol+2+middleCol){
                row[i] = String.format("[%d]", seatNumber++);
            }
            else if(i==leftCol+2+middleCol+1){
                row[i] = " ";
            }
            else{
                row[i] = String.format("[%d]", seatNumber++);
            }
        }

        return row;
    }
}
