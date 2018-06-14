package Example;

public class PPS {
	long start = 0;
	long run = 0;
	long count = 0;

	public PPS(long start) {
		this.start = start;
	}

	public long getStart() {
		return start;
	}

	public long getRun() {
		return run;
	}

	public long getCount() {
		return count;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public void setRun(long run) {
		this.run = run;
	}

	public void setCount(long l) {
		this.count = l;
	}

	public void addCount() {
		this.count++;
	}
}