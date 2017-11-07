package richey.badwords.flaming.the.firebaseminiproject.Model;

/**
 * Created by mcbud on 2017-11-08.
 */

public class User {
    public String id;
    public String email;
    public String token;

    public User(){
        // Basic Constructor for Firebase
    }

    public User(String id, String email, String token){
        this.id = id;
        this.email = email;
        this.token = token;
    }
}
