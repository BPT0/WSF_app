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
    HashMap<String, Method> path2Method = new HashMap();
    Object controller;
    Object service;

    public ToyContainer(String[] clas) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // controller와 service 자동 생성
        for( String cls : clas) {
            Class cla = Class.forName(cls);
            if (cla.getAnnotation(Controller.class) != null)
                controller = Class.forName(cls).newInstance();
            if (cla.getAnnotation(Service.class) != null)
                service = Class.forName(cls).newInstance();
        }

        Method[] methods = controller.getClass().getDeclaredMethods();
        for (Method method : methods) {
            RequestMapping req = method.getAnnotation(RequestMapping.class);
            if (req != null) {
                String path = req.value();
                path2Method.put(path, method);
            }
        }

        Field[] fields = controller.getClass().getDeclaredFields();
        for (Field field : fields) {
            Autowired  auto = field.getAnnotation(Autowired.class);
            if ( auto != null) {
                field.setAccessible(true);
                field.set(controller, service);
            }
        }
    }

    public void run() throws Exception {
        (new WebServer(this)).run();
    }
    public String request(String path) throws InvocationTargetException, IllegalAccessException, IOException {

        Map<String, String> param = new HashMap<String, String>();
        Map<String, Object> model = new HashMap<String, Object>();

        String[] qeuryString = path.split("\\?");
        Method method = path2Method.get(qeuryString[0]);
        if (qeuryString.length == 2 ) {
            String[] tokens = qeuryString[1].split("&");
            for(String token : tokens ) {
                String[] nameValue = token.split("=");
                if (nameValue.length == 2) param.put(nameValue[0], nameValue[1]);
                else param.put(nameValue[0], "");
            }
        }

        String output = (String)method.invoke(controller, param, model);
        if ( method.getAnnotation(ResponseBody.class) == null ) {
                String template = new String(Files.readAllBytes(Paths.get(output + ".html")));
                for(String key : model.keySet()) {
                    template = template.replace("@" + key, model.get(key).toString());
                }
                output = template;
        }
        return output;
    }
}