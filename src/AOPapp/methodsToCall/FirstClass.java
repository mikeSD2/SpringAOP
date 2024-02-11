package AOPapp.methodsToCall;

import org.springframework.stereotype.Component;
//в FirstClass, SecondClass методы к которым применяеться advice код
@Component
public class FirstClass {

	public void setSomeInfo() {
		
		System.out.println("Some setSomeInfo method stuff");
		
	}
	
	public String showSomeInfo1(String str, int val) {
		
		System.out.println("showSignatureAndArgs arg:" + str + ", " + val);
		
		return "Some String returned!";
	}
	
	public void methodWithError() {
		
		System.out.println(2/0);
	}
}
