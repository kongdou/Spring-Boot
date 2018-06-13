package fun.zxj.springboot.conditional;

public class MacListService implements ListService {

	@Override
	public String showListCmd() {
		return "ls";
	}

}
