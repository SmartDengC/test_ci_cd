import com.controller.BasicConfigController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Before;

import static org.junit.Assert.*;
import org.springframework.test.web.servlet.MockMvc;

public class BasicConfigControllerTest {
    @Autowired
    private BasicConfigController basicConfigController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(basicConfigController).build();
    }

    @Test
    public void checkYear() {
    }

    @Test
    public void checkData() {
    }

    @Test
    public void getData() {
    }

    @Test
    public void uploadFile() {
        // String year, @RequestParam("file")MultipartFile file, String fileType
        String year = "2019";
        String fileType = "CN";

        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,"----------ThIs_Is_tHe_bouNdaRY_$", Charset.defaultCharset());
        multipartEntity.addPart("files",new FileBody(new File("F:\\data\\kdd.xlsx"),"xlsx"));

        HttpPost request = new HttpPost("http://127.0.0.1:8080/FreshmanInfomationAnalysSystem/basicConfig/uploadfile");
        request.setEntity(multipartEntity);
        request.setEntity(year);
        request.setEntity(fileType);
        request.addHeader("Content-Type","multipart/form-data; boundary=----------ThIs_Is_tHe_bouNdaRY_$");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response =httpClient.execute(request);

        InputStream is = response.getEntity().getContent();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        System.out.println("发送消息收到的返回："+buffer.toString());


//        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/basicConfig/uploadFile").param("year", "2019").param("").);
//        MvcResult mvcResult = resultActions.andReturn();
//        String result = mvcResult.getResponse().getContentAsString();
//        System.out.println("=====客户端获得反馈数据:" + result);

    }

    @Test
    public void reUploadFile() {
    }
}