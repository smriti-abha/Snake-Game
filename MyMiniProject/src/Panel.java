import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

public final class Panel extends JPanel implements ActionListener{
    static final int width = 1200;
    static final int height= 600;
    static final int unit=50; // unit size
    Timer timer;
    Random random;

    int fx;
    int fy;
    int foodeaten;

    int bodylength=3;
    boolean game_flag=false;
    char dir = 'R';
    static final int delay=160;
    static final int gsize = (width*height)/(unit*unit);
    final int[] x_snake = new int[gsize] ;
    final int [] y_snake = new int[gsize];



    Panel(){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.GRAY);
        this.addKeyListener(new MyKey());
        this.setFocusable(true);
        random=new Random();
        Game_start();
    }
    public void Game_start(){
        newFoodPosition();
        game_flag=true;
        timer=new Timer(delay,this);
        timer.start();
    }
    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }


    public void draw(Graphics graphic){
        if(game_flag){
            //for food
            graphic.setColor(Color.orange);
            graphic.fillOval(fx, fy, unit, unit);
            //for snake head
            for(int i=0;i<bodylength;i++){
                if(i==0){
                    graphic.setColor(Color.GREEN);
                    graphic.fillRect(x_snake[i],y_snake[i],unit,unit);
                }
                //for whole body of snake
                else{
                    graphic.setColor(new Color(50,180,0));
                    graphic.fillRect(x_snake[i], y_snake[i], unit, unit);
                }
            }
            graphic.setColor(Color.RED);
            graphic.setFont(new Font("comic sans",Font.BOLD,40));
            FontMetrics font_me=getFontMetrics(graphic.getFont());
            graphic.drawString("Score: "+foodeaten, (width-font_me.stringWidth("Score:"+foodeaten))/2, graphic.getFont().getSize());


        }
        else{
            gameOver(graphic);
        }

    }
    public void move(){
        for(int i=bodylength;i>0;i--){
            x_snake[i]=x_snake[i-1];
            y_snake[i]=y_snake[i-1];
        }
        switch(dir){
            case 'U':
                y_snake[0]=y_snake[0]-unit;
                break;
            case 'L':
                x_snake[0]=x_snake[0]-unit;
                break;
            case 'D':
                y_snake[0]=y_snake[0]+unit;
                break;
            case 'R':
                x_snake[0]=x_snake[0]+unit;
                break;
        }
    }
    public void gameOver(Graphics graphic){
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("conic sans",Font.BOLD,40));
        FontMetrics font_me=getFontMetrics(graphic.getFont());
        graphic.drawString("Score:"+foodeaten, (width-font_me.stringWidth("Score:"+foodeaten))/2, graphic.getFont().getSize());

        //to display game over text
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("conic sans",Font.BOLD,40));
        FontMetrics font_me1=getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER!", (width-font_me1.stringWidth("GAME OVER!"))/2, height/2);

        //to display the promt replay
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("conic sans",Font.BOLD,40));
        FontMetrics font_me2=getFontMetrics(graphic.getFont());
        graphic.drawString("Press R To Replay", (width-font_me2.stringWidth("Press R To Replay"))/2, height/2-150);

    }

    public void newFoodPosition() {
        //to set food at random coordinates
        fx=random.nextInt(width/gsize)*unit;
        fy=random.nextInt(width/gsize)*unit;
    }
    public void checkhit(){
        //for checking collision of snake with itself and wall
        for(int i=bodylength;i>0;i--){
            if ((x_snake[0] == x_snake[i]) && (y_snake[0] == y_snake[i])) {
                game_flag = false;
                break;
            }
        }
        if(x_snake[0]<0){
            game_flag=false;
        }
        else if(x_snake[0]>width){
            game_flag=false;
        }
        else if(y_snake[0]<0){
            game_flag=false;
        }
        else if(y_snake[0]>height){
            game_flag=false;
        }
        if(!game_flag){
            timer.stop();
        }
    }
    public void eat(){
        if((x_snake[0]==fx) && (y_snake[0]==fy)){
            bodylength++;
            foodeaten++;
            newFoodPosition();
        }
    }

    public class MyKey extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(dir!='R'){
                        dir='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!='L'){
                        dir='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(dir!='D'){
                        dir='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir!='U'){
                        dir='D';
                    }
                    break;

                case KeyEvent.VK_R:
                    if(!game_flag){
                        foodeaten=0;
                        bodylength=3;
                        dir='R';
                        Arrays.fill(x_snake, 0);
                        Arrays.fill(y_snake, 0);
                        Game_start();
                    }
                    break;
            }
        }

    }
    @Override
    public void actionPerformed(ActionEvent arg0){
        if(game_flag){
            move();
            eat();
            checkhit();
        }
        repaint();
    }
}
