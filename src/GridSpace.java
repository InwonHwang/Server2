import java.util.*;

public class GridSpace {
	public class Node{
		int data;
		int posX;
		int posY;
		Node parent;		
	}
	
	Node[][] cell;
	int N;
	int countOfWall;
	int[] posOfPlayer;
	int min;
	Queue<Node> queueOfNode;	
		
	String set() {
		
		queueOfNode = new LinkedList<Node>();
		
		String ret = "";
		
		N = (int)(Math.random() * 15) + 1;
		ret += "N{" + Integer.toString(N) + "}:";
		min = N*N;
		cell = new Node[N][];
		
		for(int i = 0; i < N; i++)
			cell[i] = new Node[N];
			
		countOfWall = (int)(N * N)/10 + 1;
		
		for(int y = 0; y < N; y++) {
			for(int x = 0; x < N; x++) {
				cell[y][x] = new Node();
				cell[y][x].data = 0;
				cell[y][x].posX = x;
				cell[y][x].posY = y;
				cell[y][x].parent = null;
			}
		}		
		
		ret += "벽 정보{";
		for(int i = 0; i < countOfWall; i++) {
			int x = (int)(Math.random() * N);
			int y = (int)(Math.random() * N);
			cell[y][x].data = 1;
			ret += "(" +Integer.toString(x) + ", " +Integer.toString(y) + ")";
		}
		ret += "}:";
		
		int x = (int)(Math.random() * N);
		int y = (int)(Math.random() * N);
		while(cell[y][x].data != 0) {
			x = (int)(Math.random() * N);
			y = (int)(Math.random() * N);
		}
		posOfPlayer = new int[2];
		posOfPlayer[0] = x;
		posOfPlayer[1] = y;
		cell[posOfPlayer[1]][posOfPlayer[0]].data = 7;
		ret += "플레이어 위치{" + "(" + Integer.toString(posOfPlayer[0]) +  ", " + Integer.toString(posOfPlayer[1]) + "}";
		
		return ret;		
	}
	
	void print() {
		
		for(int y = 0; y < N; y++) {
			for(int x = 0; x < N; x++) {
			System.out.print(cell[y][x].data + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	String DFS(int endX, int endY)
	{
		if(cell[endY][endX].data == 1) {
			System.out.println("벽이 존재하는 좌표입니다.");
			return "NO\r\n";
		}
		
		queueOfNode.add(cell[posOfPlayer[1]][posOfPlayer[0]]);		
		
		while(!queueOfNode.isEmpty()) {
			Node temp = queueOfNode.remove();			
			
			if(temp.data == 9) continue;
						
			temp.data = 9;			
			
			if(temp.posX == endX && temp.posY == endY) {
				posOfPlayer[0] = endX;
				posOfPlayer[1] = endY;
				cell[posOfPlayer[1]][posOfPlayer[0]].data = 7;
				break;
			}
			
			int x = temp.posX;
			int y = temp.posY;
			
			if(x-1 >= 0 && x-1 < N && y >= 0 && y < N && cell[y][x-1].data == 0 ) {
				queueOfNode.add(cell[y][x-1]);
				cell[y][x-1].parent = temp;
			}
			
			if(x+1 >= 0 && x+1 < N && y >= 0 && y < N && cell[y][x+1].data == 0 ) {
				queueOfNode.add(cell[y][x+1]);
				cell[y][x+1].parent = temp;
			}
			
			if(x >= 0 && x < N && y-1 >= 0 && y-1 < N && cell[y-1][x].data == 0 ) {
				queueOfNode.add(cell[y-1][x]);
				cell[y-1][x].parent = temp;
			}
			
			if(x >= 0 && x < N && y+1 >= 0 && y+1 < N && cell[y+1][x].data == 0 ) {
				queueOfNode.add(cell[y+1][x]);
				cell[y+1][x].parent = temp;				
			}
		}
		
		Node t = cell[posOfPlayer[1]][posOfPlayer[0]].parent;
		while(t != null) {
			t.data = 7;
			t = t.parent;			
		}
		
		
		return "OK\r\n";
	}
	
	void reset()
	{
		queueOfNode.clear();
		for(int y = 0; y < N; y++) {
			for(int x = 0; x < N; x++) {
				if( (cell[y][x] != null && cell[y][x].data == 9) ||
					(cell[y][x] != null && cell[y][x].data == 7) ) {
					cell[y][x].data = 0;
					cell[y][x].parent = null;
				}
			}
		}
		cell[posOfPlayer[1]][posOfPlayer[0]].data = 7;
	}
}
