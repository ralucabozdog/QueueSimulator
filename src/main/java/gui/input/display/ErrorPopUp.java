package gui.input.display;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

//Clasa pentru construirea casetei de dialog ce avertizeaza user-ul ca a introdus un input invalid

public class ErrorPopUp extends JDialog {

    private JPanel contentPanel;
    private JButton okButton;
    private JLabel lblNewLabel;

    public ErrorPopUp(String message) {

        contentPanel = new JPanel();
        setBounds(100, 100, 500, 280);
        this.setLocationRelativeTo(null);
        contentPanel.setBackground(new Color(186, 200, 222));
        contentPanel.setLayout(null);
        setContentPane(contentPanel);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        okButton = new JButton("Retry");
        okButton.setForeground(new Color(255, 255, 255));
        okButton.setFont(new Font("Arial Nova Light", Font.BOLD, 14));
        okButton.setBackground(new Color(188, 143, 143));
        okButton.setBounds(180, 150, 140, 48);
        contentPanel.add(okButton);

        okButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        lblNewLabel = new JLabel(message);
        lblNewLabel.setForeground(new Color(188, 143, 143));
        lblNewLabel.setFont(new Font("Copper Black", Font.BOLD, 20));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(0, 25, 500, 66);
        contentPanel.add(lblNewLabel);
    }
}
