package lib.task;

import lib.BabushkaScript;

public class TakeItemsFromBank extends Task {

	public TakeItemsFromBank(BabushkaScript scr, int num, String item) {
		super(scr);
	}

	@Override
	public boolean canExecute() {
		
		script.objects.closest("Bank");
		return false;
	}

	@Override
	public int execute() {
		// TODO Auto-generated method stub
		return 0;
	}

}
