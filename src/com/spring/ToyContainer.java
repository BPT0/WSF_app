package com.spring;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;



public class ToyContainer {
    // path2Method(HashMap)을 사용하여 HTTP 요청URL과 해당 URL을 처리할 메서드를 저장할 HashMap 선언*초기화
    // 파라미터 String(URL), Method(method)
    HashMap<String, Method> path2Method = new HashMap<>();

    // Controller 객체와 Service 객체를 참조하는 멤버 변수선언
    Object controller;
    Object service;

    // ToyContainer의 생성자
    public ToyContainer(String[] clas) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 인자로 받은 클래스 이름 배열을 순회하며 Controller와 Service 객체를 생성
        // ex) WebServer에서 토이컨테이너를 생성하는 생성자 선언
        /*public WebServer(ToyContainer ctx) {
            this.ctx = ctx;
        }*/
        for( String cls : clas) {
            Class<?> cla = Class.forName(cls);
            // 해당 클래스에 Controller 어노테이션이 붙어 있다면 Controller 객체를 생성
            if (cla.getAnnotation(Controller.class) != null)
                controller = Class.forName(cls).newInstance();
            // 해당 클래스에 Service 어노테이션이 붙어 있다면 Service 객체를 생성
            if (cla.getAnnotation(Service.class) != null)
                service = Class.forName(cls).newInstance();
        }

        // 생성한 Controller 객체의 모든 메서드를 가져온 뒤, 
        // 그 중에서 RequestMapping 어노테이션이 붙은 메서드를 path2Method 에 (url, value) 추가
        Method[] methods = controller.getClass().getDeclaredMethods();
        for (Method method : methods) {
            RequestMapping req = method.getAnnotation(RequestMapping.class);
            if (req != null) {
                String path = req.value();
                path2Method.put(path, method);
            }
        }

        // Controller 객체의 모든 필드(멤버변수)를 가져온 뒤,
        // 그 중에서 Autowired 어노테이션이 붙은 필드에 (controller 와 Service 객체를 주입 지시)
        Field[] fields = controller.getClass().getDeclaredFields();
        for (Field field : fields) {
            Autowired auto = field.getAnnotation(Autowired.class);
            if (auto != null) {
                field.setAccessible(true); // 엑세스 가능 플레그 설정
                field.set(controller, service); // (controller 와 Service 객체를 주입 지시)
            }
        }
    }

    // ToyContainer를 실행하는 메서드, 실제로는 WebServer 객체를 생성후 실행
    public void run() throws Exception {
        (new WebServer(this)).run();
    }

    // HTTP 요청을 처리
    public String request(String path) throws InvocationTargetException, IllegalAccessException, IOException {
        // 요청 URL에서 1.쿼리 문자열을 분석하기 위해  맵(param) 선언,
        // 2.뷰에 전달할 데이터를 담을 모델 맵(model)을 생성
        Map<String, String> param = new HashMap<>(); // param 에는 페이지 경로, url 절대경로 가 넘어가면 되나요?
        Map<String, Object> model = new HashMap<>(); // html페이지에서 사용할 변수명, 데이터

        String[] queryString = path.split("\\?"); // 전달 path를 \\? 로 구분

        Method method = path2Method.get(queryString[0]); // 구분한 path 로 메소드를 호출
        // 쿼리 문자열이 있다면 이를 분석하여 매개변수 맵을 구성
        if (queryString.length == 2) {
            String[] tokens = queryString[1].split("&"); // 토큰은 &로 입력 인자 구분
            for(String token : tokens ) {
                String[] nameValue = token.split("="); // = 로 (입력인자=파라미터) put으로 삽입
                if (nameValue.length == 2) param.put(nameValue[0], nameValue[1]);
                else param.put(nameValue[0], "");
            }
        }

        // path2Method 맵에서 URL 경로에 해당하는 메서드를 찾아 실행
        String output = (String)method.invoke(controller, param, model);
        // 메서드에 ResponseBody 어노테이션이 없다면,
        // 이 메서드는 뷰 파일의 경로를 반환하는 것으로 간주하고, 해당 map(데이터)의 데이터로 view 구성
        if (method.getAnnotation(ResponseBody.class) == null ) {
            String template = new String(Files.readAllBytes(Paths.get(output + ".html")));
            // 템플릿에는 '@'로 시작하는 변수가 포함될 수 있으며, 이를 모델의 데이터로 대체.
            for(String key : model.keySet()) {
                template = template.replace("@" + key, model.get(key).toString());
            }
            output = template;
        }
        return output;
    }
}
