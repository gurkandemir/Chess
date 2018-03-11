import java.awt.Color;
import java.awt.Graphics;

public class Bishop extends Piece{

	Piece[][] pieces;
	int sx;
	int sy;
	int tx;
	int ty;

	public Bishop(boolean isBlack)
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

		g.fillOval(positionX+18*unit, 
				positionY+7*unit, 
				8*unit, 8*unit);

		g.fillRect(positionX+15*unit, 
				positionY+14*unit, 
				14*unit, 4*unit);

		g.fillOval(positionX+16*unit, 
				positionY+15*unit, 
				13*unit, 20*unit);

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

	//This method gets pieces,selectedSquareX,selectedSquareY,targetSquareX,targetSquareY from ChessFrame class
	//It checks whether the movement is possible or not.
	//For example if there is any kind of pieces on the path it return false
	@Override
	public boolean Check(int x,int y){
		pieces=ChessFrame.pieces;
		sx=ChessFrame.selectedSquareX;
		sy=ChessFrame.selectedSquareY;
		tx=ChessFrame.targetSquareX;
		ty=ChessFrame.targetSquareY;

		//Bishop can move or capture only when abs difference X and abs difference Y are equal.
		if(Math.abs(x)!=Math.abs(y))
			return false;

		else{
			//If abs X equals abs Y, it checks whether there is any kind of pieces on the path way or not.
			if(sx<tx && sy>ty){
				while(sx!=tx-1){
					if(pieces[sx+1][sy-1]!=null){
						return false;
					}

					sx++;
					sy--;
				}

				return true;
			}

			else if(sx>tx && sy<ty){
				while(sx!=tx+1){
					if(pieces[sx-1][sy+1]!=null){
						return false;
					}

					sx--;
					sy++;
				}

				return true;
			}

			else if(sx<tx && sy<ty){
				while(sx!=tx-1){
					if(pieces[sx+1][sy+1]!=null){
						return false;
					}

					sx++;
					sy++;
				}

				return true;
			}

			else if(sx>tx && sy>ty){
				while(sx!=tx+1){
					if(pieces[sx-1][sy-1]!=null){
						return false;
					}

					sx--;
					sy--;
				}

				return true;
			}
		}
		return false;
	}

}
