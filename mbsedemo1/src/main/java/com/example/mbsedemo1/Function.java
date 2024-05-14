package com.example.mbsedemo1;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//public class PythonApplication {
//    public static void main(String[] args) {
//        // python环境的绝对路径
//        String envPath = "C:\\ProgramData\\anaconda3\\envs\\py310\\python.exe";
//        // python脚本的绝对路径，在windows中用"\\"分隔，在Linux中用"/"分隔
////        String pyPath = "E:\\testDemo\\src\\resource\\modelDemo3.py";
//        String pyPath = "./model_test.py";
//        String text_1 = "The DPU-CCM shall forward CLK_MSGs to the DPU-TIS for processing immediately upon receipt (I.e. shall not enqueue the message to the command dispatcher queue).";
//        String text_2 = "Startup SequenceThe DPU FSW is booted using PROM-resident bootstrap software.  The bootstrap software performs a basic set of built-in tests, then copies the DPU FSW from EEPROM to DRAM and executes it.  In flight, there are two methods which can trigger the DPU FSW to boot:* power-on (cold boot), or* watchdog reset (warm boot, commanded reboot).";
//        String[] args1 = new String[]{envPath, pyPath, text_1 , text_2};
////        String[] args1 = new String[]{envPath, pyPath, "123", "456"};
//        try {
//            // 执行Python文件，并传入参数
//            Process process = Runtime.getRuntime().exec(args1);
//            // 获取Python输出字符串作为输入流被Java读取
//            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line = null;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            in.close();
//            process.waitFor();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
//import org.python.core.PyFunction;
//import org.python.core.PyInteger;
//import org.python.core.PyObject;
//import org.python.core.PyString;
//import org.python.util.PythonInterpreter;
//
//public class Function {
//
//    public static void main(String[] args) {
//        PythonInterpreter interpreter = new PythonInterpreter();
//        interpreter.execfile("F:\\MBSEtest\\mbsedemo\\mbsedemo1\\src\\main\\resources\\LLMmodel\\model_test.py");
//
//        // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
//        PyFunction pyFunction = interpreter.get("predict", PyFunction.class);
//        if (pyFunction == null) {
//            System.out.println("在Python脚本中没有找到名为 'predict' 的函数。");
//            return;
//        }
//        String a = "The DPU-CCM shall forward CLK_MSGs to the DPU-TIS for processing immediately upon receipt (I.e. shall not enqueue the message to the command dispatcher queue).";
//        String b = "Startup SequenceThe DPU FSW is booted using PROM-resident bootstrap software.  The bootstrap software performs a basic set of built-in tests, then copies the DPU FSW from EEPROM to DRAM and executes it.  In flight, there are two methods which can trigger the DPU FSW to boot:* power-on (cold boot), or* watchdog reset (warm boot, commanded reboot).";
//        //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
//        PyObject pyobj = pyFunction.__call__(new PyString(a), new PyString(b));
//        boolean isTrue = "True".equals(pyobj.toString());
//        System.out.println("the anwser is: " + isTrue);
//    }
//
//}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Function {

    public static boolean check(String a, String b){
        // 指定 Python 环境的绝对路径
        String pythonPath = "C:\\ProgramData\\anaconda3\\envs\\py310\\python";
        // Python 脚本的绝对路径
        String scriptPath = "F:\\MBSEtest\\mbsedemo\\mbsedemo1\\src\\main\\resources\\LLMmodel\\model_test.py";
        // 定义传入 Python 脚本的参数
//        String a = "The DPU-CCM shall forward CLK_MSGs to the DPU-TIS for processing immediately upon receipt (I.e. shall not enqueue the message to the command dispatcher queue).";
//        String b = "Startup SequenceThe DPU FSW is booted using PROM-resident bootstrap software.  The bootstrap software performs a basic set of built-in tests, then copies the DPU FSW from EEPROM to DRAM and executes it.  In flight, there are two methods which can trigger the DPU FSW to boot:* power-on (cold boot), or* watchdog reset (warm boot, commanded reboot).";

        // 构建命令和参数
        ProcessBuilder processBuilder = new ProcessBuilder(pythonPath, scriptPath, a, b);
        processBuilder.redirectErrorStream(true); // 将错误输出和标准输出合并

        try {
            // 执行 Python 脚本
            Process process = processBuilder.start();

            // 读取脚本输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
                output.append(line).append("\n");
            }

            // 等待进程结束
            int exitCode = process.waitFor();
            boolean isTrue = false;
            if (exitCode == 0) {
                // 输出结果处理
                isTrue = output.toString().trim().equalsIgnoreCase("True");

            } else {
                System.out.println("Script execution failed with exit code " + exitCode);
            }
            return isTrue;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
