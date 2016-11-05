class Snake
{
  int len;
  int size;
  int[] x = new int[100];
  int[] y = new int[100];
  int[] dir = new int[100];
  Snake(int len)
  {
    this.len=len;
    size=15;
    x[0]=200;
    y[0]=200;
    dir[0]=1;
    for(int i=1;i<len;i++)
    {
      x[i]=x[i-1]-size;
      y[i]=y[i-1];
      dir[i]=dir[i-1];
    }
  }
}
