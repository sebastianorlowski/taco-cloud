package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import tacos.data.UserDetailsService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new SCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/design", "/orders")
                .hasRole("ROLE_USER")
                .antMatchers("/","/**").permitAll();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(encoder());
    /*protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .userSearchBase("ou=people")
                .userSearchFilter("(uid={0}")
                .groupSearchBase(("ou=groups"))
                .groupSearchFilter("member={0}")
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("passcode")
                .contextSource()
                .url

             /*   .usersByUsernameQuery(
                        "select username, password, enabled from Users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from UserAuthorities where usename = ?")
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence charSequence) {
                        return null;
                    }

                    @Override
                    public boolean matches(CharSequence charSequence, String s) {
                        return false;
                    }
                });
                */

    }

        public static final String DEF_USERS_BY_USERNAME_QUERY =
                "select username, password, enabled from users where username = ?";

        public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY =
                "select username,authority from authorities where username = ?";

        public static final String DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY =
                "select g, id, g.group_name, ga.authority from groups g, group_members gm, group_authorities ga " +
                        "where gm.username = ? and g.id = ga.group_id and g.id = gm.group_id";
}
