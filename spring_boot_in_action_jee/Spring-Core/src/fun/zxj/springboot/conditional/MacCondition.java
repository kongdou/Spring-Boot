package fun.zxj.springboot.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MacCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata medadata) {
		return context.getEnvironment().getProperty("os.name").toUpperCase().contains("MAC");
	}

}
