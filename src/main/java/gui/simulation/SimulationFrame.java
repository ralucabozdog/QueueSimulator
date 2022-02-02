package gui.simulation;

import implementation.simulation.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

//clasa pentru crearea ferestrei de simulare in timp real

public class SimulationFrame extends JFrame {
    private JPanel panel;
    private SimulationPanel simulationPanel;
    private JScrollPane scrollPane;

    public SimulationFrame(SimulationManager simulationManager){
        try {
            this.simulationPanel = new SimulationPanel(simulationManager.getClientList(), new CopyOnWriteArrayList(simulationManager.getScheduler().getQueues()), simulationManager.getCurrentTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setTitle("Simulation");

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.simulationPanel.setBounds(0, 0, 3000, 4000);
        this.simulationPanel.setPreferredSize(new Dimension(3000, 4000));

        scrollPane = new JScrollPane(simulationPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, (int) dim.getWidth(), (int) dim.getHeight() - 60);

        panel = new JPanel(null);
        panel.setPreferredSize(new Dimension((int) dim.getWidth(), (int) dim.getHeight() - 60));
        panel.add(scrollPane);

        this.setBounds(100, 100, (int) dim.getWidth(), (int) dim.getHeight());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setFocusable(true);
        this.setContentPane(panel);
    }

    public SimulationPanel getSimulationPanel() {
        return simulationPanel;
    }
}
