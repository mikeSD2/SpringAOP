package AOPapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import AOPapp.methodsToCall.FirstClass;
import AOPapp.methodsToCall.SecondClass;
//Аспектно ориентированое программирование. к примеру нам нужно выполнить какой-то один и тот же кусок кода (для логирования или аутентификации) перед какими-то методами в разных классах по всему приложению.
//Таких методов в классах может быть тысяча, а то и больше. Ясно что писать этот кусок кода в этих методах будет глупо. Так вот можно вызвать этот кусок кода перед методами ДАЖЕ НЕ ОТКРЫВАЯ ФАЙЛИКИ КЛАССОВ с этими методами.
//настройка того к каким методам приложения применяеться кусок происходит в единственном файле. в этом файле можно ОДНОЙ строкой применить кусок кода к целой части приложения. Причем Эта часть приложения довольно хорошо конфигурируема.
//можно применить кусок кода к методам по всему приложению или части приложения названия которых начинаються с какого-то слова.
//АОП использует прокси паттерн. А мы помним что прокси класс это просто обертка над другим классом. Она нужна для вызова функциональности этого другого класса при этом добавляяя КАКУЮ-ТО СВОЮ ДОПОЛНИТЕЛЬНУЮ функциональность. Так вот эта дополнительная функциональность и есть наш "кусок кода", а внутренние классы прокси класса это и есть классы с методами к которым мы добавляем "кусок кода"
//немного терминов: "кусок кода" - это advice, модуль в котором написаны advic-ы и настраиваеться к каким частям приложения применять advice - это aspect. выражение которое описывет то к каким частям приложения применяеться advice - это pointcut
public class ClassWithMain {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(SpringConfigClass.class);
		
		FirstClass firstClass = context.getBean("firstClass", FirstClass.class);
		firstClass.setSomeInfo();//вызываем метод к которому был применен advice

		SecondClass secondClass = context.getBean("secondClass", SecondClass.class);
		secondClass.setSomeAnothreInfo();//вызываем метод другого класса к которому тоже был был применен advice
		
		System.out.println(firstClass.showSomeInfo1("Some String!!", 5));//вызываем метод к которому был применен advice
		//firstClass.methodWithError();
		context.close();
	}

}










