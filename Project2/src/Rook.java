import java.awt.Color;
import java.awt.Graphics;

public class Rook extends Piece{

	Piece[][] pieces;
	int sx;
	int sy;
	int tx;
	int ty;

	public Rook(boolean isBlack)
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


		g.fillRect(positionX+11*unit, 
				positionY+10*unit, 
				6*unit, 4*unit);
		
		g.fillRect(positionX+19*unit, 
				positionY+10*unit, 
				7*unit, 4*unit);
		
		g.fillRect(positionX+28*unit, 
				positionY+10*unit, 
				6*unit, 4*unit);
		
		g.fillRect(positionX+11*unit, 
				positionY+14*unit, 
				23*unit, 4*unit);
		
		g.fillRect(positionX+14*unit, 
				positionY+18*unit, 
				17*unit, 16*unit);
		
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
	public boolean Check(int x,int y){
		pieces=ChessFrame.pieces;
		sx=ChessFrame.selectedSquareX;
		sy=ChessFrame.selectedSquareY;
		tx=ChessFrame.targetSquareX;
		ty=ChessFrame.targetSquareY;

		if(y!=0 && x==0){
			int maxY=Math.max(sy, ty);
			int minY=Math.min(sy, ty);
			for(int i=minY+1;i<maxY;i++){
				if(pieces[sx][i]!=null)
					return false;

			}
			return true;
		}

		else if(x!=0 && y==0){
			int maxX=Math.max(sx, tx);
			int minX=Math.min(sx, tx);
			for(int i=minX+1;i<maxX;i++){
				if(pieces[i][sy]!=null)
					return false;
			}

			return true;
		}
		else
			return false;
	}

}

