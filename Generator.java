import java.util.Random;

class Generator implements Runnable
{

  Snake snake;
  Game game;
  Random r = new Random();
  double perc;
  int type;
  int i = 0;
  Generator(Game g, Snake s,int type,double perc)
  {
    snake=s;
    game=g;
    this.type = type;
    this.perc=perc;
  }
  @Override
  public void run()
  {

    while(true)
    {
      if(r.nextDouble()<perc && i<game.obj.length)
      {
        game.obj[i++] = new Object(type,r.nextInt(850),r.nextInt(550));
      }
      try{
        Thread.sleep(1000);
      }catch(InterruptedException e){
        e.printStackTrace();
      }
    }
  }
}
