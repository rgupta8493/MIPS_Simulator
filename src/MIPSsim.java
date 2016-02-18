
/* On my honor , I have neither given nor received unauthorized aid on this assignment  */

                                  /*PROJECT 1 - Embedded Systems --- UFID : 40419914 */
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ronak on 2/15/2016.
 */
public class MIPSsim {
    public static ArrayList INM;
    public static ArrayList INB;
    public static int[] RGF = new int[16];
    public static int[] DAM = new int[16];
    public static ArrayList AIB;
    public static ArrayList ADB;
    public static ArrayList SIB;
    public static ArrayList PRB;
    public static ArrayList<String> REB;
    public static BufferedReader in;
    public static BufferedWriter bw;
    public static String[] read;
    public static String opcode;
    public static String reg;
    public static String operand1;
    public static String operand2;
    public static Map<String, Integer> mreg;

    public static void main(String[] args) throws IOException {
        //Reading Instructions File
        bw = new BufferedWriter(new FileWriter("simulation.txt"));
        mreg = new HashMap<String, Integer>();
        INM = new ArrayList();
        INB = new ArrayList();
        AIB = new ArrayList();
        ADB = new ArrayList();
        SIB = new ArrayList();
        PRB = new ArrayList();
        REB = new ArrayList<String>();
        read = new String[4];

        try {
            String INS;
            in = new BufferedReader(new FileReader("instructions.txt"));
            while ((INS = in.readLine()) != null) {
                INM.add(INS);

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Reading Registers Tokens
        String REG;
        int RegsLoc, Regsvalue;
        in = new BufferedReader(new FileReader("registers.txt"));
        for (int i = 0; i < RGF.length; i++) {
            RGF[i] = Integer.MIN_VALUE;
        }
        while ((REG = in.readLine()) != null) {
            read = REG.replace("<", "").replace(">", "").replace("R", "").split(",");
            RegsLoc = Integer.parseInt(read[0]);
            Regsvalue = Integer.parseInt(read[1]);
            RGF[RegsLoc] = Regsvalue;

        }


        //Reading Datamemory tokens
        String DM;
        int memadd;
        int memvalue;
        in = new BufferedReader(new FileReader("datamemory.txt"));
        for (int i = 0; i < DAM.length; i++) {
            DAM[i] = Integer.MIN_VALUE;
        }
        while ((DM = in.readLine()) != null) {
            read = DM.replace("<", "").replace(">", "").split(",");
            memadd = Integer.parseInt(read[0]);
            memvalue = Integer.parseInt(read[1]);
            DAM[memadd] = memvalue;
        }


        int count = 0;
        while (INM.size() != 0 || INB.size() != 0 || AIB.size() != 0 || SIB.size() != 0 || PRB.size() != 0 || ADB.size() != 0 || REB.size() != 0) {

            bw.write("STEP" + " " + count + ":");
            bw.newLine();
            bw.write("INM:" + INM.toString().replace("[", "").replace("]", "").replace(" ", ""));
            bw.newLine();
            bw.write("INB:" + INB.toString().replace("[", "").replace("]", "").replace(" ", ""));
            bw.newLine();
            bw.write("AIB:" + AIB.toString().replace("[", "").replace("]", "").replace(" ", ""));
            bw.newLine();
            bw.write("SIB:" + SIB.toString().replace("[", "").replace("]", "").replace(" ", ""));
            bw.newLine();
            bw.write("PRB:" + PRB.toString().replace("[", "").replace("]", "").replace(" ", ""));
            bw.newLine();
            bw.write("ADB:" + ADB.toString().replace("[", "").replace("]", "").replace(" ", ""));
            bw.newLine();
            if (count == 16) {
                bw.write("REB:");
            } else {
                bw.write("REB:" + REB.toString().replace("[", "").replace("]", "").replace(" ", ""));
            }


            bw.newLine();

            int check1 = 0;
            for (int i = 0; i < RGF.length; i++) {
                if (RGF[i] != Integer.MIN_VALUE)
                    check1++;
            }
            bw.write("RGF:");
            for (int i = 0; i < RGF.length; i++) {
                if (RGF[i] != Integer.MIN_VALUE) {
                    if (check1 > 1)
                        bw.write("<" + "R" + i + "," + RGF[i] + ">" + ",");
                    else
                        bw.write("<" + "R" + i + "," + RGF[i] + ">");
                    check1--;
                }


            }
            bw.newLine();
            int check = 0;
            for (int i = 0; i < DAM.length; i++) {
                if (DAM[i] != Integer.MIN_VALUE)
                    check++;
            }
            bw.write("DAM:");
            for (int i = 0; i < DAM.length; i++) {
                if (DAM[i] != Integer.MIN_VALUE) {
                    if (check > 1)
                        bw.write("<" + i + "," + DAM[i] + ">" + ",");
                    else
                        bw.write("<" + i + "," + DAM[i] + ">");
                    check--;

                }
            }
            if(count<16) {
                bw.newLine();
                bw.newLine(); //////Adding new line after each step
            }

            String[] readREB;

            if (REB.size() != 0) {

                readREB = REB.get(0).toString().replace("<", "").replace(">", "").replace("R", "").replace("[", "").replace("]", "").split(",");
                REB.remove(0);
                RGF[Integer.parseInt(readREB[0])] = Integer.parseInt(readREB[1]);


            }


            String[] readPRB;
            if (PRB.size() != 0) {
                readPRB = PRB.get(0).toString().replace("<", "").replace(">", "").replace("[", "").replace("]", "").split(",");
                opcode = readPRB[0];
                reg = readPRB[1];
                operand1 = readPRB[2];
                operand2 = readPRB[3];
                int u = Integer.parseInt(operand1);
                int v = Integer.parseInt(operand2);
                REB.add("<" + reg + "," + (u * v) + ">");
                PRB.remove(0);
            }

            String[] readAIB;
            if (AIB.size() != 0) {
                readAIB = AIB.get(0).toString().replace("<", "").replace(">", "").replace("[", "").replace("]", "").split(",");
                AIB.remove(0);
                opcode = readAIB[0];
                reg = readAIB[1];
                operand1 = readAIB[2];
                operand2 = readAIB[3];
                int u = Integer.parseInt(operand1);
                int v = Integer.parseInt(operand2);
                if (opcode.equals("ADD"))
                    REB.add("<" + reg + "," + (u + v) + ">");
                else if (opcode.equals("SUB"))
                    REB.add("<" + reg + "," + (u - v) + ">");
                else
                    PRB.add("<" + opcode + "," + reg + "," + u + "," + v + ">");

            }

            String[] readADB;
            if (ADB.size() != 0) {
                readADB = ADB.get(0).toString().replace("<", "").replace(">", "").replace("R", "").replace("[", "").replace("]", "").split(",");
                DAM[Integer.parseInt(readADB[1])] = RGF[Integer.parseInt(readADB[0])];
                ADB.remove(0);

            }
            String[] readSIB;
            if (SIB.size() != 0) {
                readSIB = SIB.get(0).toString().replace("<", "").replace(">", "").replace("[", "").replace("]", "").split(",");
                opcode = readSIB[0];
                reg = readSIB[1];
                operand1 = readSIB[2];
                operand2 = readSIB[3];
                int u = Integer.parseInt(operand1);
                int v = Integer.parseInt(operand2);
                ADB.add("<" + reg + "," + (u + v) + ">");

                SIB.remove(0);


            }
            String[] readINB;
            if (INB.size() != 0) {
                readINB = INB.get(0).toString().replace("<", "").replace(">", "").replace("[", "").replace("]", "").split(",");
                INB.remove(0);
                opcode = readINB[0];
                reg = readINB[1];
                operand1 = readINB[2];
                operand2 = readINB[3];


                if (opcode.equals("ST")) {
                    SIB.add("<" + opcode + "," + reg + "," + operand1 + "," + operand2 + ">");

                } else {
                    AIB.add("<" + opcode + "," + reg + "," + operand1 + "," + operand2 + ">");
                }


            }
            String[] readINM;
            if (INM.size() != 0) {
                readINM = INM.get(0).toString().replace("[", "").replace("]", "").replace("<", "").replace(">", "").split(",");
                INM.remove(0);
                opcode = readINM[0];
                reg = readINM[1];
                operand1 = readINM[2];
                operand2 = readINM[3];
                operand1 = operand1.replace("R", "");

                if (opcode.equals("ST")) {

                    operand1 = String.valueOf(RGF[Integer.parseInt(operand1)]);


                } else {
                    operand1 = String.valueOf(RGF[Integer.parseInt(operand1)]);
                    operand2 = operand2.replace("R", "");
                    operand2 = String.valueOf(RGF[Integer.parseInt(operand2)]);


                }
                INB.add("<" + opcode + "," + reg + "," + operand1 + "," + operand2 + ">");


            }

            count++;
            if (count == 16) {
                int u = Integer.parseInt(operand1);
                int v = Integer.parseInt(operand2);
                REB.add("<" + reg + "," + (u - v) + ">");


            }


        }


        bw.close();

    }
}