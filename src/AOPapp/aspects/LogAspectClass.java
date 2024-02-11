package AOPapp.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspectClass {
	// @Before("execution(public void AOPapp.methodsToCall.setSomeInfo())") в аннотации указываеться метод или методы к которому будет применен код ниже. как видим указываеться модификатор, тип, директория и сам метод. мод. и директория опциональны
	// @Before("execution(* * setSome*())")//* означают любой. тоесть любой модификатор, любой возвращаемый тип и любое продолжения названия метода после *. то что директория не указана значит что методы ищються во всем приложении.
	// кроме @Before бывают и другие аннотации. можно после метода например.
	// @Before("execution(* AOPapp.methodsToCall.*.*(..))")//первая * отвечает за возврящаемый тип метода (второю можно  не указывать поскольку это модификатор доступа, а он не обязателен) далее пакет в котором будет искаться метод AOPapp.methodsToCall, далее * - любой класс, далее * любой метод, в параметрах ".." значит что любое число параметров(можно поставить * - любой один параметр или если ничего не указать - нет параметра у искомого метода)
	// можем через запятую указать сначала первый параметр искомых методов потом любые таким образом: (AOPapp.methodsToCall.SomeClass, ..). обратите внимание у параметра всегда указыветься пакет
	//
	@Before("execution(* setSome*())") //вот этой строчкой мы делаем так чтобы метод ниже был применени перед методами которые начинаються на "setSome" во всех классах проекта 
	public void beforeSetSome() {
		System.out.println("some logging stuff before method");
	}
	
	@Pointcut("execution(* setSome*())") //чтобы везде не писать перед методами аспекта длинное execution выражение мы можнм типа 
	//создать переменную содержащую это выражение и потом применять ее к советам аспекта. в @Pointcut пишеться execution
	public void someExpr() {} //someExpr название этой переменной (только это метод но для понимания называю переменной)
	
	@Before("someExpr() ") 
	public void secondBeforeSetSome() {
		System.out.println("some logging stuff before method");
	}
	
	@Before("someExpr() ") 
	public void thirdBeforeSetSome() {
		System.out.println("some authantication stuff before method");	
	}
	//также можно создать несколько таких "переменных" и обьединять их используя стандартные операторы &&, ||, !.
	//например выражение @Pointcut("someExpr() && !(someExpr1() || someExpr2())") обьединяет три Pointcut. 
	//выражение означает что ищються методы которые соответствуют someExpr и не соответствуют someExpr1 или someExpr2. таким образом можно исключить часть методов из поиска.
	
	//если есть несколько аспектов в приложении можно установить порядок их выполнения. для этого просто перед классами аспектов 
	//указываем аннотацию @Order(здесь число означающее приоритет). Приоритет важности от меньшего к большему.
	//тоесть советы в аспекте с @Order(1) будут первыми искать методы приложения и проверять соответствие этих методов советам 
	//этого аспекта. Потом все остальные аспекты.
	
	//JoinPoint - метадата метода к которому применяем advice. Из этого обьекта можно извлечь сигнатуру метода или аргументы которые ему были переданы
	
	@Before("execution(* showSomeInfo1(..))") 
	public void AfterReturningShowSomeInfo1(JoinPoint joinPoint) {
		System.out.println("before method show signature: " + joinPoint.getSignature());
		Object[] methodArgs = joinPoint.getArgs();
		for(int i=0; i!=methodArgs.length; i++) {
			System.out.println("before method show args: " + methodArgs[i]);
		}
	}
	
	//Можно что-то выполнить после УСПЕШНОГО выполнения целевого метода с помощью @AfterReturning.
	//также можно получить доступ в этом advice к тому что вернул целевой метод с помощью returning. 
	//и возвращенное значение модифицыровать например. и тот кто вызвал целевой метод получит модифицырованное значение
	//результат будет храниться в параметре метода совета (здесь targetMethodResult). важно что имена returning и параметр метода должны совпадать
	//@AfterReturning в сочетании с @Before можно например замерять время выполнения целевого метода.
	@AfterReturning(pointcut="execution(* showSomeInfo1(..))", returning = "targetMethodResult") 
	public void AfterReturningShowSomeInfo1(String targetMethodResult) {
		targetMethodResult+=" + added string";
		System.out.println("@AfterReturning method returned: " + targetMethodResult);
	}
	
	//Также очень полезна может быть обработка ОШИБОК которые произошли в целевом методе.
	//с помощью @AfterThrowing можно в совете получить данные об ошибке произошедшей в целевом методе перед тем как она появиться в main.
	//эту ошибку можно например залогировать или например отправить даные о ней себе на почту.
	@AfterThrowing(pointcut="execution(* methodWithError(..))", throwing = "targetMethodReException") 
	public void AfterThrowingShowSomeInfo1(Throwable targetMethodReException) {
		System.out.println("@AfterThrowing method error: " + targetMethodReException);
	}
	
	//@After - как @Before только после выполнения целевого метода. Этот совет выполняеться в любом случае была ошибка или нет. важный момент что @After выполняеться до @AfterThrowing
	
	//@Around - вып. до и после цел. метода. Этот метод самый интересный. Он кроме доступа к значению которое вернул цел. метод
	//и данным произошедшей ошибки (как выше) может обрабатывать ошибку (try catch) прямо в совете и мы можем контролировать когда конкретно произойдет вызов целевого метода. 
	//тоесть мы пишем какойто код до вызова цел. метода, вручную вызываем сам метод и пишем код после этого вызова и вксь этот код пишеться в одном @Around совете.
	@Around("execution(* showSomeInfo1(..))") 
	public String AroundShowSomeInfo1(ProceedingJoinPoint proceedingJoinPoint) throws Throwable { //ProceedingJoinPoint через этот параметр вызываем целевой метод
		System.out.println("Before Target Method");
		String str = null;
		try {
			str = (String)proceedingJoinPoint.proceed();//вызываем целевой метод где хотим! и получаем то что он вернул
			//и обрабатываем ошибки метода так что мейн который вызвал метод даже может не узнать что они случилась
			System.out.println("Target Method Ececuted! returned value: " + str);
		}
		catch(Exception e) {
			System.out.println("Target Method Ececuted! returned value: " + e);//логируем ошибку
			//throw e; //если хотим только залогировать, а саму ошибку не подавлять можем ее вызвать снова с помощью throw
			return "Some Default return data";//можно возващать в catch какое-то дефолтное значение если случилась ошибка.
		}
		System.out.println("After Target Method");
		return str+" + some additional string";//вернем как результат модифицырованное значение целевого метода
	}
	
}  