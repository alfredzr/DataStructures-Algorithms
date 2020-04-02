//Alfred Zane Rajan Velldaurai CS610 9879 prp

import java.util.*;
import java.io.*;

public class hits9879 {
	public static void main( String[] args) {
		if(args.length != 3) {
            System.out.println("Format: hits9879 iterations/errorRate initialValue filename");
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
			IterBasedH9879( iter, init, filename);
		else
			ErrorBasedH9879( iter, init, filename);
		System.out.println();
	}	

	public static void IterBasedH9879( int iter, int init, String filename) {
		int vert = 4;
		int edge = 4;
        int[][] matr = null;
		try {
			Scanner s = new Scanner(new File(filename));
        vert = s.nextInt();
        edge = s.nextInt();
        matr = new int[vert][vert];
        for(int i = 0; i < vert; i++)
        	for(int j = 0; j < vert; j++)
        		matr[i][j] = 0;
        for(int i=0; i<edge; ++i)
        	matr[s.nextInt()][s.nextInt()] = 1;
        s.close();
		}
		catch(FileNotFoundException e) {
	    	System.out.println("File not found");
	    	System.exit(1);
	    }
        if( vert > 10 ) {
        	VeryLargeH9879( vert, matr);
        	System.exit(0);
        }
        double[] auth = InitialH9879( init, vert);
        double[] hub = InitialH9879(init, vert);
        System.out.print("Base:0\t:");
        for(int i = 0; i < vert; i++) {
          System.out.printf(" A/H[ %d]=%.7f/%.7f",i,auth[i],hub[i]);
        }
        for( int k=1; k<=iter; k++) {
        	auth = Auth9879( hub, vert, matr);
        	hub = Hub9879( auth, vert, matr);
            System.out.print("\nIter:" + k + "\t:");
            for(int i = 0; i < vert; i++) {
                System.out.printf(" A/H[ %d]=%.7f/%.7f",i,auth[i],hub[i]); 
            }
        }
	}
	
	public static void ErrorBasedH9879( int error, int init, String filename) {
		int vert = 4;
		int edge = 4;
        int[][] matr = null;
		try {
			Scanner s = new Scanner(new File(filename));
        vert = s.nextInt();
        edge = s.nextInt();
        matr = new int[vert][vert];
        for(int i = 0; i < vert; i++)
        	for(int j = 0; j < vert; j++)
        		matr[i][j] = 0;
        for(int i=0; i<edge; ++i)
        	matr[s.nextInt()][s.nextInt()] = 1;
        s.close();
		}
		catch(FileNotFoundException e) {
	    	System.out.println("File not found");
	    	System.exit(1);
	    }
        if( vert > 10 ) {
        	VeryLargeH9879( vert, matr);
        	System.exit(0);
        }
        double[] auth = InitialH9879( init, vert);
        double[] hub = InitialH9879(init, vert);
        System.out.print("Base:0\t:");
        for(int i = 0; i < vert; i++) {
          System.out.printf(" A/H[ %d]=%.7f/%.7f",i,auth[i],hub[i]);
        }
        double err = 0.00001;
        if( error < 0 )
        	err = Math.pow(10, error);
        int flag = 0;
        int k = 1;
        double[] prevAuth,prevHub = new double[vert];
        while(flag == 0) {
        	prevAuth = Arrays.copyOf(auth, vert);
        	prevHub = Arrays.copyOf(hub, vert);
        	auth = Auth9879( hub, vert, matr);
        	hub = Hub9879( auth, vert, matr);
            System.out.print("\nIter:" + k + "\t:");
            for(int i = 0; i < vert; i++) {
                System.out.printf(" A/H[ %d]=%.7f/%.7f",i,auth[i],hub[i]); 
            }
            flag = 1;
            for( int i=0; i<vert; i++) {
            	if( Math.abs(auth[i]-prevAuth[i])>err || Math.abs(hub[i]-prevHub[i])>err )
            		flag = 0;
            }
            k++;
        }
	}
	
	public static void VeryLargeH9879( int vert, int[][] matr) {
		double[] auth = InitialH9879( -1, vert);
        double[] hub = InitialH9879( -1, vert);
        double err = 0.00001;
        int flag = 0;
        int k = 1;
        double[] prevAuth,prevHub = new double[vert];
        while(flag == 0) {
        	prevAuth = Arrays.copyOf(auth, vert);
        	prevHub = Arrays.copyOf(hub, vert);
        	auth = Auth9879( hub, vert, matr);
        	hub = Hub9879( auth, vert, matr);
            flag = 1;
            for( int i=0; i<vert; i++) {
            	if( Math.abs(auth[i]-prevAuth[i])>err || Math.abs(hub[i]-prevHub[i])>err )
            		flag = 0;
            }
            k++;
        }
	    System.out.println("Iter\t: " + k);
	    for(int i = 0; i < vert; i++) {
	        System.out.printf("A/H[ %d]=%.7f/%.7f\n",i,auth[i],hub[i]);
	    }
	}
	
	public static double[] InitialH9879( int init, int vert)	{
		
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
	
	public static double[] Auth9879( double[] hub, int vert, int[][] matr) {
		double[] auth = new double[vert];
		double sum = 0.0;
		for( int i=0; i<vert; i++)
			auth[i] = 0.0;
		for( int out=0; out<vert; out++) {
			for( int in=0; in<vert; in++)
				if( matr[in][out] == 1)
					auth[out] += hub[in];
			sum += auth[out]*auth[out];
		}
		sum = Math.sqrt(sum);
		for( int i=0; i<vert; i++)
			auth[i] = auth[i]/sum;
		return auth;
	}
	
	public static double[] Hub9879( double[] auth, int vert, int[][] matr) {
		double[] hub = new double[vert];
		double sum = 0.0;
		for( int i=0; i<vert; i++)
			hub[i] = 0.0;
		for( int in=0; in<vert; in++) {
			for( int out=0; out<vert; out++)
				if( matr[in][out] == 1)
					hub[in] += auth[out];
			sum += hub[in]*hub[in];
		}
		sum = Math.sqrt(sum);
		for( int i=0; i<vert; i++)
			hub[i] = hub[i]/sum;
		return hub;
	}
}
