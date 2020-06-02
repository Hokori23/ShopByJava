package LISTENER;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Counter implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {


        // 获取 ServletContext 对象

        ServletContext context = sce.getServletContext();

        try {

            // 从文件中读取计数器的数值

            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResourceAsStream("/count/count.txt")));

            int count = Integer.parseInt(reader.readLine());

            reader.close();  // 把计数器对象保存到 Web 应用范围

            context.setAttribute("count", count);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void contextDestroyed(ServletContextEvent sce) {


        // 获取 ServletContext 对象

        ServletContext context = sce.getServletContext();

        // 从 Web 应用范围获得计数器

        int count = (int) context.getAttribute("count");

        if (count != 0) {

            try {

                // 把计数器的数值写到 count.txt 文件中

                String filepath = context.getRealPath("/count");

                filepath = filepath + "/count.txt";

                PrintWriter pw = new PrintWriter(filepath);

                pw.println(count);

                pw.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }


}
