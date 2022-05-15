import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class sudoku implements ActionListener
{
	char[][] check = new char[9][9];
	JTextField[] cells = new JTextField[81];
	JButton solve = new JButton();
	JButton checker = new JButton();
	sudoku()
	{
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(1, 1));
		
		JPanel box = new JPanel(new GridLayout(9, 9));
		box.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		for (int i = 0; i < 81; i++) 
		{
			cells[i] = new JTextField();
			cells[i].setBackground(Color.cyan);
			cells[i].setHorizontalAlignment(JTextField.CENTER);
			cells[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, false));
			cells[i].setFont(new Font("Bahnschrift",Font.BOLD,30));
			box.add(cells[i]);
		}
		
		board.add(box);
		solve.setPreferredSize(new Dimension(15, 40));
		solve.setText("Ai Solve");
		solve.addActionListener(this);
		solve.setFont(new Font("Bahnschrift",Font.BOLD,30));
		checker.setPreferredSize(new Dimension(15, 40));
		checker.setText("Check");
		checker.addActionListener(this);
		checker.setFont(new Font("Bahnschrift",Font.BOLD,30));
	    
		
		JFrame frame = new JFrame();

		Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(board);
		cp.add(checker,BorderLayout.NORTH);
        cp.add(solve, BorderLayout.SOUTH);

        frame.setTitle("Sudoku by Aryaman");
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

		setBoard();
	}

	void setBoard()
	{
		//not the best randomization I can do, needs to be optimized
		check[0][0] =  Character.forDigit(((int)(Math.random()*9+1)), 10);
		check[(int)(Math.random()*9)][(int)(Math.random()*9)] = Character.forDigit(((int)(Math.random()*9 + 1)), 10);
		for(int i=0; i<9; i++)
			for(int j=0; j<9; j++)
				if(!Character.isDigit(check[i][j]))
					check[i][j] = '.';
		solveBoard(check);
		// there was an error with the last element, so removing the error:
		check[8][8] = '.';
		for(int i=1; i<=9; i++)
		{
			check[8][8] = Character.forDigit(i, 10);
			if(isValid(check))
				break;
		}
		
		//printing random 36 elements (easy difficulty)
		for(int i=0; i<36;)
		{
			int a = (int)(Math.random()*9);
			int b = (int)(Math.random()*9);
			if(cells[9*a+b].getText().isEmpty())
			{
				cells[9*a + b].setForeground(Color.RED);
				cells[9*a + b].setText(String.valueOf(check[a][b]));
				cells[9*a + b].setEditable(false);
				i++;
			}
			else	
				continue;
		}
	}

	boolean solveBoard(char[][] board) 
	{
		for (int r = 0; r < 9; r++) {
		  for (int c = 0; c < 9; c++) 
		  {
			if (board[r][c] == '.') 
			{
			  for (int numberToTry = 1; numberToTry <= 9; numberToTry++)
			  {
				if (isValid(board)) 
				{
				  board[r][c] = Character.forDigit(numberToTry, 10);
				  if (solveBoard(board)) 
				  {
					return true;
				  }
				  else 
				  {
					board[r][c] = '.';
				  }
				}
			  }
			  return false;
			}
		  }
		}
		return true;
	  }

	void checkBoard()
	{
		for(int i=0; i<9; i++)
			for(int j=0; j<9; j++)
				check[i][j] = cells[9*i + j].getText().charAt(0);
		if(isValid(check))
			gameWon();
		else
			return;
	}

	void gameWon()
	{
		for(int i=0; i<81; i++)
		{
			cells[i].setBackground(Color.green);
			cells[i].setEditable(false);
		}
	}
	boolean isValid(char [][]board)
    {
        for(int i=0;i<9;i++) // traversing the rows
        {
            String r="";

            for(int j= 0;j<9;j++)  //traversing the columns
            {
                String col="";
                for(int k=0;k<r.length();k++)
                    if(r.charAt(k)!='.' && r.charAt(k)==board[i][j])
                        return false;
                r = r.concat(String.valueOf(board[i][j]));

                for(int l=0;l<9;l++) // traverses rows to check for repeat
                {
                    for(int k=0;k<col.length();k++)
                        if(col.charAt(k)!='.' && col.charAt(k)==board[l][j])
                            return false;
                    col = col.concat(String.valueOf(board[l][j]));
                }

            }
        }
        int x=0;int y=0;
        String box="";
        while(x!=9)
        {
            while(y!=9)
            {
                for(int i=x;i<=x+2;i++) //rows of box
                {
                    for(int j=y;j<=y+2;j++) //columns of box
                    {
                        for(int m=0;m<box.length();m++)
                            if(box.charAt(m)!='.' && box.charAt(m)==board[i][j])
                                return false;
                        box= box.concat(String.valueOf(board[i][j]));
                    }
                }
                y+=3;
                box="";
            }
            box="";
            x+=3;
            y=0;
        }
        return true;
    }

	public static void main(String[] args) 
	{
        sudoku game1 = new sudoku();
    }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==solve)
		{
			for(int i=0; i<9; i++)
				for(int j=0; j<9; j++)
				{
					cells[9*i + j].setForeground(Color.RED);
					cells[9*i + j].setText(String.valueOf(check[i][j]));
					cells[9*i + j].setEditable(false);
				}
		}
						
		else if(e.getSource()==checker)
			checkBoard();
	}
}
