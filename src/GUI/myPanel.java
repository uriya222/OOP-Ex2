package GUI;

import algorithm.algoManager;
import api.*;
import object.AgentsInterface;
import object.PokemonInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Random;

public class myPanel extends JPanel implements MouseListener, ActionListener{
    private directed_weighted_graph mainGraph = new DWGraph_DS();
    private geo_location mouseClick = new GeoLocation(0, 0, 0);
    private double[] min_max;
    private int screenSize = 900;
    MainManager main;
    private int menu = 2;
    private int IntPlay = 27, direction = 1;//for font extra :)
    private boolean first = true;
    private int screenOffsetX;
    private int screenOffsetY;
    private boolean moreData = true;
    private BufferedImage[][] agentMove = new BufferedImage[5][4];
    protected int selectedLevel = 1;

    /**
     * constructor, takes a pointer to MainManager
     *
     * @param main - pointer
     */
    public myPanel(MainManager main){
        this.main = main;
        this.setBackground(Color.white);
        this.addMouseListener(this);
        if (main.isSet) { //if the game didn't started
            menu = 1;
            mainGraph = main.getGraph();
            minMax(mainGraph.getV());
        }
        for (int i = 0; i < 5; i++) { //load the agents pic for faster painting
            for (int j = 0; j < 4; j++) {
                agentMove[i][j] = getImage("agent/" + i + "_" + j + ".png");
            }
        }
    }

    /**
     * refresh the page
     * and choose the screen
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (menu == 1) paintArina(g);
        else if (menu == 2) mainMenu(g);
    }

    /**
     * the main menu page
     *
     * @param g
     */
    protected void mainMenu(Graphics g){
        screenSize = this.getHeight() < this.getWidth() ? this.getHeight() - 50 : this.getWidth() - 60;
/*        int counter = (int)((System.currentTimeMillis()/1000)%10)/5;
        if (direction>0){
            IntPlay+=counter;
            if (IntPlay>30)direction=-1;
        }else {
            IntPlay-=counter;
            if (IntPlay<12)direction=+1;
        }*/

        g.setColor(Color.gray); //sets the title
        g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 30));
        char[] c = ("Pokemon Game").toCharArray();
        int x = this.getWidth() / 2;
        int y = this.getHeight() / 4;
        g.drawChars(c, 0, c.length, x - (int) (IntPlay * 2) - 90, y);
        if (first) { //only ones draw the buttons
            //slider
            JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 23, 1);
            slider.setMajorTickSpacing(10);
            slider.setPaintTicks(true);
            //create the label table
            Hashtable labelTable = new Hashtable();
            for (int i = 0; i < 23; i++) {
                labelTable.put(i + 1, new JLabel("" + (i + 1)));
            }
            slider.setLabelTable(labelTable);
            //set the pos
            Dimension sizeSlider = slider.getPreferredSize();
            slider.setBounds(x -260, y * 7 / 4, sizeSlider.width +260, sizeSlider.height + 20);
            slider.setPaintLabels(true);
            this.add(slider);
            slider.addChangeListener(new ChangeListener(){
                public void stateChanged(ChangeEvent event){
                    int value = slider.getValue();
                    selectedLevel = value;
                }
            });
            //set the button
            this.setLayout(null);
            JButton button1 = new JButton("start game");
            Dimension size = button1.getPreferredSize();
            button1.setBounds(x - size.width, y * 3, size.width, size.height);
            button1.addActionListener(this);
            this.add(button1);
            first = false;
        }
    }

    /**
     * this is the arina page
     *
     * @param g
     */
    private void paintArina(Graphics g){
        screenSize = this.getHeight() < this.getWidth() ? this.getHeight() - 50 : this.getWidth() - 60;
        screenOffsetX = screenSize < this.getWidth() ? (this.getWidth() - screenSize) / 2 : 0;
        screenOffsetY = screenSize < this.getHeight() ? (this.getHeight() - screenSize) / 2 : 0;
        g.setColor(Color.gray); //edges
        for (node_data n : mainGraph.getV()
        ) {
            for (edge_data e : mainGraph.getE(n.getKey())
            ) {
                int xSrs = (int) ((mainGraph.getNode(e.getSrc()).getLocation().x() - min_max[0]) * screenSize / (min_max[1] - min_max[0])) + 5 + screenOffsetX;
                int ySrs = (int) ((mainGraph.getNode(e.getSrc()).getLocation().y() - min_max[2]) * screenSize / (min_max[3] - min_max[2])) + 5 + screenOffsetY;
                int xDest = (int) ((mainGraph.getNode(e.getDest()).getLocation().x() - min_max[0]) * screenSize / (min_max[1] - min_max[0])) + 5 + screenOffsetX;
                int yDest = (int) ((mainGraph.getNode(e.getDest()).getLocation().y() - min_max[2]) * screenSize / (min_max[3] - min_max[2])) + 5 + screenOffsetY;
                g.drawLine(xSrs, ySrs, xDest, yDest);
                int angle = (int) vectorDirection(mainGraph.getNode(e.getSrc()).getLocation(), mainGraph.getNode(e.getDest()).getLocation());
                if (!moreData)
                    g.drawString(angle + "º", (xSrs + xDest) / 2 + 4, (ySrs + yDest) / 2 + ((angle < 180) ? 10 : 0) % 20);
            }
        }

        g.setColor(Color.black);//nodes
        for (node_data n : mainGraph.getV()
        ) {
            int x = (int) ((n.getLocation().x() - min_max[0]) * screenSize / (min_max[1] - min_max[0])) + screenOffsetX;
            int y = (int) ((n.getLocation().y() - min_max[2]) * screenSize / (min_max[3] - min_max[2])) + screenOffsetY;
            g.fillOval(x, y, 10, 10);
            //g.drawImage(getImage("home.png"),x-15,y-10,30,30,null);
            char[] c = ("<" + n.getKey()).toCharArray();
            g.drawChars(c, 0, c.length, x + 11, y + 11);
        }

        g.setColor(Color.red);//PoKeballs
        for (PokemonInterface p : main.getPokemonList()
        ) {
            int x = (int) ((p.getPos().x() - min_max[0]) * screenSize / (min_max[1] - min_max[0])) + screenOffsetX;
            int y = (int) ((p.getPos().y() - min_max[2]) * screenSize / (min_max[3] - min_max[2])) + screenOffsetY;
            //g.fillOval(x,y,10,10);
            g.drawImage(getImage("pok.png"), x - 10, y - 10, 30, 30, null);
            if (!moreData) g.drawString("T:" + p.getType(), x + 20, y + 3);
            if (!moreData) g.drawString("V:" + p.getValue(), x + 20, y + 13);
        }

        g.setColor(Color.blue);//agents
        for (AgentsInterface a : main.getAgentList().values()
        ) {
            int x = (int) ((a.getPos().x() - min_max[0]) * screenSize / (min_max[1] - min_max[0])) + screenOffsetX;
            int y = (int) ((a.getPos().y() - min_max[2]) * screenSize / (min_max[3] - min_max[2])) + screenOffsetY;
            //g.fillOval(x+2,y+2,6,6);
            char[] c = ("^" + a.getId()).toCharArray();
            g.drawChars(c, 0, c.length, x, y + 40);
            int agentSize = 40;             //agent painting
            y -= 10;
            x -= 10;
            int rotate = 0;
            if (a.getDest() != -1) {
                double tx = ((mainGraph.getNode(a.getSrc()).getLocation().x() - min_max[0]) * screenSize / (min_max[1] - min_max[0])) + screenOffsetX;
                double ty = ((mainGraph.getNode(a.getSrc()).getLocation().y() - min_max[2]) * screenSize / (min_max[3] - min_max[2])) + screenOffsetY;
                geo_location src = new GeoLocation(tx, ty, 0);
                tx = ((mainGraph.getNode(a.getDest()).getLocation().x() - min_max[0]) * screenSize / (min_max[1] - min_max[0])) + screenOffsetX;
                ty = ((mainGraph.getNode(a.getDest()).getLocation().y() - min_max[2]) * screenSize / (min_max[3] - min_max[2])) + screenOffsetY;
                geo_location des = new GeoLocation(tx, ty, 0);
                int angle = (int) vectorDirection(src, des);
                if (315 + rotate < angle || angle <= 45 + rotate) {
                    if (!moreData) g.drawString(angle + rotate + "º", x + 35, y + 20);
                    agentMove(g, 1, x, y, agentSize, agentSize);
                } else if (136 + rotate >= angle && angle > 45 - rotate) {
                    if (!moreData) g.drawString(angle + "º", x + 35, y + 20);
                    agentMove(g, 2, x, y, agentSize, agentSize);
                } else if (226 + rotate >= angle && angle > 135 - rotate) {
                    if (!moreData) g.drawString(angle + "º", x + 35, y + 20);
                    agentMove(g, 3, x, y, agentSize, agentSize);
                } else if (316 + rotate >= angle && angle > 225 - rotate) {
                    if (!moreData) g.drawString(angle + "º", x + 35, y + 20);
                    agentMove(g, 4, x, y, agentSize, agentSize);
                }
            } else {
                if (!moreData) g.drawString("-1º", x + 35, y + 20);
                agentMove(g, 0, x, y, agentSize, agentSize);//standing
            }
        }
        scoreBoard(g);
        moreData(g);
    }

    /**
     * makes the animation and put the pic in place
     *
     * @param g
     * @param direction
     * @param x         pos
     * @param y         pos
     * @param w         width
     * @param h         height
     */
    private void agentMove(Graphics g, int direction, int x, int y, int w, int h){
        int counter = (int) ((System.currentTimeMillis() / 100) % 10 + 1) / 3;
        g.drawImage(agentMove[direction][counter], x, y, w, h, null);
    }

    /**
     * score board object
     *
     * @param g
     */
    private void scoreBoard(Graphics g){
        int xSpace = 240;
        int ySpace = 80;
        int dis = 15;
        g.drawRect(this.getWidth() - xSpace, this.getHeight() - ySpace, xSpace - 5, ySpace - 5);
        g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 27));
        char[] c = ("Score Board").toCharArray();
        g.drawChars(c, 0, c.length, this.getWidth() - xSpace, this.getHeight() - ySpace - 5);

        g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 13));

        c = ("time til end:  " + (int) main.timeToEnd() / 1000 + ":" + (int) main.timeToEnd() % 1000).toCharArray();
        g.drawChars(c, 0, c.length, this.getWidth() - xSpace + 5, this.getHeight() - ySpace + dis);

        c = ("grade:  " + (int) main.getGameInfo().grade()).toCharArray();
        g.drawChars(c, 0, c.length, this.getWidth() - xSpace + 5, this.getHeight() - ySpace + dis * 2);

        c = ("moves:  " + (int) main.getGameInfo().moves()).toCharArray();
        g.drawChars(c, 0, c.length, this.getWidth() - xSpace + 5, this.getHeight() - ySpace + dis * 3);

        c = ("logged in:  " + main.getGameInfo().isLoggedIn()).toCharArray();
        g.drawChars(c, 0, c.length, this.getWidth() - xSpace + 5, this.getHeight() - ySpace + dis * 4);

    }

    /**
     * debug data object for agents
     *
     * @param g
     */
    private void moreData(Graphics g){
        int agents = main.getGameInfo().agents();
        int xSpace = 100;
        int ySpace = 75 * agents;

        g.drawRect(5, 5, xSpace, ySpace);
        g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 12));
        int line = 0;
        for (int i = 0; i < main.getAgentList().size(); i++) {
            char[] c = ("agent " + i + " : ").toCharArray();
            g.drawChars(c, 0, c.length, 9, 20 + 13 * line++);
            c = ("speed: " + main.getAgentList().get(i).getSpeed()).toCharArray();
            g.drawChars(c, 0, c.length, 9, 20 + 13 * line++);
            c = ("score: " + main.getAgentList().get(i).getValue()).toCharArray();
            g.drawChars(c, 0, c.length, 9, 20 + 13 * line++);
            c = ("src: " + main.getAgentList().get(i).getSrc()).toCharArray();
            g.drawChars(c, 0, c.length, 9, 20 + 13 * line++);
            c = ("dest: " + main.getAgentList().get(i).getDest()).toCharArray();
            g.drawChars(c, 0, c.length, 9, 20 + 13 * line++);
            line++;
        }
    }

    /**
     * function to translate 2 point into angle
     *
     * @param src
     * @param dest
     * @return the angle of the vector
     */
    public double vectorDirection(geo_location src, geo_location dest){
        double ans = Math.atan2(dest.y() - src.y(), dest.x() - src.x()) * 180 / Math.PI;
        if (ans < 0) ans += 360;
        ans = (ans + 90) % 360;//offset
        return ans;
    }

    /**
     * @param file in the gameAssets folder
     * @return BufferedImage for convenient
     */
    private BufferedImage getImage(String file){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./gameAssets/" + file));
        } catch (IOException e) {
            System.out.println("reading pic failed");
        }
        return img;
    }

    /**
     * turn location into pos by pixel
     *
     * @param g
     * @return [0] - x, [1] - y
     */
    public int[] locationToPixel(geo_location g){
        int xSrs = (int) ((g.x() - min_max[0]) * screenSize / (min_max[1] - min_max[0])) + screenOffsetX;
        int ySrs = (int) ((g.y() - min_max[2]) * screenSize / (min_max[3] - min_max[2])) + screenOffsetY;
        //System.out.println(xSrs+"-"+ySrs);
        return new int[]{xSrs, ySrs};
    }

    /**
     * turn x,y into location on graph dimension
     *
     * @param x
     * @param y
     * @return location
     */
    public geo_location pixelToLocation(int x, int y){
        geo_location g = new GeoLocation(min_max[0] + ((min_max[1] - min_max[0]) * (-screenOffsetX + x)) / screenSize,
                min_max[2] + ((min_max[3] - min_max[2]) * (-screenOffsetY + y)) / screenSize, 0);
        return g;
    }

    @Override
    public void mouseClicked(MouseEvent e){
        geo_location g = pixelToLocation(e.getX(), e.getY());
        System.out.println(g.x() + "," + g.y());
        System.out.println(e.getX() + "," + e.getY());
        System.out.println(mainGraph.getNode(8).getLocation());
        mouseClick = g;
        repaint();
        System.out.println("*****");
        moreData = !moreData;

    }

    @Override
    public void mousePressed(MouseEvent e){

    }

    @Override
    public void mouseReleased(MouseEvent e){

    }

    @Override
    public void mouseEntered(MouseEvent e){

    }

    @Override
    public void mouseExited(MouseEvent e){

    }

    @Override
    public void actionPerformed(ActionEvent e){
        startupSequence();
        menu = 1;
        first = true;
        this.removeAll();
    }

    /**
     * start main with the given level
     */
    private void startupSequence(){
        main.startup(selectedLevel);
        mainGraph = main.getGraph();
        minMax(mainGraph.getV());
        Thread a = new algoManager(main);
        a.start();
    }

    /**
     * @param nodes
     * @return array of minMax values [0]-minX, [1]-maxX, [2]-minY, [3]-maxY
     */
    private void minMax(Collection<node_data> nodes){
        double minX = 0, maxX = 0;
        double minY = 0, maxY = 0;
        boolean first = true;
        for (node_data n : nodes
        ) {
            if (first) {
                minX = maxX = n.getLocation().x();
                minY = maxY = n.getLocation().y();
                first = false;
                continue;
            }
            if (n.getLocation().x() < minX) {
                minX = n.getLocation().x();
            }
            if (n.getLocation().x() > maxX) {
                maxX = n.getLocation().x();
            }
            if (n.getLocation().y() < minY) {
                minY = n.getLocation().y();
            }
            if (n.getLocation().y() > maxY) {
                maxY = n.getLocation().y();
            }
        }
        min_max = new double[]{minX, maxX, minY, maxY};
    }

}
