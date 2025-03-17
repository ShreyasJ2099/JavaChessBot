// Imports
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

// Classes
interface Pieces {
    int getX();
    int getY();
    String getName();
    String getColor();
    int getScore();
    long getCode();
    ArrayList<int[][]> valids();
    ArrayList<int[][]> attacked();
    void setPos(int yv, int xv);
}

class NoPiece implements Pieces {
    String name = "_";
    int y, x;
    String color = null;

    NoPiece(int yv, int xv) {
        this.y = yv;
        this.x = xv;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

    public int getScore() {
        return 0;
    }

    public long getCode() {
        long bit = 0000;
        return bit;
    }

    public void setPos(int yv, int xv) {
        this.y = yv;
        this.x = xv;
    }

    public ArrayList<int[][]> valids() {
        ArrayList<int[][]> valids = new ArrayList<>();
        return valids;
    }

    public ArrayList<int[][]> attacked() {
        ArrayList<int[][]> valids = new ArrayList<>();
        return valids;
    }
}

class Pawn implements Pieces {
    int y, x;
    String color, name;

    Pawn(int yv, int xv, String colorv) {
        this.y = yv;
        this.x = xv;
        this.color = colorv;

        this.name = (color == "W") ? "P" : "p";
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

    public int getScore() {
        return 100;
    }

    public long getCode() {
        long colorBit = (this.color == "W") ? 1000 : 0000;
        long pieceBit = 001;
        long bit = colorBit | pieceBit;
        return bit;
    }

    public void setPos(int yv, int xv) {
        this.y = yv;
        this.x = xv;
    }

    public ArrayList<int[][]> valids() {
        ArrayList<int[][]> valids = new ArrayList<>();
        int[] start = {this.y, this.x};

        // Single step move
        if ((this.getColor() == "B") && (this.y + 1 <= 7) && (Board.board[this.y + 1][this.x] instanceof NoPiece)) {
            int[][] move = {start, {this.y + 1, this.x}};
            valids.add(move);
        } else if ((this.getColor() == "W") && (this.y - 1 >= 0) && (Board.board[this.y - 1][this.x] instanceof NoPiece)) {
            int[][] move = {start, {this.y - 1, this.x}};
            valids.add(move);
        }

        // Double step move
        if ((this.getColor() == "B" && this.y == 1) && (Board.board[this.y + 1][this.x] instanceof NoPiece) && (Board.board[this.y + 2][this.x] instanceof NoPiece)) {
            int[][] move = {start, {this.y + 2, this.x}};
            valids.add(move);
        } else if ((this.getColor() == "W" && this.y == 6) && (Board.board[this.y - 1][this.x] instanceof NoPiece) && (Board.board[this.y - 2][this.x] instanceof NoPiece)) {
            int[][] move = {start, {this.y - 2, this.x}};
            valids.add(move);
        }

        // Diagonal capture
        if (this.getColor() == "B") {
            if (this.x + 1 <= 7 && this.y + 1 <= 7 && (Board.board[this.y + 1][this.x + 1].getColor() == "W")) {
                int[][] move = {start, {this.y + 1, this.x + 1}};
                valids.add(move);
            } 
            if (this.x - 1 >= 0 && this.y + 1 <= 7 && (Board.board[this.y + 1][this.x - 1].getColor() == "W")) {
                int[][] move = {start, {this.y + 1, this.x - 1}};
                valids.add(move);
            }
        } else if (this.getColor() == "W") {
            if (this.x + 1 <= 7 && this.y - 1 >= 0 && (Board.board[this.y - 1][this.x + 1].getColor() == "B")) {
                int[][] move = {start, {this.y - 1, this.x + 1}};
                valids.add(move);
            } 
            if (this.x - 1 >= 0 && this.y - 1 >= 0 && (Board.board[this.y - 1][this.x - 1].getColor() == "B")) {
                int[][] move = {start, {this.y - 1, this.x - 1}};
                valids.add(move);
            }
        }

        // remove moves if piece is pinned
        ArrayList<int[][]> newValids = new ArrayList<>();
        for (int[][] move : valids) {
            if (!(Board.tryMove(this.color, move[0][1], move[0][0], move[1][1], move[1][0]))) {
                newValids.add(move);
            }
        }

        return newValids;
    }

    public ArrayList<int[][]> attacked() {
        ArrayList<int[][]> valids = new ArrayList<>();
        int[] start = {this.y, this.x};

        // Single step move
        if ((this.getColor() == "B") && (this.y + 1 <= 7) && (Board.board[this.y + 1][this.x] instanceof NoPiece)) {
            int[][] move = {start, {this.y + 1, this.x}};
            valids.add(move);
        } else if ((this.getColor() == "W") && (this.y - 1 >= 0) && (Board.board[this.y - 1][this.x] instanceof NoPiece)) {
            int[][] move = {start, {this.y - 1, this.x}};
            valids.add(move);
        }

        // Double step move
        if ((this.getColor() == "B" && this.y == 1) && (Board.board[this.y + 1][this.x] instanceof NoPiece) && (Board.board[this.y + 2][this.x] instanceof NoPiece)) {
            int[][] move = {start, {this.y + 2, this.x}};
            valids.add(move);
        } else if ((this.getColor() == "W" && this.y == 6) && (Board.board[this.y - 1][this.x] instanceof NoPiece) && (Board.board[this.y - 2][this.x] instanceof NoPiece)) {
            int[][] move = {start, {this.y - 2, this.x}};
            valids.add(move);
        }

        // Diagonal capture
        if (this.getColor() == "B") {
            if (this.x + 1 <= 7 && this.y + 1 <= 7) {
                int[][] move = {start, {this.y + 1, this.x + 1}};
                valids.add(move);
            } else if (this.x - 1 >= 0 && this.y + 1 <= 7) {
                int[][] move = {start, {this.y + 1, this.x - 1}};
                valids.add(move);
            }
        } else if (this.getColor() == "W") {
            if (this.x + 1 <= 7 && this.y - 1 >= 0) {
                int[][] move = {start, {this.y - 1, this.x + 1}};
                valids.add(move);
            } else if (this.x - 1 >= 0 && this.y - 1 >= 0) {
                int[][] move = {start, {this.y - 1, this.x - 1}};
                valids.add(move);
            }
        }

        return valids;
    }
}

class Rook implements Pieces {
    int y, x;
    String color, name;

    Rook(int yv, int xv, String colorv) {
        this.y = yv;
        this.x = xv;
        this.color = colorv;

        this.name = (color == "W") ? "R" : "r";
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

    public int getScore() {
        return 500;
    }

    public long getCode() {
        long colorBit = (this.color == "W") ? 1000 : 0000;
        long pieceBit = 010;
        long bit = colorBit | pieceBit;
        return bit;
    }

    public void setPos(int yv, int xv) {
        this.y = yv;
        this.x = xv;
    }

    public ArrayList<int[][]> valids() {
        ArrayList<int[][]> valids = new ArrayList<>();
        int[] start = {this.y, this.x};

        // Upwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.y - i >= 0) && (Board.board[this.y - i][this.x].getColor() != this.color)) {
                int[][] move = {start, {this.y - i, this.x}};
                valids.add(move);
                if (!(Board.board[this.y - i][this.x] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Downwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.y + i <= 7) && (Board.board[this.y + i][this.x].getColor() != this.color)) {
                int[][] move = {start, {this.y + i, this.x}};
                valids.add(move);
                if (!(Board.board[this.y + i][this.x] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Rightwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.x + i <= 7) && (Board.board[this.y][this.x + i].getColor() != this.color)) {
                int[][] move = {start, {this.y, this.x + i}};
                valids.add(move);
                if (!(Board.board[this.y][this.x + i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Leftwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.x - i >= 0) && (Board.board[this.y][this.x - i].getColor() != this.color)) {
                int[][] move = {start, {this.y, this.x - i}};
                valids.add(move);
                if (!(Board.board[this.y][this.x - i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // remove moves if piece is pinned
        ArrayList<int[][]> newValids = new ArrayList<>();
        for (int[][] move : valids) {
            if (!(Board.tryMove(this.color, move[0][1], move[0][0], move[1][1], move[1][0]))) {
                newValids.add(move);
            }
        }

        return newValids;
    }

    public ArrayList<int[][]> attacked() {
        ArrayList<int[][]> valids = new ArrayList<>();
        int[] start = {this.y, this.x};

        // Upwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.y - i >= 0)) {
                int[][] move = {start, {this.y - i, this.x}};
                valids.add(move);
                if (!(Board.board[this.y - i][this.x] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Downwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.y + i <= 7)) {
                int[][] move = {start, {this.y + i, this.x}};
                valids.add(move);
                if (!(Board.board[this.y + i][this.x] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Rightwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.x + i <= 7)) {
                int[][] move = {start, {this.y, this.x + i}};
                valids.add(move);
                if (!(Board.board[this.y][this.x + i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Leftwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.x - i >= 0)) {
                int[][] move = {start, {this.y, this.x - i}};
                valids.add(move);
                if (!(Board.board[this.y][this.x - i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        return valids;
    }
}

class Knight implements Pieces {
    int y, x;
    String color, name;

    Knight(int yv, int xv, String colorv) {
        this.y = yv;
        this.x = xv;
        this.color = colorv;

        this.name = (color == "W") ? "N" : "n";
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

    public int getScore() {
        return 300;
    }

    public long getCode() {
        long colorBit = (this.color == "W") ? 1000 : 0000;
        long pieceBit = 011;
        long bit = colorBit | pieceBit;
        return bit;
    }

    public void setPos(int yv, int xv) {
        this.y = yv;
        this.x = xv;
    }

    public ArrayList<int[][]> valids() {
        ArrayList<int[][]> valids = new ArrayList<>();
        int[] start = {this.y, this.x};
        int[][] possibles = {{-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}};

        for (int[] d : possibles) {
            int dx = d[1];
            int dy = d[0];

            if ((this.y + dy >= 0 && this.y + dy <= 7 && this.x + dx >= 0 && this.x + dx <= 7) && (Board.board[this.y + dy][this.x + dx].getColor() != this.color)) {
                int[][] move = {start, {this.y + dy, this.x + dx}};
                valids.add(move);
            }
        }

        // remove moves if piece is pinned
        ArrayList<int[][]> newValids = new ArrayList<>();
        for (int[][] move : valids) {
            if (!(Board.tryMove(this.color, move[0][1], move[0][0], move[1][1], move[1][0]))) {
                newValids.add(move);
            }
        }

        return newValids;
    }

    public ArrayList<int[][]> attacked() {
        ArrayList<int[][]> valids = new ArrayList<>();
        int[] start = {this.y, this.x};
        int[][] possibles = {{-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}};

        for (int[] d : possibles) {
            int dx = d[1];
            int dy = d[0];

            if ((this.y + dy >= 0 && this.y + dy <= 7 && this.x + dx >= 0 && this.x + dx <= 7) && (Board.board[this.y + dy][this.x + dx].getColor() != this.color)) {
                int[][] move = {start, {this.y + dy, this.x + dx}};
                valids.add(move);
            }
        }

        return valids;
    }
}

class Bishop implements Pieces {
    int y, x;
    String color, name;

    Bishop(int yv, int xv, String colorv) {
        this.y = yv;
        this.x = xv;
        this.color = colorv;

        this.name = (color == "W") ? "B" : "b";
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

    public int getScore() {
        return 300;
    }

    public long getCode() {
        long colorBit = (this.color == "W") ? 1000 : 0000;
        long pieceBit = 100;
        long bit = colorBit | pieceBit;
        return bit;
    }

    public void setPos(int yv, int xv) {
        this.y = yv;
        this.x = xv;
    }

    public ArrayList<int[][]> valids() {
        ArrayList<int[][]> valids = new ArrayList<>();
        int[] start = {this.y, this.x};

        // Up-Right
        for (int i = 1; i <= 7; i++) {
            if ((this.x + i <= 7 && this.y - i >= 0) && Board.board[this.y - i][this.x + i].getColor() != this.color) {
                int[][] move = {start, {this.y - i, this.x + i}};
                valids.add(move);
                if (!(Board.board[this.y - i][this.x + i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Up-Left 
        for (int i = 1; i <= 7; i++) {
            if ((this.x - i >= 0 && this.y - i >= 0) && Board.board[this.y - i][this.x - i].getColor() != this.color) {
                int[][] move = {start, {this.y - i, this.x - i}};
                valids.add(move);
                if (!(Board.board[this.y - i][this.x - i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Down-Right
        for (int i = 1; i <= 7; i++) {
            if ((this.x + i <= 7 && this.y + i <= 7) && Board.board[this.y + i][this.x + i].getColor() != this.color) {
                int[][] move = {start, {this.y + i, this.x + i}};
                valids.add(move);
                if (!(Board.board[this.y + i][this.x + i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Down-Left
        for (int i = 1; i <= 7; i++) {
            if ((this.x - i >= 0 && this.y + i <= 7) && Board.board[this.y + i][this.x - i].getColor() != this.color) {
                int[][] move = {start, {this.y + i, this.x - i}};
                valids.add(move);
                if (!(Board.board[this.y + i][this.x - i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // remove moves if piece is pinned
        ArrayList<int[][]> newValids = new ArrayList<>();
        for (int[][] move : valids) {
            if (!(Board.tryMove(this.color, move[0][1], move[0][0], move[1][1], move[1][0]))) {
                newValids.add(move);
            }
        }

        return newValids;
    }

    public ArrayList<int[][]> attacked() {
        ArrayList<int[][]> valids = new ArrayList<>();
        int[] start = {this.y, this.x};

        // Up-Right
        for (int i = 1; i <= 7; i++) {
            if ((this.x + i <= 7 && this.y - i >= 0)) {
                int[][] move = {start, {this.y - i, this.x + i}};
                valids.add(move);
                if (!(Board.board[this.y - i][this.x + i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Up-Left 
        for (int i = 1; i <= 7; i++) {
            if ((this.x - i >= 0 && this.y - i >= 0)) {
                int[][] move = {start, {this.y - i, this.x - i}};
                valids.add(move);
                if (!(Board.board[this.y - i][this.x - i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Down-Right
        for (int i = 1; i <= 7; i++) {
            if ((this.x + i <= 7 && this.y + i <= 7)) {
                int[][] move = {start, {this.y + i, this.x + i}};
                valids.add(move);
                if (!(Board.board[this.y + i][this.x + i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Down-Left
        for (int i = 1; i <= 7; i++) {
            if ((this.x - i >= 0 && this.y + i <= 7)) {
                int[][] move = {start, {this.y + i, this.x - i}};
                valids.add(move);
                if (!(Board.board[this.y + i][this.x - i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        return valids;
    }
}

class Queen implements Pieces {
    int y, x;
    String color, name;

    Queen(int yv, int xv, String colorv) {
        this.y = yv;
        this.x = xv;
        this.color = colorv;

        this.name = (color == "W") ? "Q" : "q";
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

    public int getScore() {
        return 900;
    }

    public long getCode() {
        long colorBit = (this.color == "W") ? 1000 : 0000;
        long pieceBit = 101;
        long bit = colorBit | pieceBit;
        return bit;
    }

    public void setPos(int yv, int xv) {
        this.y = yv;
        this.x = xv;
    }

    public ArrayList<int[][]> valids() {
        ArrayList<int[][]> valids = new ArrayList<>();
        int[] start = {this.y, this.x};

        // Upwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.y - i >= 0) && (Board.board[this.y - i][this.x].getColor() != this.color)) {
                int[][] move = {start, {this.y - i, this.x}};
                valids.add(move);
                if (!(Board.board[this.y - i][this.x] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Downwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.y + i <= 7) && (Board.board[this.y + i][this.x].getColor() != this.color)) {
                int[][] move = {start, {this.y + i, this.x}};
                valids.add(move);
                if (!(Board.board[this.y + i][this.x] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Rightwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.x + i <= 7) && (Board.board[this.y][this.x + i].getColor() != this.color)) {
                int[][] move = {start, {this.y, this.x + i}};
                valids.add(move);
                if (!(Board.board[this.y][this.x + i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Leftwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.x - i >= 0) && (Board.board[this.y][this.x - i].getColor() != this.color)) {
                int[][] move = {start, {this.y, this.x - i}};
                valids.add(move);
                if (!(Board.board[this.y][this.x - i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Up-Right
        for (int i = 1; i <= 7; i++) {
            if ((this.x + i <= 7 && this.y - i >= 0) && Board.board[this.y - i][this.x + i].getColor() != this.color) {
                int[][] move = {start, {this.y - i, this.x + i}};
                valids.add(move);
                if (!(Board.board[this.y - i][this.x + i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Up-Left 
        for (int i = 1; i <= 7; i++) {
            if ((this.x - i >= 0 && this.y - i >= 0) && Board.board[this.y - i][this.x - i].getColor() != this.color) {
                int[][] move = {start, {this.y - i, this.x - i}};
                valids.add(move);
                if (!(Board.board[this.y - i][this.x - i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Down-Right
        for (int i = 1; i <= 7; i++) {
            if ((this.x + i <= 7 && this.y + i <= 7) && Board.board[this.y + i][this.x + i].getColor() != this.color) {
                int[][] move = {start, {this.y + i, this.x + i}};
                valids.add(move);
                if (!(Board.board[this.y + i][this.x + i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Down-Left
        for (int i = 1; i <= 7; i++) {
            if ((this.x - i >= 0 && this.y + i <= 7) && Board.board[this.y + i][this.x - i].getColor() != this.color) {
                int[][] move = {start, {this.y + i, this.x - i}};
                valids.add(move);
                if (!(Board.board[this.y + i][this.x - i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // remove moves if piece is pinned
        ArrayList<int[][]> newValids = new ArrayList<>();
        for (int[][] move : valids) {
            if (!(Board.tryMove(this.color, move[0][1], move[0][0], move[1][1], move[1][0]))) {
                newValids.add(move);
            }
        }

        return newValids;
    }

    public ArrayList<int[][]> attacked() {
        ArrayList<int[][]> valids = new ArrayList<>();
        int[] start = {this.y, this.x};

        // Upwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.y - i >= 0)) {
                int[][] move = {start, {this.y - i, this.x}};
                valids.add(move);
                if (!(Board.board[this.y - i][this.x] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Downwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.y + i <= 7)) {
                int[][] move = {start, {this.y + i, this.x}};
                valids.add(move);
                if (!(Board.board[this.y + i][this.x] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Rightwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.x + i <= 7)) {
                int[][] move = {start, {this.y, this.x + i}};
                valids.add(move);
                if (!(Board.board[this.y][this.x + i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Leftwards movement
        for (int i = 1; i <= 7; i++) {
            if ((this.x - i >= 0)) {
                int[][] move = {start, {this.y, this.x - i}};
                valids.add(move);
                if (!(Board.board[this.y][this.x - i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Up-Right
        for (int i = 1; i <= 7; i++) {
            if ((this.x + i <= 7 && this.y - i >= 0)) {
                int[][] move = {start, {this.y - i, this.x + i}};
                valids.add(move);
                if (!(Board.board[this.y - i][this.x + i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Up-Left 
        for (int i = 1; i <= 7; i++) {
            if ((this.x - i >= 0 && this.y - i >= 0)) {
                int[][] move = {start, {this.y - i, this.x - i}};
                valids.add(move);
                if (!(Board.board[this.y - i][this.x - i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Down-Right
        for (int i = 1; i <= 7; i++) {
            if ((this.x + i <= 7 && this.y + i <= 7)) {
                int[][] move = {start, {this.y + i, this.x + i}};
                valids.add(move);
                if (!(Board.board[this.y + i][this.x + i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        // Down-Left
        for (int i = 1; i <= 7; i++) {
            if ((this.x - i >= 0 && this.y + i <= 7)) {
                int[][] move = {start, {this.y + i, this.x - i}};
                valids.add(move);
                if (!(Board.board[this.y + i][this.x - i] instanceof NoPiece)) {
                    break;
                }
            } else {
                break;
            }
        }

        return valids;
    }
}

class King implements Pieces {
    int y, x;
    String color, name;

    King(int yv, int xv, String colorv) {
        this.y = yv;
        this.x = xv;
        this.color = colorv;

        this.name = (color == "W") ? "K" : "k";
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }

    public int getScore() {
        return 0;
    }

    public long getCode() {
        long colorBit = (this.color == "W") ? 1000 : 0000;
        long pieceBit = 110;
        long bit = colorBit | pieceBit;
        return bit;
    }

    public void setPos(int yv, int xv) {
        this.y = yv;
        this.x = xv;
    }

    public ArrayList<int[][]> valids() {
        ArrayList<int[][]> valids = new ArrayList<>();
        int[] start = {this.y, this.x};

        int[][] d = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int[] pos : d) {
            int dy = pos[0];
            int dx = pos[1];

            if ((this.y + dy >= 0 && this.y + dy <= 7 && this.x + dx >= 0 && this.x + dx <= 7) && (Board.board[this.y + dy][this.x + dx].getColor() != this.color)) {
                int[][] move = {start, {this.y + dy, this.x + dx}};
                valids.add(move);
            }      
        }

        // castling
        if (this.color == "W") {
            if ((this.y == 7 && this.x == 4) && (Board.board[7][7] instanceof Rook && Board.board[7][7].getColor() == "W") && (Board.board[7][6] instanceof NoPiece) && (Board.board[7][5] instanceof NoPiece)) {
                int[][] move = {start, {7, 6}};
                valids.add(move);
            }
            if ((this.y == 7 && this.x == 4) && (Board.board[7][0] instanceof Rook && Board.board[7][0].getColor() == "W") && (Board.board[7][1] instanceof NoPiece) && (Board.board[7][2] instanceof NoPiece) && (Board.board[7][3] instanceof NoPiece)) {
                int[][] move = {start, {7, 2}};
                valids.add(move);
            }
        } else if (this.color == "B") {
            if ((this.y == 0 && this.x == 4) && (Board.board[0][7] instanceof Rook && Board.board[0][7].getColor() == "W") && (Board.board[0][6] instanceof NoPiece) && (Board.board[0][5] instanceof NoPiece)) {
                int[][] move = {start, {0, 6}};
                valids.add(move);
            }
            if ((this.y == 0 && this.x == 4) && (Board.board[0][0] instanceof Rook && Board.board[0][0].getColor() == "W") && (Board.board[0][1] instanceof NoPiece) && (Board.board[0][2] instanceof NoPiece) && (Board.board[0][3] instanceof NoPiece)) {
                int[][] move = {start, {0, 2}};
                valids.add(move);
            }
        }

        // remove moves if piece is pinned
        ArrayList<int[][]> newValids = new ArrayList<>();
        for (int[][] move : valids) {
            if (!(Board.tryMove(this.color, move[0][1], move[0][0], move[1][1], move[1][0]))) {
                newValids.add(move);
            }
        }

        return newValids;
    }

    public ArrayList<int[][]> attacked() {
        ArrayList<int[][]> valids = new ArrayList<>();
        int[] start = {this.y, this.x};

        int[][] d = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int[] pos : d) {
            int dy = pos[0];
            int dx = pos[1];

            if ((this.y + dy >= 0 && this.y + dy <= 7 && this.x + dx >= 0 && this.x + dx <= 7) && (Board.board[this.y + dy][this.x + dx].getColor() != this.color)) {
                int[][] move = {start, {this.y + dy, this.x + dx}};
                valids.add(move);
            }      
        }

        return valids;
    }
}

class Board implements Cloneable {
    public static Pieces[][] board = {
        {new Rook(0, 0, "B"), new Knight(0, 1, "B"), new Bishop(0, 2, "B"), new Queen(0, 3, "B"), new King(0, 4, "B"), new Bishop(0, 5, "B"), new Knight(0, 6, "B"), new Rook(0, 7, "B")},
        {new Pawn(1, 0, "B"), new Pawn(1, 1, "B"), new Pawn(1, 2, "B"), new Pawn(1, 3, "B"), new Pawn(1, 4, "B"), new Pawn(1, 5, "B"), new Pawn(1, 6, "B"), new Pawn(1, 7, "B")},
        {new NoPiece(2, 0), new NoPiece(2, 1), new NoPiece(2, 2), new NoPiece(2, 3), new NoPiece(2, 4), new NoPiece(2, 5), new NoPiece(2, 6), new NoPiece(2, 7)},
        {new NoPiece(3, 0), new NoPiece(3, 1), new NoPiece(3, 2), new NoPiece(3, 3), new NoPiece(3, 4), new NoPiece(3, 5), new NoPiece(3, 6), new NoPiece(3, 7)},
        {new NoPiece(4, 0), new NoPiece(4, 1), new NoPiece(4, 2), new NoPiece(4, 3), new NoPiece(4, 4), new NoPiece(4, 5), new NoPiece(4, 6), new NoPiece(4, 7)},
        {new NoPiece(5, 0), new NoPiece(5, 1), new NoPiece(5, 2), new NoPiece(5, 3), new NoPiece(5, 4), new NoPiece(5, 5), new NoPiece(5, 6), new NoPiece(5, 7)},
        {new Pawn(6, 0, "W"), new Pawn(6, 1, "W"), new Pawn(6, 2, "W"), new Pawn(6, 3, "W"), new Pawn(6, 4, "W"), new Pawn(6, 5, "W"), new Pawn(6, 6, "W"), new Pawn(6, 7, "W")},
        {new Rook(7, 0, "W"), new Knight(7, 1, "W"), new Bishop(7, 2, "W"), new Queen(7, 3, "W"), new King(7, 4, "W"), new Bishop(7, 5, "W"), new Knight(7, 6, "W"), new Rook(7, 7, "W")},
    };

    public Object clone() throws CloneNotSupportedException { 
        Board b = (Board) super.clone();
        return b;
    }

    void stamp() {
        System.out.println(Main.reset + "  a b c d e f g h ");
        for (int y = 0; y < 8; y++) {
            System.out.print(String.valueOf(8 - y) + " ");
            for (int x = 0; x < 8; x++) {
                String col = (board[y][x].getColor() == "W") ? Main.blue : Main.red;
                if (board[y][x].getColor() == null) {
                    col = Main.reset;
                }
                System.out.print(col + board[y][x].getName() + Main.reset);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static String getBitboard() {
        String r = "";
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                r += board[y][x].getName();
            }
        }

        return r;
    }

    public static boolean inCheck(String color) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (board[y][x] instanceof King && board[y][x].getColor().equals(color)) {
                    int[] kingPos = {y, x};
                    for (int[][] value : attacked(Main.colors[1 - Main.index(Main.colors, color)])) {
                        int[] endPos = value[1];
                        if (endPos[0] == kingPos[0] && endPos[1] == kingPos[1]) {
                            return true;
                        }
                    }
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean inCheckmate(String color) {
        return (inCheck(color) && (valids(color).size() == 0));
    }

    public static boolean inStalemate(String color) {
        return ((!(inCheck(color))) && (valids(color).size() == 0));
    }

    public static void makeMove(int startX, int startY, int endX, int endY) {
        Pieces startPiece = Board.board[startY][startX];

        Board.board[endY][endX] = startPiece;
        Board.board[endY][endX].setPos(endY, endX);
        Board.board[startY][startX] = new NoPiece(startY, startX);
    }

    public static boolean tryMove(String color, int startX, int startY, int endX, int endY) {
        boolean check;
        Pieces endPiece = Board.board[endY][endX];
        makeMove(startX, startY, endX, endY);
        if (inCheck(color)) {
            check = true;
        } else {
            check = false;
        }
        makeMove(endX, endY, startX, startY);
        Board.board[endY][endX] = endPiece;
        return check;
    }

    public static ArrayList<int[][]> valids(String color) {
        ArrayList<int[][]> valids = new ArrayList<>();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (board[y][x].getColor() == color) {
                    valids = Main.combine(valids, board[y][x].valids());
                }
            }
        }

        return valids;
    }

    public static ArrayList<int[][]> attacked(String color) {
        ArrayList<int[][]> valids = new ArrayList<>();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (board[y][x].getColor() == color) {
                    valids = Main.combine(valids, board[y][x].attacked());
                }
            }
        }

        return valids;
    }
}

// Main
public class Main {
    // Variables
    public static final String red = "\u001B[31m";
    public static final String blue = "\u001B[34m";
    public static final String reset = "\u001B[0m";

    public static final boolean player1 = true;
    public static final boolean player2 = false;
    public static final int moveTime = 10;

    public static final String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};
    public static final String[] numbers = {"8", "7", "6", "5", "4", "3", "2", "1"};
    public static final String[] colors = {"W", "B"};
    public static final String[] colorsName = {blue + "Blue" + reset, red + "Red" + reset};
    public static int moveNumber = 0;
    public static final String[][] openings = {
        {"e2e4", "g7g6", "d2d4", "f8g7", "b1c3", "g8f6"},
        {"e2e4", "e7e5", "g1f3", "b8c6", "b1c3", "g8f6"},
        {"e2e4", "e7e5", "g1f3", "g8f6", "b1c3", "b8c6"},
        {"e2e4", "c7c5", "g1f3", "b8c6", "b1c3", "e7e5"},
        {"d2d4", "d7d5", "b1c3", "g8f6", "g1f3", "b8c6"},
        {"e2e4", "d7d5", "e4d5", "d1d5", "b2c3", "d5a5"},
    };
    public static Random r = new Random();
    public static int number = r.nextInt(openings.length);
    public static int opening1 = number;
    public static int opening2 = -1;
    public static ArrayList<String> moveList = new ArrayList<>();
    public static ArrayList<Boolean> captureList = new ArrayList<>();
    public static ArrayList<Object[]> transTable = new ArrayList<>();
    public static int turn = 0;
    static Scanner reader = new Scanner(System.in);
    public static Board board = new Board();

    public static final int[][] pawnScore = {
        { 0,  0,  0,  0,  0,  0,  0,  0},
        {99, 99, 99, 99, 99, 99, 99, 99},
        {50, 50, 50, 50, 50, 50, 50, 50},
        {40, 40, 40, 60, 60, 40, 40, 40},
        {10, 10, 10, 60, 60, -9, 10, 10},
        {10, 20, 20, 10, 10, -9, 20, 10},
        {40, 40, 40, 20, 20, 40, 40, 40},
        { 0,  0,  0,  0,  0,  0,  0,  0},
    };

    public static final int[][] knightScore = {
        { 0, 10, 15, 15, 15, 15, 10,  0},
        {10, 15, 20, 20, 20, 20, 15, 10},
        {10, 20, 20, 30, 30, 20, 20, 11},
        {15, 20, 30, 50, 50, 30, 20, 15},
        {15, 20, 30, 50, 50, 30, 20, 15},
        {15, 20, 30, 30, 30, 30, 20, 15},
        {10, 15, 20, 20, 20, 20, 15, 10},
        { 0, 10, 15, 15, 15, 15, 10,  0},
    };

    public static final int[][] queenScore = {
        { 0, 10, 15, 15, 15, 15, 10,  0},
        {10, 15, 20, 20, 20, 20, 15, 10},
        {15, 20, 30, 30, 30, 30, 20, 15},
        {15, 20, 30, 50, 50, 30, 20, 15},
        {15, 20, 30, 50, 50, 30, 20, 15},
        {15, 20, 30, 30, 30, 30, 20, 15},
        {10, 15, 20, 20, 20, 20, 15, 10},
        { 0, 10, 15, 15, 15, 15, 10,  0},
    };

    public static final int[][] kingScore = {
        {-9, -9, -9, -9, -9, -9, -9, -9},
        {-9, -9, -9, -9, -9, -9, -9, -9},
        {-9, -9, -9, -9, -9, -9, -9, -9},
        {-9, -9, -9, -9, -9, -9, -9, -9},
        {-9, -9, -9, -9, -9, -9, -9, -9},
        {-9, -9, -9, -9, -9, -9, -9, -9},
        {10, 10, -9, -9, -9, -9, 10, 10},
        {10, 20, -9, -9, 50, -9, 30, 20},
    };

    public static final int[][] rookScore = {
        {10, 10, 10, 10, 10, 10, 10, 10},
        {70, 70, 70, 70, 70, 70, 70, 70},
        {60, 60, 60, 60, 60, 60, 60, 60},
        {50, 50, 50, 50, 50, 50, 50, 50},
        {40, 40, 40, 40, 40, 40, 40, 40},
        {30, 30, 30, 30, 30, 30, 30, 30},
        {10, 10, 10, 10, 10, 10, 10, 10},
        {0, -90,  0,  0,  0,  0,-90,  0},
    };

    public static final int[][] bishopScore = {
        {10,  0,  0,  0,  0,  0,  0, 10},
        { 0, 10,  0,  0,  0,  0, 10,  0},
        { 0,  0, 10,  0,  0, 10,  0,  0},
        { 0,  0,  0, 10, 10,  0,  0,  0},
        { 0,  0,  0, 10, 10,  0,  0,  0},
        { 0,  0, 10,  0,  0, 10,  0,  0},
        { 0, 10,  0,  0,  0,  0, 10,  0},
        {10,  0,  0,  0,  0,  0,  0, 10},
    };

    // Functions
    public static void main(String[] args) {
        String beforeState = "";
        while (true) {
            // Stamping board onto screen
            System.out.print("\033\143");
            board.stamp();
            // Check end state 
            for (String color : colors) {
                if (Board.inCheckmate(color)) {
                    System.out.println();
                    System.out.println("Checkmate! " + colorsName[1 - index(colors, color)] + " wins!");
                    System.exit(0);
                } else if (Board.inStalemate(color)) {
                    System.out.println();
                    System.out.println("Stalemate!");
                    System.exit(0);
                }
            }
            // Stalemate by 50 move rule
            stalemateBy50();
            // Stalemate by repetition
            stalemateByRepetition();
            // Turn action
            System.out.println();
            System.out.println(colorsName[turn] + " to move.");
            if ((player1 && turn == 0) || (player2 && turn == 1)) {
                Main.input();
            } else {
                beforeState = makeBest(colors[turn], moveTime);
            }
            moveNumber++;
        }
    }

    public static int index(String[] array, String item) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    public static ArrayList<int[][]> combine(ArrayList<int[][]> a1, ArrayList<int[][]> a2) {
        ArrayList<int[][]> c = new ArrayList<>();

        for (int[][] item : a1) {
            c.add(item);
        }
        for (int[][] item : a2) {
            c.add(item);
        }

        return c;
    }

    public static boolean has(ArrayList<Object[]> table, Object[] item) {
        for (Object[] value : table) {
            if (value[0] == item[0] && value[1] == item[1] && value[2] == item[2]) {
                return true;
            }
        }
        return false;
    }
 
    public static boolean contains(ArrayList<int[][]> array, int[][] item) {
        for (int[][] value : array) {
            if (value[0][0] == item[0][0] && value[0][1] == item[0][1] && value[1][0] == item[1][0] && value[1][1] == item[1][1]) {
                return true;
            }
        }
        return false;
    }

    public static int[] toCoor(String alg) {
        String alg0 = Character.toString(alg.charAt(0));
        String alg1 = Character.toString(alg.charAt(1));
        int x = index(letters, alg0);
        int y = index(numbers, alg1);
        int[] coor = {y, x};

        return coor;    
    }

    public static String toAlg(int[] coor) {
        String letter = letters[coor[1]];
        String number = numbers[coor[0]];
        String alg = letter + number;

        return alg;
    }

    public static void stalemateBy50() {
        int numberOfTrue;
        if (captureList.size() > 50) {   
                numberOfTrue = 0; 
                for (int i = 0; i <= 50; i++) {
                    if (((captureList.get(captureList.size() - 1 - i)))) {
                        numberOfTrue += 1;
                        break;
                    }
                }
            } else {
                numberOfTrue = 1;
            }
        if (numberOfTrue == 0) {
            System.out.println();
            System.out.println("Stalemate by 50 move rule!");
            System.exit(0);
        }
    }

    public static void stalemateByRepetition() {
        int len = moveList.size() - 1;
        if (len - 8 >= 0) {   
            String move7 = moveList.get(len - 1);
            String move6 = moveList.get(len - 2);
            String move5 = moveList.get(len - 3);
            String move4 = moveList.get(len - 4);
            String move3 = moveList.get(len - 5);
            String move2 = moveList.get(len - 6);
            String move1 = moveList.get(len - 7);
            String move0 = moveList.get(len - 8);

            if ((move0.equals(move4)) && (move1.equals(move5)) && (move2.equals(move6)) && (move3.equals(move7))) {
                System.out.println();
                System.out.println("Stalemate by repetition!");
                System.exit(0);
            }
        }
    }

    public static int input() {
        System.out.print(">> ");
        String input = reader.nextLine();

        // asks for all valids
        if (input.equals("valids")) {
            System.out.print(">> ");
            for (int[][] move : Board.valids(colors[turn])) {
                String start = toAlg(move[0]);
                String end = toAlg(move[1]);

                System.out.print(start + end + " ");
            }
            System.out.println();
            input();
            return 0;
        }

        // asks for valids of certain piece
        if (input.length() == 2 && index(letters, Character.toString(input.charAt(0))) != -1 && index(numbers, Character.toString(input.charAt(1))) != -1) {
            int[] coor = toCoor(input);
            if (Board.board[coor[0]][coor[1]].getColor() == colors[turn]) {
                System.out.print(">> ");
                ArrayList<int[][]> valids = Board.board[coor[0]][coor[1]].valids();
                for (int i = 0; i < valids.size(); i++) {
                    System.out.print(toAlg(valids.get(i)[1]) + " ");
                }
                System.out.println();
                input();
                return 0;
            }
        }

        // inputs start and end position to move piece
        if (input.length() == 4 && index(letters, Character.toString(input.charAt(0))) != -1 && index(numbers, Character.toString(input.charAt(1))) != -1 && index(letters, Character.toString(input.charAt(2))) != -1 && index(numbers, Character.toString(input.charAt(3))) != -1) {
            int[] startCoor = toCoor(Character.toString(input.charAt(0)) + Character.toString(input.charAt(1)));
            int[] endCoor = toCoor(Character.toString(input.charAt(2)) + Character.toString(input.charAt(3)));
            int[][] fuse = {startCoor, endCoor};

            if (contains(Board.board[startCoor[0]][startCoor[1]].valids(), fuse)) {
                captureList.add(!(Board.board[endCoor[0]][endCoor[1]] instanceof NoPiece));
                Board.makeMove(startCoor[1], startCoor[0], endCoor[1], endCoor[0]);
                moveList.add(toAlg(startCoor) + toAlg(endCoor));
                turn = 1 - turn;
                return 0;
            }
        }
        return 0;
    }

    public static int makeRandom(String color) {
        ArrayList<int[][]> myValids = Board.valids(color);
        if (myValids.size() == 0) {
            return 0;
        }
        Random rand = new Random();
        int randomNumber;
        if (myValids.size() > 1) {
            randomNumber = rand.nextInt(myValids.size());
        } else {
            randomNumber = 0;
        }

        int[] endCoor = myValids.get(randomNumber)[1];
        captureList.add(!(Board.board[endCoor[0]][endCoor[1]] instanceof NoPiece));
        Board.makeMove(myValids.get(randomNumber)[0][1], myValids.get(randomNumber)[0][0], myValids.get(randomNumber)[1][1], myValids.get(randomNumber)[1][0]);
        turn = 1 - turn;
        return 0;
    }

    public static String makeBest(String color, int seconds) {
        int score = 0;
        String fuse = "";
        if ((!(Board.inCheckmate(color))) && (!(Board.inStalemate(color)))) {
            Object[] result = iterativeDeepening(color, seconds);
            score = (int) result[0];
            int[][] mover = (int[][]) result[1];
            int[] startPos = mover[0];
            int[] endPos = mover[1];
            fuse = toAlg(startPos) + toAlg(endPos);
            moveList.add(toAlg(startPos) + toAlg(endPos));
            Board.makeMove(startPos[1], startPos[0], endPos[1], endPos[0]);
            turn = 1 - turn;
        }
        return fuse;
    }

    public static Object[] iterativeDeepening(String color, int time) {
        Object[] result = {};
        Object[] value = {};
        long startTime = System.currentTimeMillis();

        // Saved openings
        int lastNumber;
        if (moveList.size() < 6) {
            if ((turn == 0 && opening1 == -1) || (turn == 1 && opening2 == -1)) {    
                // Create an arraylist of all possible next moves
                ArrayList<int[][]> nextMoves = new ArrayList<>();
                // for every opening in the opening book
                for (String[] opening : openings) {
                    // are all moves made so far the same as in the opening we are looking at?
                    lastNumber = 0;
                    for (int i = 0; i < moveList.size(); i++) {
                        if (i == 0 && (!(moveList.get(i).equals(opening[i])))) {
                            break;
                        }
                        if (!(moveList.get(i).equals(opening[i]))) {
                            lastNumber = i;
                            break;
                        }
                        lastNumber++;
                    }
                    // find the next move in the sequence and then add its coordinates to the list nextMoves
                    if (lastNumber > 0) {
                        String first = Character.toString(opening[lastNumber].charAt(0));
                        String second = Character.toString(opening[lastNumber].charAt(1));
                        String third = Character.toString(opening[lastNumber].charAt(2));
                        String fourth = Character.toString(opening[lastNumber].charAt(3));
                        String start = first + second;
                        String end = third + fourth;
                        int[] startCoor = toCoor(start);
                        int[] endCoor = toCoor(end);
                        int[][] fuse = {startCoor, endCoor};
                        nextMoves.add(fuse);
                    }
                }
                int index;
                if (nextMoves.size() != 0) {
                    if (nextMoves.size() == 1) {
                        index = 0;
                    } else {
                        Random rand = new Random();
                        index = rand.nextInt(nextMoves.size());
                    }
                    if (turn == 0) {
                        opening1 = index;
                    } else if (turn == 1) {
                        opening2 = index;
                    }
                    // return the chosen random move
                    Object[] returnS = new Object[]{0, nextMoves.get(index)};
                    return returnS;
                }
            } else if ((turn == 0 && opening1 != -1) || (turn == 1 && opening2 != -1)) {
                String[] opening = (turn == 0) ? openings[opening1] : openings[opening2];
                lastNumber = 0;
                for (int i = 0; i < moveList.size(); i++) {
                    if (i == 0 && (!(moveList.get(i).equals(opening[i])))) {
                        break;
                    }
                    if (!(moveList.get(i).equals(opening[i]))) {
                        lastNumber = i;
                        break;
                    }
                    lastNumber++;
                }
                // return the chosen random move
                String start = Character.toString(opening[lastNumber].charAt(0)) + Character.toString(opening[lastNumber].charAt(1));
                String end = Character.toString(opening[lastNumber].charAt(2)) + Character.toString(opening[lastNumber].charAt(3));
                int[][] fuse = {toCoor(start), toCoor(end)};
                if (contains(Board.valids(color), fuse)) {
                    Object[] returnS = new Object[]{0, fuse};
                    return returnS;
                }
            }
        }

        // iterate
        for (int i = 1; i <= 100; i++) {
            value = minimaxTimed(Main.board, color, 1, i, -10000, 10000, startTime, time);
            if (value[0] != "quit") {
                result = value;
            }
            long currentTime = System.currentTimeMillis();
            int difference = ((int) Math.subtractExact(currentTime, startTime)) / 1000;
            if (difference >= time) {
                break;
            }
        }

        return result;
    }

    public static Object[] minimaxTimed(Board board, String color, int mod, int depth, int alpha, int beta, long startTime, int time) {
        // Check terminal cases 
        if (board.inCheckmate(color)) {
            return new Object[]{mod * -1000, null};
        } else if (board.inCheckmate(colors[1 - index(colors, color)])) {
            return new Object[]{mod * 1000, null};
        } else if (board.inStalemate(color)) {
            return new Object[]{0, null};
        }
        // Depth limit
        if (depth <= 0) {
            return new Object[]{evaluate(board, color) * mod, null};
        }
        // check transposition table
        Board sboard;
        String scolor;
        int sdepth;
        int sbestScore;
        int[][] sbestMove;

        for (Object[] item : transTable) {
            sboard = (Board) item[0];
            scolor = (String) item[1];
            sdepth = (int) item[2];
            sbestScore = (int) item[3];
            sbestMove = (int[][]) item[4];

            if ((sboard.getBitboard() == board.getBitboard() && color.equals(scolor) && depth == sdepth) && contains(board.valids(color), sbestMove)) {
                return new Object[]{sbestScore, sbestMove};
            }
        }
        // Setup
        try {    
            Board copyBoard = (Board) board.clone();
            ArrayList<int[][]> valids = orderMoves(copyBoard, copyBoard.valids(color), color);
            int bestScore = (mod == 1) ? -10000 : 10000;
            int[][] bestMove = null;
            // Recursivity
            for (int[][] move : valids) {
                int[] startPos = move[0];
                int[] endPos = move[1];
                Pieces endPiece = copyBoard.board[endPos[0]][endPos[1]];
                copyBoard.makeMove(startPos[1], startPos[0], endPos[1], endPos[0]);
                // Branch
                Object[] result = minimaxTimed(copyBoard, colors[1 - index(colors, color)], -mod, depth - 1, alpha, beta, startTime, time);
                // Time's up?
                long currentTime = System.currentTimeMillis();
                int difference = ((int) Math.subtractExact(currentTime, startTime)) / 1000;
                if (difference >= time) {
                    // Undo move
                    copyBoard.makeMove(endPos[1], endPos[0], startPos[1], startPos[0]);
                    copyBoard.board[endPos[0]][endPos[1]] = endPiece;
                    return new Object[]{"quit"};
                }
                // Continue scoring
                int score = (int) result[0];
                int[][] mover = (int[][]) result[1];
                // Calculate if best 
                if ((mod == 1 && score > bestScore) || (mod == -1 && score < bestScore)) {
                    bestScore = score;
                    bestMove = move;
                    // Prune
                    if (mod == 1) {
                        alpha = Math.max(bestScore, alpha);
                    } else if (mod == -1) {
                        beta = Math.min(bestScore, beta);
                    }
                    if (alpha >= beta) { 
                        copyBoard.makeMove(endPos[1], endPos[0], startPos[1], startPos[0]);
                        copyBoard.board[endPos[0]][endPos[1]] = endPiece;
                        break; 
                    }
                }
                // Undo move             
                copyBoard.makeMove(endPos[1], endPos[0], startPos[1], startPos[0]);
                copyBoard.board[endPos[0]][endPos[1]] = endPiece;
            }
            // return
            Object[] id = new Object[]{board, color, depth, bestScore, bestMove};
            transTable.add(id);
            Object[] returnS = new Object[]{bestScore, bestMove};
            return returnS;
        } catch (CloneNotSupportedException e) {}
        return new Object[]{};
    }

    public static ArrayList<int[][]> orderMoves(Board board, ArrayList<int[][]> moves, String color) {
        ArrayList<int[][]> newList = new ArrayList<>();

        for (int[][] move : moves) {
            int[] start = move[0];
            int[] end = move[1];

            if (board.board[start[0]][start[1]].getScore() <= board.board[end[0]][end[1]].getScore()) {
                newList.add(move);
            } else if (Board.tryMove(colors[1 - index(colors, color)], start[1], start[0], end[1], end[0])) {
                newList.add(move);
            }
        }

        for (int[][] move : moves) {
            if (!(contains(newList, move))) {
                newList.add(move);
            }
        }

        return newList;
    }

    public static int evaluate(Board board, String color) {
        int score = 0;
        int[] pieceScores = {100, 300, 300, 500, 900, 0};
        int modifier = 1;
        // evaluate by scoring all pieces
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (!(board.board[y][x] instanceof NoPiece)) {
                    modifier = (board.board[y][x].getColor() == color) ? 1 : -1;
                    Pieces piece = board.board[y][x];
                    if (piece instanceof Pawn) {
                        score += modifier * pieceScores[0];
                        if (modifier == -1) { 
                            score -= pieceScores[0] * ((pawnScore[7 - y][x]) / 100); 
                        } else if (modifier == 1) { 
                            score += pieceScores[0] * ((pawnScore[y][x]) / 100); 
                        }
                    } else if (piece instanceof Bishop) {
                        score += modifier * pieceScores[1];
                        if (modifier == -1) { 
                            score -= pieceScores[1] * (bishopScore[7 - y][x] / 100); 
                        } else if (modifier == 1) { 
                            score += pieceScores[1] * (bishopScore[y][x] / 100); 
                        }
                    } else if (piece instanceof Knight) {
                        score += modifier * pieceScores[2];
                        if (modifier == -1) { 
                            score -= pieceScores[2] * (knightScore[7 - y][x] / 100); 
                        } else if (modifier == 1) { 
                            score += pieceScores[2] * (knightScore[y][x] / 100); 
                        }
                    } else if (piece instanceof Rook) {
                        score += modifier * pieceScores[3];
                        if (modifier == -1) {
                            score -= pieceScores[3] * (rookScore[7 - y][x] / 100);
                        } else if (modifier == 1) {
                            score += pieceScores[3] * (rookScore[y][x] / 100);
                        }
                    } else if (piece instanceof Queen) {
                        score += modifier * pieceScores[4];
                        if (modifier == -1) { 
                            score -= pieceScores[4] * (queenScore[7 - y][x] / 100); 
                        } else if (modifier == 1) { 
                            score += pieceScores[4] * (queenScore[y][x] / 100); 
                        }
                    } else if (piece instanceof King) {
                        score += modifier * pieceScores[5];
                        if (modifier == -1) { 
                            score -= (kingScore[7 - y][x]); 
                        } else if (modifier == 1) { 
                            score += (kingScore[y][x]); 
                        }
                    }
                }
            }
        }
        // return 
        return score;
    }
}
