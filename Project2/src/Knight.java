import java.awt.Color;
import java.awt.Graphics;

public class Knight extends Piece{

	public Knight(boolean isBlack)
	{
		super(isBlack);
	}
	@Override
	public void drawYourself(Graphics g, int positionX, int positionY, int squareWidth) {
		// TODO Auto-generated method stub

		int unit=squareWidth/45;
		
		if(isBlack)
		{
			g.setColor(Color.black);
		}
		else
		{
			g.setColor(Color.white);
		}

		g.fillOval(positionX+8*unit, 
				positionY+8*unit, 
				13*unit, 20*unit);
		
		g.fillRect(positionX+10*unit, 
				positionY+13*unit, 
				25*unit, 6*unit);
		
		g.fillRect(positionX+29*unit, 
				positionY+13*unit, 
				8*unit, 26*unit);
		
		g.fillRect(positionX+10*unit, 
				positionY+33*unit, 
				25*unit, 6*unit);
	}

	@Override
	public boolean canMove(int x, int y) {
		// TODO Auto-generated method stub
		return Check(x,y);
	}

	@Override
	public boolean canCapture(int x, int y) {
		// TODO Auto-generated method stub
		return Check(x,y);	
	}

	@Override
	//This method gets pieces,selectedSquareX,selectedSquareY,targetSquareX,targetSquareY from ChessFrame class
		//It checks whether the movement is possible or not.
		//For example if there is any kind of pieces on the path it return false
	public boolean Check(int x, int y) {
		// TODO Auto-generated method stub
		if(Math.abs(x)==2 && Math.abs(y)==1)
			return true;

		else if(Math.abs(x)==1 && Math.abs(y)==2)
			return true;

		else
			return false;	}

}
