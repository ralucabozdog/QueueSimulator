package gui.simulation;

import model.types.Client;
import model.types.ClientsQueue;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

//clasa pentru panoul ce reda simularea in timp real

public class SimulationPanel extends JPanel{
    private final BufferedImage imageQueue = ImageIO.read(new File("src\\main\\resources\\cashier.png"));   //imaginea pentru fiecare coada
    private final BufferedImage imageClient = ImageIO.read(new File("src\\main\\resources\\client.png"));   //imagine pentru fiecare client

    private CopyOnWriteArrayList<Client> waitingClients;        //datele necesare simularii
    private CopyOnWriteArrayList<ClientsQueue> queues;
    private int currentTime;

    public SimulationPanel(CopyOnWriteArrayList waitingClients, CopyOnWriteArrayList queues, AtomicInteger currentTime) throws IOException {

        setBackground(new Color(186, 200, 222));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setForeground(new Color(0, 0, 0));
        this.waitingClients = waitingClients;
        this.queues = queues;
        this.currentTime = currentTime.get();
    }

    //metoda de desenare a obiectelor de pe panou
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setFont(new Font("Copper Black", Font.BOLD, 15));
        g.drawString("Current time: " + this.currentTime, 10, 20);
        g.drawString("Waiting clients:", 10, 50);

        int w = 0;
        while(w < this.waitingClients.size()){          //desenarea clientilor care inca asteapta
            Client c = this.waitingClients.get(w);
            g.drawImage(imageClient, 150 + w * 85, 50, null);
            g.setFont(new Font("Copper Black", Font.PLAIN, 13));
            g.drawString("(" + c.getId() + ", " + c.getArrivalTime() + ", " + c.getServiceTime() + ")", 150 + w * 85, 50);
            w++;
        }
        int i = 0;
        for(ClientsQueue q : this.queues) {          //desenarea inceputului fiecarei cozi
            g.drawImage(imageQueue, 20, 150 + i * 160, null);
            g.setFont(new Font("Copper Black", Font.BOLD, 13));
            g.drawString("Queue " + (i + 1), 10, 170 + i * 160);
            int j = 0;
            for(Client client : q.getClients()){    //desenarea clientilor care asteapta in fiecare coada
                g.drawImage(imageClient, 150 + j * 85, 160 + i * 160, null);
                g.setFont(new Font("Copper Black", Font.PLAIN, 13));
                g.drawString("(" + client.getId() + ", " + client.getArrivalTime() + ", " + client.getServiceTime() + ")", 150 + j * 85, 160 + i * 160);
                j++;
            }
            i++;
        }
    }

    //metoda de redesenare a panoului la fiecare secunda (dupa actualizarea datelor de simulare)
    public void repaintComponent(CopyOnWriteArrayList<Client> waitingClients, CopyOnWriteArrayList<ClientsQueue> queues, AtomicInteger currentTime){
        this.waitingClients = waitingClients;
        this.queues = queues;
        this.currentTime = currentTime.get();
        this.update(this.getGraphics());
    }
}
