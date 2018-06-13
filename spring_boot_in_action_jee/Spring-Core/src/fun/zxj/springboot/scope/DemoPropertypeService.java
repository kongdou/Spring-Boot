package fun.zxj.springboot.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype") //声明为prototype
public class DemoPropertypeService {

}
