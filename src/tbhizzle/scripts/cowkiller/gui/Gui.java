package tbhizzle.scripts.cowkiller.gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import tbhizzle.scripts.cowkiller.CowKiller;
import tbhizzle.scripts.cowkiller.data.*;
import tbhizzle.scripts.cowkiller.tasks.TanHides;

public class Gui extends JFrame {
	CowKiller cowKiller;
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	
	final JCheckBox  chckbxCrafting;
	final JComboBox<Item> comboBoxCrafter;
//	public static void main(String[] args){
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Gui frame = new Gui(new CowKiller());
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	public Gui(CowKiller c){
		cowKiller = c;

		setTitle("Cow Killer");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 253);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		final JCheckBox chckbxTanning = new JCheckBox("Tanning");

		GridBagConstraints gbc_chckbxTanning = new GridBagConstraints();
		gbc_chckbxTanning.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTanning.gridx = 1;
		gbc_chckbxTanning.gridy = 0;
		contentPane.add(chckbxTanning, gbc_chckbxTanning);
		
		chckbxCrafting = new JCheckBox("Crafting");
		GridBagConstraints gbc_chckbxCrafting = new GridBagConstraints();
		gbc_chckbxCrafting.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxCrafting.gridx = 2;
		gbc_chckbxCrafting.gridy = 0;
		contentPane.add(chckbxCrafting, gbc_chckbxCrafting);
		
		JCheckBox chckbxBanking = new JCheckBox("Banking");
		GridBagConstraints gbc_chckbxBanking = new GridBagConstraints();
		gbc_chckbxBanking.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxBanking.gridx = 3;
		gbc_chckbxBanking.gridy = 0;
		contentPane.add(chckbxBanking, gbc_chckbxBanking);
		
		final JRadioButton rdbtnHardLeather = new JRadioButton("Hard Leather");
		GridBagConstraints gbc_rdbtnHardLeather = new GridBagConstraints();
		gbc_rdbtnHardLeather.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnHardLeather.gridx = 1;
		gbc_rdbtnHardLeather.gridy = 1;
		contentPane.add(rdbtnHardLeather, gbc_rdbtnHardLeather);
		
		comboBoxCrafter = new JComboBox<Item>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		contentPane.add(comboBoxCrafter, gbc_comboBox);
		
		final JRadioButton rdbtnLeather = new JRadioButton("Leather");
		GridBagConstraints gbc_rdbtnLeather = new GridBagConstraints();
		gbc_rdbtnLeather.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnLeather.gridx = 1;
		gbc_rdbtnLeather.gridy = 2;
		contentPane.add(rdbtnLeather, gbc_rdbtnLeather);
		
		ButtonGroup leatherGroup = new ButtonGroup();
		leatherGroup.add(rdbtnLeather);
		leatherGroup.add(rdbtnHardLeather);
		
		JTextPane txtpnThis = new JTextPane();
		txtpnThis.setEditable(false);
		txtpnThis.setText("This script defaults to selling cow hides to the general store, Jack Oval. Addition actions can be specified. The actions are Tanning, Crafting and Banking. Tanning and Crafting both serve to modify the cowhide, while Banking banks the finished product.");
		
		GridBagConstraints gbc_txtpnThis = new GridBagConstraints();
		gbc_txtpnThis.insets = new Insets(0, 0, 5, 0);
		gbc_txtpnThis.gridwidth = 3;
		gbc_txtpnThis.fill = GridBagConstraints.BOTH;
		gbc_txtpnThis.gridx = 1;
		gbc_txtpnThis.gridy = 3;
		contentPane.add(txtpnThis, gbc_txtpnThis);
		
		JButton btnStart = new JButton("Start");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.gridx = 3;
		gbc_btnStart.gridy = 4;
		contentPane.add(btnStart, gbc_btnStart);
		
		chckbxTanning.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean selected = ((JCheckBox)e.getSource()).isSelected();
				cowKiller.setTanning(selected);
				if(!selected){
					if(chckbxCrafting.isSelected()){
						chckbxCrafting.doClick();
					}
					chckbxCrafting.setVisible(false);
					

				}else{
					chckbxCrafting.setVisible(true);
					
					chckbxCrafting.doClick(1);
				}
				rdbtnHardLeather.setVisible(selected);
				rdbtnLeather.setVisible(selected);
			}
		});
		
		chckbxCrafting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean selected = ((JCheckBox)e.getSource()).isSelected();
				cowKiller.setCrafting(selected);
				comboBoxCrafter.setVisible(selected);
			}
		});
		chckbxBanking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean selected = ((JCheckBox)e.getSource()).isSelected();
				cowKiller.setBanking(selected);
			}
		});
		
		rdbtnLeather.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cowKiller.setTanningItem(TanHides.LEATHER);
				setCraftingItems(true);
			}
		});
		rdbtnHardLeather.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cowKiller.setTanningItem(TanHides.HARD_LEATHER);
				setCraftingItems(false);
			}
		});
		
		comboBoxCrafter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Item item = (Item)comboBoxCrafter.getSelectedItem();
				cowKiller.setCraftingItem(item);
			}
		});
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cowKiller.exitGui();
			}
		});
		
		rdbtnLeather.doClick();
		chckbxCrafting.doClick();
		chckbxTanning.doClick();
		chckbxBanking.doClick();
		chckbxTanning.doClick();
		chckbxBanking.doClick();
	}
	
	private void setCraftingItems(boolean softLeather){
		comboBoxCrafter.removeAllItems();
		if(softLeather){
			
			for(LeatherItem l: LeatherItem.values()){
				comboBoxCrafter.addItem(l.getItem());
			}
		}else{
			comboBoxCrafter.removeAll();
			for(HardLeatherItem l: HardLeatherItem.values()){
				comboBoxCrafter.addItem(l.getItem());
			}
		}
	}

}
