package tbhizzle.oldschool.script.smelter;

import tbhizzle.oldschool.script.smelter.data.Bar;
import tbhizzle.oldschool.script.smelter.data.Cannonball;
import tbhizzle.oldschool.script.smelter.data.Jewelry;
import tbhizzle.oldschool.script.smelter.data.Smeltable;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmelterGui extends JFrame {

    private JPanel contentPane;
    private final ButtonGroup smeltType = new ButtonGroup();

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

    public SmelterGui(Smelter s) {

        super("My Smelter");
        smelter = s;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        MyListener myListener = new MyListener();

        JLabel lblMytitle = new JLabel("MyTitle");
        contentPane.add(lblMytitle,BorderLayout.NORTH);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
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
        gbc_txtrThisScriptRuns.insets = new Insets(0, 0, 5, 5);
        gbc_txtrThisScriptRuns.fill = GridBagConstraints.BOTH;
        gbc_txtrThisScriptRuns.gridx = 0;
        gbc_txtrThisScriptRuns.gridy = 0;
        panel.add(txtrThisScriptRuns, gbc_txtrThisScriptRuns);



        selectionList = new JComboBox();
        GridBagConstraints gbc_selectionList = new GridBagConstraints();
        gbc_selectionList.gridheight = 3;
        gbc_selectionList.insets = new Insets(0, 0, 5, 0);
        gbc_selectionList.fill = GridBagConstraints.HORIZONTAL;
        gbc_selectionList.gridx = 2;
        gbc_selectionList.gridy = 2;
        panel.add(selectionList, gbc_selectionList);

        final JRadioButton rdbtnBars = new JRadioButton("Bars");
        rdbtnBars.setHorizontalAlignment(SwingConstants.LEFT);
        smeltType.add(rdbtnBars);
        GridBagConstraints gbc_rdbtnBars = new GridBagConstraints();
        gbc_rdbtnBars.anchor = GridBagConstraints.WEST;
        gbc_rdbtnBars.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnBars.gridx = 0;
        gbc_rdbtnBars.gridy = 2;
        panel.add(rdbtnBars, gbc_rdbtnBars);
        rdbtnBars.addActionListener(myListener);

        final JRadioButton rdbtnJewelry = new JRadioButton("Jewelry");
        rdbtnJewelry.setHorizontalAlignment(SwingConstants.LEFT);
        smeltType.add(rdbtnJewelry);
        GridBagConstraints gbc_rdbtnJewlery = new GridBagConstraints();
        gbc_rdbtnJewlery.anchor = GridBagConstraints.WEST;
        gbc_rdbtnJewlery.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnJewlery.gridx = 0;
        gbc_rdbtnJewlery.gridy = 3;
        panel.add(rdbtnJewelry, gbc_rdbtnJewlery);
        rdbtnJewelry.addActionListener(myListener);

        final JRadioButton rdbtnCannonBalls = new JRadioButton("Cannon Balls");
        rdbtnCannonBalls.setHorizontalAlignment(SwingConstants.LEFT);
        smeltType.add(rdbtnCannonBalls);
        GridBagConstraints gbc_rdbtnCannonBalls = new GridBagConstraints();
        gbc_rdbtnCannonBalls.anchor = GridBagConstraints.WEST;
        gbc_rdbtnCannonBalls.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnCannonBalls.gridx = 0;
        gbc_rdbtnCannonBalls.gridy = 4;
        panel.add(rdbtnCannonBalls, gbc_rdbtnCannonBalls);
        rdbtnCannonBalls.addActionListener(myListener);

        JButton btnStart = new JButton("Start");
        GridBagConstraints gbc_btnStart = new GridBagConstraints();
        gbc_btnStart.gridx = 2;
        gbc_btnStart.gridy = 6;
        panel.add(btnStart, gbc_btnStart);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Smeltable smeltable = null;
                if(rdbtnBars.isSelected()) {
                    System.out.println("Bar");
                    smeltable = Bar.values()[selectionList.getSelectedIndex()];
                }
                if(rdbtnJewelry.isSelected()){
                    System.out.println("Jewelry");
                    smeltable = Jewelry.values()[selectionList.getSelectedIndex()];
                }
                if(rdbtnCannonBalls.isSelected()) {
                    System.out.println("Cannonball");
                    smeltable = new Cannonball();
                }
                if(smeltable != null) {
                    System.out.print("Smeltable id: " + smeltable.getPrimaryId());
                    smelter.setSmeltable(smeltable);

                    SmelterGui.this.setVisible(false);
                    SmelterGui.this.dispose();
                }
                else {
                    System.out.println("error selecting item");
                }
            }

        });



    }

    private class MyListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() instanceof JRadioButton){
                System.out.println("This is a radio button");
            }
            setComboBox((JRadioButton) e.getSource());
        }
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



}
