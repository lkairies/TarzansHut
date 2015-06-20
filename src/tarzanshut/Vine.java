package tarzanshut;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Vine {
	public enum Direction {
		UP, DOWN, FREE;
	}

	private Direction direction = Direction.FREE;

	private int apesOnVine = 0;
	private int waiting_up = 0;
	private int waiting_down = 0;
	private Lock lock = new ReentrantLock();
	private Condition free = lock.newCondition();

	public void climb(Direction dir) throws InterruptedException {
		boolean climbing = false;
		while (!climbing) {
			lock.lock();
			try {
				if (direction == Direction.FREE) {
					direction = dir;
					apesOnVine++;
					printStatus();
					climbing = true;
				} else if (direction == dir) {
					apesOnVine++;
					printStatus();
					climbing = true;
				} else {
					if (dir == Direction.DOWN) {
						waiting_down++;
					} else {
						waiting_up++;
					}
					free.await();
					if (dir == Direction.DOWN) {
						waiting_down--;
					} else {
						waiting_up--;
					}
				}
			} finally {
				lock.unlock();
			}
		}
	}

	public void leave() {
		lock.lock();
		try {
			apesOnVine--;
			if (apesOnVine == 0) {
				direction = Direction.FREE;
				printStatus();
				free.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}

	private void printStatus() {
		System.out.print("\r Direction: " + direction.toString() + "\tAmount: "
				+ apesOnVine + "\t Waiting up: " + waiting_up
				+ "\t Waiting down: " + waiting_down);
	}

}
