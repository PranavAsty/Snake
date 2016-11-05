class Update implements Runnable
{
  Snake snake;
  int c=0,dir;
  Update(Snake s,int dir)
  {
    snake=s;
    this.dir = dir;
  }
  @Override
  public void run()
  {
    while(true)
    {
      if(c<snake.dir.length)
        snake.dir[c++]=dir;
      else
        break;
      try{
        Thread.sleep(300);
      }catch(InterruptedException e){
        e.printStackTrace();
      }
    }
  }
}
