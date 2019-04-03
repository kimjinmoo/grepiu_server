package com.grepiu.test.application.socket;

import com.grepiu.www.process.sample.util.socket.module.SejongFactory;
import com.grepiu.www.process.sample.util.socket.module.SejongFactory.TYPE;
import com.grepiu.www.process.sample.util.socket.module.model.FileDown;
import com.grepiu.www.process.sample.util.socket.module.model.SejongSocket;

import com.grepiu.www.process.sample.util.socket.module.model.SejongMap;
import java.io.File;
import java.util.Arrays;
import java.util.Random;
import org.apache.commons.io.FileUtils;

/**
 *
 * 소켓 테스트
 *
 */
public class SocketClient {

  private static int loop = 30;

  public static void main(String...args) throws Exception {
//    SejongSocket socket = SejongFactory.create(TYPE.WATCH_GRADE);
//    SejongMap map = new SejongMap();
//    socket.send(map).forEach(v->{
//      v.forEach((k, f)->{
//        System.out.println("k : " + k);
//        System.out.println("f : " + f);
//      });
//    });
    System.out.println("CheckVO.isX() : [" +CheckVO.isX(3,"한글")+"]");

  }

  public static String getString(  byte[ ]  input  )
  {

    //  StringBuffer객체를 선언하여 String을 이어 붙입니다.
    StringBuffer  rtn  =  new  StringBuffer();

    //  byte[  ]의 길이만큼 반복해서 byte 하나 하나씩 처리합니다.
    for(  int i = 0;  i < input.length; )
    {
      //  한글처리부분
      //   (  input[  i  ]   &  0x80  )  ==  0x80 이조건을 만족하면 input[i]는
      //  음수 즉, 한글의 두개의 byte중 첫번째 byte라는 뜻이므로
      //  input[++i]와 함께 String으로 만들어서 한글 한글자를 생성합니다.
      if(  (  input[  i  ]   &  0x80  )  ==  0x80  )
      {
        byte[ ]  hangle  =  new  byte[  2  ];
        hangle[  0  ]  =  input[  i  ];
        hangle[  1  ]  =  input[  ++ i  ];
        rtn.append(  new  String(  hangle  )  );

      }

      //  한글이외 처리부분 (  영어, 숫자, 특문등등  )
      else  rtn.append(  (  char  )input[  i  ]  );

      //  다음번 byte를 읽기 위해 input의 index증가시키는 부분
      ++i;
    }
    return  rtn.toString(  );

  }
}
