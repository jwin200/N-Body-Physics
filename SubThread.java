
public class SubThread extends Thread {
	private int num;
	
	public SubThread(int num) {
		this.num = num;
	}
	
	public void run() {
		Universe.main(new String[0]);
	}
}
