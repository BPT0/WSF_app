package com.spring;

import com.demo.MovieDto;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;



public class ToyContainer {
    // path2Method를 사용하여 HTTP 요청의 URL 경로와 해당 URL을 처리할 메서드를 연결
    HashMap<String, Method> path2Method = new HashMap<>();

    // Controller 객체와 Service 객체를 참조하는 멤버 변수선언
    Object controller;
    Object service;

    // ToyContainer의 생성자
    public ToyContainer(String[] clas) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 인자로 받은 클래스 이름 배열을 순회하며 Controller와 Service 객체를 생성
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
        // 그 중에서 RequestMapping 어노테이션이 붙은 메서드를 path2Method 맵에 추가
        Method[] methods = controller.getClass().getDeclaredMethods();
        for (Method method : methods) {
            RequestMapping req = method.getAnnotation(RequestMapping.class);
            if (req != null) {
                String path = req.value();
                path2Method.put(path, method);
            }
        }

        // Controller 객체의 모든 필드를 가져온 뒤, 
        // 그 중에서 Autowired 어노테이션이 붙은 필드에 Service 객체를 주입
        Field[] fields = controller.getClass().getDeclaredFields();
        for (Field field : fields) {
            Autowired auto = field.getAnnotation(Autowired.class);
            if (auto != null) {
                field.setAccessible(true);
                field.set(controller, service);
            }
        }
    }

    // ToyContainer를 실행하는 메서드, 실제로는 WebServer 객체를 생성후 실행
    public void run() throws Exception {
        (new WebServer(this)).run();
    }

    // HTTP 요청을 처리
    public String request(String path) throws InvocationTargetException, IllegalAccessException, IOException {
        // 요청 URL에서 쿼리 문자열을 분석하여 매개변수 맵(param)을 생성하고, 뷰에 전달할 데이터를 담을 모델 맵(model)을 생성
        Map<String, String> param = new HashMap<>();
        Map<String, Object> model = new HashMap<>();

        String[] queryString = path.split("\\?");
        Method method = path2Method.get(queryString[0]);
        // 쿼리 문자열이 있다면 이를 분석하여 매개변수 맵을 구성
        if (queryString.length == 2) {
            String[] tokens = queryString[1].split("&");
            for(String token : tokens ) {
                String[] nameValue = token.split("=");
                if (nameValue.length == 2) param.put(nameValue[0], nameValue[1]);
                else param.put(nameValue[0], "");
            }
        }

        // path2Method 맵에서 URL 경로에 해당하는 메서드를 찾아 실행
        String output = (String)method.invoke(controller, param, model);
        // 메서드에 ResponseBody 어노테이션이 없다면, 이 메서드는 뷰 파일의 경로를 반환하는 것으로 간주하고, 해당 파일을 읽어 템플릿으로 사용
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
