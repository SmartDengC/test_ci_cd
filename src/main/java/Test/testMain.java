package Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName testMain
 * @Version 1.0
 * @Author dell
 * @Date 2019/11/24 19:53
 * @Description TODO
 * Modification User:
 * Modification Date:
 */
public class testMain {

    public static void main(String[] args) throws JsonProcessingException {

        List<test> list1=new ArrayList<test>();
        List<test2> list2=new ArrayList<test2>();

        for(int i=1;i<=3;i++){
            test test=new test();
            test.setId(i);
            test.setName("name"+i);

            test2 test2=new test2();
            test2.setSex("sex"+i);
            test2.setCollege("cddx"+i);

            list1.add(test);
            list2.add(test2);
        }

        ObjectMapper mapper=new ObjectMapper();
        String str1=mapper.writeValueAsString(list1);
        String str2=mapper.writeValueAsString(list2);

        String allStr="{\"first\":"+str1+","+"\"second\":"+str2+"}";
        System.out.println(str1);
        System.out.println(str2);
        System.out.println(allStr);


    }


}
