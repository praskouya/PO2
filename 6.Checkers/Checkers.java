
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Checkers extends JPanel {
    public static void main(String[] args) {
        JFrame window = new JFrame("Checkers");
        Checkers content = new Checkers();
        window.setContentPane(content);
        window.pack();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((screensize.width - window.getWidth()) / 2,
                (screensize.height - window.getHeight()) / 2);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
    }

    private JButton newGameButton;
    private JButton resignButton;
    private JLabel message;

    public Checkers() {
        setLayout(null);
        setPreferredSize(new Dimension(700, 500));
        setBackground(new Color(255, 105, 180)); // hot pink background

        Board board = new Board();

        add(board);
        add(newGameButton);
        add(resignButton);
        add(message);

        board.setBounds(20, 20, 328, 328);
        newGameButton.setBounds(420, 120, 240, 60);
        resignButton.setBounds(420, 220, 240, 60);
        message.setBounds(0, 400, 700, 50);
    }

    private static class CheckersMove {
        int fromRow, fromCol;
        int toRow, toCol;

        CheckersMove(int r1, int c1, int r2, int c2) {
            fromRow = r1;
            fromCol = c1;
            toRow = r2;
            toCol = c2;
        }

        boolean isJump() {
            return (fromRow - toRow == 2 || fromRow - toRow == -2);
        }
    }

    private class Board extends JPanel implements ActionListener, MouseListener {

        CheckersData board;
        boolean gameInProgress;
        int currentPlayer;
        int selectedRow, selectedCol;
        CheckersMove[] legalMoves;

        Board() {
            setBackground(new Color(255, 105, 180)); // hot pink background
            addMouseListener(this);
            resignButton = new JButton("Resign");
            resignButton.addActionListener(this);
            newGameButton = new JButton("New Game");
            newGameButton.addActionListener(this);
            message = new JLabel("", JLabel.CENTER);
            message.setFont(new Font("Serif", Font.BOLD, 24));
            message.setForeground(Color.black); // hot pink
            board = new CheckersData();
            doNewGame();
        }

        public void actionPerformed(ActionEvent evt) {
            Object src = evt.getSource();
            if (src == newGameButton)
                doNewGame();
            else if (src == resignButton)
                doResign();
        }

        void doNewGame() {
            if (gameInProgress) {
                message.setText("Finish the current game first!");
                return;
            }
            board.setUpGame();
            currentPlayer = CheckersData.PINK;
            legalMoves = board.getLegalMoves(CheckersData.PINK);
            selectedRow = -1;
            message.setText("Pink: Make your move.");
            gameInProgress = true;
            newGameButton.setEnabled(false);
            resignButton.setEnabled(true);
            repaint();
        }

        void doResign() {
            if (!gameInProgress) {
                message.setText("No game in progress!");
                return;
            }
            if (currentPlayer == CheckersData.PINK)
                gameOver("Pink slaayed the competition");
            else
                gameOver("Yellow ate and left no crumbs.");
        }

        void gameOver(String str) {
            message.setText(str);
            newGameButton.setEnabled(true);
            resignButton.setEnabled(false);
            gameInProgress = false;
        }

        void doClickSquare(int row, int col) {
            for (CheckersMove legalMove : legalMoves)
                if (legalMove.fromRow == row && legalMove.fromCol == col) {
                    selectedRow = row;
                    selectedCol = col;
                    if (currentPlayer == CheckersData.PINK)
                        message.setText("Pink: Make your move.");
                    else
                        message.setText("Yellow: Make your move.");
                    repaint();
                    return;
                }

            if (selectedRow < 0) {
                message.setText("Click the piece you want to move.");
                return;
            }

            for (CheckersMove legalMove : legalMoves)
                if (legalMove.fromRow == selectedRow && legalMove.fromCol == selectedCol
                        && legalMove.toRow == row && legalMove.toCol == col) {
                    doMakeMove(legalMove);
                    return;
                }

            message.setText("Click the square you want to move to.");
        }

        void doMakeMove(CheckersMove move) {
            board.makeMove(move);
            if (move.isJump()) {
                legalMoves = board.getLegalJumpsFrom(currentPlayer, move.toRow, move.toCol);
                if (legalMoves != null) {
                    if (currentPlayer == CheckersData.PINK)
                        message.setText("Pink: You must continue jumping.");
                    else
                        message.setText("Yellow: You must continue jumping.");
                    selectedRow = move.toRow;
                    selectedCol = move.toCol;
                    repaint();
                    return;
                }
            }

            if (currentPlayer == CheckersData.PINK) {
                currentPlayer = CheckersData.YELLOW;
                legalMoves = board.getLegalMoves(currentPlayer);
                if (legalMoves == null)
                    gameOver("Yellow has no moves. Pink wins.");
                else if (legalMoves[0].isJump())
                    message.setText("Yellow: You must jump.");
                else
                    message.setText("Yellow: Make your move.");
            } else {
                currentPlayer = CheckersData.PINK;
                legalMoves = board.getLegalMoves(currentPlayer);
                if (legalMoves == null)
                    gameOver("Pink has no moves. Yellow wins.");
                else if (legalMoves[0].isJump())
                    message.setText("Pink: You must jump.");
                else
                    message.setText("Pink: Make your move.");
            }

            selectedRow = -1;
            if (legalMoves != null) {
                boolean sameStartSquare = true;
                for (int i = 1; i < legalMoves.length; i++)
                    if (legalMoves[i].fromRow != legalMoves[0].fromRow
                            || legalMoves[i].fromCol != legalMoves[0].fromCol) {
                        sameStartSquare = false;
                        break;
                    }
                if (sameStartSquare) {
                    selectedRow = legalMoves[0].fromRow;
                    selectedCol = legalMoves[0].fromCol;
                }
            }

            repaint();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.black);
            g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
            g.drawRect(1, 1, getSize().width - 3, getSize().height - 3);

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (row % 2 == col % 2)
                        g.setColor(new Color(255, 182, 193)); // light pink
                    else
                        g.setColor(new Color(255, 255, 224)); // creamy white
                    g.fillRect(2 + col * 40, 2 + row * 40, 40, 40);
                    switch (board.pieceAt(row, col)) {
                        case CheckersData.PINK:
                            g.setColor(new Color(255, 105, 180)); // hot pink
                            g.fillOval(4 + col * 40, 4 + row * 40, 35, 35);
                            break;
                        case CheckersData.YELLOW:
                            g.setColor(Color.YELLOW);
                            g.fillOval(4 + col * 40, 4 + row * 40, 35, 35);
                            break;
                        case CheckersData.PINK_QUEEN:
                            g.setColor(new Color(255, 105, 180)); // hot pink
                            g.fillOval(4 + col * 40, 4 + row * 40, 35, 35);
                            g.setColor(Color.BLACK);
                            g.setFont(new Font("Serif", Font.BOLD, 20)); // Set font size for heart
                            g.drawString("\u2764", 14 + col * 40, 30 + row * 40); // Draw a bigger heart for queen
                            break;
                        case CheckersData.YELLOW_QUEEN:
                            g.setColor(Color.YELLOW);
                            g.fillOval(4 + col * 40, 4 + row * 40, 35, 35);
                            g.setColor(Color.BLACK);
                            g.setFont(new Font("Serif", Font.BOLD, 20)); // Set font size for heart
                            g.drawString("\u2764", 14 + col * 40, 30 + row * 40); // Draw a bigger heart for queen
                            break;
                    }
                }
            }

            if (gameInProgress) {
                g.setColor(Color.cyan);
                for (CheckersMove legalMove : legalMoves) {
                    g.drawRect(2 + legalMove.fromCol * 40, 2 + legalMove.fromRow * 40, 39, 39);
                    g.drawRect(3 + legalMove.fromCol * 40, 3 + legalMove.fromRow * 40, 37, 37);
                }
                if (selectedRow >= 0) {
                    g.setColor(Color.white);
                    g.drawRect(2 + selectedCol * 40, 2 + selectedRow * 40, 39, 39);
                    g.drawRect(3 + selectedCol * 40, 3 + selectedRow * 40, 37, 37);
                    g.setColor(Color.green);
                    for (CheckersMove legalMove : legalMoves) {
                        if (legalMove.fromCol == selectedCol && legalMove.fromRow == selectedRow) {
                            g.drawRect(2 + legalMove.toCol * 40, 2 + legalMove.toRow * 40, 39, 39);
                            g.drawRect(3 + legalMove.toCol * 40, 3 + legalMove.toRow * 40, 37, 37);
                        }
                    }
                }
            }

        }

        public void mousePressed(MouseEvent evt) {
            if (!gameInProgress)
                message.setText("Click \"New Game\" to start.");
            else {
                int col = (evt.getX() - 2) / 40;
                int row = (evt.getY() - 2) / 40;
                if (col >= 0 && col < 8 && row >= 0 && row < 8)
                    doClickSquare(row, col);
            }
        }

        public void mouseReleased(MouseEvent evt) {
        }

        public void mouseClicked(MouseEvent evt) {
        }

        public void mouseEntered(MouseEvent evt) {
        }

        public void mouseExited(MouseEvent evt) {
        }

    }

    private static class CheckersData {

        static final int EMPTY = 0, PINK = 1, PINK_QUEEN = 2, YELLOW = 3, YELLOW_QUEEN = 4;
        int[][] board;

        CheckersData() {
            board = new int[8][8];
            setUpGame();
        }

        void setUpGame() {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (row % 2 == col % 2) {
                        if (row < 3)
                            board[row][col] = YELLOW;
                        else if (row > 4)
                            board[row][col] = PINK;
                        else
                            board[row][col] = EMPTY;
                    } else {
                        board[row][col] = EMPTY;
                    }
                }
            }
        }

        int pieceAt(int row, int col) {
            return board[row][col];
        }

        void makeMove(CheckersMove move) {
            makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
        }

        void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
            board[toRow][toCol] = board[fromRow][fromCol];
            board[fromRow][fromCol] = EMPTY;
            if (fromRow - toRow == 2 || fromRow - toRow == -2) {
                int jumpRow = (fromRow + toRow) / 2;
                int jumpCol = (fromCol + toCol) / 2;
                board[jumpRow][jumpCol] = EMPTY;
            }
            if (toRow == 0 && board[toRow][toCol] == PINK)
                board[toRow][toCol] = PINK_QUEEN;
            if (toRow == 7 && board[toRow][toCol] == YELLOW)
                board[toRow][toCol] = YELLOW_QUEEN;
        }

        CheckersMove[] getLegalMoves(int player) {
            if (player != PINK && player != YELLOW)
                return null;

            int playerQueen;
            if (player == PINK)
                playerQueen = PINK_QUEEN;
            else
                playerQueen = YELLOW_QUEEN;

            ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (board[row][col] == player || board[row][col] == playerQueen) {
                        if (canJump(player, row, col, row + 1, col + 1, row + 2, col + 2))
                            moves.add(new CheckersMove(row, col, row + 2, col + 2));
                        if (canJump(player, row, col, row - 1, col + 1, row - 2, col + 2))
                            moves.add(new CheckersMove(row, col, row - 2, col + 2));
                        if (canJump(player, row, col, row + 1, col - 1, row + 2, col - 2))
                            moves.add(new CheckersMove(row, col, row + 2, col - 2));
                        if (canJump(player, row, col, row - 1, col - 1, row - 2, col - 2))
                            moves.add(new CheckersMove(row, col, row - 2, col - 2));
                    }
                }
            }

            if (moves.size() == 0) {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (board[row][col] == player || board[row][col] == playerQueen) {
                            if (canMove(player, row, col, row + 1, col + 1))
                                moves.add(new CheckersMove(row, col, row + 1, col + 1));
                            if (canMove(player, row, col, row - 1, col + 1))
                                moves.add(new CheckersMove(row, col, row - 1, col + 1));
                            if (canMove(player, row, col, row + 1, col - 1))
                                moves.add(new CheckersMove(row, col, row + 1, col - 1));
                            if (canMove(player, row, col, row - 1, col - 1))
                                moves.add(new CheckersMove(row, col, row - 1, col - 1));
                        }
                    }
                }
            }

            if (moves.size() == 0)
                return null;
            else {
                CheckersMove[] moveArray = new CheckersMove[moves.size()];
                moves.toArray(moveArray);
                return moveArray;
            }

        }

        CheckersMove[] getLegalJumpsFrom(int player, int row, int col) {
            if (player != PINK && player != YELLOW)
                return null;
            int playerQueen;
            if (player == PINK)
                playerQueen = PINK_QUEEN;
            else
                playerQueen = YELLOW_QUEEN;
            ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();
            if (board[row][col] == player || board[row][col] == playerQueen) {
                if (canJump(player, row, col, row + 1, col + 1, row + 2, col + 2))
                    moves.add(new CheckersMove(row, col, row + 2, col + 2));
                if (canJump(player, row, col, row - 1, col + 1, row - 2, col + 2))
                    moves.add(new CheckersMove(row, col, row - 2, col + 2));
                if (canJump(player, row, col, row + 1, col - 1, row + 2, col - 2))
                    moves.add(new CheckersMove(row, col, row + 2, col - 2));
                if (canJump(player, row, col, row - 1, col - 1, row - 2, col - 2))
                    moves.add(new CheckersMove(row, col, row - 2, col - 2));
            }
            if (moves.size() == 0)
                return null;
            else {
                CheckersMove[] moveArray = new CheckersMove[moves.size()];
                moves.toArray(moveArray);
                return moveArray;
            }
        }

        private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {
            if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
                return false;
            if (board[r3][c3] != EMPTY)
                return false;
            if (player == PINK) {
                if (board[r1][c1] == PINK && r3 > r1)
                    return false;
                if (board[r2][c2] != YELLOW && board[r2][c2] != YELLOW_QUEEN)
                    return false;
                return true;
            } else {
                if (board[r1][c1] == YELLOW && r3 < r1)
                    return false;
                if (board[r2][c2] != PINK && board[r2][c2] != PINK_QUEEN)
                    return false;
                return true;
            }
        }

        private boolean canMove(int player, int r1, int c1, int r2, int c2) {
            if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
                return false;
            if (board[r2][c2] != EMPTY)
                return false;
            if (player == PINK) {
                if (board[r1][c1] == PINK && r2 > r1)
                    return false;
                return true;
            } else {
                if (board[r1][c1] == YELLOW && r2 < r1)
                    return false;
                return true;
            }
        }

    }

}
