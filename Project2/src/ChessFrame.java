import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ChessFrame extends JFrame implements MouseListener{

	/**
	 * width of one square on the board.
	 * Change this to have a bigger or smaller 
	 * game frame. 
	 */
	public static final int SQUARE_WIDTH =90;

	/**
	 * margins of the board on the frame
	 */
	public static final int BOARD_MARGIN = 110;
	public static int selectedSquareX = -1;
	public static int selectedSquareY = -1;
	//I declare targetSquareX and targetSqaure as static int in order to reach them in pieces' classes easily
	public static int targetSquareX;
	public static int targetSquareY; 
	//I declare Piece pieces static, in order to reach board configuration in other classes.
	public static Piece pieces[][] = new Piece[8][8];
	//I declare int count to learn whose turn it is. In each movement, count increases by one
	static int count=0;
	Stack<Piece[][]> s=new Stack<>();

	public ChessFrame()
	{
		initializeChessBoard();
		setTitle("Chess Game");
		//let the screen size fit the board size
		setSize(SQUARE_WIDTH*11+BOARD_MARGIN*2, SQUARE_WIDTH*8+BOARD_MARGIN*2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(this);
		setLayout(null);

		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save("game.txt");

			}
		});
		save.setBounds(2*BOARD_MARGIN+8*SQUARE_WIDTH, BOARD_MARGIN+SQUARE_WIDTH/3, SQUARE_WIDTH, SQUARE_WIDTH/2);
		add(save);

		JButton undo = new JButton("Undo");
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undo();

			}
		});
		undo.setBounds(SQUARE_WIDTH*8+BOARD_MARGIN*2, SQUARE_WIDTH+4*BOARD_MARGIN/3, SQUARE_WIDTH, SQUARE_WIDTH/2);
		add(undo);

		JLabel label1=new JLabel("8");
		label1.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label1.setBounds(BOARD_MARGIN-SQUARE_WIDTH/2, BOARD_MARGIN-SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label1);

		JLabel label2=new JLabel("7");
		label2.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label2.setBounds(BOARD_MARGIN-SQUARE_WIDTH/2, BOARD_MARGIN+4*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label2);

		JLabel label3=new JLabel("6");
		label3.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label3.setBounds(BOARD_MARGIN-SQUARE_WIDTH/2, BOARD_MARGIN+9*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label3);

		JLabel label4=new JLabel("5");
		label4.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label4.setBounds(BOARD_MARGIN-SQUARE_WIDTH/2, BOARD_MARGIN+14*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label4);

		JLabel label5=new JLabel("4");
		label5.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label5.setBounds(BOARD_MARGIN-SQUARE_WIDTH/2, BOARD_MARGIN+19*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label5);

		JLabel label6=new JLabel("3");
		label6.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label6.setBounds(BOARD_MARGIN-SQUARE_WIDTH/2, BOARD_MARGIN+24*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label6);

		JLabel label7=new JLabel("2");
		label7.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label7.setBounds(BOARD_MARGIN-SQUARE_WIDTH/2, BOARD_MARGIN+29*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label7);

		JLabel label8=new JLabel("1");
		label8.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label8.setBounds(BOARD_MARGIN-SQUARE_WIDTH/2, BOARD_MARGIN+34*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label8);

		JLabel label11=new JLabel("8");
		label11.setFont(new Font("Tahoma", Font.PLAIN,SQUARE_WIDTH/3));
		label11.setBounds(BOARD_MARGIN+8*SQUARE_WIDTH, BOARD_MARGIN-SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label11);

		JLabel label21=new JLabel("7");
		label21.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label21.setBounds(BOARD_MARGIN+8*SQUARE_WIDTH, BOARD_MARGIN+4*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label21);

		JLabel label31=new JLabel("6");
		label31.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label31.setBounds(BOARD_MARGIN+8*SQUARE_WIDTH, BOARD_MARGIN+9*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label31);

		JLabel label41=new JLabel("5");
		label41.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label41.setBounds(BOARD_MARGIN+8*SQUARE_WIDTH, BOARD_MARGIN+14*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label41);

		JLabel label51=new JLabel("4");
		label51.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label51.setBounds(BOARD_MARGIN+8*SQUARE_WIDTH, BOARD_MARGIN+19*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label51);

		JLabel label61=new JLabel("3");
		label61.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label61.setBounds(BOARD_MARGIN+8*SQUARE_WIDTH, BOARD_MARGIN+24*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label61);

		JLabel label71=new JLabel("2");
		label71.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label71.setBounds(BOARD_MARGIN+8*SQUARE_WIDTH, BOARD_MARGIN+29*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label71);

		JLabel label81=new JLabel("1");
		label81.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		label81.setBounds(BOARD_MARGIN+8*SQUARE_WIDTH, BOARD_MARGIN+34*SQUARE_WIDTH/5, SQUARE_WIDTH/2, SQUARE_WIDTH/2);
		add(label81);

		JLabel labelA=new JLabel("A");
		labelA.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelA.setBounds(BOARD_MARGIN+SQUARE_WIDTH/3, BOARD_MARGIN-SQUARE_WIDTH, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelA);

		JLabel labelB=new JLabel("B");
		labelB.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelB.setBounds(BOARD_MARGIN+4*SQUARE_WIDTH/3, BOARD_MARGIN-SQUARE_WIDTH, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelB);

		JLabel labelC=new JLabel("C");
		labelC.setFont(new Font("Tahoma", Font.PLAIN,SQUARE_WIDTH/3));
		labelC.setBounds(BOARD_MARGIN+7*SQUARE_WIDTH/3, BOARD_MARGIN-SQUARE_WIDTH, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelC);

		JLabel labelD=new JLabel("D");
		labelD.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelD.setBounds(BOARD_MARGIN+10*SQUARE_WIDTH/3, BOARD_MARGIN-SQUARE_WIDTH, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelD);

		JLabel labelE=new JLabel("E");
		labelE.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelE.setBounds(BOARD_MARGIN+13*SQUARE_WIDTH/3, BOARD_MARGIN-SQUARE_WIDTH, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelE);

		JLabel labelF=new JLabel("F");
		labelF.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelF.setBounds(BOARD_MARGIN+16*SQUARE_WIDTH/3, BOARD_MARGIN-SQUARE_WIDTH, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelF);

		JLabel labelG=new JLabel("G");
		labelG.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelG.setBounds(BOARD_MARGIN+19*SQUARE_WIDTH/3, BOARD_MARGIN-SQUARE_WIDTH, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelG);

		JLabel labelH=new JLabel("H");
		labelH.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelH.setBounds(BOARD_MARGIN+22*SQUARE_WIDTH/3, BOARD_MARGIN-SQUARE_WIDTH, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelH);

		JLabel labelA1=new JLabel("A");
		labelA1.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelA1.setBounds(BOARD_MARGIN+SQUARE_WIDTH/3, BOARD_MARGIN+7*SQUARE_WIDTH+SQUARE_WIDTH/2, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelA1);

		JLabel labelB1=new JLabel("B");
		labelB1.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelB1.setBounds(BOARD_MARGIN+4*SQUARE_WIDTH/3, BOARD_MARGIN+7*SQUARE_WIDTH+SQUARE_WIDTH/2, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelB1);

		JLabel labelC1=new JLabel("C");
		labelC1.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelC1.setBounds(BOARD_MARGIN+7*SQUARE_WIDTH/3, BOARD_MARGIN+7*SQUARE_WIDTH+SQUARE_WIDTH/2, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelC1);

		JLabel labelD1=new JLabel("D");
		labelD1.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelD1.setBounds(BOARD_MARGIN+10*SQUARE_WIDTH/3, BOARD_MARGIN+7*SQUARE_WIDTH+SQUARE_WIDTH/2, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelD1);

		JLabel labelE1=new JLabel("E");
		labelE1.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelE1.setBounds(BOARD_MARGIN+13*SQUARE_WIDTH/3, BOARD_MARGIN+7*SQUARE_WIDTH+SQUARE_WIDTH/2, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelE1);

		JLabel labelF1=new JLabel("F");
		labelF1.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelF1.setBounds(BOARD_MARGIN+16*SQUARE_WIDTH/3, BOARD_MARGIN+7*SQUARE_WIDTH+SQUARE_WIDTH/2, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelF1);

		JLabel labelG1=new JLabel("G");
		labelG1.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelG1.setBounds(BOARD_MARGIN+19*SQUARE_WIDTH/3, BOARD_MARGIN+7*SQUARE_WIDTH+SQUARE_WIDTH/2, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelG1);

		JLabel labelH1=new JLabel("H");
		labelH1.setFont(new Font("Tahoma", Font.PLAIN, SQUARE_WIDTH/3));
		labelH1.setBounds(BOARD_MARGIN+22*SQUARE_WIDTH/3, BOARD_MARGIN+7*SQUARE_WIDTH+SQUARE_WIDTH/2, SQUARE_WIDTH/2,SQUARE_WIDTH/2);
		add(labelH1);

	}

	public void initializeChessBoard()
	{
		for(int i = 0; i<8; i++)
		{
			for(int j = 0; j<8; j++)
			{
				if(j == 1){
					//add a black pawn here
					pieces[i][j] = new Pawn(true);
				}
				else if(j == 6){
					//add a white pawn here
					pieces[i][j] = new Pawn(false);
				}
				else
					pieces[i][j] = null;
			}
		}
		//I configure Rook in the correct places of board
		pieces[0][0]=new Rook(true);
		pieces[7][0]=new Rook(true);
		pieces[0][7]=new Rook(false);
		pieces[7][7]=new Rook(false);

		//I configure Knight in the correct places of board
		pieces[1][0]=new Knight(true);
		pieces[6][0]=new Knight(true);
		pieces[1][7]=new Knight(false);
		pieces[6][7]=new Knight(false);

		//I configure Bishop in the correct places of board
		pieces[2][0]=new Bishop(true);
		pieces[5][0]=new Bishop(true);
		pieces[2][7]=new Bishop(false);
		pieces[5][7]=new Bishop(false);

		//I configure Queen in the correct places of board
		pieces[3][0]=new Queen(true);
		pieces[3][7]=new Queen(false);

		//I configure King in the correct places of board
		pieces[4][0]=new King(true);
		pieces[4][7]=new King(false);
	}


	/**
	 * It checks whether the movement is possible or not according to regular chess rules.
	 * @param from : starting point
	 * @param to : destination point
	 * @return whether move is possible or not
	 */
	public boolean move(String from, String to){
		from=from.toUpperCase();
		to=to.toUpperCase();
		Piece[][] board=new Piece[8][8];

		//turn them into a digit
		selectedSquareX=from.charAt(0)-65;
		selectedSquareY=8-from.charAt(1)+48;
		targetSquareX=to.charAt(0)-65;
		targetSquareY=8-to.charAt(1)+48;

		if(selectedSquareX >= 0 && selectedSquareY >= 0 &&
				selectedSquareX < 8 && selectedSquareY < 8 &&
				targetSquareX >= 0 && targetSquareY >= 0 &&
				targetSquareX < 8 && targetSquareY < 8)
		{
			if(pieces[selectedSquareX][selectedSquareY] != null)
			{

				//System.out.println("selected");
				int diffX = targetSquareX - selectedSquareX;
				int diffY = targetSquareY - selectedSquareY;
				if(pieces[targetSquareX][targetSquareY] != null )
				{
					//System.out.println("a target");
					if(pieces[selectedSquareX][selectedSquareY].canCapture(diffX, diffY))
					{
						//It checks whose turn it is.
						if((pieces[selectedSquareX][selectedSquareY].isBlack && count%2==1) || 
								(!pieces[selectedSquareX][selectedSquareY].isBlack && count%2==0 ))
						{
							//It checks whether the capture movement is possible or not. If black wants to capture black piece, it is not possible
							if((pieces[selectedSquareX][selectedSquareY].isBlack && !pieces[targetSquareX][targetSquareY].isBlack) ||
									(!pieces[selectedSquareX][selectedSquareY].isBlack && pieces[targetSquareX][targetSquareY].isBlack))
							{
								for(int i=0;i<8;i++){
									for(int j=0;j<8;j++)
										board[i][j]=pieces[i][j];
								}	
								//it pushes current board configuration into a stack in order to undo
								s.push(board);	
								//System.out.println("can capture");
								pieces[targetSquareX][targetSquareY] = 
										pieces[selectedSquareX][selectedSquareY];
								pieces[selectedSquareX][selectedSquareY] = null;
								//Increments count by one, in order to change the turn.
								if(count%2==1 && targetSquareY==7 && pieces[targetSquareX][targetSquareY].isBlack && pieces[targetSquareX][targetSquareY] instanceof Pawn){
									pieces[targetSquareX][targetSquareY]=new Queen(true);
								}
								if(count%2==0 && targetSquareY==0 && !pieces[targetSquareX][targetSquareY].isBlack && pieces[targetSquareX][targetSquareY] instanceof Pawn){
									pieces[targetSquareX][targetSquareY]=new Queen(false);
								}

								//if king is in check in return false; and undos the movement
								//check=true;
								if(isInCheck()){
									undo();
									count++;
									return false;
								}

								count++;

								repaint();

								return true;


							}
						}
					}
				}
				else
				{
					//System.out.println("no target");
					if(pieces[selectedSquareX][selectedSquareY].canMove(diffX, diffY))
					{
						//It checks whose turn it is.
						if((pieces[selectedSquareX][selectedSquareY].isBlack && count%2==1) || 
								(!pieces[selectedSquareX][selectedSquareY].isBlack && count%2==0 ))
						{
							for(int i=0;i<8;i++){
								for(int j=0;j<8;j++)
									board[i][j]=pieces[i][j];
							}	
							s.push(board);
							//System.out.println("can move");
							pieces[targetSquareX][targetSquareY] = 
									pieces[selectedSquareX][selectedSquareY];
							pieces[selectedSquareX][selectedSquareY] = null;
							if(targetSquareY==7 && pieces[targetSquareX][targetSquareY].isBlack && pieces[targetSquareX][targetSquareY] instanceof Pawn){
								pieces[targetSquareX][targetSquareY]=new Queen(true);
							}
							if(count%2==0 && targetSquareY==0 && !pieces[targetSquareX][targetSquareY].isBlack && pieces[targetSquareX][targetSquareY] instanceof Pawn){
								pieces[targetSquareX][targetSquareY]=new Queen(false);
							}

							//check=true;

							if(isInCheck()){
								undo();
								count++;
								return false;
							}

							count++;

							repaint();

							return true;



						}
					}
				}
			}
		}



		repaint();
		return false;
	}

	/**
	 * It returns the type-color of pieces in that position
	 * @param pos : Current position
	 * @return : the piece in that position
	 */
	public String at(String pos){
		pos=pos.toUpperCase();
		//it turns them into a digit
		int x=pos.charAt(0)-65;
		int y=8-pos.charAt(1)+48;
		String s="";

		if(pieces[x][y]!=null){

			if(pieces[x][y].isBlack)
				s+="black";

			else
				s+="white";

			if(pieces[x][y] instanceof Rook)
				s+="-rook";

			else if(pieces[x][y] instanceof Knight)
				s+="-knight";

			else if(pieces[x][y] instanceof Pawn)
				s+="-pawn";

			else if(pieces[x][y] instanceof Queen)
				s+="-queen";

			else if(pieces[x][y] instanceof Bishop)
				s+="-bishop";

			else if(pieces[x][y] instanceof King)
				s+="-king";
		}
		return s;
	}

	/**
	 * It checks castling is possible or not.
	 * @param isKingSide : It emphasizes the type of castling
	 * @return : Whether the castling is possible or not, if true it does the castling
	 */
	public boolean castling(boolean isKingSide){
		Piece[][] board=new Piece[8][8];
		if(count%2==1){
			if(!isKingSide){
				if(pieces[0][0] instanceof Rook && pieces[4][0] instanceof King
						&& pieces[0][0].isBlack && pieces[4][0].isBlack){
					int c=0;
					for(int i=1;i<4;i++){
						if(pieces[i][0]!=null){
							c++;
							break;
						}
					}

					if(c==0){
						for(int i=0;i<8;i++){
							for(int j=0;j<8;j++)
								board[i][j]=pieces[i][j];
						}	
						s.push(board);

						pieces[0][0]=null;
						pieces[4][0]=null;
						pieces[3][0]=new Rook(true);
						pieces[2][0]=new King(true);

						if(isInCheck()){
							undo();
							count++;
							return false;
						}
						count++;
						return true;
					}
				}
			}

			else{
				if(pieces[7][0] instanceof Rook && pieces[4][0] instanceof King
						&& pieces[7][0].isBlack && pieces[4][0].isBlack){
					int c=0;
					for(int i=5;i<7;i++){
						if(pieces[i][0]!=null){
							c++;
							break;
						}
					}

					if(c==0){

						for(int i=0;i<8;i++){
							for(int j=0;j<8;j++)
								board[i][j]=pieces[i][j];
						}
						s.push(board);

						pieces[4][0]=null;
						pieces[7][0]=null;
						pieces[5][0]=new Rook(true);
						pieces[6][0]=new King(true);
						if(isInCheck()){
							undo();
							count++;
							return false;
						}
						count++;
						return true;
					}

				}
			}
		}

		else{

			if(!isKingSide){
				if(pieces[0][7] instanceof Rook && pieces[4][7] instanceof King
						&& !pieces[0][7].isBlack && !pieces[4][7].isBlack){
					int c=0;
					for(int i=1;i<4;i++){
						if(pieces[i][7]!=null){
							c++;
							break;
						}
					}

					if(c==0){
						for(int i=0;i<8;i++){
							for(int j=0;j<8;j++)
								board[i][j]=pieces[i][j];
						}
						s.push(board);

						pieces[0][7]=null;
						pieces[4][7]=null;
						pieces[3][7]=new Rook(false);
						pieces[2][7]=new King(false);

						if(isInCheck()){
							undo();
							count++;
							return false;
						}
						count++;
						return true;
					}
				}
			}

			else{
				if(pieces[7][7] instanceof Rook && pieces[4][7] instanceof King
						&& !pieces[7][7].isBlack && !pieces[4][7].isBlack){
					int c=0;
					for(int i=5;i<7;i++){
						if(pieces[i][7]!=null){
							c++;
							break;
						}
					}
					if(c==0){
						for(int i=0;i<8;i++){
							for(int j=0;j<8;j++)
								board[i][j]=pieces[i][j];
						}
						s.push(board);
						pieces[4][7]=null;
						pieces[7][7]=null;
						pieces[5][7]=new Rook(false);
						pieces[6][7]=new King(false);

						if(isInCheck()){
							undo();
							count++;
							return false;
						}
						count++;
						return true;
					}
				}
			}
		}
		repaint();
		return false;
	}

	/**
	 * Undos the last moves.
	 */
	public void undo(){
		if(!s.isEmpty()){
			Piece[][] board=new Piece[8][8];
			board=s.pop();
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++)
					pieces[i][j]=board[i][j];
			}
			count--;
		}
		repaint();

	}

	/**
	 * 
	 * @return : Whether the king is in check or not
	 */
	public boolean isInCheck(){
		int x=-1;
		int y=-1;
		if(count%2==0){
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					if(pieces[i][j]!=null){
						if(!pieces[i][j].isBlack){
							//finds king's current position
							if(pieces[i][j] instanceof King){
								x=i;
								y=j;
								j=8;
								i=8;
							}
						}
					}
				}
			}

			if(x==-1 && y==-1)
				return false;

			targetSquareX=x;
			targetSquareY=y;
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					if(pieces[i][j]!=null){
						selectedSquareX=i;
						selectedSquareY=j;
						if(pieces[i][j].isBlack){
							if(pieces[i][j].canCapture(x-i, y-j)){
								return true;
							}
						}
					}
				}
			}

			return false;

		}

		else if(count%2==1){
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					if(pieces[i][j]!=null){
						if(pieces[i][j].isBlack){
							//finds king's current posisiton
							if(pieces[i][j] instanceof King){
								x=i;
								y=j;
								j=8;
								i=8;
							}
						}
					}
				}
			}

			if(x==-1 && y==-1)
				return false;

			targetSquareX=x;
			targetSquareY=y;
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					if(pieces[i][j]!=null){
						if(!pieces[i][j].isBlack){
							selectedSquareX=i;
							selectedSquareY=j;
							if(pieces[i][j].canCapture(x-i, y-j)){
								return true;
							}
						}
					}
				}
			}
			return false;
		}

		return false;
	}

	/**
	 * 
	 * @return: Whether the game is over or not
	 */
	public boolean isCheckmate(){

		if(isInCheck()){
			if(count%2==0){
				for(int i=0;i<8;i++){
					for(int j=0;j<8;j++){
						if(pieces[i][j]!=null){
							if(!pieces[i][j].isBlack){
								for(int r=0;r<8;r++){
									for(int k=0;k<8;k++){
										selectedSquareX=i;
										selectedSquareY=j;
										targetSquareX=r;
										targetSquareY=k;
										if(pieces[r][k]==null){

											if(pieces[i][j].canMove(r-i, k-j)){

												Piece temp=pieces[r][k];
												pieces[r][k]=pieces[i][j];
												pieces[i][j]= null;

												if(!isInCheck()){
													pieces[i][j]=pieces[r][k];
													pieces[r][k]=temp;
													return false;
												}
												pieces[i][j]=pieces[r][k];
												pieces[r][k]=temp;


											}
										}

										else if(pieces[r][k].isBlack){
											if(pieces[i][j].canCapture(r-i, k-j)){
												Piece temp=pieces[r][k];
												pieces[r][k]=pieces[i][j];
												pieces[i][j]=null;

												if(!isInCheck()){
													pieces[i][j]=pieces[r][k];
													pieces[r][k]=temp;
													return false;
												}
												pieces[i][j]=pieces[r][k];
												pieces[r][k]=temp;

											}
										}
									}
								}
							}
						}
					}
				}
				return true;
			}

			else{
				for(int i=0;i<8;i++){
					for(int j=0;j<8;j++){
						if(pieces[i][j]!=null){
							if(pieces[i][j].isBlack){
								for(int r=0;r<8;r++){
									for(int k=0;k<8;k++){
										selectedSquareX=i;
										selectedSquareY=j;
										targetSquareX=r;
										targetSquareY=k;
										if(pieces[r][k]==null){
											if(pieces[i][j].canMove(r-i, k-j)){

												Piece temp=pieces[r][k];
												pieces[r][k]=pieces[i][j];
												pieces[i][j]=null;
												if(!isInCheck()){
													pieces[i][j]=pieces[r][k];
													pieces[r][k]=temp;
													return false;
												}
												pieces[i][j]=pieces[r][k];
												pieces[r][k]=temp;

											}
										}

										else if(!pieces[r][k].isBlack){
											if(pieces[i][j].canCapture(r-i, k-j)){
												Piece temp=pieces[r][k];
												pieces[r][k]=pieces[i][j];
												pieces[i][j]=null;

												if(!isInCheck()){
													pieces[i][j]=pieces[r][k];
													pieces[r][k]=temp;
													return false;
												}
												pieces[i][j]=pieces[r][k];
												pieces[r][k]=temp;

											}
										}


									}
								}
							}
						}
					}
				}

			}
			return true;
		}
		return false;

	}


	/**
	 * Saves the board's current configuration
	 * @param fileName : The destination path of the file
	 * @throws FileNotFoundException
	 */
	public void save(String fileName) {
		PrintStream output;
		try {
			output = new PrintStream(new File(fileName));
			if(count%2==0)
				output.println("white");
			else
				output.println("black");

			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++){
					if(pieces[i][j]!=null){
						String pos=(char)(i+65) +""+(char)(56-j);
						output.println(at(pos)+"-"+pos);

					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Loads board configuration from a file
	 * @param fileName : Path of a gile
	 * @return : Current board configuration
	 * @throws FileNotFoundException
	 */
	public static ChessFrame load(String fileName) {
		ChessFrame cf=new ChessFrame();
		Scanner input;
		try {
			input = new Scanner(new File(fileName));
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++)
					pieces[i][j]=null;
			}

			if(input.nextLine().toLowerCase().equals("white"))
				cf.count=0;

			else
				cf.count=1;

			if(input!=null){
				while(input.hasNextLine()){
					String line=input.nextLine();

					String first=line.substring(0,5);
					String second=line.substring(6,9);
					String third=line.charAt(line.length()-2)+""+line.charAt(line.length()-1);
					third=third.toUpperCase();
					second=second.toLowerCase();
					int x=third.charAt(0)-65;
					int y=8-third.charAt(1)+48;

					if(second.startsWith("ro"))
						pieces[x][y]=new Rook(first.equals("black"));

					else if(second.startsWith("kn"))
						pieces[x][y]=new Knight(first.equals("black"));

					else if(second.startsWith("bi"))
						pieces[x][y]=new Bishop(first.equals("black"));

					else if(second.startsWith("qu"))
						pieces[x][y]=new Queen(first.equals("black"));

					else if(second.startsWith("ki"))
						pieces[x][y]=new King(first.equals("black"));

					else if(second.startsWith("pa"))
						pieces[x][y]=new Pawn(first.equals("black"));

				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return cf;
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		//print the board 's lines to show squares
		for(int i = 0; i<8; i++)
		{
			for(int j=0;j<8;j++){

				if((i+j)%2==0)
					g.setColor(Color.ORANGE);

				else{
					g.setColor(Color.LIGHT_GRAY);
				}

				g.fillRect(BOARD_MARGIN+j*SQUARE_WIDTH, 
						BOARD_MARGIN+(i)*SQUARE_WIDTH, 
						SQUARE_WIDTH, 
						SQUARE_WIDTH);



			}

		}
		//print the pieces
		for(int i = 0; i<8; i++)
		{
			for(int j = 0; j<8; j++)
			{
				if(pieces[i][j] != null)
				{
					pieces[i][j].drawYourself(g, i*SQUARE_WIDTH+BOARD_MARGIN, 
							j*SQUARE_WIDTH+BOARD_MARGIN, SQUARE_WIDTH);
				}
			}
		}

		if(isCheckmate()){
			if(count%2==0)
				JOptionPane.showMessageDialog(null, "GAME OVER,BLACK WON");

			else
				JOptionPane.showMessageDialog(null, "GAME OVER,WHITE WON");

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("Clicked");

	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("Pressed");
		//calculate which square is selected 
		selectedSquareX = (e.getX()-BOARD_MARGIN)/SQUARE_WIDTH;
		selectedSquareY = (e.getY()-BOARD_MARGIN)/SQUARE_WIDTH;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		//calculate which square is targeted
		targetSquareX = (e.getX()-BOARD_MARGIN)/SQUARE_WIDTH;
		targetSquareY = (e.getY()-BOARD_MARGIN)/SQUARE_WIDTH;

		String from ,to;
		from=(char)(selectedSquareX+65)+"";
		from+=8-selectedSquareY;
		to=(char)(targetSquareX+65)+"";
		to+=8-targetSquareY;

		if(!move(from,to)){
			if(selectedSquareX >= 0 && selectedSquareY >= 0 &&
					selectedSquareX < 8 && selectedSquareY < 8 &&
					targetSquareX >= 0 && targetSquareY >= 0 &&
					targetSquareX < 8 && targetSquareY < 8)
			{
				if((pieces[selectedSquareX][selectedSquareY]!=null && pieces[targetSquareX][targetSquareY]!=null)){
					if((((pieces[selectedSquareX][selectedSquareY].isBlack && pieces[targetSquareX][targetSquareY].isBlack)&& count%2==1) ||
							(count%2==0 && (!pieces[selectedSquareX][selectedSquareY].isBlack && !pieces[targetSquareX][targetSquareY].isBlack)))&&(pieces[selectedSquareX][selectedSquareY] instanceof King &&
									pieces[targetSquareX][targetSquareY] instanceof Rook)){
						if(targetSquareX==0)
							castling(false);

						if(targetSquareX==7)
							castling(true);
					}
				}
			}


		}

		repaint();


	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("Entered");

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("Exited");

	}

}
