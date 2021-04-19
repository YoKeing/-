import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {

    @Test
    public void testBCryptPasswordEncoder(){
        String encode = new BCryptPasswordEncoder().encode("123123");

        System.out.println(encode);
    }

}
