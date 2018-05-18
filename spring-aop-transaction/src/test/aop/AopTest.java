package aop;

import com.mycode.aop.service.MemberService;
import com.mycode.model.Member;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 蛮小江
 * 2018/5/13 17:43
 */
@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AopTest {
    @Autowired
    private MemberService memberService;
    @Test
    public  void xmlTest(){
        memberService.save(new Member());
        System.out.println("=====================================================");
        try {
            memberService.delete((long) 10);
        } catch (Exception e) {
          //  e.printStackTrace();
        }
    }

    @Test
    public  void annotationoTest(){
          //去掉aop.xml里面的xml中关于aop的配置，启用注解
        xmlTest();
    }

    @Test
    public void argsTest(){
        //去掉aop.xml里面的xml中关于aop的配置，启用注解，启用ArgsAspect类
        xmlTest();
    }
}
