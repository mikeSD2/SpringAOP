package AOPapp.methodsToCall;

import org.springframework.stereotype.Component;

@Component
public class SecondClass {

	public String setSomeAnothreInfo() {
		
		System.out.println("Some setSomeAnothreInfo method stuff ");
		
		return "some string";
	}
}
