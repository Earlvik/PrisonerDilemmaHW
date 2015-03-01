package ru.hhschool.prisonerdilemma.ui;

import ru.hhschool.prisonerdilemma.model.Game;
import ru.hhschool.prisonerdilemma.model.Player;
import ru.hhschool.prisonerdilemma.model.Strategy;
import ru.hhschool.prisonerdilemma.model.strategies.EvilStrategy;
import ru.hhschool.prisonerdilemma.model.strategies.KindStrategy;
import ru.hhschool.prisonerdilemma.model.strategies.RandomStrategy;
import ru.hhschool.prisonerdilemma.model.strategies.TitForTatStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Earlviktor on 04.02.2015.
 */
public class PrisonerDilemmaFrame extends JFrame {


    private JPanel panel;
    private JLabel bothCoopLabel;
    private JTextField bothCoopTextField;
    private JLabel bothDefectLabel;
    private JTextField bothDefectTextField;
    private JLabel oneDefectLabel;
    private JTextField oneDefectTextField;
    private JLabel bothCoopPersonalLabel;
    private JLabel bothDefectPersonalLabel;
    private JLabel youDefectLabel;
    private JLabel theyDefectLabel;
    private JTextField bothCoopPersonalTextField;
    private JTextField bothDefectPersonalTextField;
    private JTextField youDefectTextField;
    private JTextField theyDefectTextField;
    private JList playerList;
    private JComboBox playerComboBox;
    private JButton addPlayerButton;
    private JTextArea resultTextArea;
    private JButton startButton;
    private JLabel numberLabel;
    private JTextField numTextField;
    private GraphPanel graphPanel;
    private final Game game;


    @SuppressWarnings("unchecked")
    public PrisonerDilemmaFrame() {
        this.setTitle("Prisoner dilemma");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(panel);
        panel.setSize(200, 300);
        panel.setVisible(true);

        game = new Game();

        final DefaultListModel<Player> model = new DefaultListModel<Player>();

        playerComboBox.setModel(new DefaultComboBoxModel(PlayerType.values()));

        bothCoopTextField.setText(game.getBOTH_COOP() + "");
        bothDefectTextField.setText(game.getBOTH_DEFECT() + "");
        bothCoopPersonalTextField.setText(game.getBOTH_COOP_PERSONAL() + "");
        bothDefectPersonalTextField.setText(game.getBOTH_DEFECT_PERSONAL() + "");
        oneDefectTextField.setText(game.getONE_DEFECT() + "");
        youDefectTextField.setText(game.getYOU_DEFECT() + "");
        theyDefectTextField.setText(game.getTHEY_DEFECT() + "");

        playerList.setModel(model);


        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerType playerType = (PlayerType) playerComboBox.getSelectedItem();
                Player player;
                Strategy strategy;
                switch (playerType) {
                    case Evil: {
                        strategy = new EvilStrategy();
                        break;
                    }
                    case Kind: {
                        strategy = new KindStrategy();
                        break;
                    }
                    case Random: {
                        strategy = new RandomStrategy();
                        break;
                    }
                    case TitForTat30: {
                        strategy = new TitForTatStrategy(0.3);
                        break;
                    }
                    case TitForTat50: {
                        strategy = new TitForTatStrategy(0.5);
                        break;
                    }
                    case TitForTat80: {
                        strategy = new TitForTatStrategy(0.8);
                        break;
                    }
                    default: {
                        strategy = new RandomStrategy();
                    }
                }
                player = new Player(strategy);
                game.addPlayer(player);
                model.addElement(player);

            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    game.reset();
                    game.setGameRules(Integer.parseInt(bothCoopTextField.getText()),
                            Integer.parseInt(bothDefectTextField.getText()),
                            Integer.parseInt(oneDefectTextField.getText()),
                            Integer.parseInt(bothCoopPersonalTextField.getText()),
                            Integer.parseInt(bothDefectPersonalTextField.getText()),
                            Integer.parseInt(youDefectTextField.getText()),
                            Integer.parseInt(theyDefectTextField.getText()));
                    int gamesNum = Integer.parseInt(numTextField.getText());
                    if(gamesNum <= 0 ) return;
                    graphPanel.points = new int[gamesNum][];
                    for (int i = 0; i < gamesNum; i++) {
                        graphPanel.points[i] = game.playEveryone();
                    }
                    resultTextArea.setText(game.results());
                    model.removeAllElements();
                    graphPanel.reformatPoints();
                    graphPanel.repaint();
                    for (Player player : game.getPlayers()) {
                        model.addElement(player);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PrisonerDilemmaFrame.this, "Some of input numbers are incorrect!");
                }
            }
        });

        playerList.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    Player player = (Player) playerList.getSelectedValue();
                    if (player != null) {
                        model.removeElement(player);
                        game.removePlayer(player);
                    }
                }
            }
        });


    }

    private void createUIComponents() {
        graphPanel = new GraphPanel();
    }

    enum PlayerType {Evil, Kind, Random, TitForTat30, TitForTat50, TitForTat80}

    public class GraphPanel extends JPanel {
        public static final int bigMargin = 10;
        public static final int smallMargin = 5;
        public int[][] points;
        List<Color> colors = new ArrayList<>();
        Random rnd = new Random();
        private int width;
        private int height;

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            width = g.getClipBounds().width;
            height = g.getClipBounds().height;
            paintAxis(g);
            if(points!= null) drawLines(g);


        }

        private void paintAxis(Graphics g){
            g.setColor(Color.BLACK);
            g.drawLine(bigMargin, height - smallMargin, bigMargin, smallMargin); //Vertical axis
            g.drawLine(bigMargin, smallMargin, bigMargin - 3, bigMargin + 5);
            g.drawLine(bigMargin, smallMargin, bigMargin + 3, bigMargin + 5);
            g.drawLine(smallMargin, height - bigMargin, width - smallMargin, height - bigMargin); //Horizontal axis
            g.drawLine(width - smallMargin, height - bigMargin, width - bigMargin, height - (bigMargin + 3));
            g.drawLine(width - smallMargin, height - bigMargin, width - bigMargin, height - (bigMargin - 3));

        }

        private void drawLines(Graphics g){
            if(points.length <= 1 ) return;
            int maxY = 0;
            for (int[] point : points) {
                for (int j = 0; j < points[0].length; j++) {
                    if (point[j] > maxY) {
                        maxY = point[j];
                    }
                }
            }
            for(int i = 0; i < points.length; i++){
                int[] playerPoints = points[i];
                if(colors.size() <= i){
                    colors.add(new Color(rnd.nextFloat()*0.7F, rnd.nextFloat()*0.7F, rnd.nextFloat()*0.7F));
                }
                g.setColor(colors.get(i));
                int xLegend = 20;
                int yLegend = 15 + i*15;
                g.drawLine(xLegend, yLegend, xLegend + 5, yLegend);
                String name = (i == 0)?"Game score":game.getPlayers()[i - 1].shortInfo();
                g.drawString(name, xLegend + 7, yLegend + 3);
                for(int j = 1; j < playerPoints.length; j++){
                    float x1 = bigMargin + (float)(width - smallMargin - bigMargin)/(playerPoints.length + 1) * j;
                    float x2 = bigMargin + (float)(width - smallMargin - bigMargin)/(playerPoints.length + 1) * (j + 1);
                    float y1 = (height - bigMargin) - (float)(height - smallMargin - bigMargin)/(maxY) * (playerPoints[j - 1]);
                    float y2 = (height - bigMargin) - (float)(height - smallMargin - bigMargin)/(maxY) * (playerPoints[j]);
                    g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
                }
            }
        }

        public void reformatPoints(){
            int[][] temp = new int[points[0].length][];
            for(int  i = 0; i < points[0].length; i++){
                temp[i] = new int[points.length];
            }
            for( int i = 0; i< points.length; i++){
                for(int j = 0; j < temp.length; j++ ){
                    temp[j][i] = points[i][j];
                }
            }
            points = temp;
        }
    }
}
