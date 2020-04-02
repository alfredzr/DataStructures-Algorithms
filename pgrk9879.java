//Alfred Zane Rajan Velldaurai CS610 9879 prp

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class pgrk9879 {
	public static void main( String[] args) {
		if(args.length != 3) {
            System.out.println("Format: pgrk9879 iterations/errorRate initialValue filename");
            System.exit(1);
		}
		int iter = Integer.parseInt(args[0]);
		int init = Integer.parseInt(args[1]);
		if( init<-2 || init>1 ) {
	    	System.out.println("initialValue should be in range -2 to 1");
	        System.exit(1);
	    }
		String filename = args[2];
		if( iter > 0 )
			IterBasedP9879( iter, init, filename);
		else
			ErrorBasedP9879( iter, init, filename);
		System.out.println();
	}	

	public static void IterBasedP9879( int iter, int init, String filename) {
		int vert = 4;
		int edge = 4;
        int[][] matr = null;
        int[] c = null;
		try {
			Scanner s = new Scanner(new File(filename));
	        vert = s.nextInt();
	        edge = s.nextInt();
	        matr = new int[vert][vert];
	        c = new int[vert];
	        int in = 0;
	        int out = 0;
	        for(int i = 0; i < vert; i++) {
	        	for(int j = 0; j < vert; j++)
	        		matr[i][j] = 0;
	        	c[i] = 0;
	        }
	        for(int i=0; i<edge; ++i) {
	        	in = s.nextInt();
	        	out = s.nextInt();
	        	matr[in][out] = 1;
	        	c[in]++;
	        }
	        s.close();
		}
		catch(FileNotFoundException e) {
	    	System.out.println("File not found");
	    	System.exit(1);
	    }
        if( vert > 10 ) {
        	VeryLargeP9879( vert, matr, c);
        	System.exit(0);
        }
        double[] rank = InitialP9879( init, vert);
        System.out.print("Base:0\t:");
        for(int i = 0; i < vert; i++) {
          System.out.printf(" A/H[ %d]=%.7f",i,rank[i]);
        }
        for( int k=1; k<=iter; k++) {
        	rank = Rank9879( rank, vert, matr, c);
            System.out.print("\nIter:" + k + "\t:");
            for(int i = 0; i < vert; i++) {
                System.out.printf(" A/H[ %d]=%.7f",i,rank[i]); 
            }
        }
	}
	
	public static void ErrorBasedP9879( int error, int init, String filename) {
		int vert = 4;
		int edge = 4;
        int[][] matr = null;
        int[] c = null;
		try {
			Scanner s = new Scanner(new File(filename));
	        vert = s.nextInt();
	        edge = s.nextInt();
	        matr = new int[vert][vert];
	        c = new int[vert];
	        int in = 0;
	        int out = 0;
	        for(int i = 0; i < vert; i++) {
	        	for(int j = 0; j < vert; j++)
	        		matr[i][j] = 0;
	        	c[i] = 0;
	        }
	        for(int i=0; i<edge; ++i) {
	        	in = s.nextInt();
	        	out = s.nextInt();
	        	matr[in][out] = 1;
	        	c[in]++;
	        }
	        s.close();
		}
		catch(FileNotFoundException e) {
	    	System.out.println("File not found");
	    	System.exit(1);
	    }
        if( vert > 10 ) {
        	VeryLargeP9879( vert, matr, c);
        	System.exit(0);
        }
        double[] rank = InitialP9879( init, vert);
        System.out.print("Base:0\t:");
        for(int i = 0; i < vert; i++) {
          System.out.printf(" A/H[ %d]=%.7f",i,rank[i]);
        }
        double err = 0.00001;
        if( error < 0 )
        	err = Math.pow(10, error);
        int flag = 0;
        int k = 1;
        double[] prevRank = new double[vert];
        while(flag==0) {
        	prevRank = Arrays.copyOf(rank, vert);
        	rank = Rank9879( prevRank, vert, matr, c);
            System.out.print("\nIter:" + k + "\t:");
            for(int i = 0; i < vert; i++) {
                System.out.printf(" A/H[ %d]=%.7f",i,rank[i]); 
            }
            flag = 1;
            for( int i=0; i<vert; i++) {
            	if( Math.abs(rank[i]-prevRank[i])>err )
            		flag = 0;
            }
            k++;
        }
	}
	
	public static void VeryLargeP9879( int vert, int[][] matr, int[] c) {
		double[] rank = InitialP9879( -1, vert);
        double err = 0.00001;
        int flag = 0;
        int k = 1;
        double[] prevRank = new double[vert];
        while(flag == 0) {
        	prevRank = Arrays.copyOf(rank, vert);
        	rank = Rank9879( prevRank, vert, matr, c);
            flag = 1;
            for( int i=0; i<vert; i++) {
            	if( Math.abs(rank[i]-prevRank[i])>err )
            		flag = 0;
            }
            k++;
        }
	    System.out.println("Iter\t: " + k);
	    for(int i = 0; i < vert; i++) {
	        System.out.printf("A/H[ %d]=%.7f/%.7f\n",i,rank[i]);
	    }
	}
	
	public static double[] InitialP9879( int init, int vert)	{
		
		double initial = 0.0;
		if(init == 0 ) {
			initial = 0.0;
		}
		else if(init == 1) {
			initial = 1.0;
		}
		else if(init == -1) {			
			initial = 1.0/vert;
		}
		else if(init == -2) {
			initial = 1.0/Math.sqrt( vert );
		}
		double[] arr = new double[vert];
		for( int i=0; i<vert; i++)
			arr[i] = initial;
		return arr;
	}
	
	public static double[] Rank9879( double[] prevRank, int vert, int[][] matr, int[] c) {
		double[] rank = new double[vert];
		for( int i=0; i<vert; i++)
			rank[i] = 0.0;
		for( int out=0; out<vert; out++) {
			for( int in=0; in<vert; in++)
				if( matr[in][out] == 1)
					rank[out] += prevRank[in]/c[in];
			rank[out] *= 0.85;
			rank[out] += 0.15/vert;
		}
		return rank;
	}
}
