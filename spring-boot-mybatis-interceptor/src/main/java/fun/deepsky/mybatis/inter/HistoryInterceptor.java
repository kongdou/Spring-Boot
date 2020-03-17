package fun.deepsky.mybatis.inter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import fun.deepsky.mybatis.SpringContextHelper;
import fun.deepsky.mybatis.model.UtiOperateHistory;
import fun.deepsky.mybatis.service.user.UtiOperateHistoryService;
import fun.deepsky.mybatis.tag.PkTag;
@Intercepts({ 
	@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
@Component
public class HistoryInterceptor implements Interceptor {
	
	private static final Object[] ZERO_OBJECTS = new Object[0];

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		UtiOperateHistoryService service = (UtiOperateHistoryService) SpringContextHelper.getBean("utiOperateHistoryService");
		// 拦截目标
		Object target = invocation.getTarget();
		Object result = null;
		if (target instanceof Executor) {
			UtiOperateHistory history = new UtiOperateHistory();
			Object[] args = invocation.getArgs();
			MappedStatement ms = (MappedStatement) args[0];
			String commandName = ms.getSqlCommandType().name();
			// 删除对象
			if ("DELETE".equals(commandName)) {
				// 获取操作类
				Object parameter = args[1];
				Class entity = parameter.getClass();
				String classze = parameter.getClass().getName();
				//System.out.println("类名："+classze);
				history.setEntity(classze.substring(classze.lastIndexOf(".")+1,classze.length()));
				history.setOperateType(3);
				List<Method> getters = getIdGetter(parameter.getClass());
				//循环次数，如果值>8,取8个
				int forTimes = getters.size()<8?getters.size():8;
				for(int i=0;i<forTimes;i++) {
					System.out.println(((Method) getters.get(i)).getName());
					String value = getStringValue((Method) getters.get(i), parameter);
					if(i==0) {
						if(value!=null && !"".equals(value)) {
							history.setEntityKey1(value);
						}
					}else if(i==1) {
						if(value!=null && !"".equals(value)) {
							history.setEntityKey2(value);
						}
					}else if(i==2) {
						if(value!=null && !"".equals(value)) {
							history.setEntityKey3(value);
						}
					}else if(i==3) {
						if(value!=null && !"".equals(value)) {
							history.setEntityKey4(value);
						}
					}else if(i==4) {
						if(value!=null && !"".equals(value)) {
							history.setEntityKey5(value);
						}
					}else if(i==5) {
						if(value!=null && !"".equals(value)) {
							history.setEntityKey6(value);
						}
					}else if(i==6) {
						if(value!=null && !"".equals(value)) {
							history.setEntityKey7(value);
						}
					}else if(i==7) {
						if(value!=null && !"".equals(value)) {
							history.setEntityKey8(value);
						}
					}
				}
				service.addUtiOperateHistory(history);
			}
			result = invocation.proceed();
		}
		return result;
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
		return target;
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}

	private List<Method> getIdGetter(Class cl) {
		List<Method> list = new ArrayList<Method>();
		Method[] methods = cl.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String methodName = method.getName();
			if(!isIdMethod(method)) {
				continue;
			}
			if ((methodName.startsWith("get")) || (methodName.startsWith("is"))) {
				list.add(method);
			}
		}
		return list;
	}
	
	private List<Method> getGetter(Class cl) {
		List<Method> list = new ArrayList<Method>();
		Method[] methods = cl.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String methodName = method.getName();
			if ((methodName.startsWith("get")) || (methodName.startsWith("is"))) {
				list.add(method);
			}
		}
		for (;;) {
			cl = cl.getSuperclass();
			if (cl == Object.class) {
				break;
			}
			list.addAll(getGetter(cl));
		}
		return list;
	}

	private String getStringValue(Method method, Object obj)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Object value = method.invoke(obj, ZERO_OBJECTS);
		return getStringValue(value);
	}

	private String getStringValue(Object obj) {
		String val = null;
		if (obj == null) {
			return null;
		}
		val = obj.toString();
		return val;
	}
	
	private boolean isIdMethod(Method method) {
		if(method == null) {
			return false;
		}
		//获取IdTag标签
		PkTag tag = method.getAnnotation(PkTag.class);
		if(tag == null) {
			return false;
		}
		if(tag.value()!= null && tag.value().equals("pk")) {
			return true;
		}
		return false;
	}
}
