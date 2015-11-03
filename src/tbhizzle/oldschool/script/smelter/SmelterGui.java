package tbhizzle.oldschool.script.smelter;

import tbhizzle.oldschool.script.smelter.data.*;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SmelterGui extends JFrame {

    private JPanel contentPane;
    private final ButtonGroup smeltType = new ButtonGroup();
    private final ButtonGroup bankLocation = new ButtonGroup();
    private File prefrences;
    /**
     * Launch the application.
     */
    /*public static void main(String[] args) {
        Smelter s = Smelter.this;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SmelterGui frame = new SmelterGui(s);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

    /**
     * Create the frame.
     */

    private Smelter smelter;
    final JComboBox selectionList;

    final JRadioButton rdbtnBars;
    final JRadioButton rdbtnJewelry;
    final JRadioButton rdbtnCannonBalls;

    final JRadioButton rdbtnEdgeville;
    final JRadioButton rdbtnAlKharid;
    private JComboBox jewelBox;

    public SmelterGui(Smelter s, File prefrences) {
        super("My Smelter");
        this.prefrences = prefrences;

        smelter = s;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JewelListener jewelListener = new JewelListener();

        JLabel lblMytitle = new JLabel("MyTitle");
        contentPane.add(lblMytitle,BorderLayout.NORTH);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JTextArea txtrThisScriptRuns = new JTextArea();
        txtrThisScriptRuns.setLineWrap(true);
        txtrThisScriptRuns.setText("this script runs in Al Kharid and uses the furnace to smelt things. Select the" +
                " category of Item you would like to make then specify the type");
        txtrThisScriptRuns.setEditable(false);
        GridBagConstraints gbc_txtrThisScriptRuns = new GridBagConstraints();
        gbc_txtrThisScriptRuns.gridwidth = 3;
        gbc_txtrThisScriptRuns.gridheight = 2;
        gbc_txtrThisScriptRuns.insets = new Insets(0, 0, 5, 0);
        gbc_txtrThisScriptRuns.fill = GridBagConstraints.BOTH;
        gbc_txtrThisScriptRuns.gridx = 0;
        gbc_txtrThisScriptRuns.gridy = 0;
        panel.add(txtrThisScriptRuns, gbc_txtrThisScriptRuns);



        selectionList = new JComboBox();
        GridBagConstraints gbc_selectionList = new GridBagConstraints();
        gbc_selectionList.gridheight = 2;
        gbc_selectionList.insets = new Insets(0, 0, 5, 0);
        gbc_selectionList.fill = GridBagConstraints.HORIZONTAL;
        gbc_selectionList.gridx = 2;
        gbc_selectionList.gridy = 2;
        panel.add(selectionList, gbc_selectionList);


        rdbtnBars = new JRadioButton("Bars");
        rdbtnBars.setHorizontalAlignment(SwingConstants.LEFT);
        smeltType.add(rdbtnBars);
        GridBagConstraints gbc_rdbtnBars = new GridBagConstraints();
        gbc_rdbtnBars.anchor = GridBagConstraints.WEST;
        gbc_rdbtnBars.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnBars.gridx = 0;
        gbc_rdbtnBars.gridy = 2;
        panel.add(rdbtnBars, gbc_rdbtnBars);
        rdbtnBars.addActionListener(jewelListener);

        rdbtnJewelry = new JRadioButton("Jewelry");
        rdbtnJewelry.setHorizontalAlignment(SwingConstants.LEFT);
        smeltType.add(rdbtnJewelry);
        GridBagConstraints gbc_rdbtnJewlery = new GridBagConstraints();
        gbc_rdbtnJewlery.anchor = GridBagConstraints.WEST;
        gbc_rdbtnJewlery.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnJewlery.gridx = 0;
        gbc_rdbtnJewlery.gridy = 3;
        panel.add(rdbtnJewelry, gbc_rdbtnJewlery);
        rdbtnJewelry.addActionListener(jewelListener);

        rdbtnCannonBalls = new JRadioButton("Cannon Balls");
        rdbtnCannonBalls.setHorizontalAlignment(SwingConstants.LEFT);
        smeltType.add(rdbtnCannonBalls);
        GridBagConstraints gbc_rdbtnCannonBalls = new GridBagConstraints();
        gbc_rdbtnCannonBalls.anchor = GridBagConstraints.WEST;
        gbc_rdbtnCannonBalls.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnCannonBalls.gridx = 0;
        gbc_rdbtnCannonBalls.gridy = 4;
        panel.add(rdbtnCannonBalls, gbc_rdbtnCannonBalls);
        rdbtnCannonBalls.addActionListener(jewelListener);

        jewelBox = new JComboBox<Jewel>(Jewel.values());
        GridBagConstraints gbc_jewelBox = new GridBagConstraints();
        gbc_jewelBox.gridheight = 2;
        gbc_jewelBox.insets = new Insets(0, 0, 5, 0);
        gbc_jewelBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_jewelBox.gridx = 2;
        gbc_jewelBox.gridy = 4;
        panel.add(jewelBox, gbc_jewelBox);

        JLabel lblBanking = new JLabel("Banking Location:");
        GridBagConstraints gbc_lblBanking = new GridBagConstraints();
        gbc_lblBanking.anchor = GridBagConstraints.WEST;
        gbc_lblBanking.insets = new Insets(0, 0, 5, 5);
        gbc_lblBanking.gridx = 0;
        gbc_lblBanking.gridy = 5;
        panel.add(lblBanking, gbc_lblBanking);

        rdbtnAlKharid = new JRadioButton("Al Kharid");
        GridBagConstraints gbc_rdbtnAlKharid = new GridBagConstraints();
        bankLocation.add(rdbtnAlKharid);
        gbc_rdbtnAlKharid.anchor = GridBagConstraints.WEST;
        gbc_rdbtnAlKharid.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnAlKharid.gridx = 0;
        gbc_rdbtnAlKharid.gridy = 6;
        panel.add(rdbtnAlKharid, gbc_rdbtnAlKharid);

        rdbtnEdgeville = new JRadioButton("Edgeville");
        GridBagConstraints gbc_rdbtnEdgeville = new GridBagConstraints();
        bankLocation.add(rdbtnEdgeville);
        gbc_rdbtnEdgeville.anchor = GridBagConstraints.WEST;
        gbc_rdbtnEdgeville.insets = new Insets(0, 0, 0, 5);
        gbc_rdbtnEdgeville.gridx = 0;
        gbc_rdbtnEdgeville.gridy = 7;
        panel.add(rdbtnEdgeville, gbc_rdbtnEdgeville);

        JButton btnStart = new JButton("Start");
        GridBagConstraints gbc_btnStart = new GridBagConstraints();
        gbc_btnStart.insets = new Insets(0, 0, 5, 0);
        gbc_btnStart.gridx = 2;
        gbc_btnStart.gridy = 6;
        panel.add(btnStart, gbc_btnStart);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Smeltable smeltable = null;
                String type = "";
                if(rdbtnBars.isSelected()) {
                    System.out.println("Bar");
                    smeltable = Bar.values()[selectionList.getSelectedIndex()];
                    type = "Bar";
                }
                if(rdbtnJewelry.isSelected()){
                    System.out.println("Jewelry");
                    {
                        smeltable = Jewelry.values()[selectionList.getSelectedIndex()];
                        type = "Jewelry";
                        if(jewelBox.getSelectedIndex() != 0){
                            ((Jewelry)smeltable).setJewel(Jewel.values()[jewelBox.getSelectedIndex()]);
                            type = "Jewelry"+jewelBox.getSelectedIndex();
                        }

                    }
                }
                if(rdbtnCannonBalls.isSelected()) {
                    System.out.println("Cannonball");
                    smeltable = new Cannonball();
                    type = "Cannonball";
                }
                if(smeltable != null) {
                    System.out.print("Smeltable id: " + smeltable.getPrimaryId());
                    smelter.setSmeltable(smeltable);
                    String location;
                    if (rdbtnEdgeville.isSelected()) {
                        smelter.setLocation(1);
                        location = "Ed";
                    }else{
                        smelter.setLocation(0);
                        location = "Al";
                    }
                    savePrefrences(location, type, selectionList.getSelectedIndex());
                    SmelterGui.this.setVisible(false);
                    SmelterGui.this.dispose();
                }
                else {
                    System.out.println("error selecting item");
                }
            }

        });


        rdbtnBars.doClick();
        rdbtnAlKharid.doClick();
        loadPrefrences();
    }

    private void setComboBox(JRadioButton source){
        selectionList.removeAll();
        selectionList.removeAllItems();
        if (source.getText().equals("Bars")) {
            for (Bar b : Bar.values()) {
                selectionList.addItem(b.name());
            }
        }
        if(source.getText().equals("Jewelry")) {
            for (Jewelry j : Jewelry.values())
                selectionList.addItem(j.name());

        }
        if(source.getText().equals("Cannon Balls")) {
            selectionList.addItem("Cannon Balls");
        }

    }

    public void loadPrefrences(){
        if(prefrences.exists()){
            try{
                BufferedReader reader = new BufferedReader(new FileReader(prefrences));

                String line;
                line = reader.readLine().split(":")[1];
                smelter.log(line);
                setLocation(line);
                line = reader.readLine().split(":")[1];
                smelter.log(line);
                setType(line);
                line = reader.readLine().split(":")[1];
                smelter.log(line);
                setIndex(Integer.parseInt(line));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void savePrefrences(String location, String type, int i){
        try {
            prefrences.delete();
            prefrences.createNewFile();
            smelter.log("Saving");
            PrintWriter writer = new PrintWriter(new FileWriter(prefrences));
            writer.println("Location:"+location);
            smelter.log("Location:" + location);
            writer.println("Type:" + type);
            smelter.log("Location:" + location);
            writer.println("Index:" + i);
            smelter.log("Location:"+location);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setLocation(String location){
        if(location.equals("Al")){
            rdbtnAlKharid.doClick();
        }else if(location.equals("Ed")){
            rdbtnEdgeville.doClick();
        }
    }
    private void setType(String type){
        if(type.startsWith("Bars")){
            rdbtnBars.doClick();
        }else if(type.startsWith("Jewelry")){
            rdbtnJewelry.doClick();
            smelter.log("jewel number: " + type.split("Jewelry")[1]);
            jewelBox.setSelectedIndex(Integer.parseInt(type.split("Jewelry")[1]));
        } else if(type.startsWith("Cannonball"))
            rdbtnCannonBalls.doClick();
    }
    private void setIndex(int index){
        selectionList.setSelectedIndex(index);
    }


    private class JewelListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            jewelBox.setVisible(e.getSource() == rdbtnJewelry);
            setComboBox((JRadioButton) e.getSource());
        }
    }

}
