import java.util.ArrayList;

public class ThreadTest extends Thread {
	private ArrayList<Integer> integers = new ArrayList<> ( );

	public static void main ( String[] args ) {
		ThreadTest threadTest = new ThreadTest ( );
		T1 t1 = new T1 ( threadTest );
		t1.start ( );
		threadTest.start ( );
	}

	public void addInt () {
		integers.add ( 1 );
	}

	public ArrayList<Integer> getIntegers () {
		return integers;
	}

	public void run () {
		while ( true ) {
			synchronized (integers) {

				System.out.println ( "base" + integers.size ( ) );
			}
		}
	}
}

class MyThread implements Runnable {
	@Override
	public void run () {
		for ( int i = 0 ; i <= 50 ; i++ ) {
			System.out.println ( Thread.currentThread ( ).getName ( ) + "\t" + i );
		}
	}
}

class T1 extends Thread {
	private ThreadTest threadTest;

	T1 ( ThreadTest threadTest ) {
		this.threadTest = threadTest;
	}

	@Override
	public void run () {
		while ( true ) {
			synchronized (threadTest.getIntegers ()) {
				threadTest.addInt ( );
				System.out.println ( "inteagers" + threadTest.getIntegers ().size ( ) );
			}
		}
	}
}