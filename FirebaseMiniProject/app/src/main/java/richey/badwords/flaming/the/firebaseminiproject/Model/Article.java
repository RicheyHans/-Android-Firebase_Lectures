package richey.badwords.flaming.the.firebaseminiproject.Model;

import java.io.File;
import java.util.Date;

/**
 * Created by mcbud on 2017-11-09.
 */

public class Article {
   public String year;
   public String month;
   public String day;
   public String time;

   public String subject;
   public String content;

   public File[] img;

   public Article(String year, String month, String day, String time, String subject, String content){
      this.year = year;
      this.month = month;
      this.day = day;
      this.time = time;

      this.subject = subject;
      this.content = content;
   }
}
