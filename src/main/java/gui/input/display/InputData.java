package gui.input.display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

//Clasa pentru construirea interfetei grafice de introducere a datelor de intrare

public class InputData extends JFrame {
    private JPanel panel;
    private JLabel inputData;
    private JLabel labelClients;
    private JTextField clients;
    private JLabel labelQueues;
    private JTextField queues;
    private JLabel labelInterval;
    private JTextField interval;
    private JLabel labelMinArrival;
    private JTextField minArrival;
    private JLabel labelMaxArrival;
    private JTextField maxArrival;
    private JLabel labelMinService;
    private JTextField minService;
    private JLabel labelMaxService;
    private JTextField maxService;
    private JLabel labelSelectionPolicy;
    private JComboBox selectionPolicy;
    private JLabel labelResultFile;
    private JTextField resultFile;
    private JButton btnOk;


    public InputData() {
        setTitle("Queue simulator");
        setBounds(100, 100, 500, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setBackground(new Color(186, 200, 222));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        inputData = new JLabel("Queue simulator - input data");
        inputData.setHorizontalAlignment(SwingConstants.CENTER);
        inputData.setForeground(new Color(188, 143, 143));
        inputData.setFont(new Font("Copper Black", Font.BOLD, 30));
        inputData.setBounds(0, 30, 480, 52);
        panel.add(inputData);

        labelClients = new JLabel("Number of clients: ");
        labelClients.setHorizontalAlignment(SwingConstants.LEFT);
        labelClients.setForeground(new Color(188, 143, 143));
        labelClients.setFont(new Font("Copper Black", Font.BOLD, 20));
        labelClients.setBounds(67, 120, 250, 30);
        panel.add(labelClients);

        clients = new JTextField();
        clients.setForeground(new Color(188, 143, 143));
        clients.setFont(new Font("Copper Black", Font.BOLD, 20));
        clients.setColumns(10);
        clients.setBounds(317, 120, 100, 30);
        panel.add(clients);

        labelQueues = new JLabel("Number of queues: ");
        labelQueues.setHorizontalAlignment(SwingConstants.LEFT);
        labelQueues.setForeground(new Color(188, 143, 143));
        labelQueues.setFont(new Font("Copper Black", Font.BOLD, 20));
        labelQueues.setBounds(67, 170, 250, 30);
        panel.add(labelQueues);

        queues = new JTextField();
        queues.setForeground(new Color(188, 143, 143));
        queues.setFont(new Font("Copper Black", Font.BOLD, 20));
        queues.setColumns(10);
        queues.setBounds(317, 170, 100, 30);
        panel.add(queues);

        labelInterval = new JLabel("Simulation interval: ");
        labelInterval.setHorizontalAlignment(SwingConstants.LEFT);
        labelInterval.setForeground(new Color(188, 143, 143));
        labelInterval.setFont(new Font("Copper Black", Font.BOLD, 20));
        labelInterval.setBounds(67, 220, 250, 30);
        panel.add(labelInterval);

        interval = new JTextField();
        interval.setForeground(new Color(188, 143, 143));
        interval.setFont(new Font("Copper Black", Font.BOLD, 20));
        interval.setColumns(10);
        interval.setBounds(317, 220, 100, 30);
        panel.add(interval);

        labelMinArrival = new JLabel("Minimum arrival time: ");
        labelMinArrival.setHorizontalAlignment(SwingConstants.LEFT);
        labelMinArrival.setForeground(new Color(188, 143, 143));
        labelMinArrival.setFont(new Font("Copper Black", Font.BOLD, 20));
        labelMinArrival.setBounds(67, 270, 250, 30);
        panel.add(labelMinArrival);

        minArrival = new JTextField();
        minArrival.setForeground(new Color(188, 143, 143));
        minArrival.setFont(new Font("Copper Black", Font.BOLD, 20));
        minArrival.setColumns(10);
        minArrival.setBounds(317, 270, 100, 30);
        panel.add(minArrival);

        labelMaxArrival = new JLabel("Maximum arrival time: ");
        labelMaxArrival.setHorizontalAlignment(SwingConstants.LEFT);
        labelMaxArrival.setForeground(new Color(188, 143, 143));
        labelMaxArrival.setFont(new Font("Copper Black", Font.BOLD, 20));
        labelMaxArrival.setBounds(67, 320, 250, 30);
        panel.add(labelMaxArrival);

        maxArrival = new JTextField();
        maxArrival.setForeground(new Color(188, 143, 143));
        maxArrival.setFont(new Font("Copper Black", Font.BOLD, 20));
        maxArrival.setColumns(10);
        maxArrival.setBounds(317, 320, 100, 30);
        panel.add(maxArrival);

        labelMinService = new JLabel("Minimum service time: ");
        labelMinService.setHorizontalAlignment(SwingConstants.LEFT);
        labelMinService.setForeground(new Color(188, 143, 143));
        labelMinService.setFont(new Font("Copper Black", Font.BOLD, 20));
        labelMinService.setBounds(67, 370, 250, 30);
        panel.add(labelMinService);

        minService = new JTextField();
        minService.setForeground(new Color(188, 143, 143));
        minService.setFont(new Font("Copper Black", Font.BOLD, 20));
        minService.setColumns(10);
        minService.setBounds(317, 370, 100, 30);
        panel.add(minService);

        labelMaxService = new JLabel("Maximum service time: ");
        labelMaxService.setHorizontalAlignment(SwingConstants.LEFT);
        labelMaxService.setForeground(new Color(188, 143, 143));
        labelMaxService.setFont(new Font("Copper Black", Font.BOLD, 20));
        labelMaxService.setBounds(67, 420, 250, 30);
        panel.add(labelMaxService);

        maxService = new JTextField();
        maxService.setForeground(new Color(188, 143, 143));
        maxService.setFont(new Font("Copper Black", Font.BOLD, 20));
        maxService.setColumns(10);
        maxService.setBounds(317, 420, 100, 30);
        panel.add(maxService);

        labelSelectionPolicy = new JLabel("Selection criterion: ");
        labelSelectionPolicy.setHorizontalAlignment(SwingConstants.LEFT);
        labelSelectionPolicy.setForeground(new Color(188, 143, 143));
        labelSelectionPolicy.setFont(new Font("Copper Black", Font.BOLD, 20));
        labelSelectionPolicy.setBounds(67, 470, 250, 30);
        panel.add(labelSelectionPolicy);

        String[] policies = new String[2];
        policies[0] = "queue";
        policies[1] = "time";

        selectionPolicy = new JComboBox(policies);
        selectionPolicy.setForeground(new Color(188, 143, 143));
        selectionPolicy.setFont(new Font("Copper Black", Font.BOLD, 20));
        selectionPolicy.setBounds(317, 470, 100, 30);
        panel.add(selectionPolicy);

        labelResultFile = new JLabel("Result file name: ");
        labelResultFile.setHorizontalAlignment(SwingConstants.LEFT);
        labelResultFile.setForeground(new Color(188, 143, 143));
        labelResultFile.setFont(new Font("Copper Black", Font.BOLD, 20));
        labelResultFile.setBounds(67, 520, 250, 30);
        panel.add(labelResultFile);

        resultFile = new JTextField();
        resultFile.setForeground(new Color(188, 143, 143));
        resultFile.setFont(new Font("Copper Black", Font.BOLD, 20));
        resultFile.setColumns(10);
        resultFile.setBounds(317, 520, 100, 30);
        panel.add(resultFile);


        btnOk = new JButton("OK");
        btnOk.setForeground(new Color(255, 255, 255));
        btnOk.setFont(new Font("Copper Black", Font.BOLD, 25));
        btnOk.setBackground(new Color(188, 143, 143));
        btnOk.setBounds(170, 600, 150, 50);
        panel.add(btnOk);
    }

    //metode getter pentru extragerea informatiilor din campurile completate de utilizator
    public JTextField getClients() {
        return clients;
    }

    public JTextField getQueues() {
        return queues;
    }

    public JTextField getInterval() {
        return interval;
    }

    public JTextField getMinArrival() {
        return minArrival;
    }

    public JTextField getMaxArrival() {
        return maxArrival;
    }

    public JTextField getMinService() {
        return minService;
    }

    public JTextField getMaxService() {
        return maxService;
    }

    public JTextField getResultFile() {
        return resultFile;
    }

    public JComboBox getSelectionPolicy() {
        return selectionPolicy;
    }

    //metoda pentru definirea actiunilor ce trebuie sa se execute la apasarea butonului OK
    public void addOkListener(ActionListener ok){
        this.btnOk.addActionListener(ok);
    }

}
