
import java.util.Scanner;

public class Main{
	public static final int interval = 150;
	public static final int width = 30;
	public static final int height = 30;
	public static String[][] grid = new String[width][height];
	public static String[][] workspace = new String[width][height];
	
	public static void main(String[] args) throws InterruptedException{
		draw();
		make_workspace();
		calibration();
		redaction();
		fix_cmd_lag();

		
			
		
		while(true) {
			fill();
		
			display();
			
			engine();
			make_workspace();
			Thread.sleep(interval);
		}
	}
	
	public static void redaction() {
		draw();
		Scanner in = new Scanner(System.in);
		boolean create = true;
		boolean delete = false;
		boolean cursor = false;
		String key = "0";
		int pos_x = width/2;
		int pos_y = height/2;
		grid[pos_y][pos_x] = "#";
		
		while(!(key.equals("e"))) {
			display();
			System.out.print("e = start game, w/a/s/d /x/c/v     ");
			int bufer_x = pos_x;
			int bufer_y = pos_y;

			key = in.nextLine();
			
			if(key.equals("x")) {
				create = false;
				delete = false;
				cursor = true;}
			
			if(key.equals("c")) {
				create = true;
				delete = false;
				cursor = false;
			}
			if(key.equals("v")) {
				delete = true;
				create = false;
				cursor = false;
			}
			if(key.equals("q")) {
				calibration();
			}
			
			if(key.equals("8") || key.equals("w")) pos_y--;
			if(key.equals("2")|| key.equals("s")) pos_y++;
			if(key.equals("4")|| key.equals("a")) pos_x--;
			if(key.equals("6") || key.equals("d")) pos_x++;
			
			switch(pos_x) {
			case -1:
				pos_x = width-1;
				break;
			case width:
				pos_x = 0;
				break;
			}
			
			switch(pos_y) {
			case -1:
				pos_y = height-1;
				break;
			case height:
				pos_y = 0;
				break;
		
			}
			if(cursor) {if(grid[pos_y][pos_x] != "#") grid[pos_y][pos_x] = "K";
			if(grid[bufer_y][bufer_x] != "#") grid[bufer_y][bufer_x] = " ";}
			
			if(create) {grid[pos_y][pos_x] = "#";}
			
			if(delete) {grid[pos_y][pos_x] = " ";}
		}
	}

	public static void draw() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < width; j++) {
				grid[i][j] = " ";
			}
		}
		
		
	}
	
	public static void fill() {
		for(int i = 0; i <= height-2; i++) {
		System.out.print("                                   ");
		System.out.print("                                   ");
		System.out.print("                                   ");
		System.out.print("                                   ");
		System.out.print("                                   ");
		System.out.print("                                   ");
		System.out.print("                                   ");
		System.out.print("                                   ");
		
		}
		
	}
	
	public static void calibration() {
		String width_string = "0";
		for(int x = 0; x < width; x++) {
			width_string += "000";
		}
		System.out.print(width_string);
		int counter = height;
		while(counter>=7) {
			if(counter == height/2) System.out.print("pull the window to zero!\n"
					+ "x = cursor\nc = create life\n"
					+ "v = destroy life\nwasd = keyboard\n"
					+ "press key, to continue");
			System.out.println();
			counter--;
		}
		System.out.print(width_string);
		Scanner in = new Scanner(System.in);
		in.next();
	}
	
	public static void display() {
		for(String i[]: grid) {
			System.out.println();
			for(String x: i) {
				System.out.print(x);
			}
		}
		}
	
	public static int count_alive(int y, int x) {
		int counter_n = 0;
		
		int corner_x = x-1;

		int corner_y = y-1;
		
		int prev_x = corner_x;
	
		int counter_out;
		int counter_in;
		
		boolean start = true;
		
		
		for(counter_out = 0; counter_out < 3; counter_out++) {
			for(counter_in = 0; counter_in < 3; counter_in++) {
				
				
				switch(corner_x) {
				case -1: corner_x = width-1; break;
				case width: corner_x = 0;
				}
				if(start) {
				switch(corner_y) {
				case -1: corner_y = height-1; break;
				case height: corner_y = 0; break;

				} start = false;}
				
				if(workspace[corner_y][corner_x] == "#") counter_n++;
				corner_x++;
			}
			start = true;
			corner_y++;
			corner_x = prev_x;
		}
	return counter_n;
	}
	
	public static void make_workspace() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				workspace[i][j] = grid[i][j];
			}
		}
		
		}
	
	public static void engine() {
		for(int y = 0; y < width; y++) {
			for(int x = 0; x < width; x++) {
				int alive = count_alive(y, x);
				
				if(workspace[y][x] == " ") {
					if(alive == 3) grid[y][x] = "#"; 
				}
				if(workspace[y][x] == "#") {
					if(alive == 4 || alive == 3) continue;
					grid[y][x] = " ";
				}
			}
		}

	}
	public static void fix_cmd_lag() {
		for(int i = 1000; i>0; i--) {
			System.out.print("\n\n\n\n\n\n\n\n\n\n");
		}
	}
	
}

