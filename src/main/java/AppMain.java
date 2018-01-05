
import com.bootcamp.entities.Question;
import com.bootcamp.services.QuestionService;
import com.bootcamp.services.StatQuestion;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;



public class AppMain {
    public static void main(String[] strings) throws SQLException {
                
//        StatQuestion statQuestion = new StatQuestion();
        
//        QuestionService questionService = new QuestionService();
//        Question question = questionService.read(2);
//        HashMap<String,Long> typeReponses =  question.getTypeReponses();
        
//        for (Map.Entry<String, Long> entry : typeReponses.entrySet()) {
//            String key = entry.getKey();
//            Long value = entry.getValue();
//            
//        }
        
//        for (String s : typeReponses.keySet()) {
//            System.out.print(s + " ");
//            System.out.print(typeReponses.get("oui"));
            System.out.print("jhkjbkjddoui");
//        }
    
         // Create a HashMap
        HashMap<Integer, String> hmap = new HashMap<Integer, String>();

        //add elements to HashMap
        hmap.put(1, "AA");
        hmap.put(2, "BB");
        hmap.put(3, "CC");
        hmap.put(4, "DD");

        // Getting values from HashMap
        String val=hmap.get(4);
        System.out.println("The Value mapped to Key 4 is:"+ val);

        /* Here Key "5" is not mapped to any value so this 
         * operation returns null.
         */
        String val2=hmap.get(5);
        System.out.println("The Value mapped to Key 5 is:"+ val2);
        
//        for (Map.Entry<Integer, String> entry : hmap.entrySet()) {
//            Integer key = entry.getKey();
//            String value = entry.getValue();
//            
//        }
        
        for (Integer s : hmap.keySet()) {
            for (int i = 0; i < 1; i++) {
                System.out.println("nombreReponseValeur"+s);
            }
            System.out.print(s + " ");
            System.out.print(" ------ ");
            System.out.print(hmap.get(s)+"\n");
        }
    
    }
}
