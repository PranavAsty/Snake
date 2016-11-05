import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.Random;

class Game extends JPanel
{
  Snake snake;
  Object[] obj = new Object[30];
  void init()
  {
    snake = new Snake(10);
  }

  @Override
  public void paint(Graphics g)
  {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);
    for(int i=0;i<snake.len;i++)
    {
      g2d.fillOval(snake.x[i],snake.y[i],snake.size,snake.size);
    }
    int i=0;
    for(int k=0;k<obj.length;k++)
    {
      if(obj[i]!=null)
      {
        if(obj[i].type==1)
          g2d.setColor(Color.GREEN);
        else if(obj[i].type==2)
          g2d.setColor(Color.RED);
        g2d.fillOval(obj[i].x,obj[i].y,15,15);
        i++;
      }
    }
  }

  private void checkCollision()
  {
    //A-walls B-itself C-food D-killers
    if(snake.x[0]<0 || snake.x[0]>(900-snake.size) || snake.y[0]<0 || snake.y[0]>(600-snake.size))
    {
      JOptionPane.showMessageDialog(null,"Game over!");
      System.exit(0);
    }

    Rectangle[] r1 = new Rectangle[100];
    Rectangle[] r2 = new Rectangle[30];
    for(int i=0;i<snake.len;i++)
    {
      r1[i]= new Rectangle(snake.x[i],snake.y[i],snake.size,snake.size);
      for(int k=0;k<i-2;k++)
      {
        if(r1[i].intersects(r1[k]))
        {
          JOptionPane.showMessageDialog(null,"Game over!");
          System.exit(0);
        }
      }
    }
    int i=0;
    for(int k=0;k<obj.length;k++)
    {
      if(obj[i]!=null)
      {
        r2[i] = new Rectangle(obj[i].x,obj[i].y,15,15);
        if(r2[i].intersects(r1[0]))
        {
          if(obj[i].type==1)
          {
            snake.len++;
            int diffx=0,diffy=0 ;
            switch(snake.dir[snake.len-1])
            {
              case 1: diffx=-snake.size;diffy=0;break;
              case -1: diffx=snake.size;diffy=0;break;
              case 2: diffx=0;diffy=snake.size;break;
              case -2: diffx=0;diffy=-snake.size;break;
            }
            //System.out.println(snake.x[snake.len-2]+"  "+snake.y[snake.len-2]);
            snake.x[snake.len-1]=snake.x[snake.len-2]+diffx;
            snake.y[snake.len-1]=snake.y[snake.len-2]+diffy;
            snake.dir[snake.len-1]=snake.dir[snake.len-2];

          }
          else if(obj[i].type==2)
          {
            snake.len-=2;
            if(snake.len<3)
            {
              JOptionPane.showMessageDialog(null,"Game over!");
              System.exit(0);
            }
          }
          obj[i]=null;
        }
        i++;
      }
    }



  }

  public static void main(String args[]) throws InterruptedException
  {
    Game game = new Game();
    JFrame frame = new JFrame();

    game.init();
    new Thread(new Generator(game,game.snake,1,0.1)).start();
    new Thread(new Generator(game,game.snake,2,0.1)).start();
    game.checkCollision();

    frame.add(game);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(new Dimension(900,600));
    frame.setMinimumSize(new Dimension(900,600));
    game.repaint();
    frame.setResizable(false);
    frame.setVisible(true);




    frame.addKeyListener(new KeyListener(){
      private long i=0;
      private long k=0;

        @Override
        public void keyReleased(KeyEvent evt) {


          switch(evt.getKeyCode())
          {
            case KeyEvent.VK_UP:{
              if(game.snake.dir[0]!=-2 && game.snake.dir[0]!=2)
              {
                if(System.currentTimeMillis()-i>300)
                {
                  i = System.currentTimeMillis();
                  new Thread(new Update(game.snake,2)).start();
                }
              }
              break;
            }
            case KeyEvent.VK_DOWN:{
              if(game.snake.dir[0]!=-2 && game.snake.dir[0]!=2)
              {
                if(System.currentTimeMillis()-i>300)
                {
                  i = System.currentTimeMillis();
                  new Thread(new Update(game.snake,-2)).start();
                }
              }
              break;
            }
            case KeyEvent.VK_LEFT:{
              if(game.snake.dir[0]!=-1 && game.snake.dir[0]!=1)
              {
                if(System.currentTimeMillis()-i>300)
                {
                  i = System.currentTimeMillis();
                  new Thread(new Update(game.snake,-1)).start();
                }
              }
              break;
            }
            case KeyEvent.VK_RIGHT:{
              if(game.snake.dir[0]!=-1 && game.snake.dir[0]!=1)
              {
                if(System.currentTimeMillis()-i>300)
                {
                  i = System.currentTimeMillis();
                  new Thread(new Update(game.snake,1)).start();
                }
              }
              break;
            }

          }
        }
        @Override
        public void keyPressed(KeyEvent evt) {
        }
        @Override
        public void keyTyped(KeyEvent evt) {
        }
    });



    long time=0;
    while(true)
    {
      if(System.currentTimeMillis()-time>400)
      {
        time=System.currentTimeMillis();
        game.checkCollision();
      }
      //System.out.println(game.snake.len);
      for(int i=0;i<game.snake.len;i++)
      {
        switch(game.snake.dir[i])
        {
          case 1:game.snake.x[i]+=5;break;
          case -1:game.snake.x[i]-=5;break;
          case +2:game.snake.y[i]-=5;break;
          case -2:game.snake.y[i]+=5;break;
        }
      }

      game.repaint();
      Thread.sleep(100);
    }
  }
}
