package com.spring;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.util.Arrays;

// 웹서버에서 경로를 루트로만 입력받아 사용되는 부분과
// 루트 이외의 경로를 사용했을때 토이컨테이너를 활용하여 그에따른 값을 처리해준다
public class WebServer {
    private ToyContainer ctx; // 토이컨테이너 객체 선언
    public WebServer(ToyContainer ctx) {
        this.ctx = ctx;
    }  // 웹서버 클래스에서 토이 컨테이너를 인수로 받는 생성자 가져옴

    public void run() throws Exception {
        int port = 3000; // 포트번호 3000번으로 설정

        // 서버에서 port:3000 인 서버 소켓을 생성한다.
        ServerSocket serverSocket = new ServerSocket(port);

        while (true) { // 무한반복
            // 클라이언트 소켓에 서버 소켓이 accept 되었다는 상태를 알려준자
            Socket clientSocket = serverSocket.accept();

            // (클라이언트---(버퍼)-->서버)
            // 버퍼리더는 클라이언트으로부터 들어온 버퍼로 들어온 값을 읽음
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // 클라이언트의 소켓을 연다
            OutputStream os = clientSocket.getOutputStream();

            String s; // 버퍼로부터 읽어온 문장 전체를 저장할 문자열
            String filename = ""; //

            while ((s = in.readLine()) != null) { // 버퍼가 읽은 값이 null이 아닐때
                if ( s.contains("GET /")) { // 문자열에 "GET /"이 포함되어있다면
                    System.out.println("--> " + s); // --> 과 문자열을 출력한다
                    // 문자열을 " "(공백) 으로 구분하고 그것을 배열의 요소들로 나눔
                    // 1번인덱스는 문자열이 "안녕 하세요"라면 "하세요"가 되게 된다
                    // 그 1번인덱스의 문자열 값을 path 에 저장한다
                    String path = s.split(" ")[1];

                    // path 가 "/" 라면  filename = "/index.html" 설정
                    if ( path.equals("/") )  filename = "/index.html";
                    // 아니면 path로 설정
                    else filename = path;
                }
                // 문자열이 비었다면 멈춰라 --> 예외처리...
                if (s.isEmpty()) break;
            }

            try {
                // web server 라면
                // 위에서 저장한 filename 의 문자열에 "."의 인덱스가 0보다 같거나 크다면
                if (filename.indexOf('.') >= 0) {
                    // header 에 200(서버가 정상 작동했음을 알려주고
                    // getContentTypeFor(filename) 메서드는 지정된 파일 이름과 관련된
                    // MIME 유형(콘텐츠 유형) 을 알려주는 문자열로 초기화
                    // * \r\n을 2번 넣은 이유 header와 body를 구분하기 위해

                    String header = "HTTP/1.0 200 OK\r\n" +
                            URLConnection.getFileNameMap().getContentTypeFor(filename) + "\r\n" +
                            "\r\n";
                    // 아웃풋 스트림에서 헤더를 문자열로 변환시키고 이것을 바이트로 변환한다
                    // * 바이트로 변환하는 이유: 자바는 1바이트단위로 코드를 읽기 때문
                    os.write(header.toString().getBytes());
                    // file 객체를 경로명 . 과 위에 입력받은 filename을 합해서 초기화한다
                    File file = new File("." + filename);
                    // 파일 인풋스트림으로 파일을 읽어들이고
                    FileInputStream input = new FileInputStream(file);
                    // 1024 크기 버퍼를 초기화
                    byte[] buffer = new byte[1024];
                    int readData;
                    while ((readData = input.read(buffer)) > 0) { // if 읽어들인 데이터가 있다면
                        // 아웃풋 스트림에서 버퍼에 있는값을 write한다
                        os.write(buffer, 0, readData);
                    }
                    // 잇풋스트림을 닫는다
                    input.close();
                } else {
                    // spring container
                    // 200 정상작동을 나타내도
                    // text/html으로 컨텐츠 타입을 표시해서 보여주는 header
                    String header = "HTTP/1.0 200 OK\nContent-Type: text/html\r\n\r\n";
                    // body : 컨테이너에서 request(filename) 메소드를 활용해 값을 가져옴
                    String body = ctx.request(filename);

                    os.write(header.toString().getBytes());
                    os.write(body.toString().getBytes());
                }

            } catch (Exception e) {
                e.printStackTrace();
                // 500 서버에러이고
                // text/html으로 컨텐츠 타입을 표시해서 보여주는 header
                String header = "HTTP/1.0 500 ERROR\r\nContent-Type: text/html\r\n\n";
                // 에러페이지를 보여주는 body
                String body = "<html><h3><p>Error : " + filename + ": " + e.toString() + "</p>" + Arrays.toString(e.getStackTrace()) + "</h3></html>";
                os.write(header.toString().getBytes());
                os.write(body.toString().getBytes());
            } finally {
                os.flush(); // 버퍼에 저장된 데이터 강제로 출력스트림으로 내보넴
                os.close(); // 아웃풋 스트림 종료
                in.close(); // 잇풋 스트림 종료
                clientSocket.close(); // 서버 스캔 종료
            }
        }
    }
}