package tbhizzle.oldschool.script.runecrafter;

import tbhizzle.oldschool.script.runecrafter.RuneCrafter;
import tbhizzle.oldschool.script.runecrafter.data.Altar;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RuneCrafterGui extends JFrame {

    private JPanel contentPane;
    Altar altar;

    /**
     * Create the frame.
     */
    public RuneCrafterGui(final RuneCrafter runeCrafter, File preferences) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 318, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        contentPane.setLayout(gbl_contentPane);

        JTextPane textPane = new JTextPane();
        textPane.setText("This Runecrafting script is still in early developement."
                + " I will slowly be adding more locations and features."
                + " Post requests and suggestions in the thread.");

        GridBagConstraints gbc_textPane = new GridBagConstraints();
        gbc_textPane.gridheight = 3;
        gbc_textPane.gridwidth = 6;
        gbc_textPane.insets = new Insets(0, 0, 5, 5);
        gbc_textPane.fill = GridBagConstraints.BOTH;
        gbc_textPane.gridx = 0;
        gbc_textPane.gridy = 0;
        contentPane.add(textPane, gbc_textPane);
        ButtonGroup altars = new ButtonGroup();
        final JRadioButton rdbtnAir = new JRadioButton("Air");
        altars.add(rdbtnAir);
        GridBagConstraints gbc_rdbtnAir = new GridBagConstraints();
        gbc_rdbtnAir.anchor = GridBagConstraints.WEST;
        gbc_rdbtnAir.gridwidth = 2;
        gbc_rdbtnAir.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnAir.gridx = 0;
        gbc_rdbtnAir.gridy = 3;
        contentPane.add(rdbtnAir, gbc_rdbtnAir);
        rdbtnAir.doClick();

        final JRadioButton rdbtnEarth = new JRadioButton("Earth");
        altars.add(rdbtnEarth);
        GridBagConstraints gbc_rdbtnEarth = new GridBagConstraints();
        gbc_rdbtnEarth.anchor = GridBagConstraints.WEST;
        gbc_rdbtnEarth.gridwidth = 2;
        gbc_rdbtnEarth.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnEarth.gridx = 0;
        gbc_rdbtnEarth.gridy = 4;
        contentPane.add(rdbtnEarth, gbc_rdbtnEarth);

        final JRadioButton rdbtnFire = new JRadioButton("Fire");
        altars.add(rdbtnFire);
        GridBagConstraints gbc_rdbtnFire = new GridBagConstraints();
        gbc_rdbtnFire.anchor = GridBagConstraints.WEST;
        gbc_rdbtnFire.gridwidth = 2;
        gbc_rdbtnFire.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnFire.gridx = 0;
        gbc_rdbtnFire.gridy = 5;
        contentPane.add(rdbtnFire, gbc_rdbtnFire);

        final JRadioButton rdbtnBody = new JRadioButton("Body");
        altars.add(rdbtnBody);
        GridBagConstraints gbc_rdbtnBody = new GridBagConstraints();
        gbc_rdbtnBody.anchor = GridBagConstraints.WEST;
        gbc_rdbtnBody.gridwidth = 2;
        gbc_rdbtnBody.insets = new Insets(0, 0, 5, 5);
        gbc_rdbtnBody.gridx = 0;
        gbc_rdbtnBody.gridy = 6;
        contentPane.add(rdbtnBody, gbc_rdbtnBody);

        JCheckBox chckbxAbyss = new JCheckBox("Does Nothing");
        GridBagConstraints gbc_chckbxAbyss = new GridBagConstraints();
        gbc_chckbxAbyss.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxAbyss.gridx = 3;
        gbc_chckbxAbyss.gridy = 6;
        contentPane.add(chckbxAbyss, gbc_chckbxAbyss);

        JButton btnStart = new JButton("Start");
        GridBagConstraints gbc_btnStart = new GridBagConstraints();
        gbc_btnStart.gridwidth = 3;
        gbc_btnStart.gridx = 3;
        gbc_btnStart.gridy = 8;
        contentPane.add(btnStart, gbc_btnStart);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Altar altar;
                if (rdbtnAir.isSelected()) {
                    altar = Altar.AIR;
                }else if(rdbtnEarth.isSelected()) {
                    altar = Altar.EARTH;
                }else if(rdbtnFire.isSelected()){
                    altar = Altar.FIRE;
                }else if (rdbtnBody.isSelected()) {
                    altar = Altar.BODY;
                }else{
                    return;
                }
                System.out.println(altar.name());
                RuneCrafterGui.this.setAltar(altar);
                RuneCrafterGui.this.setVisible(false);
                RuneCrafterGui.this.dispose();


            }
        });


    }
    public void setAltar(Altar a){
        this.altar = a;
    }
    public Altar getAltar(){
        return this.altar;
    }

}
