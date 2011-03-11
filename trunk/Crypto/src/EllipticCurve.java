import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author sheetal
 *
 */
class Point{
	
	double x, y;
	static Point Zero = new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	boolean isEqual(Point p2)
	{
		if(p2!=null)
		{
			if(this.x==p2.x && this.y==p2.y)
				return true;
		}
		return false;
	}
}
public class EllipticCurve {

	/**
	 * @param args
	 */
	int A, B;
	public EllipticCurve(int A, int B)
	{
		//y2 = x3 + Ax+ B
		this.A = A;
		this.B = B;
		
	}
	boolean isValid()
	{
		double determinant = 4*A*A*A + 27*B*B;
		if(determinant==0)
			return false;
		return true;
	}
	//if fp==0 then do general addition, otherwise do fp addition
	
	public Point addition(Point p1, Point p2, long fp) 
	{
		Point p3;
		double x3, y3;
		double numerator;
		double denominator;
		
		if(p1==null || p2 ==null)
			return null;
		if(p1.isEqual(Point.Zero))
			return p2;
		else if(p2.isEqual(Point.Zero))
			return p1;
		else
		{
			if(p1.x==p2.x && p1.y==-p2.y)
			  return Point.Zero;
			else
			{
				double slope = 0;
				if(p1.isEqual(p2))
				{
					numerator = (3*p1.x*p1.x) + A;
					denominator = 2*p1.y;
					
					
				}
				else
				{
					numerator = (p2.y-p1.y);
					denominator = (p2.x-p1.x);
					
					
				}
				if(fp!=0)
				{
					if(denominator<0)
						denominator+=fp;
					denominator = crypto.inverse(fp, (long)denominator);
				}
				else
				{
					denominator = 1/denominator;
				}
				slope = numerator*denominator;
				x3 = slope*slope - p1.x - p2.x;
				y3 = slope*(p1.x - x3) - p1.y;
				
				if(fp!=0)
				{
					x3 = x3%fp;
					y3 = y3%fp;
					if(y3<0)
						y3+=fp;
				}
				p3 = new Point(x3, y3);
				return p3;
			}
		}
	}
	public List<Point> getPoints(int fp)
	{
		long y2 = 0;
		List<Point> allPoints = new ArrayList<Point>();
		allPoints.add(Point.Zero);
		
		for(int x=0;x<fp;x++)
		{
			//get y
			y2 = x*x*x + this.A*x+this.B;
			List<Long> sr = SquareRoot.squareRoot(y2, fp);
			for(long y:sr){
				    if(y==1||y==fp-1) continue;
					Point p = new Point(x,y);
					allPoints.add(p);
					
				}
			
		}
		return allPoints;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int A = 3;
		int B = 8;
		int fp = 13;
		
		EllipticCurve ec = new EllipticCurve(A, B);
		
        List<Point> allPoints = ec.getPoints(fp);
        for(Point p:allPoints)
        {
        	System.out.println("("+p.x+","+p.y+")");
        }
        //addition
        Point p1 =  allPoints.get(6);
        Point p2 =  allPoints.get(2);
        
        Point q = ec.addition(p1, p2, fp);
        System.out.println("("+p1.x+","+p1.y+") + ("+p2.x+","+p2.y+") ="+"("+q.x+","+q.y+")");
	}

}
